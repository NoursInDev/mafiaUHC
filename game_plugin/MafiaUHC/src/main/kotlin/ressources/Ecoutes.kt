package org.noursindev.mafiauhc.ressources

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.resources.Joueur
import org.noursindev.mafiauhc.ressources.inventaires.nouvelOpener

class Ecoutes(private val main: MafiaUHC) : Listener {

    val configurateur = nouvelOpener()

    @EventHandler
    fun arriveeJoueur(event: PlayerJoinEvent) {
        if (main.getPhase() == Phases.Configuration) {
            main.config.joueurs.add(Joueur(event.player as CraftPlayer))
            if(event.player.isOp) {
                event.player.inventory.addItem(nouvelOpener())
            }
        } else {
            if ((main.config.joueurs.find { it.player == event.player } == null) && (!event.player.isOp)) {
                event.player.kickPlayer("@${main.name} Une partie est déjà en cours et vous n'êtes pas autorisé à la rejoindre.")
            } else if (main.config.joueurs.find { it.player.name == event.player.name } == null) {
                event.player.gameMode = org.bukkit.GameMode.SPECTATOR
                event.player.sendMessage("@${main.name} " + ChatColor.RED.toString() + "Vous êtes en mode spectateur car une partie est déjà en cours.")
            } else {
                event.player.sendMessage("@${main.name} " + ChatColor.GREEN.toString() + "Bienvenue dans la partie!")
            }
        }
    }

    @EventHandler
    fun departJoueur(event: PlayerQuitEvent) {
        if (main.getPhase() == Phases.Configuration && main.config.joueurs.find { it.player == event.player } != null) {
            main.config.joueurs.remove(main.config.joueurs.find { it.player == event.player }!!)
        }
    }

    @EventHandler
    fun dropItemProtege(event: PlayerDropItemEvent) {
        val item = event.itemDrop.itemStack
        if (item.itemMeta.displayName == configurateur.itemMeta.displayName && item.itemMeta.itemFlags == configurateur.itemMeta.itemFlags) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val inv = event.inventory
        println("OnClick : ${inv.name}")
        val joueur = main.config.joueurs.find { it.player.name == event.whoClicked.name }
        if (joueur != null) {
            println("Joueur : ${joueur.player.name}")
        } else {
            println("Joueur : null")
        }
        val item = event.currentItem

        if (inv == null || inv.name == null || item == null || joueur == null) {
            println("onClick Problem : $inv, ${inv.name}, $item, $joueur")
            return
        }

        if (inv.name == "§dBoite de Cigares") {
            event.isCancelled = true
            when (item.itemMeta.displayName) {
                "pierres" -> {
                    joueur.player.closeInventory()
                    Bukkit.getScheduler().runTask(main, Runnable { joueur.player.performCommand("mf prendre pierres") })
                }

                "fideles" -> {
                    joueur.player.closeInventory()
                    Bukkit.getScheduler().runTask(main, Runnable { joueur.player.performCommand("mf prendre fidele") })
                }

                "agents" -> {
                    joueur.player.closeInventory()
                    Bukkit.getScheduler().runTask(main, Runnable { joueur.player.performCommand("mf prendre agent") })
                }

                "chauffeurs" -> {
                    joueur.player.closeInventory()
                    Bukkit.getScheduler()
                        .runTask(main, Runnable { joueur.player.performCommand("mf prendre chauffeur") })
                }

                "nettoyeurs" -> {
                    joueur.player.closeInventory()
                    Bukkit.getScheduler()
                        .runTask(main, Runnable { joueur.player.performCommand("mf prendre nettoyeur") })
                }

                "enfants des rues" -> {
                    joueur.player.closeInventory()
                    Bukkit.getScheduler()
                        .runTask(main, Runnable { joueur.player.performCommand("mf prendre enfantdesrues") })

                }
            }
        }
        if (inv.name == "§dPierres") {
            event.isCancelled = true
            var count = inv.contents[4].amount
            when (item.itemMeta.displayName) {
                "-1" -> {
                    if (count > 1) {
                        count--
                        inv.contents[4].amount = count
                        print("-1 -> Count : $count ; Pierres : ${main.config.boite.pierres}")
                    }
                }

                "+1" -> {
                    if (main.config.boite.pierres > count) {
                        count++
                        inv.contents[4].amount = count
                        print("+1 -> Count : $count ; Pierres : ${main.config.boite.pierres}")
                    }
                }

                "Annuler" -> {
                    joueur.player.closeInventory()
                    Bukkit.getScheduler().runTask(main, Runnable { joueur.player.performCommand("mf ouvrir") })
                }

                "Valider" -> {
                    Bukkit.getScheduler()
                        .runTask(main, Runnable { joueur.player.performCommand("mf prendre pierres $count") })
                    joueur.player.closeInventory()
                }
            }
        }

    }
}