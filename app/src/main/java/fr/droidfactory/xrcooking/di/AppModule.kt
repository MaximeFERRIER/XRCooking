package fr.droidfactory.xrcooking.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.droidfactory.xrcooking.BuildConfig
import fr.droidfactory.xrcooking.data.MealDbService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AppModule {

    companion object {
        @Provides
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .readTimeout(4L, TimeUnit.SECONDS)
            .apply {
                if(BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    )
                }
            }.build()

        @Provides
        fun provideRetrofitClient(
            okHttpClient: OkHttpClient
        ): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

        @Provides
        fun provideProductsService(
            retrofit: Retrofit
        ): MealDbService = retrofit.create(MealDbService::class.java)
    }
}