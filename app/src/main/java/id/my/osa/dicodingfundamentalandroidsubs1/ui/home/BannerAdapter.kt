package id.my.osa.dicodingfundamentalandroidsubs1.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.databinding.ItemBannerEventBinding

class BannerAdapter(
    private val onItemClick: ((Event) -> Unit)? = null
) : ListAdapter<Event, BannerAdapter.BannerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(event)
        }
    }

    class BannerViewHolder(
        private val binding: ItemBannerEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.tvBannerEventName.text = event.name
            binding.tvBannerEventOwner.text = event.ownerName
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.ivBannerEvent)
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Event>() {
                override fun areItemsTheSame(oldItem: Event, newItem: Event) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Event, newItem: Event) =
                    oldItem == newItem
            }
    }
}
