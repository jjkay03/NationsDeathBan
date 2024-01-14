package com.jjkay03.nationsdeathban

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.World
import org.bukkit.entity.Player
import java.util.logging.Logger

class NationsDeathBan : JavaPlugin(), Listener {

    companion object {
        lateinit var instance: NationsDeathBan; private set // Define a companion object to hold the instance
        lateinit var logger: Logger; private set // Define a companion object to hold logger
        var LIGHTNING_ON_DEATH = true
        var BAN_MESSAGE = ""

    }

    // Plugin startup logic
    override fun onEnable() {
        instance = this

        // Register event listener
        server.pluginManager.registerEvents(this, this)

        // Startup info
        logger.info("NationsDeathBan is running!")
        logger.info("Plugin version: ${description.version}")

        // Config stuff
        saveDefaultConfig() // Save the default configuration if it doesn't exist
        reloadConfig() // Reload the configuration

        // Get config settings
        LIGHTNING_ON_DEATH = config.getBoolean("lightning-on-death", true)
        BAN_MESSAGE =config.getString("ban-message") ?: ""
    }

    // Plugin shutdown logic
    override fun onDisable() {

    }

    // Event handler for player death
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val deadPlayer = event.entity
        val location = deadPlayer.location
        val cause = event.entity.lastDamageCause

        // Get the player receiving the death message
        val receivingPlayer = event.entity
        val receivingPlayerName = receivingPlayer.name

        // Set a custom death message based on the cause
        val deathMessage = when {
            cause is org.bukkit.event.entity.EntityDamageByEntityEvent -> {
                "§4☠ §c$receivingPlayerName §4was killed"
            }
            else -> "§4☠ §c$receivingPlayerName §4died"
        }

        // Send a message to players with "nations.deathban.viewkiller" permission
        val killerName = (cause as? org.bukkit.event.entity.EntityDamageByEntityEvent)?.damager
            .let { it as? Player }?.name ?: "someone"

        Bukkit.getOnlinePlayers().filter { it.hasPermission("nations.deathban.viewkiller") }
            .forEach { player ->
                player.sendMessage("§7ℹ $receivingPlayerName was killed by $killerName")
            }

        event.deathMessage = deathMessage

        // Check if the player has the bypass permission
        if (!deadPlayer.hasPermission("nations.deathban.bypass")) {
            // Ban the player with the specified reason
            deadPlayer.banPlayer(BAN_MESSAGE)

            // Play the sound to all players on the server
            Bukkit.getOnlinePlayers().forEach { player ->
                player.playSound(player.location, Sound.AMBIENT_BASALT_DELTAS_MOOD, 1.0f, 1.0f)
            }

            // Summon lightning at the location of the player's death
            if (LIGHTNING_ON_DEATH) {
                val world: World = location.world
                world.strikeLightningEffect(location)
            }
        }
    }
}
