package warehouse

import shared.Quantity

interface Warehouse : Map<Article, Quantity> {

    fun loadCurrentStock(inventory: Map<Article, Quantity>)

    fun reserve(articles: Map<Article, Quantity>): Reservation
}
