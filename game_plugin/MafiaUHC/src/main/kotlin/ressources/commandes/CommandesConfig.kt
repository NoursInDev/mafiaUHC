package org.noursindev.mafiauhc.ressources.commandes

import org.bukkit.command.CommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Phases
import org.noursindev.mafiauhc.ressources.Starter
import org.noursindev.mafiauhc.ressources.inventaires.nouvelOpener

class CommandesConfig(private val main: MafiaUHC) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender.isOp && main.getPhase() == Phases.Configuration) {
            if (args.isEmpty()) {

                sender.sendMessage("Vous devez spécifier une action.")
                return false
            } else {
                when (args[0]) {
                    "joueurs" -> {
                        sender.sendMessage("Liste des joueurs:")
                        main.config.joueurs.forEach { sender.sendMessage(it.player.name) }
                    }

                    "rmjoueur", "addjoueur" -> {
                        if (args.size < 2 || main.server.getPlayer(args[1]) == null) {
                            sender.sendMessage("Usage: /mfc ${args[0]} <pseudo>\nLe joueur doit être connecté.")
                        } else {
                            if (main.config.joueurs.find { it.player.name == args[1] } != null) {
                                if (args[0] == "rmjoueur") {
                                    main.config.joueurs.remove(main.config.joueurs.find { it.player.name == args[1] }!!)
                                    sender.sendMessage("Le joueur ${args[1]} a été retiré de la partie.")
                                } else {
                                    sender.sendMessage("Le joueur ${args[1]} est déjà dans la partie.")
                                }
                            } else {
                                if (args[0] == "addjoueur") {
                                    main.config.joueurs.add(main.config.joueurs.find { it.player.name == args[1] }!!)
                                    sender.sendMessage("Le joueur ${args[1]} a été ajouté à la partie.")
                                } else {
                                    sender.sendMessage("Le joueur ${args[1]} n'est pas dans la partie.")
                                }

                            }
                        }
                    }

                    "boite" -> {
                        sender.sendMessage("Boite de la partie:")
                        main.config.boite.retourneBoite().forEach { (k, v) -> sender.sendMessage("$k: $v") }
                    }

                    "autoconfig" -> {
                        main.config.boite.autoConfig()
                        sender.sendMessage("La boite a été automatiquement configurée.")
                    }

                    "set" -> {

                        /* Def Parrain /mfc set parrain <pseudo>
                        *   random : /mfc set parrain rd
                        *   null (DEV ONLY): /mfc set parrain no  => VERSION FINALE DEVIENT /mfc set parrain nom = donne nom parrain.
                        *   normal : /mfc set parrain <pseudo> ou <pseudo> est un joueur connecté et dans la partie
                        */

                        if (args[1] == "parrain" && (args[2] == "no" || args[2] == "rd" || (main.server.getPlayer(args[2]) != null && main.config.joueurs.find { it.player.name == args[2] } != null))) {
                            when (args[2]) {
                                "no" -> {
                                    main.config.updateParrain(null)
                                    sender.sendMessage("Le parrain a été retiré.")
                                }

                                "rd" -> {
                                    main.config.setRandomParrain()
                                    sender.sendMessage("Le parrain a été fixé aléatoirement.")
                                }

                                else -> {
                                    main.config.updateParrain(main.config.joueurs.find { it.player.name == args[2] })
                                    sender.sendMessage("Le parrain a été fixé à ${args[2]}.")
                                }
                            }
                            return true
                        }
                        if (args.size > 2 && args[2].toIntOrNull() != null) {
                            val argument: Int = args[2].toInt()
                            when (args[1]) {
                                "pierres" -> {
                                    main.config.boite.pierres = argument
                                    sender.sendMessage("Le nombre de pierres a été fixé à $argument.")
                                }

                                "fideles" -> {
                                    main.config.boite.fideles = argument
                                    sender.sendMessage("Le nombre de fidèles a été fixé à $argument.")
                                }

                                "agents" -> {
                                    main.config.boite.agents = argument
                                    sender.sendMessage("Le nombre d'agents a été fixé à $argument.")
                                }

                                "chauffeurs" -> {
                                    main.config.boite.chauffeurs = argument
                                    sender.sendMessage("Le nombre de chauffeurs a été fixé à $argument.")
                                }

                                "nettoyeurs" -> {
                                    main.config.boite.nettoyeurs = argument
                                    sender.sendMessage("Le nombre de nettoyeurs a été fixé à $argument.")
                                }

                                else -> {
                                    sender.sendMessage("Usage: /mfc set <pierres|fideles|agents|chauffeurs|nettoyeurs|parrain> <argument>")
                                }
                            }
                        } else {
                            sender.sendMessage("Vous n'avez pas entré assez d'arguments !")
                        }
                    }

                    "start" -> {
                        run {
                            val start = Starter(main)
                            start.runTaskTimer(main, 0, 20)
                            main.setPhase(Phases.Minage)
                        }
                    }

                    "configurateur" -> {
                        if (sender is Player) {
                            sender.player.inventory.addItem(nouvelOpener())
                        } else {
                            sender.sendMessage("Vous devez être un joueur pour ouvrir le configurateur.")
                        }
                    }

                    else -> {
                        sender.sendMessage("Usage: /mfc <start|set|rmjoueur|addjoueur|joueurs>")
                    }
                }
            }
        } else {
            sender.sendMessage("Vous n'êtes pas autorisé à configurer la partie. Vous manquez de permissions ou la partie a déjà commencé.")
        }
        return true
    }
}