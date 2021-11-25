package gg.scala.mango.runnable

import gg.scala.mango.MangoStandaloneApplication
import kotlin.properties.Delegates

/**
 * @author GrowlyX
 * @since 11/23/2021
 */
object DisplayNameUpdater : Thread()
{
    private val client = MangoStandaloneApplication.client

    private val mappings = mutableMapOf(
        883839967049154560L to "PA",
        883839977979523212L to "HA",
        883839990226898964L to "SA",
        883840011781419048L to "SrMod",
    )

    private val exempt = mutableListOf(
        890049538784641064L,
        907340064927907863L,
        890049499177812059L,
    )

    override fun run()
    {
        try
        {
            val guild = client.getGuildById(883838987901468742L)!!
            val members = guild.loadMembers().get()

            for (member in members)
            {
                val displayRole = member.roles
                    .maxByOrNull { it.positionRaw } ?: continue

                if (displayRole == guild.publicRole)
                {
                    continue
                }

                if (exempt.contains(displayRole.idLong))
                {
                    continue
                }

                val formatted = "[${
                    mappings[displayRole.idLong] ?: displayRole.name
                }] ${member.user.name}"

                if (member.effectiveName == formatted)
                {
                    continue
                }

                member.modifyNickname(formatted).queue()
            }
        } catch (exception: Exception)
        {
            exception.printStackTrace()
        }

        try
        {
            sleep(2000L)
        } catch (exception: Exception)
        {
            exception.printStackTrace()
        }
    }
}
