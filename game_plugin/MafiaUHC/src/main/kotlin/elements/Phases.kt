package org.noursindev.mafiauhc.elements

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.Server

interface Phase {
    val nomphase : String
}

class ConfigPhase(configurateur : CommandSender, players : Array<Player>) : Phase {
    override val nomphase = "Phase de configuration"
    private val configurateur : CommandSender
    var boite : Boite

    init {
        this.configurateur = configurateur
        configurateur.sendMessage("Configuration de la partie")
        this.boite = Boite(players)
    }

    fun donneConfigurateur() : CommandSender {
        return configurateur
    }
}

class GamePhase(joueurs : Array<Player>) : Phase {
    override val nomphase = "Phase de jeu"
    private val joueurs : MutableList<Player> = mutableListOf()
    private val serveur : Server = joueurs[0].server

    init {
        for (j in joueurs){
            this.joueurs.add(j)
        }
    }

    fun donneJoueurs() : MutableList<Player> {
        return joueurs
    }

    fun donneServeur() : Server {
        return serveur
    }
}

class FinalPhase(joueurs : Array<Player>) : Phase {
    override val nomphase = "Phase finale"
    private val joueurs : MutableList<Player> = mutableListOf()
    private val serveur : Server = joueurs[0].server

    init {
        for (j in joueurs){
            this.joueurs.add(j)
        }
    }

    fun donneJoueurs() : MutableList<Player> {
        return joueurs
    }

    fun donneServeur() : Server {
        return serveur
    }
}

class EndPhase(joueurs : Array<Player>) : Phase {
    override val nomphase = "Phase de fin"
    private val joueurs : MutableList<Player> = mutableListOf()
    private val serveur : Server = joueurs[0].server

    init {
        for (j in joueurs){
            this.joueurs.add(j)
        }
    }

    fun donneJoueurs() : MutableList<Player> {
        return joueurs
    }

    fun donneServeur() : Server {
        return serveur
    }
}