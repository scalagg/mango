package gg.scala.mango.command

import gg.scala.mango.MangoStandaloneApplication
import gg.scala.mango.util.MangoUtil.getDefaultEmbed
import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.annotation.Description
import io.github.revxrsal.cub.annotation.Subcommand
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
@Command(
    "mango",
    aliases = ["magconf", "mangoconfig", "mangoadmin"]
)
class MangoAdminCommand {

    @OwnerOnly
    @Subcommand("ticket-panel")
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
            Button.primary("create", "Click to Create")
                .withStyle(ButtonStyle.SUCCESS)
                .withEmoji(Emoji.fromUnicode("✉"))
        ).queue()
    }

    @Subcommand("verify-panel")
    @Description("Dispatches the server verification panel.")
    fun onVerifyPanel(context: JDACommandSubject) {
        context.parentEvent.channel.sendMessage("**Verification**\n" +
                "You'll receive a private message from us asking you to reply with a randomly generated code once you've clicked the provided button.\n" +
                "\n" +
                "**I didn't receive any dm, what do I do?**\n" +
                "Please double-check your privacy settings and make sure you're allowing messages from server members. Once you've enabled them, rejoin our discord server to click the button again.\n" +
                "\n" +
                "Having trouble verifying? Create a ticket in #support." +
                ""
        ).setActionRow(
            Button.primary("verify", "Click to Verify")
                .withStyle(ButtonStyle.SUCCESS)
                .withEmoji(Emoji.fromUnicode("✅"))
        ).queue();
    }

}
