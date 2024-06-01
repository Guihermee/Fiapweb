package br.com.fiap.fiapweb.utils

import androidx.room.TypeConverter
import br.com.fiap.fiapweb.model.Email
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromEmailList(value: List<Email>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Email>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toEmailList(value: String): List<Email> {
        val gson = Gson()
        val type = object : TypeToken<List<Email>>() {}.type
        return gson.fromJson(value, type)
    }
}
