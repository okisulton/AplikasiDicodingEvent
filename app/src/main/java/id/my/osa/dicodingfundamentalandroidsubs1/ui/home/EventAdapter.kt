package id.my.osa.dicodingfundamentalandroidsubs1.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.response.EventResponse
import id.my.osa.dicodingfundamentalandroidsubs1.databinding.ItemEventBinding

class EventAdapter : ListAdapter<EventResponse.ListEventsItem, EventAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(event)
        }
    }

    class MyViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventResponse.ListEventsItem) {
            binding.tvEventName.text = event.name
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.ivEvent)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: EventResponse.ListEventsItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventResponse.ListEventsItem>() {
            override fun areItemsTheSame(oldItem: EventResponse.ListEventsItem, newItem: EventResponse.ListEventsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: EventResponse.ListEventsItem, newItem: EventResponse.ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}