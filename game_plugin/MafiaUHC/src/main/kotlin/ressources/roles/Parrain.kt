package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Joueur


class Parrain(main : MafiaUHC):RoleSuper(main = main) {
    override val nom: String = "Parrain"
    override val description: String = "Vous êtes le Parrain de la mafia."
    override fun roleShow(): String {
        return description
    }

    override fun mfReunion(joueur: Joueur): Boolean {
        return false
    }

    override fun mfPierres(joueur: Joueur): Int? {
        return null
    }

}