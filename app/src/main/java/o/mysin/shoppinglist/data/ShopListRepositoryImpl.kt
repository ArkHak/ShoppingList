package o.mysin.shoppinglist.data

import android.app.Application
import android.view.animation.Transformation
import androidx.constraintlayout.widget.ConstraintSet.Transform
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import o.mysin.shoppinglist.domain.ShopItem
import o.mysin.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

class ShopListRepositoryImpl(
    application: Application,
) : ShopListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun removeShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        addShopItem(shopItem)
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

        override fun getShopList(): LiveData<List<ShopItem>> = shopListDao.getShopList().map {
        mapper.mapListDbModelToListEntity(it)
    }

//    override fun getShopList(): LiveData<List<ShopItem>> =
//        MediatorLiveData<List<ShopItem>>().apply {
//            addSource(shopListDao.getShopList()) {
//                value = mapper.mapListDbModelToListEntity(it)
//            }
//        }

}
