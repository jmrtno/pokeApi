package com.example.pokeapi.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokeapi.data.models.PokedexListEntry
import com.example.pokeapi.repository.PokemonRepository
import com.example.pokeapi.util.Constants.PAGE_SIZE
import com.example.pokeapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
   private val repository: PokemonRepository
): ViewModel() {

   private var curPage = 0

   var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
   var loadError = mutableStateOf("")
   var isLoading = mutableStateOf(false)
   var endReached = mutableStateOf(false)

   private var cachedPokemonList = listOf<PokedexListEntry>()
   private var isSearchStarting = true // solo cuando la searchbar esta vacía
   var isSearching = mutableStateOf(false)

   init {
      loadPokemonPaginated()
   }

   fun searchPokemonList(query: String) {
      val listToSearch = if(isSearchStarting) {
         pokemonList.value
      } else {
         cachedPokemonList
      }
      viewModelScope.launch(Dispatchers.Default) {
         if(query.isEmpty()) {
            pokemonList.value = cachedPokemonList
            isSearching.value = false
            isSearchStarting = true
            return@launch
         }
         val results = listToSearch.filter {
            it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                    it.number.toString() == query.trim()
         }
         if(isSearchStarting) {
            cachedPokemonList = pokemonList.value
            isSearchStarting = false
         }
         pokemonList.value = results
         isSearching.value = true
      }
   }

   fun loadPokemonPaginated() {
      viewModelScope.launch {
         isLoading.value = true
         when(val result = repository.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)){
            is Resource.Success -> {
               endReached.value = curPage * PAGE_SIZE >= result.data!!.count
               Timber.tag("loadPokemonPaginated").d("called loadPokemonPaginated")
               val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                  val number = if(entry.url.endsWith("/")) {             // en la url de los pokemon elimina "/" y coge los
                     entry.url.dropLast(1).takeLastWhile { it.isDigit()  }  // siguientes elementos siempre y cuando sean numeros
                  } else {
                     entry.url.takeLastWhile { it.isDigit()  }
                  }
                  val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                  PokedexListEntry(entry.name.replaceFirstChar {
                     if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                     ) else it.toString()
                  }, url, number.toInt())
               }
               curPage++

               loadError.value = ""
               isLoading.value = false
               pokemonList.value += pokedexEntries
            }
            is Resource.Error -> {
               loadError.value = result.message!!
               isLoading.value = false
            }

            is Resource.Loading -> TODO()
         }
      }
   }

   fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
      val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

      Palette.from(bmp).generate { palette ->
         palette?.dominantSwatch?.rgb?.let { colorValue ->
            onFinish(Color(colorValue))
         }
      }
   }
}