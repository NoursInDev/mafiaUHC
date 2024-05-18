package org.noursindev.mafiauhc.ressources.inventaires

import org.bukkit.inventory.Inventory
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.ItemMeta
import org.noursindev.mafiauhc.MafiaUHC
import org.noursindev.mafiauhc.ressources.Joueur
import org.noursindev.mafiauhc.ressources.Boite
import org.noursindev.mafiauhc.ressources.Configuration
import org.noursindev.mafiauhc.ressources.roles.Parrain

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

    val retour = ItemStack(Material.ARROW, 1)
    val retourmeta = retour.itemMeta
    retourmeta.displayName = "§aRetour"
    retour.itemMeta = retourmeta

    inventaire.setItem(0, retour)

    return inventaire
}

fun joueursConfigConstructeur(main : MafiaUHC, numpage : Int, joueurs : Array<Player>, liste : MutableSet<Joueur>): Inventory {
    val inventaire: Inventory = Bukkit.createInventory(null, 6 * 9, "§aJoueurs")
    for (i in numpage * 45 until (numpage + 1) * 45) {
        if (i < joueurs.size) {
            val joueur = joueurs.elementAt(i)
            val stack: ItemStack = createPlayerHead(joueur.name, joueur.name, 1)
            val stackmeta: ItemMeta = stack.itemMeta

            if (liste.find { it.player.name == joueur.name } != null) {
                println("valide meta dans")
                stackmeta.lore = mutableListOf("§aJoueur dans la partie.")
                if (joueur.player.name == main.config.parrain?.player?.name) {
                    stackmeta.lore = mutableListOf("§eParrain actuellement définit.")
                }
            } else {
                stackmeta.lore = mutableListOf("§cJoueur hors partie.")
            }
            println("meta : ${stackmeta.lore}")
            stack.itemMeta = stackmeta
            println(stack.itemMeta.lore)
            inventaire.addItem(stack)
        } else {
            break
        }
    }

    val page = ItemStack(Material.PAPER, numpage + 1)
    val pagemeta = page.itemMeta
    pagemeta.displayName = "§aPage ${numpage + 1}"
    page.itemMeta = pagemeta

    val totalpage = ItemStack(Material.PAPER, joueurs.size / 45 + 1)
    val totalpagemeta = totalpage.itemMeta
    totalpagemeta.displayName = "§aTotal de pages : ${joueurs.size / 45 + 1}"
    totalpage.itemMeta = totalpagemeta

    val retour = ItemStack(Material.ARROW, 1)
    val retourmeta = retour.itemMeta
    retourmeta.displayName = "§aRetour"
    retour.itemMeta = retourmeta

    val suivant = ItemStack(Material.WATER_BUCKET, 1)
    val suivantmeta = suivant.itemMeta
    suivantmeta.displayName = "§aSuivant"
    suivant.itemMeta = suivantmeta

    val precedent = ItemStack(Material.LAVA_BUCKET, 1)
    val precedentmeta = precedent.itemMeta
    precedentmeta.displayName = "§aPrécédent"
    precedent.itemMeta = precedentmeta

    inventaire.setItem(45, page)
    inventaire.setItem(53, totalpage)
    inventaire.setItem(49, retour)
    inventaire.setItem(50, suivant)
    inventaire.setItem(48, precedent)

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

    tdmeta.lore = mutableListOf("TD")
    tfmeta.lore = mutableListOf("TF")
    ddmeta.lore = mutableListOf("DD")
    dfmeta.lore = mutableListOf("DF")
    dammeta.lore = mutableListOf("DAM")

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

    val retour = ItemStack(Material.ARROW, 1)
    val retourmeta = retour.itemMeta
    retourmeta.displayName = "§aRetour"
    retour.itemMeta = retourmeta

    inventaire.setItem(18, retour)

    return inventaire
}

fun lancementConfigConstructeur(): Inventory {
    val inventaire: Inventory = Bukkit.createInventory(null, 9, "§dLancer la partie")

    val lancer = ItemStack(Material.EMERALD_BLOCK, 1)
    val stop = ItemStack(Material.REDSTONE_BLOCK, 1)

    val lancermeta = lancer.itemMeta
    val stopmeta = stop.itemMeta

    lancermeta.displayName = "§aLancer"
    stopmeta.displayName = "§aAnnuler"

    lancer.itemMeta = lancermeta
    stop.itemMeta = stopmeta

    inventaire.setItem(4, lancer)
    inventaire.setItem(8, stop)

    return inventaire
}

fun stuffShowroomConstructeur() : Inventory {
    val inventaire: Inventory = Bukkit.createInventory(null, 3 * 9, "§dStuffs")

    val retour = ItemStack(Material.ARROW, 1)
    val retourmeta = retour.itemMeta
    retourmeta.displayName = "§aRetour"
    retour.itemMeta = retourmeta

    inventaire.setItem(26, retour)

    inventaire.addItem(ItemStack(Material.APPLE, 64)) // 64 apples
    inventaire.addItem(ItemStack(Material.BOOK, 7)) // 7 books
    inventaire.addItem(ItemStack(Material.GOLDEN_CARROT, 64)) // 64 golden carrots
    inventaire.addItem(ItemStack(Material.LOG, 64)) // 64 oak logs

    val pickaxe = ItemStack(Material.IRON_PICKAXE)
    pickaxe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DIG_SPEED, 2) // Efficiency II
    pickaxe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 3) // Unbreaking III

    val axe = ItemStack(Material.IRON_AXE)
    axe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DIG_SPEED, 2) // Efficiency II
    axe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 3) // Unbreaking III

    val shovel = ItemStack(Material.IRON_SPADE)
    shovel.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DIG_SPEED, 2) // Efficiency II
    shovel.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 3) // Unbreaking III

    inventaire.addItem(pickaxe)
    inventaire.addItem(axe)
    inventaire.addItem(shovel)

    return inventaire
}