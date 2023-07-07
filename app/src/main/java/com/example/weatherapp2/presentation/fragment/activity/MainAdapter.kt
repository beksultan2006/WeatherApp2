package com.example.weatherapp2.presentation.fragment.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp2.data.model.Forecastday
import com.example.weatherapp2.databinding.ItemItemBinding

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var list: ArrayList<Forecastday> = arrayListOf()


    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<Forecastday>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size


    inner class MainViewHolder(private var binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(mainModel: Forecastday) {
            binding.txtData.text = mainModel.date
            binding.grad35.text = mainModel.day.maxtemp_c.toString()
            binding.grad26.text = mainModel.day.mintemp_c.toString()
            binding.imgSunny.loadImage(mainModel.day.condition.icon)
        }
    }
}