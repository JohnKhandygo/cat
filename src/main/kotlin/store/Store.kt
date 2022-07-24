package store

import shared.Quantity

interface Store {

    fun loadCurrentRangeOfProducts(range: Set<Product>): Map<Product, Quantity>

    fun countAvailableProducts(): Map<Product, Quantity>

    fun sell(product: Product): Boolean
}
