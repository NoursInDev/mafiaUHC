package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC


class Voleur(main : MafiaUHC):RoleSuper(main = main) {
    override val nom: String = "Voleur"
    override val description: String = "Vous êtes un voleur, vous devez voler des pierres et tuer le parrain."
    override fun roleShow(): String {
        return description
    }

}