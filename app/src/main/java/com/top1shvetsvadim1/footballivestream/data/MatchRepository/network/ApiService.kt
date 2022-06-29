package com.top1shvetsvadim1.footballivestream.data.MatchRepository.network

import com.top1shvetsvadim1.footballivestream.domain.InfoFootball.ListOfMatches
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("feed/")
    suspend fun getListOfMatch(
        @Query("token") token : String = TOKEN
    ) : ListOfMatches

    companion object{
        private const val TOKEN = "MjA5NzJfMTY1NTgwMTkzNF8xYjc5NjhkN2U4MTU3YjVlZDE0YmEzNjhiYmRmNGQ0OWZiNWM0OWZj"
    }
}