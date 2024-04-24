package org.noursindev.mafiauhc.gamefuncs

import org.bukkit.Location
import org.bukkit.entity.Player
import kotlin.random.Random
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack


fun teleport(players : Array<Player>) {
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

fun donneStuff(players : Array<Player>, clear : Boolean = true) {
    for (player in players) {
        if (clear) {
            player.inventory.clear()
        }
        // Add items to the player's inventory
        player.inventory.addItem(ItemStack(Material.APPLE, 64)) // 64 apples
        player.inventory.addItem(ItemStack(Material.BOOK, 7)) // 7 books
        player.inventory.addItem(ItemStack(Material.GOLDEN_CARROT, 64)) // 64 golden carrots
        player.inventory.addItem(ItemStack(Material.LOG, 64)) // 64 oak logs

        // Create tools with enchantments
        val pickaxe = ItemStack(Material.IRON_PICKAXE)
        pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2) // Efficiency II
        pickaxe.addUnsafeEnchantment(Enchantment.DURABILITY, 3) // Unbreaking III

        val axe = ItemStack(Material.IRON_AXE)
        axe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2) // Efficiency II
        axe.addUnsafeEnchantment(Enchantment.DURABILITY, 3) // Unbreaking III

        val shovel = ItemStack(Material.IRON_SPADE)
        shovel.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2) // Efficiency II
        shovel.addUnsafeEnchantment(Enchantment.DURABILITY, 3) // Unbreaking III

        // Add tools to the player's inventory
        player.inventory.addItem(pickaxe)
        player.inventory.addItem(axe)
        player.inventory.addItem(shovel)
    }
}