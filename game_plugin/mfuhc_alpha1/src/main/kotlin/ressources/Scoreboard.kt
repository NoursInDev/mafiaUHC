package org.noursindev.mafiauhc.ressources

import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard
import org.bukkit.scoreboard.ScoreboardManager
import org.noursindev.mafiauhc.MafiaUHC

class ScoreboardsGestionnaire(val main : MafiaUHC) {

    fun createGameScoreboard() {
        println("scoreboard creation")
        for (joueur in main.config.joueurs) {
            val manager : ScoreboardManager = Bukkit.getScoreboardManager()
            val scoreboard = manager.newScoreboard
            var objective = scoreboard.registerNewObjective("MafiaUHC", "dummy")

            objective.displaySlot = org.bukkit.scoreboard.DisplaySlot.SIDEBAR
            var score = objective.getScore("§d§lMafiaUHC")
            score.score = 100

            joueur.player.scoreboard = scoreboard as CraftScoreboard
        }
    }
}