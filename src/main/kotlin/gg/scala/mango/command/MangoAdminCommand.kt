package gg.scala.mango.command

import gg.scala.mango.util.MangoUtil.getDefaultEmbed
import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.annotation.Description
import io.github.revxrsal.cub.annotation.Subcommand
import io.github.revxrsal.cub.jda.JDACommandSubject
import io.github.revxrsal.cub.jda.annotation.OwnerOnly
import io.github.revxrsal.cub.jda.annotation.RolePermission
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

    @RolePermission(
        names = ["Owner"]
    )
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

}
