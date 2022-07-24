package store.impl

import shared.Quantity
import store.Product
import store.Store
import warehouse.OK
import warehouse.Warehouse

class RepositoryBasedStore(
    private val warehouse: Warehouse,
    private val products: ProductRepository,
) : Store {

    override fun loadCurrentRangeOfProducts(range: Set<Product>): Map<Product, Quantity> {
        products.replaceAllWith(range)
        return countNumberOf(range)
    }

    override fun countAvailableProducts(): Map<Product, Quantity> {
        return countNumberOf(products.getAll())
    }

    override fun sell(product: Product): Boolean {
        val reservation = warehouse.reserve(product.consistsOf)
        return reservation is OK
    }

    private fun countNumberOf(products: Iterable<Product>) = products.associateWith {
        it.consistsOf.map { (a, q) -> (warehouse[a] ?: 0L) / q }.min()
    }
}
