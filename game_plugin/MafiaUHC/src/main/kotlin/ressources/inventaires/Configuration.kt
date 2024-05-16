package org.noursindev.mafiauhc.ressources.inventaires

import org.bukkit.inventory.Inventory
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.Material

fun configInvConstructeur() : Inventory {
    val inventaire : Inventory = Bukkit.createInventory(null, 5*9, "§dConfiguration")
    val joueurs : ItemStack = createPlayerHead("Steve", "§aJoueurs", 0)
    val roles : ItemStack = createPlayerHead("illusion96", "§9Roles", 0)
    val bordure : ItemStack = ItemStack(Material.GLASS, 1)
    val lanceur : ItemStack = ItemStack(Material.COMPASS, 1)
    val stuff : ItemStack = ItemStack(Material.CHEST, 1)

    val joueursmeta = joueurs.itemMeta
    val rolesmeta = roles.itemMeta
    val borduremeta = bordure.itemMeta
    val lanceurmeta = lanceur.itemMeta
    val stuffmeta = stuff.itemMeta

    joueursmeta.lore = mutableListOf("§eClic pour modifier les joueurs présents dans la partie et le titulaire du rôle du Parrain.")
    rolesmeta.lore = mutableListOf("§eClic pour modifier les roles présents dans la Boite de Cigares ainsi que pour définir un Parrain aléatoire.")
    borduremeta.lore = mutableListOf("§eClic pour modifier les bordures de la map.")
    lanceurmeta.lore = mutableListOf("§eClic pour lancer la partie.")
    stuffmeta.lore = mutableListOf("§eClic pour consulter les stuffs des joueurs")

    borduremeta.displayName = "§dBordures"
    lanceurmeta.displayName = "§dLancer la partie"
    stuffmeta.displayName = "§dStuffs"

    joueurs.itemMeta = joueursmeta
    roles.itemMeta = rolesmeta
    bordure.itemMeta = borduremeta
    lanceur.itemMeta = lanceurmeta
    stuff.itemMeta = stuffmeta

    inventaire.setItem(10, joueurs)
    inventaire.setItem(16, roles)
    inventaire.setItem(28, bordure)
    inventaire.setItem(31, lanceur)
    inventaire.setItem(34, stuff)

    return inventaire
}