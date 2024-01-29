package com.appweaver.ricknroll.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}



