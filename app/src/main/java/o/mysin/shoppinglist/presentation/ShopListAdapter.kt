package o.mysin.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import o.mysin.shoppinglist.databinding.ItemShopDisabledBinding
import o.mysin.shoppinglist.databinding.ItemShowEnabledBinding
import o.mysin.shoppinglist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter :
    ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val binding = when (viewType) {

            ActiveItemType.ENABLE.type -> ItemShowEnabledBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            ActiveItemType.DISABLE.type -> ItemShopDisabledBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            else -> throw RuntimeException("Unknown view type: $viewType")
        }

        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        when (binding) {
            is ItemShopDisabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }

            is ItemShowEnabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).enabled) {
            true -> ActiveItemType.ENABLE.type
            false -> ActiveItemType.DISABLE.type
        }
    }

    private enum class ActiveItemType(val type: Int) {
        ENABLE(type = ENABLE_TYPE),
        DISABLE(type = DISABLE_TYPE),
    }

    companion object {
        const val ENABLE_TYPE = 1
        const val DISABLE_TYPE = 2

        const val MAX_POOL_SIZE = 15
    }
}
