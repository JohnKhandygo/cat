package store.impl.repository

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import store.Product

class InMemoryProductRepositoryTest {

    private lateinit var repository: InMemoryProductRepository

    @BeforeEach
    internal fun setUp() {
        repository = InMemoryProductRepository.emptyOne()
    }

    @Test
    internal fun `can hold products`() {
        // given
        val products = setOf(mockk<Product>())
        // when
        repository.replaceAllWith(products)
        // then
        assertThat(repository.getAll()).containsExactlyElementsOf(products)
    }
}
