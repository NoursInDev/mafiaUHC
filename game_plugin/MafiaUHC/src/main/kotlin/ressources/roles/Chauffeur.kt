package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Joueur


class Chauffeur(main : MafiaUHC, private val ami: Joueur):RoleSuper(main = main) {
    override val nom: String = "Chauffeur"
    override val description: String = "Vous êtes un chauffeur de bus."

    override fun roleShow(): String {
        return description
    }

    override fun mfLocalise(joueur: Joueur): Array<Int> {
        return arrayOf(ami.player.location.x.toInt(), ami.player.location.z.toInt())
    }

}