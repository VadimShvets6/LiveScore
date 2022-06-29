package com.top1shvetsvadim1.footballivestream.domain.InfoFootball

interface MatchRepository {
    suspend fun downloadingMatchData() : List<MatchInformation>
}