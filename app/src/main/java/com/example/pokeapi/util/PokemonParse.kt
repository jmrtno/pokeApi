package com.example.pokeapi.util

import androidx.compose.ui.graphics.Color
import com.example.pokeapi.data.remote.responses.Stat
import com.example.pokeapi.data.remote.responses.Type
import com.example.pokeapi.ui.theme.AtkColor
import com.example.pokeapi.ui.theme.DefColor
import com.example.pokeapi.ui.theme.HPColor
import com.example.pokeapi.ui.theme.SpAtkColor
import com.example.pokeapi.ui.theme.SpDefColor
import com.example.pokeapi.ui.theme.SpdColor
import com.example.pokeapi.ui.theme.TypeBug
import com.example.pokeapi.ui.theme.TypeDark
import com.example.pokeapi.ui.theme.TypeDragon
import com.example.pokeapi.ui.theme.TypeElectric
import com.example.pokeapi.ui.theme.TypeFairy
import com.example.pokeapi.ui.theme.TypeFighting
import com.example.pokeapi.ui.theme.TypeFire
import com.example.pokeapi.ui.theme.TypeFlying
import com.example.pokeapi.ui.theme.TypeGhost
import com.example.pokeapi.ui.theme.TypeGrass
import com.example.pokeapi.ui.theme.TypeGround
import com.example.pokeapi.ui.theme.TypeIce
import com.example.pokeapi.ui.theme.TypeNormal
import com.example.pokeapi.ui.theme.TypePoison
import com.example.pokeapi.ui.theme.TypePsychic
import com.example.pokeapi.ui.theme.TypeRock
import com.example.pokeapi.ui.theme.TypeSteel
import com.example.pokeapi.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: Type): Color {
   return when(type.type.name.lowercase(Locale.ROOT)) {
      "normal" -> TypeNormal
      "fire" -> TypeFire
      "water" -> TypeWater
      "electric" -> TypeElectric
      "grass" -> TypeGrass
      "ice" -> TypeIce
      "fighting" -> TypeFighting
      "poison" -> TypePoison
      "ground" -> TypeGround
      "flying" -> TypeFlying
      "psychic" -> TypePsychic
      "bug" -> TypeBug
      "rock" -> TypeRock
      "ghost" -> TypeGhost
      "dragon" -> TypeDragon
      "dark" -> TypeDark
      "steel" -> TypeSteel
      "fairy" -> TypeFairy
      else -> Color.Black
   }
}

fun parseStatToColor(stat: Stat): Color {
   return when(stat.stat.name.lowercase(Locale.ROOT)) {
      "hp" -> HPColor
      "attack" -> AtkColor
      "defense" -> DefColor
      "special-attack" -> SpAtkColor
      "special-defense" -> SpDefColor
      "speed" -> SpdColor
      else -> Color.White
   }
}

fun parseStatToAbbr(stat: Stat): String {
   return when(stat.stat.name.lowercase(Locale.ROOT)) {
      "hp" -> "HP"
      "attack" -> "Atk"
      "defense" -> "Def"
      "special-attack" -> "SpAtk"
      "special-defense" -> "SpDef"
      "speed" -> "Spd"
      else -> ""
   }
}