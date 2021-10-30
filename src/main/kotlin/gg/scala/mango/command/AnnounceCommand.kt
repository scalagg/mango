package gg.scala.mango.command

import gg.scala.mango.util.MangoUtil.getDefaultEmbed
import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.jda.JDACommandSubject
import io.github.revxrsal.cub.jda.annotation.RolePermission
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import java.util.concurrent.TimeUnit


/**
 * @author GrowlyX
 * @since 9/17/2021
 */

class AnnounceCommand {

    @RolePermission(
        names = ["Owner"]
    )
    @Command(
        "announcement",
        aliases = ["announce"]
    )
    fun onTicketPanel(context: JDACommandSubject, message: String) {
        val broadcast = message.endsWith(" -a")

        val embedBuilder = EmbedBuilder()
        embedBuilder.setColor(Color.CYAN)
        embedBuilder.setTitle("Announcement")
        embedBuilder.setFooter(context.asMember().effectiveName, context.asUser().avatarUrl)
        embedBuilder.setDescription("${
            if (broadcast)
            {
                context.parentEvent.guild.publicRole.asMention
            } else
            {
                ""
            }
        } ${message.removeSuffix(" -a")}")

        context.parentEvent.channel.sendMessage(embedBuilder.build()).queue()

        if (broadcast)
        {
            context.parentEvent.channel.sendMessage(
                context.parentEvent.guild.publicRole.asMention
            ).queue { toDelete ->
                toDelete.delete().queueAfter(10L, TimeUnit.MILLISECONDS)
            }
        }
    }

}
