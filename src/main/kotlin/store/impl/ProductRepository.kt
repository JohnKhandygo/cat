package store.impl

import store.Product

interface ProductRepository {

    fun replaceAllWith(products: Set<Product>)

    fun getAll(): Set<Product>
}
