package ru.netology.yandmap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.yandmap.R
import ru.netology.yandmap.databinding.PlaceItemBinding
import ru.netology.yandmap.dto.Place

interface Listener {
    fun onClick(place: Place)
    fun onDelete(place: Place)
    fun onEdit(place: Place)
}
class PlacesAdapter(
    private val listener: Listener,
) : ListAdapter<Place, PlacesViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val binding = PlaceItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlacesViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
class PlacesViewHolder(
    private val binding: PlaceItemBinding,
    private val listener: Listener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(place: Place) {
        with(binding) {
            title.text = place.name

            binding.root.setOnClickListener {
                listener.onClick(place)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.place_menu)
                    setOnMenuItemClickListener{ item ->
                        when (item.itemId) {
                            R.id.delete -> {
                                listener.onDelete(place)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(place)
                                true
                            }

                            else -> false
                        }
                    }
                }
            }
        }
    }
}

object DiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean =
        oldItem == newItem
}
