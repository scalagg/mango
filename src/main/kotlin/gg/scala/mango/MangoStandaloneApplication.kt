package gg.scala.mango

import com.google.common.io.Files
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import gg.scala.mango.command.MangoAdminCommand
import gg.scala.mango.listener.UserListener
import com.xenomachina.argparser.ArgParser
import gg.scala.mango.cache.ExtendedTicketCache
import gg.scala.mango.cache.LocaleCache
import gg.scala.mango.command.CloseCommand
import gg.scala.mango.runnable.StatusRunnable
import gg.scala.mango.util.MangoStatus
import io.github.revxrsal.cub.jda.JDACommandHandler
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @author GrowlyX
 * @since 9/17/2021
 */
object MangoStandaloneApplication {

    val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .setLongSerializationPolicy(LongSerializationPolicy.STRING)
        .create()

    lateinit var client: JDA
    private lateinit var commandHandler: JDACommandHandler

    lateinit var settings: MangoArgs

    @JvmStatic
    fun main(args: Array<String>) {
        settings = gson.fromJson(
            Files.newReader(
                File("settings.json"), Charsets.UTF_8
            ),
            MangoArgs::class.java
        )

        client = JDABuilder.createDefault(settings.token)
            .enableIntents(
                GatewayIntent.GUILD_BANS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.DIRECT_MESSAGES
            )
            .addEventListeners(
                UserListener
            ).build()

        LocaleCache.init()

        client.awaitReady().let {
            commandHandler = JDACommandHandler.create(
                it, JDACommandHandler.Settings.builder()
                    .prefix("-")
                    .stripMarkdown(false)
                    .build()
            )

            commandHandler.registerCommand(
                MangoAdminCommand(),
                CloseCommand()
            );

            val timer = Timer()
            timer.schedule(StatusRunnable, 5000L)
        }
    }
}

class MangoArgs(
    val token: String = "",

    val primary: String = "#ffbc00",
    val secondary: String = "#ffbc00",

    val discordName: String = "PvPBar",
    val guild: String = "883838987901468742",
    val supportRoles: String = "Platform Admin,Head Admin,Admin,Senior Mod,Developer"
)
