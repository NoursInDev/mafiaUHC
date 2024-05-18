package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC


class Agent(main : MafiaUHC):RoleSuper(main = main) {
    override val nom: String = "Agent"
    override val description: String = "Vous êtes un agent secret, vous devez vous infiltrer dans la mafia."
    var vulnerable : Boolean = false

    override fun roleShow(): String {
        return description
    }
}