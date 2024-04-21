package org.noursindev.mafiauhc.commandes

import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.elements.ConfigPhase
import org.bukkit.command.CommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.noursindev.mafiauhc.elements.GamePhase
import java.io.ObjectInputFilter.Config

class commandRead(private val plugin: MafiaUHC) : CommandExecutor{

    fun analyseArguments(command: Command, args: Array<out String>, sender: CommandSender){
        when {
            args.isEmpty() || args[0] == "infos" || args[0] == "info" -> {
                val pluginDt = plugin.description
                sender.sendMessage(pluginDt.name + " v" + pluginDt.version + " by " + pluginDt.authors )
            }
            args[0] == "config" -> {
                if (sender.isOp) { plugin.phase = ConfigPhase(sender, plugin.server.onlinePlayers.toTypedArray()) }
            }
            else -> {
                if (plugin.phase != null){
                    when (plugin.phase!!.javaClass){
                        ConfigPhase::class.java -> {
                            when (args[0]){
                                "rmplayer" -> {
                                    val configPhase = plugin.phase as ConfigPhase
                                    if (args.size == 1 || plugin.server.getPlayer(args[1]) == null) {
                                        sender.sendMessage("Joueur introuvable")
                                    } else if (configPhase.boite.playerDansListe(args[1])) {
                                        sender.sendMessage("Retrait d'un joueur")
                                    } else {
                                        sender.sendMessage("Aucun joueur à retirer")
                                    }
                                }

                                "start" -> {
                                    sender.sendMessage("Démarrage de la partie")
                                    plugin.phase = GamePhase(plugin.server.onlinePlayers.toTypedArray())
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