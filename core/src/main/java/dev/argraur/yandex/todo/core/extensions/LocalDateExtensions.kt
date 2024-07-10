package dev.argraur.yandex.todo.core.extensions

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

const val millisToDaysRatio: Int = 1000 * 60 * 60 * 24

fun LocalDate.localizedFormat(): String {
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
    return toJavaLocalDate().format(dateTimeFormatter)
}

fun LocalDate.Companion.fromEpochMillis(epochMillis: Long): LocalDate {
    return fromEpochDays((epochMillis / millisToDaysRatio).toInt())
}

fun LocalDate.toEpochMillis(): Long {
    return toEpochDays() * millisToDaysRatio.toLong()
}