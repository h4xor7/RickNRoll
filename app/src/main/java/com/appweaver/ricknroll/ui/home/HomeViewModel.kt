package com.appweaver.ricknroll.ui.home

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.appweaver.ricknroll.model.CharacterResponse
import com.appweaver.ricknroll.repository.HomeRepository
import com.appweaver.ricknroll.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    var characterList = mutableStateOf<List<CharacterResponse>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadCharacters()
    }


    fun loadCharacters() {
        viewModelScope.launch {
            isLoading.value = true
            val result = homeRepository.getCharacters()
            when (result) {
                is Resource.Success -> {
                    isLoading.value = false
                    characterList.value += result.data!!
                    loadError.value = ""
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else -> {
                    loadError.value = "Unknown error"
                    isLoading.value = false
                }
            }

        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch ?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}



