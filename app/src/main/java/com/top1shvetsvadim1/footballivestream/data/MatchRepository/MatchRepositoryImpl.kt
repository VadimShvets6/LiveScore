package com.top1shvetsvadim1.footballivestream.data.MatchRepository

import com.top1shvetsvadim1.footballivestream.data.MatchRepository.network.ApiFactory
import com.top1shvetsvadim1.footballivestream.domain.InfoFootball.MatchInformation
import com.top1shvetsvadim1.footballivestream.domain.InfoFootball.MatchRepository

object MatchRepositoryImpl : MatchRepository{

    private val apiService = ApiFactory.apiService

    override suspend fun downloadingMatchData(): List<MatchInformation> {
        return apiService.getListOfMatch().listOfMatches
    }
}