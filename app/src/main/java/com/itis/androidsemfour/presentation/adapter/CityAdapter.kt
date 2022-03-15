package com.itis.androidsemfour.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidsemfour.presentation.adapter.holder.CityHolder
import com.itis.androidsemfour.data.response.City

class CityAdapter(
    private val list: List<City>,
    private val action: (Int) -> Unit
) : RecyclerView.Adapter<CityHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityHolder = CityHolder.create(parent, action)

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
