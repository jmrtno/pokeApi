package com.example.pokeapi.di

import com.example.pokeapi.data.remote.PokeApi
import com.example.pokeapi.repository.PokemonRepository
import com.example.pokeapi.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

   @Singleton
   @Provides
   fun providePokemonRepository(
      api: PokeApi
   ) = PokemonRepository(api)

   @Singleton
   @Provides
   fun providePokeApi(): PokeApi {
      return Retrofit.Builder()
         .addConverterFactory(GsonConverterFactory.create())
         .baseUrl(BASE_URL)
         .build()
         .create(PokeApi:: class.java)
   }
}