package org.noursindev.mafiauhc.ressources

import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard
import org.bukkit.entity.Player
import org.bukkit.scoreboard.ScoreboardManager
import org.noursindev.mafiauhc.MafiaUHC

class ScoreboardsGestionnaire(val main: MafiaUHC) {

    fun gameScoreboard(joueur: Joueur) {
        val manager: ScoreboardManager = Bukkit.getScoreboardManager()
        val scoreboard = manager.newScoreboard
        val objective = scoreboard.registerNewObjective("§4Mafia§0§lUHC", "dummy")

        objective.displaySlot = org.bukkit.scoreboard.DisplaySlot.SIDEBAR

        val infos = objective.getScore("§6--- §4infos")
        val en_vie = objective.getScore("En vie: ${main.config.joueurs.filter { it.vivant }.size}/${main.config.joueurs.size}")
        val role = objective.getScore("Role: ${if (joueur.role != null) joueur.role!!.nom else "Non défini"}")
        val pierres = objective.getScore("Pierres: ${if (joueur.role != null) joueur.role!!.pierres else 0}")
        val effets = objective.getScore("§6--- §4effets")

        var count = 0

        if (joueur.role?.vmult != 1F) {
            val vitesse = objective.getScore("Vitesse: x${joueur.role?.dmult}")
            vitesse.score = count
            count++
        }

        if (joueur.role?.rmult != 1F) {
            val resi = objective.getScore("Resistance: x${joueur.role?.dmult}")
            resi.score = count
            count++
        }

        if (joueur.role?.dmult != 1F) {
            val degats = objective.getScore("Dégats: x${joueur.role?.dmult}")
            degats.score = count
            count++
        }

        val scoreObjects = listOf(
            effets to "effets",
            pierres to "pierres",
            role to "role",
            en_vie to "en_vie",
            infos to "infos"
        )

        scoreObjects.forEachIndexed { index, pair ->
            pair.first.score = index + count
        }

        joueur.player.scoreboard = scoreboard as CraftScoreboard

    }

    fun waitingScoreboard(player: Player) {
        val manager: ScoreboardManager = Bukkit.getScoreboardManager()
        val scoreboard = manager.newScoreboard
        val objective = scoreboard.registerNewObjective("§4Mafia§0§lUHC", "dummy")

        objective.displaySlot = org.bukkit.scoreboard.DisplaySlot.SIDEBAR

        val infos = objective.getScore("§6--- §4infos")
        val joueurs = objective.getScore("Joueurs: ${main.config.joueurs.size}/${main.server.onlinePlayers.size}")
        val enregistre = objective.getScore("Enregistré: ${if (main.config.joueurs.find { it.player.name == player.name } != null) "Oui" else "Non"}")
        val bordure = objective.getScore("§6--- §4bordure")
        val taille = objective.getScore("Taille: ${main.config.bordure[2]} to ${main.config.bordure[3]}m")
        val temps = objective.getScore("Temps: ${main.config.bordure[0]} to ${main.config.bordure[1]}min")
        val degats = objective.getScore("Dégats: ${main.config.bordure[4]}/sec")
        val plugininfos = objective.getScore("§6--- §4plugin")
        val version = objective.getScore("Version: ${main.description.version}")
        val auteur = objective.getScore("Auteur: @${main.description.authors[0]}")
        val orga = objective.getScore("Organisation: @ReWorkMC")

        val scoreObjects = listOf(
            orga to "orga",
            auteur to "auteur",
            version to "version",
            plugininfos to "plugininfos",
            degats to "degats",
            temps to "temps",
            taille to "taille",
            bordure to "bordure",
            enregistre to "enregistre",
            joueurs to "joueurs",
            infos to "infos"
        )

        scoreObjects.forEachIndexed { index, pair ->
            pair.first.score = index + 1
        }

        player.scoreboard = scoreboard as CraftScoreboard
    }
}