package com.solexgames.mango.cache

import com.solexgames.mango.ticketing.extended.ExtendedTicketHandler
import com.solexgames.mango.ticketing.extended.impl.PunishmentTicketHandler
import com.solexgames.mango.ticketing.extended.impl.TransactionTicketHandler

/**
 * @author GrowlyX
 * @since 7/20/2021
 */
object ExtendedTicketCache {

    val LOCALES: MutableMap<String, ExtendedTicketHandler> = mutableMapOf()

    fun fetchHandler(name: String): ExtendedTicketHandler? {
        return LOCALES.values.firstOrNull { it.identifier().equals(name, true) }
    }

    init {
        LOCALES["Punishment Appeal"] = PunishmentTicketHandler()
        LOCALES["Payment Support"] = TransactionTicketHandler()
    }
}
