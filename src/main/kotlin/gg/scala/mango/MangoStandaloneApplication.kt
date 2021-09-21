package gg.scala.mango

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import gg.scala.mango.command.MangoAdminCommand
import gg.scala.mango.listener.UserListener
import com.xenomachina.argparser.ArgParser
import gg.scala.mango.cache.ExtendedTicketCache
import gg.scala.mango.cache.LocaleCache
import gg.scala.mango.runnable.StatusRunnable
import gg.scala.mango.util.MangoStatus
import io.github.revxrsal.cub.jda.JDACommandHandler
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
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
    lateinit var commandHandler: JDACommandHandler

    lateinit var settings: MangoArgs

    @JvmStatic
    fun main(args: Array<String>) {
        ArgParser(args).parseInto(::MangoArgs).let { mangoArgs ->
            client = JDABuilder.createDefault(mangoArgs.token)
                .addEventListeners(
                    UserListener
                )
                .build()

            LocaleCache.init()

            client.awaitReady().let {
                commandHandler = JDACommandHandler.create(
                    it, JDACommandHandler.Settings.builder()
                        .prefix("-")
                        .stripMarkdown(false)
                        .build()
                )

                commandHandler.registerCommand(
                    MangoAdminCommand()
                ); settings = mangoArgs

                val executor = Executors.newScheduledThreadPool(1)
                executor.schedule(StatusRunnable, 5L, TimeUnit.SECONDS)
            }
        }
    }
}

class MangoArgs(parser: ArgParser) {
    val token by parser.storing("--token", help = "Discord OAuth Token")

    val primary by parser.storing("--primary", help = "Primary Hex Color")
    val secondary by parser.storing("--secondary", help = "Secondary Hex Color")

    val discordName by parser.storing("--name", help = "Discord Name")
    val guild by parser.storing("--guild", help = "Discord Primary Guild")
    val supportRoles by parser.storing("--supportRoles", help = "Discord Primary Guild")
}

