package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Joueur


class Parrain(main : MafiaUHC):RoleSuper(main = main) {
    override val nom: String = "Parrain"
    override val description: String = "Vous êtes le Parrain de la mafia."

    override fun updateEffects() {
        rmult = 1F + (pierres/200)
    }

    override fun roleShow(): String {
        return description
    }

    override fun mfReunion(joueur: Joueur): Boolean {
        return false
    }

    override fun mfPierres(joueur: Joueur): Int? {
        return joueur.role?.pierres
    }

    override fun mfGuess(joueur: Joueur): Boolean {
        if (joueur.role is Agent) {
            (joueur.role as Agent).vulnerable = true
            return true
        }
        return false
    }

    override fun mfForcerecup() : Boolean{
        for (j in main.config.joueurs) {
            if (j.role is Fidele) {
                pierres += j.role!!.pierres
                j.role!!.pierres = 0
            }
        }
        return true
    }

    fun updateParrainEffects(fidele : Boolean) {
        if (fidele) {
            main.config.parrain!!.player.maxHealth -= 4.0
        } else {
            main.config.parrain!!.player.maxHealth += 1.0
        }
    }

}