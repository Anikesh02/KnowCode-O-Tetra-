package com.example.eldcare.controllers.apis

import android.util.Log
import com.example.eldcare.models.ChatRequest
import com.example.eldcare.models.ChatResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:5000/chat/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(ChatAPI::class.java)

fun getResponse(user_input: String, callback: (String?) -> Unit) {
    val chatRequest = ChatRequest(user_input)
    val call = api.getChat(user_input)

    call.enqueue(object : Callback<ChatResponse> {
        override fun onResponse(call: Call<String>, response: Response<ChatResponse>) {
            if (response.isSuccessful) {
                val chatResponse = response.body()
                if (chatResponse != null) {
                    Log.d("HTTPSuccess", chatResponse.chat)
                    callback(chatResponse.chat)
                }
            } else {
                Log.d("HTTPFail", "Response not successful")
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            Log.d("HTTPFail", t.toString())
        }
    })
}