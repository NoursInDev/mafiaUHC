package org.noursindev.mafiauhc.ressources.roles

abstract class RoleSuper {
    abstract val nom: String
    abstract val description: String

    var pierres : Int = 0
    var vivant : Boolean = true

    abstract fun roleShow() : String
}