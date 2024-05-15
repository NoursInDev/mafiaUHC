package org.noursindev.mafiauhc.ressources

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.resources.Joueur
import org.noursindev.mafiauhc.ressources.inventaires.pierresInvConstruct
import org.noursindev.mafiauhc.ressources.roles.Agent
import org.noursindev.mafiauhc.ressources.roles.Chauffeur
import org.noursindev.mafiauhc.ressources.roles.Fidele
import org.noursindev.mafiauhc.ressources.roles.Nettoyeur
import org.noursindev.mafiauhc.ressources.roles.Voleur

class Ecoutes(private val main: MafiaUHC) : Listener {
    @EventHandler
    fun arriveeJoueur(event: PlayerJoinEvent) {
        if (main.getPhase() == Phases.Configuration) {
            main.joueurs.add(Joueur(event.player as CraftPlayer))
        } else {
            if ((main.joueurs.find { it.player == event.player } == null) && (!event.player.isOp)) {
                event.player.kickPlayer("@${main.name} Une partie est déjà en cours et vous n'êtes pas autorisé à la rejoindre.")
            } else if (main.joueurs.find { it.player.name == event.player.name } == null) {
                event.player.gameMode = org.bukkit.GameMode.SPECTATOR
                event.player.sendMessage("@${main.name} " + ChatColor.RED.toString() + "Vous êtes en mode spectateur car une partie est déjà en cours.")
            } else {
                event.player.sendMessage("@${main.name} " + ChatColor.GREEN.toString() + "Bienvenue dans la partie!")
            }
        }
    }

    @EventHandler
    fun departJoueur(event: PlayerQuitEvent) {
        if (main.getPhase() == Phases.Configuration && main.joueurs.find { it.player == event.player } != null) {
            main.joueurs.remove(main.joueurs.find { it.player == event.player }!!)
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val inv = event.inventory
        println("OnClick : ${inv.name}")
        val joueur = main.joueurs.find { it.player.name == event.whoClicked.name }
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
            when (item.itemMeta.displayName) {
                "pierres" -> {
                    joueur.player.closeInventory()
                    Bukkit.getScheduler().runTask(main, Runnable { joueur.player.performCommand("mf prendre pierres") })
                }

                "fideles" -> {
                    main.boite.fideles--
                    joueur.role = Fidele(main)
                    joueur.player.closeInventory()
                }

                "agents" -> {
                    main.boite.agents--
                    joueur.role = Agent(main)
                    joueur.player.closeInventory()
                }

                "chauffeurs" -> {
                    main.boite.chauffeurs--
                    joueur.role = Chauffeur(main)
                    joueur.player.closeInventory()
                }

                "nettoyeurs" -> {
                    main.boite.nettoyeurs--
                    joueur.role = Nettoyeur(main)
                    joueur.player.closeInventory()
                }
            }
        }
        if (inv.name == "§dPierres") {
            event.isCancelled = true
            var count = 1
            when (item.itemMeta.displayName) {
                "-1" -> {
                    if (main.boite.pierres > count && count > 1) {
                        count--
                        inv.contents[4].amount = count
                    }
                }

                "+1" -> {
                    if (main.boite.pierres > count && count > 1) {
                        count++
                        inv.contents[4].amount = count
                    }
                }

                "Valider" -> {
                    main.boite.pierres -= count
                    joueur.role = Voleur(main)
                    joueur!!.role!!.pierres = count
                    joueur.player.closeInventory()
                }
            }
        }

    }
}