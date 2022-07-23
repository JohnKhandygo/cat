package store

typealias Quantity = ULong

interface Store : Map<Product, Quantity> {

    fun loadCurrentRangeOfProducts(products: Set<Product>): Map<Product, Quantity>

    fun sell(product: Product): Quantity
}
