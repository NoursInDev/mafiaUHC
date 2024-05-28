package org.noursindev.mafiauhc.ressources.roles

import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Joueur

abstract class RoleSuper(val main : MafiaUHC) {
    abstract val nom: String
    abstract val description: String

    var donnees : Int = 0
    var pierres : Int = 0
    var vivant : Boolean = true

    var declencheur = false

    var dmult = 1F // multiplicateur de dégats
    var rmult = 1F // multiplicateur de résistance
    var vmult = 1F // multiplicateur de vitesse

    abstract fun roleShow() : String
    open fun updateEffects() { }

    // effets roles
    // Parrain
    open fun mfReunion(joueur: Joueur)      : Boolean? { return null }
    open fun mfPierres(joueur: Joueur)      : Int? { return null }
    open fun mfGuess(joueur: Joueur)        : Boolean? { return null }
    open fun mfForcerecup()                 : Boolean? { return null }

    // Fidele
    open fun mfPression(joueur: Joueur)     : Boolean? { return null }

    //Voleur
    open fun mfActivate()                   : Boolean? { return null }

    //Enfant des Rues

    //Chauffeur de Bus
    open fun mfLocalise()     : Array<Int>? { return null }

    //Agent
    open fun mfParrain(joueur: Joueur)      : Boolean? { return null }

    //Nettoyeur
}