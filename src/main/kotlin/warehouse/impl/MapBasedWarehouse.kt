package warehouse.impl

import shared.Quantity
import warehouse.Article
import warehouse.Failure
import warehouse.OK
import warehouse.Reservation
import warehouse.Rollback
import warehouse.Warehouse

class MapBasedWarehouse private constructor(
    private val map: MutableMap<Article, Quantity>,
) : Warehouse, Map<Article, Quantity> by map {

    override fun loadCurrentStock(inventory: Map<Article, Quantity>) {
        map.clear()
        map.putAll(inventory)
    }

    override fun reserve(articles: Map<Article, Quantity>): Reservation {

        fun List<() -> Unit>.invokeAll() = forEach { it() }

        val rollbacks = mutableListOf<Rollback>()
        for ((a, q) in articles) {
            when (val reservation = reserve(a, q)) {
                is OK -> rollbacks.add(reservation.rollback)
                is Failure -> {
                    rollbacks.invokeAll()
                    return Failure
                }
            }
        }
        return OK(rollback = rollbacks::invokeAll)
    }

    private fun reserve(article: Article, quantity: Quantity): Reservation {
        val left = map.compute(article) { _, q -> if (q == null) null else q - quantity }
        val rollback: Rollback = { map.computeIfPresent(article) { _, q -> q + quantity } }
        return when {
            left == null -> Failure
            left < 0L -> Failure.also { rollback() }
            else -> OK(rollback)
        }
    }

    companion object {

        fun emptyOne() = MapBasedWarehouse(mutableMapOf())
    }
}
