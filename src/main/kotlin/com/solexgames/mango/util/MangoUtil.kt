package com.solexgames.mango.util

import com.solexgames.mango.MangoStandaloneApplication
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color

/**
 * @author GrowlyX
 * @since 9/17/2021
 */
object MangoUtil {

    @JvmStatic
    fun getDefaultEmbed(title: String, vararg description: String): EmbedBuilder {
        return EmbedBuilder()
            .setTitle(title).setDescription(description.joinToString(separator = "\n"))
            .setColor(Color.decode(MangoStandaloneApplication.settings.primary))
    }
}
