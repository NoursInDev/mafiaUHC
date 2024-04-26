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
import kotlin.random.Random

class Starter(private val main : MafiaUHC):BukkitRunnable() {
    private var timer : Int = 10

    override fun run() {
        if(timer == 0) {
            val playerlist : MutableSet<CraftPlayer> = mutableSetOf()
            main.joueurs.forEach() {
                donneStuff(it.player)
                playerlist.add(it.player)
            }
            teleport(playerlist)
            main.setPhase(Phases.Minage)
            this.cancel()
        }
        Bukkit.broadcastMessage("Le jeu commence dans $timer secondes.")
        main.joueurs.forEach() {
            it.player.playSound(it.player.location, org.bukkit.Sound.NOTE_PLING, 1.0f, 1.0f)
        }
        timer--
    }

    fun donneStuff(player: Player) {
        player.inventory.clear()

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

    fun teleport(players : MutableSet<CraftPlayer>) {
        for (p in players) {
            var x: Int
            var z: Int
            do {
                x = Random.nextInt(-1000, 1000)
                z = Random.nextInt(-1000, 1000)
            } while (x*x + z*z < 300*300)

            val y = p.world.getHighestBlockYAt(x, z)
            p.teleport(Location(p.world, x.toDouble(), y.toDouble(), z.toDouble()))
        }
    }
}