package com.example.pokeapi.pokemondetail

import androidx.lifecycle.ViewModel
import com.example.pokeapi.data.remote.responses.Pokemon
import com.example.pokeapi.repository.PokemonRepository
import com.example.pokeapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
   private val repository: PokemonRepository
) : ViewModel() {

   suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
      return repository.getPokemonInfo(pokemonName)
   }
}