package org.noursindev.mafiauhc.ressources

import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.resources.Joueur

class Ecoutes(main : MafiaUHC) : Listener {
    private val main: MafiaUHC
    init {
        this.main = main
    }

    @EventHandler
    fun arriveeJoueur(event: PlayerJoinEvent) {
        if (main.getPhase() == Phases.Configuration) {
            main.joueurs.add(Joueur(event.player as CraftPlayer))
        } else {
            if ((main.joueurs.find { it.player == event.player } == null) && (!event.player.isOp)) {
                event.player.kickPlayer("@${main.name} Une partie est déjà en cours et vous n'êtes pas autorisé à la rejoindre.")
            } else if (event.player.isOp && main.joueurs.find { it.player == event.player } == null) {
                event.player.gameMode = org.bukkit.GameMode.SPECTATOR
                event.player.sendMessage("@${main.name} " + ChatColor.RED.toString() + "Vous êtes en mode spectateur car une partie est déjà en cours.")
            }
        }
    }

    @EventHandler
    fun departJoueur(event: PlayerJoinEvent) {
        if (main.getPhase() == Phases.Configuration) {
            main.joueurs.remove(Joueur(event.player as CraftPlayer))
        }
    }
}