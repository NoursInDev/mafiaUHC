package org.noursindev.mafiauhc.elements

import org.bukkit.entity.Player

class Boite(players : Array<Player>, config: Array<Int> = arrayOf(0, 0, 0, 0, 0)) {
    private var pierres : Int = 0
    private var fideles : Int = 0
    private var agents : Int = 0
    private var chauffeurs : Int = 0
    private var nettoyeurs : Int = 0

    private var players : Array<Player>

    init {
        this.players = players
        this.pierres = config[0]
        this.fideles = config[1]
        this.agents = config[2]
        this.chauffeurs = config[3]
        this.nettoyeurs = config[4]
    }

    fun donneContenu() : Array<Int> {
        return arrayOf(pierres, fideles, agents, chauffeurs, nettoyeurs)
    }

    fun donneOrdre() : Array<Player> {
        return players
    }

    fun resetPierres(nb : Int) {
        pierres = nb
    }

    fun resetFideles(nb : Int) {
        fideles = nb
    }

    fun resetAgents(nb : Int) {
        agents = nb
    }

    fun resetChauffeurs(nb : Int) {
        chauffeurs = nb
    }

    fun resetNettoyeurs(nb : Int) {
        nettoyeurs = nb
    }

    fun retirePlayer(player : Player) : Boolean {
        val newPlayers = mutableListOf<Player>()
        for (p in players){
            if (p == player){
                break
            }
            return false
        }
        for (p in players){
            if (p != player){
                newPlayers.add(p)
            }
        }
        players = newPlayers.toTypedArray()
        return true
    }

    fun ajoutePlayer(player : Player) : Boolean {
        val newPlayers = mutableListOf<Player>()
        for (p in players){
            if (p == player){
                return false
            }
        }
        for (p in players){
            newPlayers.add(p)
        }
        newPlayers.add(player)
        players = newPlayers.toTypedArray()
        return true
    }

}