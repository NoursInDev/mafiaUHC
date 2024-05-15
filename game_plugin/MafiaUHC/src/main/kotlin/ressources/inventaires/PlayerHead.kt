package org.noursindev.mafiauhc.ressources.inventaires

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.SkullType
import org.bukkit.Bukkit

fun createPlayerHead(playername : String, displayname : String, amount : Int): ItemStack {
    val skull = ItemStack(Material.SKULL_ITEM, amount, SkullType.PLAYER.ordinal.toShort())
    val meta = Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM) as SkullMeta
    meta.owner = playername
    meta.displayName = displayname
    skull.itemMeta = meta

    return skull
}

val headLink = mapOf(
    "pierres" to "baria1166",
    "fideles" to "Kieran_68",
    "agents" to "Mangeur2Noi",
    "chauffeurs" to "illusion96",
    "nettoyeurs" to "Hadesh",
    "enfants des rues" to "AnGab",
    "parrain" to "lolog18"

)