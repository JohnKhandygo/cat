package warehouse.impl

import warehouse.Article
import warehouse.Quantity
import warehouse.Warehouse

class MapBasedWarehouse(
    private val map: MutableMap<Article, Quantity>,
) : Warehouse, Map<Article, Quantity> by map {

    override fun loadCurrentStock(inventory: Map<Article, Quantity>) {
        map.putAll(inventory)
    }
}
