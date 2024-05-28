package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Joueur


class Chauffeur(main: MafiaUHC, val ami: Joueur, val joueur: Joueur) : RoleSuper(main = main) {
    override val nom: String = "Chauffeur"
    override val description: String = "Vous êtes un chauffeur de bus."

    override fun roleShow(): String {
        return description
    }

    override fun updateEffects() {
        var distance_ami_joueur = Math.sqrt(
            Math.pow(
                ami.player.location.x - joueur.player.location.x,
                2.0
            ) + Math.pow(ami.player.location.z - joueur.player.location.z, 2.0)
        )

        if (distance_ami_joueur < 50) {
            joueur.role!!.rmult = 1.1F
        } else {
            joueur.role!!.rmult = 1F
        }
    }

    override fun mfLocalise(): Array<Int> {
        return arrayOf(ami.player.location.x.toInt(), ami.player.location.z.toInt())
    }

}