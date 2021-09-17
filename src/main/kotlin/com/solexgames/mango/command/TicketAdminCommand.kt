package com.solexgames.mango.command

import com.solexgames.mango.util.MangoUtil.getDefaultEmbed
import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.annotation.Description
import io.github.revxrsal.cub.jda.JDACommandContext
import io.github.revxrsal.cub.jda.JDACommandSubject
import io.github.revxrsal.cub.jda.annotation.OwnerOnly
import net.dv8tion.jda.api.entities.Emoji
import net.dv8tion.jda.api.interactions.components.Button
import net.dv8tion.jda.api.interactions.components.ButtonStyle

/**
 * @author GrowlyX
 * @since 9/17/2021
 */
class TicketAdminCommand {

    @OwnerOnly
    @Command("ticket-panel")
    @Description("Dispatches the ticket creation panel.")
    fun onTicketPanel(context: JDACommandSubject) {
        context.parentEvent.channel.sendMessage(
            getDefaultEmbed(
                title = "**Create a Ticket**",
                "Have a question? Want to talk to us privately?",
                "",
                "*React with the envelope to create a new support ticket.*",
                "*Abuse of the ticket system will result in a punishment.*"

            ).build()
        ).setActionRow(
            Button.primary("create", "Click to Create").withStyle(ButtonStyle.SUCCESS).withEmoji(Emoji.fromUnicode("✉"))
        ).queue()
    }

}
