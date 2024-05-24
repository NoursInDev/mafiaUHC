package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC


class Nettoyeur(main: MafiaUHC) : Fidele(main = main) {
    override val nom: String = "Nettoyeur"
    override val description: String = "Vous êtes nettoyeur, vous servez vos propres interets."
    override fun roleShow(): String {
        return description
    }

}