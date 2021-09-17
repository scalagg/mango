package com.solexgames.mango

import com.solexgames.mango.command.TicketAdminCommand
import com.xenomachina.argparser.ArgParser
import io.github.revxrsal.cub.jda.JDACommandHandler
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder


/**
 * @author GrowlyX
 * @since 9/17/2021
 */
object MangoStandaloneApplication {

    lateinit var client: JDA
    lateinit var commandHandler: JDACommandHandler

    lateinit var settings: MangoArgs

    @JvmStatic
    fun main(args: Array<String>) {
        ArgParser(args).parseInto(::MangoArgs).let { mangoArgs ->
            this.client = JDABuilder.createDefault(mangoArgs.token)
                .addEventListeners()
                .build()

            this.client.awaitReady().let {
                commandHandler = JDACommandHandler.create(
                    it, JDACommandHandler.Settings.builder()
                        .prefix("-")
                        .stripMarkdown(false)
                        .build()
                )

                commandHandler.registerCommand(
                    TicketAdminCommand()
                ); settings = mangoArgs
            }
        }
    }
}

class MangoArgs(parser: ArgParser) {
    val token by parser.storing("--token", help = "Discord OAuth Token")

    val primary by parser.storing("--primary", help = "Primary Hex Color")
    val secondary by parser.storing("--secondary", help = "Secondary Hex Color")

    val discordName by parser.storing("--name", help = "Discord Name")
}

