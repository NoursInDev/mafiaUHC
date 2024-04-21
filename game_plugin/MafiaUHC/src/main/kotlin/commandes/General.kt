package org.noursindev.mafiauhc.commandes

import org.noursindev.mafiauhc.elements.ConfigPhase
import org.bukkit.command.CommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class commandRead : CommandExecutor{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage("Commande Validée: " + command.toString())
        if (args.isEmpty()){
            sender.sendMessage("MafiaUHC @NoursInDev")
            return true
        } else if (args[0] == "help" && args.size == 1){
            sender.sendMessage("Aide au jeu")
            return true
        } else if (args[0] == "config" && args.size == 1){
            if (sender.isOp){
                lanceConfig(sender)
                return true
            } else {
                sender.sendMessage("Vous n'avez pas les droits pour configurer la partie.")
                return true
            }
        }
        for (arg in args){
            sender.sendMessage(arg)
        }
        return true
    }

    fun lanceConfig(sender : CommandSender) {
        val config = ConfigPhase(sender)
    }
}