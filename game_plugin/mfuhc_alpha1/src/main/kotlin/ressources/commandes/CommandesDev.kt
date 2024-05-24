package org.noursindev.mafiauhc.ressources.commandes

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.roles.*

class CommandesDev(private val main : MafiaUHC) : CommandExecutor {
    override fun onCommand(p0: CommandSender?, p1: Command?, p2: String?, p3: Array<out String>?): Boolean {
        if (p0 is ConsoleCommandSender && p3?.get(0) == "devmode") {
            main.devmode = !main.devmode
            return true
        }

        if (p0?.isOp == true && main.devmode && p3 != null) {
            if (p3.size >= 3 && p3[0] == "set") {
                val jcible = main.config.joueurs.find { it.player.name == p3[1] }
                if (jcible != null) {
                    when (p3[2]) {
                        "voleur" -> {
                            jcible.role = Voleur(main)
                        }

                        "fidele" -> {
                            jcible.role = Fidele(main)
                        }

                        "parrain" -> {
                            jcible.role = Parrain(main)
                            main.config.parrain = jcible
                        }

                        "agent" -> {
                            jcible.role = Agent(main)
                        }

                        "chauffeur" -> {
                            if (p3.size >= 4) {
                                val ami = main.config.joueurs.find { it.player.name == p3[3] }
                                if (ami != null) {
                                    jcible.role = Chauffeur(main, ami)
                                }
                            }
                        }

                        "enfantdesrues" -> {
                            jcible.role = EnfantDesRues(main)
                        }

                        "nettoyeur" -> {
                            jcible.role = Nettoyeur(main)
                        }
                    }
                }
            }
            if (p3.size >= 3 && p3[0] == "pierres" && p3[2].toIntOrNull()!= null) {
                val p = p3[2].toInt()
                if (p3[1] == "tt") {
                    main.config.initialBoite?.pierres = p
                } else if (p3[1] == "boite") {
                    main.config.boite.pierres = p
                } else {
                    val jcible = main.config.joueurs.find { it.player.name == p3[1] }
                    if (jcible != null) {
                        jcible.role?.pierres = p
                    }
                }
            }
            return true
        }
        return false
    }
}