package id.my.osa.dicodingfundamentalandroidsubs1.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.my.osa.dicodingfundamentalandroidsubs1.databinding.ItemEventVerticalBinding
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event

class EventVerticalAdapter(
    private val onItemClick: ((Event) -> Unit)? = null
) : ListAdapter<Event, EventVerticalAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(event)
        }
    }

    class MyViewHolder(private val binding: ItemEventVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.tvEventName.text = event.name
            binding.tvEventOwner.text = event.ownerName
            binding.tvEventDate.text = event.beginTime
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.ivEvent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }
        }
    }
}
