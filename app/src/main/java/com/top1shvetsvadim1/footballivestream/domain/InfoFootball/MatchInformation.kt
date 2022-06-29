package com.top1shvetsvadim1.footballivestream.domain.InfoFootball

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MatchInformation(
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("competition")
    @Expose
    val competition : String,
    @SerializedName("matchviewUrl")
    @Expose
    val matchviewUrl : String,
    @SerializedName("competitionUrl")
    @Expose
    val competitionUrl : String,
    @SerializedName("thumbnail")
    @Expose
    val thumbnail : String,
    @SerializedName("date")
    @Expose
    val date : String,
)
