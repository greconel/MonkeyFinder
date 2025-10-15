package be.ucll.monkeyfinder.services

import be.ucll.monkeyfinder.models.Monkey
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL =
    "https://monkeyfinderapi20240912162421.azurewebsites.net/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface MonkeyApiService {
    @GET("monkeys")
    suspend fun getMonkeys(): List<Monkey>

    @GET("monkeys/{id}")
    suspend fun getMonkey(@Path("id") monkeyId: Int): Monkey
}

object MonkeyApi {
    val retrofitService : MonkeyApiService by lazy {
        retrofit.create(MonkeyApiService::class.java)
    }
}