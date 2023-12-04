package o.mysin.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import o.mysin.shoppinglist.data.ShopListRepositoryImpl
import o.mysin.shoppinglist.domain.EditShopItemUseCase
import o.mysin.shoppinglist.domain.GetShopListUseCase
import o.mysin.shoppinglist.domain.RemoveShopItemUseCase
import o.mysin.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}
