package org.noursindev.mafiauhc.ressources

import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.noursindev.mafiauhc.MafiaUHC
import org.bukkit.event.entity.PlayerDeathEvent
import org.noursindev.mafiauhc.ressources.roles.Agent
import org.noursindev.mafiauhc.ressources.roles.Nettoyeur
import org.noursindev.mafiauhc.ressources.roles.Parrain


fun checkFinal(main: MafiaUHC, event: PlayerDeathEvent) : Int {
    val joueur = main.config.joueurs.find { it.player.name == event.entity.name }
    val tueur = main.config.joueurs.find { it.player.name == event.entity.killer?.name }
    if (tueur != null && joueur != null && ((joueur.role is Agent && tueur.role is Parrain) || (joueur.role is Nettoyeur))) {
        if (!(joueur.role as Agent).vulnerable && main.config.parrain?.player?.name == tueur.player.name) {
            event.keepInventory = true
            joueur.player.teleport(Location(joueur.player.world, 0.0, joueur.player.world.getHighestBlockYAt(0, 0).toDouble() , 0.0))
            guerreCivile(main, joueur)
        }
        if (tueur.role is Nettoyeur){
            guerreCivile(main, tueur)
            return 0
        }
    }
    return -1
}

fun guerreCivile(main: MafiaUHC, joueur : Joueur) {
    main.setPhase(Phases.Finale)
    main.server.broadcastMessage("La guerre civile a éclaté. §c§lBonne Chance!")
    joueur.role?.declencheur = true
    joueur.role?.updateEffects()
}