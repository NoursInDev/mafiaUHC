package org.noursindev.mafiauhc.ressources.commandes

import org.bukkit.Material
import org.bukkit.command.CommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.inventaires.boiteInvConstruct
import org.noursindev.mafiauhc.ressources.inventaires.pierresInvConstruct
import org.noursindev.mafiauhc.ressources.roles.*
import org.noursindev.mafiauhc.ressources.Phases

@Suppress("UNUSED_EXPRESSION")
class CommandesIG(private val main: MafiaUHC) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        val joueur = main.config.joueurs.find { it.player.name == sender.name }    // recup joueur

        if (args.isNotEmpty() && main.getPhase() != Phases.Configuration) {
            when (args[0]) {
                "ordre" -> {
                    sender.sendMessage("Ordre des joueurs:")
                    for (j in 0 until (main.ordre?.size ?: 0)) {
                        sender.sendMessage("${j + 1}. ${main.ordre?.get(j)?.player?.name}")
                    }
                }

                "ouvrir" -> {
                    if (joueur != null && joueur.tour) {
                        println(main.ordre)
                        if (main.ordre!![0].player.name == joueur.player.name) {
                            joueur.player.openInventory(boiteInvConstruct(main.config.boite, "Boite de Cigares", true))
                            return true
                        }
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
                                    joueur.role = Chauffeur(main, main.config.joueurs.filter { it.player != joueur.player }.random(), joueur)
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

                "eloigner" -> {
                    if (args.size >= 2 && joueur?.player?.name == main.ordre!![0].player.name) {
                        when (args[1]) {
                            "fidele" -> {
                                if (main.config.boite.fideles > 0) {
                                    main.config.boite.fideles--
                                } else {
                                    sender.sendMessage("Vous ne pouvez pas éloigner de fidèle.")
                                }
                            }
                            "agent" -> {
                                if (main.config.boite.agents > 0) {
                                    main.config.boite.agents--
                                } else {
                                    sender.sendMessage("Vous ne pouvez pas éloigner d'agent.")
                                }
                            }
                            "chauffeur" -> {
                                if (main.config.boite.chauffeurs > 0) {
                                    main.config.boite.chauffeurs--
                                } else {
                                    sender.sendMessage("Vous ne pouvez pas éloigner de chauffeur.")
                                }
                            }
                            "nettoyeur" -> {
                                if (main.config.boite.nettoyeurs > 0) {
                                    main.config.boite.nettoyeurs--
                                } else {
                                    sender.sendMessage("Vous ne pouvez pas éloigner de nettoyeur.")
                                }
                            }
                            else -> {
                                sender.sendMessage("Usage: /mf eloigner <fidele|agent|chauffeur|nettoyeur>")
                            }
                        }
                    }
                    else {
                        sender.sendMessage("Vous ne pouvez pas éloigner de role.")
                    }
                }

                // Spe Joueurs
                // Spe Parrain

                "reunion" -> {
                    joueur?.role?.mfReunion(joueur)
                }

                "pierres" -> {
                    val cible = main.config.joueurs.find { it.player.name == args[1] }
                    if (cible?.role != null) {
                        val pierres = joueur?.role?.mfPierres(cible)
                        if (pierres != null) {
                            sender.sendMessage("Pierres de ${cible.player.name}: $pierres")
                        } else {
                            sender.sendMessage("Vous ne pouvez pas utiliser cette commande.")
                        }
                    } else {
                        sender.sendMessage("Joueur introuvable.")
                    }
                }

                "guess" -> {
                    val guess = joueur?.role?.mfGuess(joueur)
                    when (guess) {
                        true -> {
                            sender.sendMessage("Vous avez trouvé un agent.")
                        }
                        false -> {
                            sender.sendMessage("La personne ciblée n'est pas agent.")
                        }
                        else -> {
                            sender.sendMessage("Vous ne pouvez pas utiliser cette commande.")
                        }
                    }
                }

                "forcerecup" -> {
                    joueur?.role?.mfForcerecup()
                }

                // Spe Fidele

                "pression" -> {
                    val cible = main.config.joueurs.find { it.player.name == args[1] }
                    if (cible?.role != null) {
                        val pression = joueur?.role?.mfPression(cible)
                        if (pression != null) {
                            sender.sendMessage("Pression sur ${cible.player.name}: $pression")
                            if (pression) {
                                sender.sendMessage("Ce joueur est voleur.")
                            } else {
                                sender.sendMessage("Ce joueur n'est pas voleur. Vous perdez 5 coeurs de manière permanente.")
                                joueur.player.maxHealth -= 10.0
                            }
                        } else {
                            sender.sendMessage("Vous ne pouvez pas utiliser cette commande.")
                        }
                    } else {
                        sender.sendMessage("Joueur introuvable.")
                    }
                }

                // Spe Voleur

                "active" -> {
                    joueur?.role?.mfActivate()
                }

                // Spe Chauffeur

                "localise" -> {
                    val localise = joueur?.role?.mfLocalise()
                    if (localise != null) {
                        sender.sendMessage("Localisation de l'Ami en ${localise[0]}, ${localise[1]}")
                    } else {
                        sender.sendMessage("Vous ne pouvez pas utiliser cette commande.")
                    }
                }

                // Spe Agent

                "parrain" -> {
                    val cible = main.config.joueurs.find { it.player.name == args[1] }
                    if (cible != null) {
                        val parrain = joueur?.role?.mfParrain(cible)
                        when (parrain) {
                            true -> {
                                sender.sendMessage("${cible.player.name} est le Parrain.")
                            }
                            false -> {
                                sender.sendMessage("${cible.player.name} n'est pas le Parrain.")
                            }
                            else -> {
                                sender.sendMessage("Vous ne pouvez pas utiliser cette commande.")
                            }
                        }
                    } else {
                        sender.sendMessage("Joueur introuvable.")
                    }
                }

                // COMMANDES GENERIQUES

                "nbp" -> {
                    sender.sendMessage("Nombre de pierres que vous possédez: ${joueur?.role?.pierres}")
                }

                "donnepierres" -> {
                    if (args.size >= 2) {
                        val nb = args[1].toIntOrNull()
                        if (main.config.parrain != null && joueur?.role != null && nb != null && nb != 0 && joueur.role?.pierres != 0) {
                            if (joueur.role?.pierres!! <= nb) {
                                main.config.parrain?.role?.pierres = main.config.parrain?.role?.pierres?.plus(joueur.role!!.pierres)!!
                                joueur.role?.donnees = joueur.role?.donnees!! + joueur.role?.pierres!!
                                joueur.role?.pierres = 0
                                sender.sendMessage("Vous avez donné toutes vos pierres au Parrain.")
                            } else if (joueur.role?.pierres != null) {
                                joueur.role?.pierres = joueur.role?.pierres!! - nb
                                joueur.role?.donnees = joueur.role?.donnees!! + nb
                                main.config.parrain?.role?.pierres = main.config.parrain?.role?.pierres?.plus(nb)!!
                                sender.sendMessage("Vous avez donné $nb pierres au Parrain.")
                            }
                            if (joueur.role!!.donnees - (joueur.role!! as Fidele).nbchoix >= 3) {
                                val augm = (joueur.role!!.donnees - (joueur.role!! as Fidele).nbchoix - 3) / 3
                                (joueur.role as Fidele).nbchoix += augm
                                sender.sendMessage("Vous pouvez choisir entre 2% de résistance (/mf choix resi) et une pomme d'or (/mf choix pomme).")
                            }
                        }
                    }
                    main.config.joueurs.find { it.player.name == main.config.parrain?.player?.name }?.role?.updateEffects()
                }

                "choix" -> {
                    if (args.size >= 2 && joueur?.role is Fidele && (joueur.role as Fidele).nbchoix - (joueur.role as Fidele).choixeffectues >= 1 && (args[1] == "pomme" || args[1] == "resi")) {
                        (joueur.role as Fidele).choixeffectues++
                        when (args[1]) {
                            "pomme" -> {
                                joueur.player.inventory.addItem(ItemStack(Material.GOLDEN_APPLE, 1))
                                sender.sendMessage("Vous avez choisi la Pomme.")
                            }
                            "resi" -> {
                                (joueur.role as Fidele).rmult += 0.02F
                                sender.sendMessage("Vous avez choisi la Résistance.")
                                joueur.role?.updateEffects()
                            }
                        }
                    }
                }

                else -> {
                    sender.sendMessage("Usage: /mf <ordre|ouvrir|prendre|role|boite|ect...>\nMerci de vous référer au wiki pour les commandes spécifiques à votre role.")
                }
            }
        } else { "Erreur lors de la commande. Vérifiez que vous êtes dans la partie et que celle-ci est lancée." }
        return true
    }
}