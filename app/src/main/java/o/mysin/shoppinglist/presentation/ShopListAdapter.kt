package o.mysin.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import o.mysin.shoppinglist.R
import o.mysin.shoppinglist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter :
    ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layoutId = when (viewType) {
            ActiveItemType.ENABLE.type -> R.layout.item_show_enabled
            ActiveItemType.DISABLE.type -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
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
