package com.top1shvetsvadim1.footballivestream.domain.InfoFootball

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListOfMatches(
    @SerializedName("response")
    @Expose
    val listOfMatches : List<MatchInformation>
)
