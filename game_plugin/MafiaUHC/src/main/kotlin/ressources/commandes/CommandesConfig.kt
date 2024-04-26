package org.noursindev.mafiauhc.ressources.commandes

import org.bukkit.command.CommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Phases
import org.noursindev.mafiauhc.ressources.Starter

class CommandesConfig(private val main: MafiaUHC) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.isOp || main.getPhase() != Phases.Configuration) {
            sender.sendMessage("Vous n'êtes pas autorisé à utiliser cette commande maintenant. Vous manquez de permissions ou la partie a déjà commencé.")
        } else when (args[0]) {
            "start" -> { run {
                val start = Starter(main)
                start.runTaskTimer(main, 0, 20)
            }}
            "set" -> {
                //TODO
            }
            "rmjoueur" -> {
                if (args.size < 2) {
                    sender.sendMessage("Usage: /rmjoueur <pseudo>")
                } else {
                    if (main.joueurs.size == 0) {
                        sender.sendMessage("Il n'y a pas de joueur dans la partie.")
                    } else if (main.server.getPlayer(args[1]) == null) {
                        sender.sendMessage("Le joueur ${args[1]} n'est pas connecté.")
                    } else if (main.joueurs.find { it.player.name == args[1] } == null) {
                        sender.sendMessage("Le joueur ${args[1]} n'est pas dans la partie.")
                    } else {
                        main.joueurs.remove(main.joueurs.find { it.player.name == args[1] }!!)
                        sender.sendMessage("Le joueur ${args[1]} a été retiré de la partie.")
                    }
                }
            }
            "addjoueur" -> {
                if (args.size < 2) {
                    sender.sendMessage("Usage: /addjoueur <pseudo>")
                } else {
                    if (main.joueurs.size == 0) {
                        sender.sendMessage("Il n'y a pas de joueur dans la partie.")
                    } else if (main.server.getPlayer(args[1]) == null) {
                        sender.sendMessage("Le joueur ${args[1]} n'est pas connecté.")
                    } else if (main.joueurs.find { it.player.name == args[1] } != null) {
                        sender.sendMessage("Le joueur ${args[1]} est déjà dans la partie.")
                    } else {
                        main.joueurs.add(main.joueurs.find { it.player.name == args[1] }!!)
                        sender.sendMessage("Le joueur ${args[1]} a été ajouté à la partie.")
                    }
                }
            }
            "joueurs" -> {
                sender.sendMessage("Liste des joueurs:")
                main.joueurs.forEach { sender.sendMessage(it.player.name) }
            }
            else -> {
                sender.sendMessage("Usage: /mfc <start|set|rmjoueur|addjoueur|joueurs>")
            }
        }
        return true
    }
}