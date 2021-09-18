package com.solexgames.mango.cache

import com.solexgames.mango.locale.Locale
import com.solexgames.mango.locale.impl.EnglishLocale
import com.solexgames.mango.locale.impl.FrenchLocale
import com.solexgames.mango.locale.impl.ItalianLocale
import com.solexgames.mango.locale.impl.SpanishLocale
import java.util.function.Predicate

/**
 * @author GrowlyX
 * @since 7/14/2021
 */
object LocaleCache {

    private val LOCALES: MutableMap<String, Locale> = mutableMapOf()

    val DEF: EnglishLocale = EnglishLocale()

    fun init() {
        listOf(DEF, ItalianLocale(), SpanishLocale(), FrenchLocale()).forEach {
            it.init(); LOCALES[it.id] = it
        }
    }

    fun id(id: String): Locale? {
        return LOCALES.values.firstOrNull { it.id.equals(id, true) }
    }
}
