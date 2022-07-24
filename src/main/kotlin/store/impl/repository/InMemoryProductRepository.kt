package store.impl.repository

import store.Product
import store.impl.ProductRepository

class InMemoryProductRepository private constructor(
    private val delegate: MutableSet<Product>,
) : ProductRepository {

    override fun replaceAllWith(products: Set<Product>) {
        delegate.clear()
        delegate.addAll(products)
    }

    override fun getAll(): Set<Product> {
        return delegate
    }

    companion object {

        fun emptyOne() = InMemoryProductRepository(mutableSetOf())
    }
}
