package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Joueur


open class Fidele(main : MafiaUHC):RoleSuper(main = main) {
    override val nom: String = "Fidèle"
    override val description: String = "Vous êtes fidèle à votre maître, vous ne pouvez pas le trahir."
    var resistance = 0
    var nbchoix = 0
    override fun roleShow(): String {
        return description
    }

    override fun mfPression(joueur: Joueur) : Boolean {
        return (joueur.role is Voleur)
    }
}