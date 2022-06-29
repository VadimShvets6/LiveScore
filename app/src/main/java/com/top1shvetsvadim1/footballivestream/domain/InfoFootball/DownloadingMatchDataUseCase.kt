package com.top1shvetsvadim1.footballivestream.domain.InfoFootball

class DownloadingMatchDataUseCase(
    private val repository: MatchRepository
) {
    suspend operator fun invoke() = repository.downloadingMatchData()
}