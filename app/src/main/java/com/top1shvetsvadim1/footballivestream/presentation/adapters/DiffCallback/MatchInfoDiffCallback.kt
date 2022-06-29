package com.top1shvetsvadim1.footballivestream.presentation.adapters.DiffCallback

import androidx.recyclerview.widget.DiffUtil
import com.top1shvetsvadim1.footballivestream.domain.InfoFootball.MatchInformation

object MatchInfoDiffCallback : DiffUtil.ItemCallback<MatchInformation>(){
    override fun areItemsTheSame(oldItem: MatchInformation, newItem: MatchInformation): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: MatchInformation, newItem: MatchInformation): Boolean {
        return oldItem == newItem
    }
}