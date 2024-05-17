package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Joueur

abstract class RoleSuper(val main : MafiaUHC) {
    abstract val nom: String
    abstract val description: String

    var pierres : Int = 0
    var vivant : Boolean = true

    abstract fun roleShow() : String

    // effets roles
    // Parrain
    open fun mfReunion(joueur: Joueur)      : Boolean? { return null }
    open fun mfPierres(joueur: Joueur)      : Int? { return null }
    open fun mfGuess(joueur: Joueur)        : Boolean? { return null }
    open fun mfForcerecup(joueur: Joueur)   : Boolean? { return null }

    // Fidele
    open fun mfPression(joueur: Joueur)     : Boolean? { return null }

    //Voleur
    open fun mfActivate(joueur: Joueur)     : Boolean? { return null }

    //Enfant des Rues

    //Chauffeur de Bus
    open fun mfLocalise(joueur: Joueur)     : Array<Int>? { return null }

    //Agent
    open fun mfParrain(joueur: Joueur)      : Boolean? { return null}

    //Nettoyeur
}