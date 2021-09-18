package com.solexgames.mango.ticketing.extended.impl

import com.solexgames.mango.ticketing.TicketData
import com.solexgames.mango.ticketing.extended.ExtendedTicketHandler
import com.solexgames.mango.util.MangoUtil.sendMessage
import net.dv8tion.jda.api.entities.Member

/**
 * @author GrowlyX
 * @since 7/20/2021
 */
class TransactionTicketHandler : ExtendedTicketHandler {

    override fun identifier(): String {
        return "Payment Support"
    }

    override fun handle(): (Member, TicketData) -> Unit {
        return { _, ticketData ->
            sendMessage(
                ticketData.channel!!,
                "**Payment Support**",
                "This ticket has been marked as a payment support ticket.",
                "",
                "> If you are having issues processing your store payment, tell us what's wrong.",
                "> If you want to receive your in-game rewards, please provide your PayPal transaction ID."
            )
        }
    }
}
