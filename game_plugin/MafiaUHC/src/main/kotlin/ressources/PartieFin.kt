package org.noursindev.mafiauhc.ressources

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.event.entity.PlayerDeathEvent
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.resources.Joueur
import org.noursindev.mafiauhc.ressources.roles.Parrain
import org.noursindev.mafiauhc.ressources.roles.RoleSuper
import org.noursindev.mafiauhc.ressources.roles.Voleur

fun checkFin(main: MafiaUHC, event: PlayerDeathEvent) {
    val joueur = main.config.joueurs.find { it.player == event.entity.player as CraftPlayer }

    if (main.getPhase() == Phases.Active) {
        // Condition de Victoire Voleurs
        if (joueur!!.role is Parrain) {
            finPartie(main, Voleur(main))
        }

        // Condition de Victoire Fidèles
        if (main.config.parrain!!.role!!.pierres == main.config.initialBoite?.pierres) {
            finPartie(main, Parrain(main))
        }
    }
    if (main.getPhase() == Phases.Finale) {
        //todo
    }

}

fun finPartie(main : MafiaUHC, role : RoleSuper) {
    if (role is Parrain) {
        main.server.broadcastMessage("Le Parrain ainsi que les fidèles ont gagné la partie.")
    }
    if (role is Voleur) {
        main.server.broadcastMessage("Le Voleur possédant le plus de pierres ainsi que les Enfants des Rues ont gagné la partie.")
    }
}