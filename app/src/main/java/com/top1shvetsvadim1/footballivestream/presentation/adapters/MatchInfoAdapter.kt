package com.top1shvetsvadim1.footballivestream.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.top1shvetsvadim1.footballivestream.databinding.ItemMatchBinding
import com.top1shvetsvadim1.footballivestream.domain.InfoFootball.MatchInformation
import com.top1shvetsvadim1.footballivestream.presentation.adapters.DiffCallback.MatchInfoDiffCallback
import com.top1shvetsvadim1.footballivestream.presentation.adapters.ViewHolders.MatchInfoViewHolder
import java.text.SimpleDateFormat
import java.util.*

class MatchInfoAdapter : ListAdapter<MatchInformation, MatchInfoViewHolder>(MatchInfoDiffCallback) {


    var onMatchItemClickListener: ((MatchInformation) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchInfoViewHolder {
        val binding = ItemMatchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MatchInfoViewHolder(binding)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MatchInfoViewHolder, position: Int) {
        val matchItem = getItem(position)
        with(holder.binding) {
            with(matchItem) {
                val strDateTime = date
                val sdfInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+0000'", Locale.getDefault())
                val dates = sdfInput.parse(strDateTime)
                val sdfOutput = SimpleDateFormat("dd.MM.yyyy, HH:mm:ss")
                sdfOutput.timeZone = TimeZone.getTimeZone("Etc/GMT-6")
                val formatted = sdfOutput.format(dates)
                tvTimeMatch.text = formatted
                tvMatchName.text = title
                tvChampionName.text = competition
                Picasso.get().load(thumbnail).into(ivLogoMatch)
                root.setOnClickListener {
                    onMatchItemClickListener?.invoke(matchItem)
                }
            }
        }
    }
}