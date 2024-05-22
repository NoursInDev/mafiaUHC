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
        return joueur.role?.pierres
    }

    override fun mfGuess(joueur: Joueur): Boolean {
        return (joueur.role is Agent)
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

    fun updateParrainEffects(killed : Boolean) {
        if (killed) {
            main.config.parrain!!.player.maxHealth -= 4.0
        }
    }

}