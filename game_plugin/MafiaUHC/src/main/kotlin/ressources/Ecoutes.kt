package org.noursindev.mafiauhc.ressources

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.resources.Joueur
import org.noursindev.mafiauhc.ressources.inventaires.*

class Ecoutes(private val main: MafiaUHC) : Listener {

    val configurateur = nouvelOpener()

    @EventHandler
    fun arriveeJoueur(event: PlayerJoinEvent) {
        if (main.getPhase() == Phases.Configuration) {
            main.config.joueurs.add(Joueur(event.player as CraftPlayer))
            if (event.player.isOp) {
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
    fun itemUtil(event: PlayerInteractEvent) {
        val item = event.item
        if (item.itemMeta.displayName == configurateur.itemMeta.displayName && item.itemMeta.itemFlags == configurateur.itemMeta.itemFlags) {
            event.isCancelled = true
            event.player.openInventory(configInvConstructeur())
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val inv = event.inventory
        println("OnClick : ${inv.name}")
        val joueur = main.config.joueurs.find { it.player.name == event.whoClicked.name }
        val player = event.whoClicked as CraftPlayer

        val item = event.currentItem

        // PROBLEM CHECK

        if (inv == null || inv.name == null || item == null) {
            println("onClick Problem : $inv, ${inv.name}, $item, $joueur")
            return
        }

        // INGAME CHECK

        if (inv.name == "§dBoite de Cigares" && joueur != null && joueur.tour) {
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
        if (inv.name == "§dPierres" && joueur != null && joueur.tour) {
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

        // CONFIG CHECK

        if (inv.name == "§dConfiguration") {
            event.isCancelled = true
            when (item.itemMeta.displayName) {
                "§aJoueurs" -> {
                    player.closeInventory()
                    player.openInventory(joueursConfigConstructeur())
                }

                "§9Roles" -> {
                    player.closeInventory()
                    player.openInventory(rolesConfigInvConstructeur(main.config.boite))
                }

                "§dBordures" -> {
                    player.closeInventory()
                    player.openInventory(borduresConfigConstructeur(main.config))
                }

                "§dLancer la partie" -> {
                    player.closeInventory()
                    player.openInventory(lancementConfigConstructeur())
                }

                "§dStuffs" -> {
                    player.closeInventory()
                }

                else -> {
                    player.sendMessage("Erreur, item config non reconnu")
                }

            }
        }

        if (inv.name == "§9Roles") {
            event.isCancelled = true
            when (event.click) {
                ClickType.LEFT -> {
                    when (item.itemMeta.displayName) {
                        "pierres" -> {
                            if (main.config.boite.pierres > 1) {
                                main.config.boite.pierres--
                                item.amount = main.config.boite.pierres
                            }
                        }

                        "fideles" -> {
                            if (main.config.boite.fideles > 0) {
                                main.config.boite.fideles--
                                item.amount = main.config.boite.fideles
                            }
                        }

                        "agents" -> {
                            if (main.config.boite.agents > 0) {
                                main.config.boite.agents--
                                item.amount = main.config.boite.agents
                            }
                        }

                        "chauffeurs" -> {
                            if (main.config.boite.chauffeurs > 0) {
                                main.config.boite.chauffeurs--
                                item.amount = main.config.boite.chauffeurs
                            }
                        }

                        "nettoyeurs" -> {
                            if (main.config.boite.nettoyeurs > 0) {
                                main.config.boite.nettoyeurs--
                                item.amount = main.config.boite.nettoyeurs
                            }
                        }

                        else -> {}
                    }
                }

                ClickType.RIGHT -> {
                    when (item.itemMeta.displayName) {
                        "pierres" -> {
                            main.config.boite.pierres++
                            item.amount = main.config.boite.pierres
                        }

                        "fideles" -> {
                            main.config.boite.fideles++
                            item.amount = main.config.boite.fideles
                        }

                        "agents" -> {
                            main.config.boite.agents++
                            item.amount = main.config.boite.agents
                        }

                        "chauffeurs" -> {
                            main.config.boite.chauffeurs++
                            item.amount = main.config.boite.chauffeurs
                        }

                        "nettoyeurs" -> {
                            main.config.boite.nettoyeurs++
                            item.amount = main.config.boite.nettoyeurs
                        }
                    }
                }

                else -> {}
            }
        }

    }
}