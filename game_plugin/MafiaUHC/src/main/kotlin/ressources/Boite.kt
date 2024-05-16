package org.noursindev.mafiauhc.ressources

import org.noursindev.mafiauhc.MafiaUHC

class Boite(main : MafiaUHC) {
    var pierres: Int = 0
    var fideles: Int = 0
    var agents: Int = 0
    var chauffeurs: Int = 0
    var nettoyeurs: Int = 0
    val main: MafiaUHC

    init {
        this.main = main
    }

    fun retourneBoite() : Map<String, Int> {
        val boite = mutableMapOf<String, Int>()
        boite["pierres"] = this.pierres
        boite["fideles"] = this.fideles
        boite["agents"] = this.agents
        boite["chauffeurs"] = this.chauffeurs
        boite["nettoyeurs"] = this.nettoyeurs
        return boite
    }

    fun autoConfig() {
        val nbj : Int = main.config.joueurs.size
        this.pierres = nbj
        this.agents = nbj / 10 * 2
        this.chauffeurs = nbj / 11 * 2
        this.nettoyeurs = nbj / 12
        this.fideles = nbj / 10 * 4 - this.nettoyeurs
    }
}