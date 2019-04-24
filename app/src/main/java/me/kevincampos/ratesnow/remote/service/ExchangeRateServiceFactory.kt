package me.kevincampos.ratesnow.remote.service

import com.google.gson.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import me.kevincampos.ratesnow.remote.model.RateResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


object ExchangeRateServiceFactory {

    fun makeExchangeRateService(isDebug: Boolean): ExchangeRateService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor((isDebug))
        )

        val gson = GsonBuilder()
            .setLenient()
            .registerTypeAdapter(
                ExchangeRateService.RatesResponse::class.java,
                RatesResponseDeserializer()
            )
            .create()

        return makeExchangeRateService(
            okHttpClient,
            gson
        )
    }

    class RatesResponseDeserializer : JsonDeserializer<ExchangeRateService.RatesResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): ExchangeRateService.RatesResponse {
            val rawRatesResponse = json.asJsonObject
            val ratesJson = rawRatesResponse.getAsJsonObject("rates")
            val keySet = ratesJson.keySet()
            val ratesResponse: List<RateResponse> = keySet.map {
                RateResponse(it, ratesJson.get(it).asFloat)
            }
            return ExchangeRateService.RatesResponse(ratesResponse)
        }
    }

    private fun makeExchangeRateService(okHttpClient: OkHttpClient, gson: Gson): ExchangeRateService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://revolut.duckdns.org/")
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ExchangeRateService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BASIC
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

}