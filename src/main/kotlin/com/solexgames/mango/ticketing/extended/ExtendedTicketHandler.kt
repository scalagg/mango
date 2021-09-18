package com.solexgames.mango.ticketing.extended

import com.solexgames.mango.ticketing.TicketData
import net.dv8tion.jda.api.entities.Member

/**
 * @author GrowlyX
 * @since 7/20/2021
 */
interface ExtendedTicketHandler {

    fun identifier(): String

    fun handle(): (Member, TicketData) -> Unit

}
