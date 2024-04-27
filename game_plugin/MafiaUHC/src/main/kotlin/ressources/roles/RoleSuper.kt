package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC

abstract class RoleSuper(val main : MafiaUHC) {
    abstract val nom: String
    abstract val description: String

    var pierres : Int = 0
    var vivant : Boolean = true

    abstract fun roleShow() : String
}