package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC


class Nettoyeur(main: MafiaUHC) : Fidele(main = main) {
    override val nom: String = "Nettoyeur"
    override val description: String = "Vous êtes nettoyeur, vous servez vos propres interets."
    var killcount = 0
    override fun updateEffects() {
        if (declencheur) {
            dmult = 1.1F + (killcount * 0.05F)
            rmult = 1.1F
        }
    }

    override fun roleShow(): String {
        return description
    }
}