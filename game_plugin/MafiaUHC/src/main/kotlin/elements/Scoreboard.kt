package org.noursindev.mafiauhc.elements

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scoreboard.Score
import org.noursindev.mafiauhc.MafiaUHC

class ScoreboardInfo(private val plugin: MafiaUHC) {

    fun createScoreboard() {
        val manager = Bukkit.getScoreboardManager()
        val board = manager?.newScoreboard

        val objective = board?.registerNewObjective("info", "dummy")
        objective?.displayName = ChatColor.GREEN.toString() + "Info"

        val phaseScore: Score = objective?.getScore(ChatColor.AQUA.toString() + "Phase: " + plugin.phase?.javaClass?.simpleName)!!
        phaseScore.score = 2

        val playersAliveScore: Score = objective?.getScore(ChatColor.AQUA.toString() + "Joueurs en vie: " + plugin.server.onlinePlayers.size)!!
        playersAliveScore.score = 1

        val pluginInfoScore: Score = objective?.getScore(ChatColor.AQUA.toString() + "Plugin: " + plugin.description.name + " v" + plugin.description.version)!!
        pluginInfoScore.score = 3

        // Set the scoreboard for all online players
        for (player in Bukkit.getOnlinePlayers()) {
            player.scoreboard = board!!
        }
    }
}