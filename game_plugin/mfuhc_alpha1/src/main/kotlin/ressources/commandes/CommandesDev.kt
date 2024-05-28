package org.noursindev.mafiauhc.ressources.commandes

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Phases
import org.noursindev.mafiauhc.ressources.roles.*

class CommandesDev(private val main : MafiaUHC) : CommandExecutor {
    override fun onCommand(p0: CommandSender?, p1: Command?, p2: String?, p3: Array<out String>?): Boolean {
        if (p0 is ConsoleCommandSender && p3?.get(0) == "devmode") {
            main.devmode = !main.devmode
            println("DevMode: ${main.devmode}")
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
                                    jcible.role = Chauffeur(main, ami, jcible)
                                }
                            }
                        }

                        "enfantdesrues" -> {
                            jcible.role = EnfantDesRues(main)
                        }

                        "nettoyeur" -> {
                            jcible.role = Nettoyeur(main)
                        }

                        else -> {
                            p0?.sendMessage("§cRole inconnu.")
                            return true
                        }
                    }
                    p0?.sendMessage("§a${jcible.player.name} est maintenant ${jcible.role?.nom}.")
                    println("${jcible.player.name} est maintenant ${jcible.role?.nom}.")
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
                println("Pierres: $p to cible ${p3[1]}.")
            }
            if (p3.size >=2 && p3[0] == "infos") {
                val jcible = main.config.joueurs.find { it.player.name == p3[1] }
                if (jcible != null) {
                    p0.sendMessage("§a${jcible.player.name} est ${jcible.role?.nom}")
                    p0.sendMessage("§aIl a ${jcible.role?.pierres} pierres et est ${if (jcible.role?.vivant == true) "vivant" else "mort"}.")
                }
            }
            if (p3.size >= 2 && p3[0] == "phase") {
                when (p3[1]) {
                    "config" -> main.setPhase(Phases.Configuration)
                    "active" -> main.setPhase(Phases.Active)
                    "minage" -> main.setPhase(Phases.Minage)
                    "finale" -> main.setPhase(Phases.Finale)
                    else -> p0?.sendMessage("§cPhase inconnue. Liste des Phases: config, active, minage, finale.")
                }
            }
            return true
        }

        p0?.sendMessage("§cCommande inconnue, utilisez probablement /mfd set joueur role ou /mfd pierres joueur nombre ou /mfd infos joueur")

        return false
    }
}