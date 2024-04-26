package org.noursindev.mafiauhc.resources

import org.bukkit.entity.Player
import org.noursindev.mafiauhc.ressources.roles.RoleSuper

class Joueur(player: Player) {
    val player : Player
    var role : RoleSuper? = null

    init {
        this.player = player
    }
}