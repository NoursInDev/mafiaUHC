package org.noursindev.mafiauhc.ressources.inventaires

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun nouvelOpener() : ItemStack {
    val opener = ItemStack(Material.COMPASS, 1)
    val openermeta = opener.itemMeta

    openermeta.displayName = "§dConfigurateur"
    openermeta.enchants.put(org.bukkit.enchantments.Enchantment.DURABILITY, 10)
    openermeta.lore = mutableListOf("§7Clic droit pour ouvrir le configurateur.")
    openermeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS)
    opener.itemMeta = openermeta

    return opener
}