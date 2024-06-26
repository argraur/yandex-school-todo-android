package dev.argraur.yandex.todo.core.extensions

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun LocalDate.localizedFormat(): String {
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
    return toJavaLocalDate().format(dateTimeFormatter)
}