package org.noursindev.mafiauhc.ressources

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.noursindev.mafiauhc.ressources.roles.RoleSuper

class Joueur(val player: CraftPlayer) {
    var role : RoleSuper? = null
    var tour : Boolean = false
}