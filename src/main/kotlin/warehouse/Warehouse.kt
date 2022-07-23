package warehouse

typealias Quantity = ULong

typealias Difference = Long

interface Warehouse : Map<Article, Quantity> {

    fun loadCurrentStock(inventory: Map<Article, Quantity>): Map<Article, Difference>
}
