package org.noursindev.mafiauhc.commandes

import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.elements.ConfigPhase
import org.bukkit.command.CommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.noursindev.mafiauhc.elements.GamePhase
import org.noursindev.mafiauhc.gamefuncs.donneStuff
import org.noursindev.mafiauhc.gamefuncs.teleport

class commandRead(private val plugin: MafiaUHC) : CommandExecutor {

    fun analyseArguments(command: Command, args: Array<out String>, sender: CommandSender) {
        when {
            args.isEmpty() || args[0] == "infos" || args[0] == "info" -> {
                val pluginDt = plugin.description
                sender.sendMessage(pluginDt.name + " v" + pluginDt.version + " by " + pluginDt.authors)
                sender.sendMessage("Pour plus d'informations, /mf help")
            }

            args[0] == "config" -> {
                if (sender.isOp) {
                    plugin.phase = ConfigPhase(sender, plugin.server.onlinePlayers.toTypedArray())
                } else {
                    sender.sendMessage("Vous n'avez pas les permissions nécessaires pour configurer la partie.")
                }
            }

            else -> {
                if (plugin.phase != null) {
                    when (plugin.phase!!.javaClass) {
                        ConfigPhase::class.java -> {
                            val configPhase = plugin.phase as ConfigPhase
                            when (args[0]) {
                                "rmplayer" -> {
                                    if (args.size == 1 || plugin.server.getPlayer(args[1]) == null) {
                                        sender.sendMessage("Joueur introuvable")
                                    } else if (configPhase.boite.playerDansListe(args[1])) {
                                        configPhase.boite.retirePlayer(plugin.server.getPlayer(args[1]))
                                        sender.sendMessage("Retrait d'un joueur")
                                    } else {
                                        sender.sendMessage("Aucun joueur à retirer")
                                    }
                                }

                                "addplayer" -> {
                                    if (args.size == 1 || plugin.server.getPlayer(args[1]) == null) {
                                        sender.sendMessage("Joueur introuvable")
                                    } else if (!configPhase.boite.playerDansListe(args[1])) {
                                        configPhase.boite.ajoutePlayer(plugin.server.getPlayer(args[1]))
                                        sender.sendMessage("Ajout d'un joueur")
                                    } else {
                                        sender.sendMessage("Joueur déjà présent dans la liste")
                                    }
                                }

                                "set" -> {
                                    if (args.size >= 3) {
                                        val nbstring = args[2]
                                        val nb = nbstring.toInt()
                                        when (args[1]) {
                                            "pierres" -> {
                                                configPhase.boite.resetPierres(nb)
                                                sender.sendMessage("Nombre de pierres mis à " + nbstring)
                                            }

                                            "fideles" -> {
                                                configPhase.boite.resetFideles(nb)
                                                sender.sendMessage("Nombre de fidèles mis à " + nbstring)
                                            }

                                            "agents" -> {
                                                configPhase.boite.resetAgents(nb)
                                                sender.sendMessage("Nombre d'agents mis à " + nbstring)
                                            }

                                            "chauffeurs" -> {
                                                configPhase.boite.resetChauffeurs(nb)
                                                sender.sendMessage("Nombre de chauffeurs mis à " + nbstring)
                                            }

                                            "nettoyeurs" -> {
                                                configPhase.boite.resetNettoyeurs(nb)
                                                sender.sendMessage("Nombre de nettoyeurs mis à " + nbstring)
                                            }

                                            else -> {
                                                sender.sendMessage("Rôle ${args[1]} inconnu. Avez-vous mis au pluriel?")
                                            }
                                        }
                                    }
                                }

                                "list" -> {
                                    sender.sendMessage(
                                        "Liste des joueurs:" + configPhase.boite.donnePlayers()
                                            .joinToString(", ") { p -> p.name })
                                    sender.sendMessage(
                                        "Liste des rôles: " + configPhase.boite.donneContenu()
                                            .joinToString(", ") { i -> i.toString() } + " (pierres, fideles, agents, chauffeurs, nettoyeurs)")
                                }

                                "start" -> {
                                    if (configPhase.boite.donnePlayers().size < 1) {
                                        sender.sendMessage("Pas assez de joueurs")
                                    } else {
                                        sender.sendMessage("Démarrage de la partie")
                                        teleport(configPhase.boite.donnePlayers())
                                        donneStuff(configPhase.boite.donnePlayers())
                                        plugin.phase = GamePhase(configPhase.boite)
                                        configPhase.boite.donnePlayers()
                                            .forEach { p -> p.sendMessage("La partie commence!") }
                                    }
                                }

                                else -> {
                                    sender.sendMessage("Commande inconnue ou indisponible.")
                                }
                            }
                        }

                        GamePhase::class.java -> {
                        }
                    }
                } else {
                    sender.sendMessage("Commande inconnue ou indisponible.")
                }
            }
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage("Commande Validée: " + command.toString())
        analyseArguments(command, args, sender)
        return true
    }
}