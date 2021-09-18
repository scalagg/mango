package gg.scala.mango.listener

import gg.scala.mango.MangoStandaloneApplication
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

/**
 * @author GrowlyX
 * @since 9/17/2021
 */
object UserListener : ListenerAdapter() {

    private val memberCodeMap = hashMapOf<Long, String>()

    override fun onPrivateMessageReceived(event: PrivateMessageReceivedEvent) {
        val rawMessage = event.message.contentRaw
        val code = memberCodeMap[event.author.idLong]

        val privateChannel = event.channel
        val guild = event.jda.getGuildById(MangoStandaloneApplication.settings.guild)

        if (rawMessage == code) {
            val role = guild!!.getRolesByName("Player", false)[0]

            if (role != null) {
                guild.addRoleToMember(event.author.idLong, role).queue()
                memberCodeMap.remove(event.author.idLong)

                privateChannel.sendMessage("You've been verified in the **${MangoStandaloneApplication.settings.secondary} Discord**, have fun!").queue()
            } else {
                privateChannel.sendMessage("Something went wrong whilst verifying you.").queue()
            }
        }
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val size = event.message.mentionedUsers.size

        if (size >= 10) {
            val role = event.guild.getRolesByName("Muted", false)[0]
            val playerRole = event.guild.getRolesByName("Player", false)[0]

            event.message.delete().queue()

            if (role != null && playerRole != null) {
                event.guild.addRoleToMember(event.member!!, role).queue {
                    event.channel.sendMessage(
                        event.member!!.asMention + " has been permanently muted for `pinging more than ten users`."
                    ).queue()
                }
            }
        }
    }

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        val channel = event.guild.getTextChannelsByName("arrivals", true)[0]
        channel?.sendMessage("Welcome to the ${MangoStandaloneApplication.settings.discordName} Discord Server, " + event.member.asMention + "! \uD83D\uDC4B")
            ?.queue()
    }

    override fun onButtonClick(event: ButtonClickEvent) {
        val role = event.guild!!.getRolesByName("Player", false)[0]
        val muted = event.guild!!.getRolesByName("Muted", false)[0]

        if (role == null || muted == null) {
            return
        }

        if (event.member!!.roles.contains(role) || event.member!!.roles.contains(muted)) {
            return
        }

        if (memberCodeMap.containsKey(event.member!!.idLong)) {
            event.channel.sendMessage(event.member!!.asMention + ", you were sent a message by our bot. Check your direct messages for the code.")
                .queue { message: Message ->
                    message.delete().queueAfter(2L, TimeUnit.SECONDS)
                }
            return
        }

        event.member!!.user.openPrivateChannel().submit().whenComplete { privateChannel, _ ->
            val generatedCode = getRandomString(10)

            try {
                privateChannel.sendMessage(
                    EmbedBuilder()
                        .setTitle("**Verification**")
                        .setColor(Color.GREEN)
                        .setDescription(
                            """
                                Please reply with the following code to become verified in the ${MangoStandaloneApplication.settings.discordName} Discord Server:
                                
                                Verification Code: `$generatedCode`
                            """.trimIndent()
                        )
                        .build()
                ).queue()

                memberCodeMap[event.member!!.idLong] = generatedCode
            } catch (ignored: Exception) {
                event.channel.sendMessage(
                    event.member!!
                        .asMention + ", your messages are disabled."
                ).queue { message: Message ->
                    message.delete().queueAfter(2L, TimeUnit.SECONDS)
                }
            }
        }
    }

    fun getRandomString(size: Int): String {
        val saltedString = StringBuilder()

        while (saltedString.length < size) {
            val index = (ThreadLocalRandom.current()
                .nextFloat() * "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".length.toFloat()).toInt()
            saltedString.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"[index])
        }

        return saltedString.toString()
    }
}
