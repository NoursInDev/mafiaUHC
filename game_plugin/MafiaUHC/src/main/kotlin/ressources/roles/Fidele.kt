package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC


open class Fidele(main : MafiaUHC):RoleSuper(main = main) {
    override val nom: String = "Fidèle"
    override val description: String = "Vous êtes fidèle à votre maître, vous ne pouvez pas le trahir."
    override fun roleShow(): String {
        return description
    }

}