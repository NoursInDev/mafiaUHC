package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC


class EnfantDesRues(main : MafiaUHC):RoleSuper(main = main) {
    override val nom: String = "Enfant des rues"
    override val description: String = "Vous êtes un enfant des rues, vous devez aider les voleurs."
    override fun roleShow(): String {
        return description
    }

    override fun updateEffects() {
        if (pierres >= 3) {
            dmult = 1F + (pierres/100) -3
            if (pierres >= 5) {
                vmult = 1F + ((pierres * 1.5F) /100) -5
            }
        } else {
            dmult = 0.9F
            vmult = 1F
        }
    }

}