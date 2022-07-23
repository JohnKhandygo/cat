package store

import warehouse.Article

data class Product(
    val name: String,
    val consistsOf: Map<Article, Quantity>,
)
