package br.com.fiap.fiapweb.service

import Message
import OpenAIRequest
import RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getOpenAICompletion(prompt: String, model: String = "gpt-3.5-turbo"): String {
    return withContext(Dispatchers.IO) {
        try {
            val request = OpenAIRequest(
                model = model,
                messages = listOf(Message(role = "user", content = prompt)),
                max_tokens = 200,
                temperature = 0.7
            )
            val response = RetrofitInstance.api.getCompletion(request)
            response.choices.firstOrNull()?.message?.content ?: "No response"
        } catch (e: Exception) {
            e.printStackTrace()
            "Error: ${e.message}"
        }
    }
}