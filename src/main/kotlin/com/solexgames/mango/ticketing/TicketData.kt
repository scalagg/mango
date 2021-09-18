package com.solexgames.mango.ticketing

import com.solexgames.mango.locale.Locale
import net.dv8tion.jda.api.entities.TextChannel

/**
 * @author GrowlyX
 * @since 7/14/2021
 */
data class TicketData(
    val metaData: Map<String, String> = mutableMapOf(),
    val responses: Map<TicketDataStage, String> = mutableMapOf(),
) {
    var channel: TextChannel? = null
    val locale: Locale? = null

    val stage: TicketDataStage = TicketDataStage.FIRST
}
