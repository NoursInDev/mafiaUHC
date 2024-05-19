package org.noursindev.mafiauhc.ressources.commandes

import org.bukkit.command.CommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.inventaires.boiteInvConstruct
import org.noursindev.mafiauhc.ressources.inventaires.pierresInvConstruct
import org.noursindev.mafiauhc.ressources.roles.*

class CommandesIG(private val main: MafiaUHC) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        val joueur = main.config.joueurs.find { it.player.name == sender.name }    // recup joueur

        if (args.isNotEmpty()) {
            when (args[0]) {
                "ordre" -> {
                    sender.sendMessage("Ordre des joueurs:")
                    for (j in 0 until (main.ordre?.size ?: 0)) {
                        sender.sendMessage("${j + 1}. ${main.ordre?.get(j)?.player?.name}")
                    }
                }

                "ouvrir" -> {
                    if (joueur != null && joueur.tour) {
                        joueur.player.openInventory(boiteInvConstruct(main.config.boite))
                    } else {
                        sender.sendMessage("Vous ne pouvez pas ouvrir la Boite.")
                    }
                }

                "role" -> {
                    if (joueur?.role != null) {
                        sender.sendMessage(joueur.role!!.description)
                    } else {
                        sender.sendMessage("Vous n'avez pas de role.")
                    }
                }

                "boite" -> {
                    if (args.size > 1 && (args[1] == "init" || args[1] == "tour" || args[1] == "final") && joueur?.role != null && main.config.passage) {
                        when (args[1]) {
                            "init" -> {
                                joueur.player.openInventory(
                                    boiteInvConstruct(
                                        main.config.initialBoite!!,
                                        "Boite de Cigares Initiale"
                                    )
                                )
                            }

                            "tour" -> {
                                if (joueur.boite != null) {
                                    joueur.player.openInventory(
                                        boiteInvConstruct(
                                            joueur.boite!!,
                                            "Boite de Cigares de ton Tour"
                                        )
                                    )
                                } else { sender.sendMessage("La Boite n'est pas passée par vous.") }
                            }

                            "final" -> {
                                joueur.player.openInventory(
                                    boiteInvConstruct(
                                        main.config.boite,
                                        "Boite de Cigares Finale"
                                    )
                                )
                            }
                        }
                    } else {
                        sender.sendMessage("Usage: /mf boite <init|tour|final> après tour de la boite.")
                    }
                }

                "prendre" -> {
                    if (args.size > 1 && joueur != null && joueur.tour) {
                        when (args[1]) {
                            "pierres" -> {
                                if (args.size > 2 && main.config.boite.pierres >= args[2].toInt()) {
                                    main.config.boite.pierres -= args[2].toInt()
                                    joueur.role = Voleur(main)
                                    sender.sendMessage("Vous avez pris ${args[2]} pierres. Vous êtes Voleur.")
                                } else if (args.size == 2) {
                                    joueur.player.openInventory(pierresInvConstruct(main))
                                } else {
                                    sender.sendMessage("Il n'y a pas assez de pierres dans la boite.")
                                }
                            }

                            "fidele" -> {
                                if (main.config.boite.fideles > 0) {
                                    main.config.boite.fideles--
                                    joueur.role = Fidele(main)
                                } else {
                                    sender.sendMessage("Il n'y a plus de fidèles dans la boite")
                                }
                            }

                            "agent" -> {
                                if (main.config.boite.agents > 0) {
                                    main.config.boite.agents--
                                    joueur.role = Agent(main)
                                } else {
                                    sender.sendMessage("Il n'y a plus d'agents dans la boite")
                                }
                            }

                            "chauffeur" -> {
                                if (main.config.boite.chauffeurs > 0) {
                                    main.config.boite.chauffeurs--
                                    joueur.role = Chauffeur(main)
                                } else {
                                    sender.sendMessage("Il n'y a plus de chauffeurs dans la boite")
                                }
                            }

                            "nettoyeur" -> {
                                if (main.config.boite.nettoyeurs > 0) {
                                    main.config.boite.nettoyeurs--
                                    joueur.role = Nettoyeur(main)
                                } else {
                                    sender.sendMessage("Il n'y a plus de nettoyeurs dans la boite")
                                }
                            }

                            "enfantdesrues" -> {
                                if (main.ordre?.lastOrNull() == joueur || main.config.boite.retourneBoite()
                                        .all { it.value == 0 }
                                ) {
                                    joueur.role = EnfantDesRues(main)
                                } else {
                                    sender.sendMessage("Il reste des roles dans la boite")
                                }
                            }
                        }
                        if (joueur.role != null) {
                            sender.sendMessage(joueur.role!!.description)
                        }
                    } else {
                        sender.sendMessage("Commande incorrecte ou manque d'arguments.")
                    }
                }

                else -> {
                    sender.sendMessage("Usage: /mf <ordre|ouvrir|prendre|role|boite>")
                }
            }
        }
        return true
    }
}