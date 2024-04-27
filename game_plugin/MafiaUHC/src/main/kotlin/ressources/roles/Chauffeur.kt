package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC


class Chauffeur(main : MafiaUHC):RoleSuper(main = main) {
    override val nom: String = "Chauffeur"
    override val description: String = "Vous êtes un chauffeur de bus."
    override fun roleShow(): String {
        return description
    }

}