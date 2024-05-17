package org.noursindev.mafiauhc.ressources

import org.noursindev.mafiauhc.resources.Joueur


class Configuration(joueurs : MutableSet<Joueur>, boite : Boite) {
    var joueurs : MutableSet<Joueur>
    var boite : Boite
    var initialBoite : Boite? = null
    var bordure : Array<Int>
    var parrain : Joueur? = null

    init {
        this.joueurs = joueurs
        this.boite = boite
        this.bordure = arrayOf(90, 120, 1000, 100, 1) // 90 to 120 minutes bordure, de 1000 -1000 à 100 -100, 1HP/S
    }

    fun setRandomParrain() {
        parrain = joueurs.random()
    }
}