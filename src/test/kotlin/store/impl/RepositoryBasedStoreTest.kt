package store.impl

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import store.Product
import warehouse.Article
import warehouse.Warehouse

@ExtendWith(MockKExtension::class)
class RepositoryBasedStoreTest {

    @MockK
    private lateinit var warehouse: Warehouse

    @MockK
    private lateinit var productRepository: ProductRepository

    @InjectMockKs
    private lateinit var store: RepositoryBasedStore

    @Test
    internal fun `can count number of products available`() {
        // given
        val article1 = mockk<Article>()
        val article2 = mockk<Article>()
        val product = Product(
            name = "whatever",
            consistsOf = mapOf(
                article1 to 1L,
                article2 to 4L
            )
        )
        // and
        every { productRepository.getAll() } returns setOf(product)
        every { warehouse[article1] } returns 10L
        every { warehouse[article2] } returns 6L
        // when
        val availableProducts = store.countAvailableProducts()
        // then
        assertThat(availableProducts).containsKey(product)
        assertThat(availableProducts[product]).isEqualTo(1L)
    }
}
