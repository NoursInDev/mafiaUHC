package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC


class Parrain(main : MafiaUHC):RoleSuper(main = main) {
    override val nom: String = "Parrain"
    override val description: String = "Vous êtes le Parrain de la mafia."
    override fun roleShow(): String {
        return description
    }

}