package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC


class EnfantDesRues(main : MafiaUHC):RoleSuper(main = main) {
    override val nom: String = "Enfant des rues"
    override val description: String = "Vous êtes un enfant des rues, vous devez aider les voleurs."
    override fun roleShow(): String {
        return description
    }

}