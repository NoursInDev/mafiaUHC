package org.noursindev.mafiauhc.ressources

import org.noursindev.mafiauhc.MafiaUHC

class Boite(main : MafiaUHC) {
    private var pierres: Int = 0
    private var fideles: Int = 0
    private var agents: Int = 0
    private var chauffeurs: Int = 0
    private var nettoyeurs: Int = 0
    private val main: MafiaUHC

    init {
        this.main = main
        autoConfig()
    }

    fun setPierres(pierres: Int) {
        this.pierres = pierres
    }

    fun setFideles(fideles: Int) {
        this.fideles = fideles
    }

    fun setAgents(agents: Int) {
        this.agents = agents
    }

    fun setChauffeurs(chauffeurs: Int) {
        this.chauffeurs = chauffeurs
    }

    fun setNettoyeurs(nettoyeurs: Int) {
        this.nettoyeurs = nettoyeurs
    }

    fun autoConfig() {
        val nbj : Int = main.joueurs.size
        this.pierres = nbj
        this.agents = nbj / 10 * 2
        this.chauffeurs = nbj / 11 * 2
        this.nettoyeurs = nbj / 12
        this.fideles = nbj / 10 * 4 - this.nettoyeurs
    }
}