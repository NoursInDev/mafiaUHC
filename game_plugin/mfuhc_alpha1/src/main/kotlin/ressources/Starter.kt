package org.noursindev.mafiauhc.ressources

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import org.noursindev.mafiauhc.MafiaUHC
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.noursindev.mafiauhc.ressources.roles.Parrain
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

class Starter(private val main: MafiaUHC) : BukkitRunnable() {
    private var timer: Int = 10

    override fun run() {
        if (timer == 0) {
            Bukkit.broadcastMessage("Le jeu commence! (fin de l'invincibilité dans 30 secondes)")
            val playerlist: MutableSet<CraftPlayer> = mutableSetOf()

            if (main.config.parrain == null) {
                val newparrain = main.config.joueurs.random()
                main.config.parrain = newparrain
                newparrain.role = Parrain(main)
            }

            main.config.joueurs.forEach {
                it.player.playSound(it.player.location, org.bukkit.Sound.NOTE_PLING, 1.0f, 2.0f)
                donneStuff(it.player)
                if (main.config.parrain != null && it == main.config.parrain) {
                    it.player.sendMessage("Vous êtes le Parrain de cette partie.")
                    it.role = Parrain(main)
                    it.player.maxHealth = 26.0
                    (it.role as Parrain).hasForce = Random.nextBoolean()
                    if (!(it.role as Parrain).hasForce) {
                        it.player.walkSpeed *= 1.1F
                        it.player.sendMessage("Vous avez vitesse.")
                    } else {
                        it.player.sendMessage("Vous avez force.")
                    }
                } else {
                    it.player.sendMessage("Le Parrain de cette partie est ${main.config.parrain?.player?.name}.")
                    it.player.maxHealth = 20.0
                }
                playerlist.add(it.player)
                it.player.gameMode = org.bukkit.GameMode.SURVIVAL
                it.player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 255, false, false))
            }


            reduceWorldBorder(main.config.world!!, main.config.bordure[0].toDouble(), 0)
            setBorderDamage(main.config.world!!, main.config.bordure[4].toDouble())
            setWorldBorderCenter(main.config.world!!, 0.0, 0.0)
            activateWorldBorder(main.config.world!!, main.config.bordure)
            main.ordre = main.config.joueurs.shuffled().toTypedArray()
            main.ordre = main.ordre?.filterNot { it.role is Parrain }?.toTypedArray()
            main.lancePartie()


            teleport(playerlist)
        } else if (timer > 0) {
            Bukkit.broadcastMessage("Le jeu commence dans $timer secondes.")
            main.config.joueurs.forEach {
                it.player.playSound(it.player.location, org.bukkit.Sound.NOTE_PLING, 1.0f, 1.0f)
                it.player.level = timer
            }
        } else if (timer == -30) {
            Bukkit.broadcastMessage("Fin de l'invincibilité!")
            for (joueur in main.config.joueurs) {
                joueur.player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE)
            }
            cancel()
        }
        timer--
    }

    private fun donneStuff(player: Player) {
        player.inventory.clear()
        player.level = 0
        player.health = 20.0
        player.foodLevel = 20

        player.inventory.addItem(ItemStack(Material.APPLE, 64)) // 64 apples
        player.inventory.addItem(ItemStack(Material.BOOK, 7)) // 7 books
        player.inventory.addItem(ItemStack(Material.GOLDEN_CARROT, 64)) // 64 golden carrots
        player.inventory.addItem(ItemStack(Material.LOG, 64)) // 64 oak logs

        val pickaxe = ItemStack(Material.IRON_PICKAXE)
        pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2) // Efficiency II
        pickaxe.addUnsafeEnchantment(Enchantment.DURABILITY, 3) // Unbreaking III

        val axe = ItemStack(Material.IRON_AXE)
        axe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2) // Efficiency II
        axe.addUnsafeEnchantment(Enchantment.DURABILITY, 3) // Unbreaking III

        val shovel = ItemStack(Material.IRON_SPADE)
        shovel.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2) // Efficiency II
        shovel.addUnsafeEnchantment(Enchantment.DURABILITY, 3) // Unbreaking III

        player.inventory.addItem(pickaxe)
        player.inventory.addItem(axe)
        player.inventory.addItem(shovel)
    }

    private fun teleport(players: MutableSet<CraftPlayer>) {
        val dist = main.config.bordure[2]
        for (p in players) {
            var x: Int
            var z: Int
            do {
                x = Random.nextInt(-dist, dist)
                z = Random.nextInt(-dist, dist)
            } while (x * x + z * z < (-dist / 2) * (dist / 2))
            val y = p.world.getHighestBlockYAt(x, z) + 2
            p.teleport(Location(p.world, x.toDouble(), y.toDouble(), z.toDouble()))
        }
    }
}