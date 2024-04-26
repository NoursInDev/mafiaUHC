package org.noursindev.mafiauhc.ressources.commandes

import org.bukkit.command.CommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Phases

class CommandesConfig(private val main: MafiaUHC) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.isOp) {
            sender.sendMessage("Vous n'êtes pas autorisé à utiliser cette commande.")
            return false
        } else when (args[0]) {
            "start" -> {
                main.setPhase(Phases.Minage)
            }
            "set" -> {
                //TODO
            }
            "rmjoueur" -> {
                if (args.size != 1) {
                    sender.sendMessage("Usage: /rmjoueur <pseudo>")
                    return false
                } else {
                    val joueur = main.joueurs.find { it.player.name == args[1] }
                    if (joueur != null) {
                        main.joueurs.remove(joueur)
                    } else {
                        sender.sendMessage("Le joueur ${args[0]} n'est pas dans la partie.")
                    }
                }
            }
            "addjoueur" -> {
                if (args.size != 1) {
                    sender.sendMessage("Usage: /addjoueur <pseudo>")
                    return false
                } else {
                    val joueur = main.joueurs.find { it.player.name == args[1] }
                    if (joueur != null) {
                        sender.sendMessage("Le joueur ${args[0]} est déjà dans la partie.")
                    } else {
                        main.joueurs.add(main.joueurs.find { it.player.name == args[1] }!!)
                    }
                }
            }
            else -> {
                sender.sendMessage("Usage: /mfc <start|set|rmjoueur|addjoueur>")
                return false
            }
        }
        return true
    }
}