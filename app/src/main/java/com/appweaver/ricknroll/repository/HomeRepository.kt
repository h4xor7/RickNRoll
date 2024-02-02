package com.appweaver.ricknroll.repository

import com.appweaver.ricknroll.model.CharacterResponse
import com.appweaver.ricknroll.remote.RickNRollApi
import com.appweaver.ricknroll.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val api: RickNRollApi) {

    suspend fun getCharacters(page: Int): Resource<CharacterResponse> {
        val response = try {
            api.getCharacters(page)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}



