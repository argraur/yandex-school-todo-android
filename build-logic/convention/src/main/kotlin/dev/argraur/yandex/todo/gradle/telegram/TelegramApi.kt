package dev.argraur.yandex.todo.gradle.telegram

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.TelegramFile
import java.io.File

class TelegramApi(
    private val token: String,
    private val chatId: String
) {
    private val bot by lazy {
        bot {
            token = this@TelegramApi.token
        }
    }

    private val channel by lazy {
        ChatId.fromChannelUsername(chatId)
    }

    fun sendMessage(message: String) {
        bot.sendMessage(channel, message)
    }

    fun sendFile(file: File, fileName: String, caption: String) {
        bot.sendDocument(channel, TelegramFile.ByByteArray(file.readBytes(), fileName), caption = caption, parseMode = ParseMode.MARKDOWN)
    }
}