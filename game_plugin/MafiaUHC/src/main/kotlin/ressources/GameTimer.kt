package org.noursindev.mafiauhc.ressources

import org.bukkit.scheduler.BukkitRunnable
import org.noursindev.mafiauhc.MafiaUHC
import org.bukkit.Bukkit
import org.noursindev.mafiauhc.resources.Joueur
import org.noursindev.mafiauhc.ressources.roles.*

class GameTimer(private val main : MafiaUHC): BukkitRunnable() {
    private var time: Int = 0
    override fun run() {
        when (time) {
            60 -> {
                println("Tour de la boite.")
                lancerTours()
            }
        }
        time++
    }

    private fun lancerTours() {
        main.ordre?.forEach { joueur ->
            joueur.player.sendMessage("C'est à vous de choisir un role.")
            joueur.player.sendMessage("Voici le contenu de la boite: ")
            main.boite.retourneBoite().forEach { (key, value) ->
                joueur.player.sendMessage("$key : $value")
            }
            joueur.tour = true

            Bukkit.getScheduler().runTaskLater(main, Runnable {
                if (joueur.role == null) {
                    roleAttribution(joueur)
                }

                joueur.tour = false
            }, 600L)
        }
    }

    private fun roleAttribution(joueur: Joueur) {
            lateinit var roleChoisi : RoleSuper
            val rolesDisponibles = mutableListOf<RoleSuper>()
            if (main.boite.fideles > 0) rolesDisponibles.add(Fidele(main))
            if (main.boite.agents > 0) rolesDisponibles.add(Agent(main))
            if (main.boite.chauffeurs > 0) rolesDisponibles.add(Chauffeur(main))
            if (main.boite.nettoyeurs > 0) rolesDisponibles.add(Nettoyeur(main))
            if (main.boite.pierres > 0) rolesDisponibles.add(Voleur(main))

            if (rolesDisponibles.isEmpty()) {
                joueur.role = EnfantDesRues(main)

            } else {
                joueur.role = rolesDisponibles.random()
                when (roleChoisi) {
                    Fidele(main) -> main.boite.fideles--
                    Agent(main) -> main.boite.agents--
                    Chauffeur(main) -> main.boite.chauffeurs--
                    Nettoyeur(main) -> main.boite.nettoyeurs--
                    Voleur(main) -> main.boite.pierres--
                }
        }
        joueur.player.sendMessage("Votre rôle a été définit automatiquement. Vous êtes ${joueur.role!!.nom}.")
    }
}