package org.noursindev.mafiauhc.ressources.inventaires

import net.minecraft.server.v1_8_R3.Enchantment
import org.bukkit.inventory.Inventory
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.Material
import org.bukkit.inventory.meta.ItemMeta
import org.noursindev.mafiauhc.ressources.Boite
import org.noursindev.mafiauhc.ressources.Configuration

fun configInvConstructeur(): Inventory {
    val inventaire: Inventory = Bukkit.createInventory(null, 5 * 9, "§dConfiguration")
    val joueurs: ItemStack = createPlayerHead("Steve", "§aJoueurs", 0)
    val roles: ItemStack = createPlayerHead("illusion96", "§9Roles", 0)
    val bordure = ItemStack(Material.GLASS, 1)
    val lanceur = ItemStack(Material.COMPASS, 1)
    val stuff = ItemStack(Material.CHEST, 1)

    val joueursmeta = joueurs.itemMeta
    val rolesmeta = roles.itemMeta
    val borduremeta = bordure.itemMeta
    val lanceurmeta = lanceur.itemMeta
    val stuffmeta = stuff.itemMeta

    joueursmeta.lore =
        mutableListOf("§eClic pour modifier les joueurs présents dans la partie et le titulaire du rôle du Parrain.")
    rolesmeta.lore =
        mutableListOf("§eClic pour modifier les roles présents dans la Boite de Cigares ainsi que pour définir un Parrain aléatoire.")
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

    joueurs.amount = 1
    roles.amount = 1

    inventaire.setItem(10, joueurs)
    inventaire.setItem(16, roles)
    inventaire.setItem(28, bordure)
    inventaire.setItem(31, lanceur)
    inventaire.setItem(34, stuff)

    return inventaire
}

fun rolesConfigInvConstructeur(boite: Boite): Inventory {
    val inventaire: Inventory = Bukkit.createInventory(null, 9, "§9Roles")

    var count = inventaire.size - 1

    for (element in 0 until boite.retourneBoite().size) {
        val key = boite.retourneBoite().keys.elementAt(element)
        val playerName = headLink[key] ?: "Notch"
        val stack: ItemStack = createPlayerHead(
            playerName,
            boite.retourneBoite().keys.elementAt(element),
            boite.retourneBoite().values.elementAt(element)
        )
        val stackmeta: ItemMeta = stack.itemMeta
        stackmeta.displayName = boite.retourneBoite().keys.elementAt(element)
        stack.itemMeta = stackmeta
        inventaire.setItem(count, stack)
        count--
    }

    return inventaire
}

fun joueursConfigConstructeur(): Inventory {
    val inventaire: Inventory = Bukkit.createInventory(null, 6 * 9, "§aJoueurs")
    return inventaire
}

fun borduresConfigConstructeur(config : Configuration): Inventory {
    val inventaire: Inventory = Bukkit.createInventory(null, 3 * 9, "§dBordures")

    //td = temps debut, tf = temps fin, dd = distance debut, df = distance fin, dam = damage
    val td = ItemStack(Material.WATCH, 1)
    val tf = ItemStack(Material.WATCH, 1)
    val dd = ItemStack(Material.DIAMOND, 1)
    val df = ItemStack(Material.DIAMOND, 1)
    val dam = ItemStack(Material.GOLD_SWORD, 1)

    val tdmeta = td.itemMeta
    val tfmeta = tf.itemMeta
    val ddmeta = dd.itemMeta
    val dfmeta = df.itemMeta
    val dammeta = dam.itemMeta

    tdmeta.displayName = "§dDébut de Réduction: ${config.bordure[0]} minutes"
    tfmeta.displayName = "§dFin de Réduction : ${config.bordure[1]} minutes"
    ddmeta.displayName = "§dDistance de début : ${config.bordure[2]} blocs"
    dfmeta.displayName = "§dDistance de fin : ${config.bordure[3]} blocs"
    dammeta.displayName = "§dDégâts / seconde: ${config.bordure[4]}"

    td.itemMeta = tdmeta
    tf.itemMeta = tfmeta
    dd.itemMeta = ddmeta
    df.itemMeta = dfmeta
    dam.itemMeta = dammeta

    tf.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 10)
    df.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 10)
    dam.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 10)

    inventaire.setItem(10, td)
    inventaire.setItem(11, tf)
    inventaire.setItem(13, dam)
    inventaire.setItem(15, dd)
    inventaire.setItem(16, df)

    return inventaire
}

fun lancementConfigConstructeur(): Inventory {
    val inventaire: Inventory = Bukkit.createInventory(null, 9, "§dLancer la partie")
    return inventaire
}