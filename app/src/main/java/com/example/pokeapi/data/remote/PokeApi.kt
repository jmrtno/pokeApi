package com.example.pokeapi.data.remote

import com.example.pokeapi.data.remote.responses.Pokemon
import com.example.pokeapi.data.remote.responses.PokemonList
import retrofit2.http.*

interface PokeApi {

   @GET("pokemon")
   suspend fun getPokemonList(
      @Query("limit") limit: Int,
      @Query("offset") offset: Int
   ): PokemonList

   @GET("pokemon/{name}")
   suspend fun getPokemonInfo(
      @Path("name") name: String
   ): Pokemon
}