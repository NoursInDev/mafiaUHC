package org.noursindev.mafiauhc.resources

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.noursindev.mafiauhc.ressources.roles.RoleSuper

class Joueur(player: CraftPlayer) {
    val player : CraftPlayer
    var role : RoleSuper? = null

    init {
        this.player = player
    }
}