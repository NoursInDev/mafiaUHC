package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Joueur


class Agent(main : MafiaUHC):RoleSuper(main = main) {
    override val nom: String = "Agent"
    override val description: String = "Vous êtes un agent secret, vous devez vous infiltrer dans la mafia."
    var vulnerable : Boolean = false
    var killcount = 0
    override fun updateEffects() {
        dmult = 1.1F
        if (!vulnerable) {
            rmult = 1.1F
        }

        if (declencheur) {
            dmult = 1.1F + (killcount * 0.05F)
            rmult = 1.1F
        }
    }

    override fun roleShow(): String {
        return description
    }

    override fun mfParrain(joueur: Joueur): Boolean {
        return joueur.role is Parrain
    }
}