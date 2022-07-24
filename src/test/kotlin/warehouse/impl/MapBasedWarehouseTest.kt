package warehouse.impl

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import warehouse.Article
import warehouse.Failure
import warehouse.OK

@ExtendWith(MockKExtension::class)
class MapBasedWarehouseTest {

    private lateinit var warehouse: MapBasedWarehouse

    @BeforeEach
    internal fun setUp() {
        warehouse = MapBasedWarehouse.emptyOne()
    }

    @Test
    internal fun `can load current stock into empty warehouse`() {
        // given
        val inventory = mapOf(
            mockk<Article>() to 1L
        )
        // when
        warehouse.loadCurrentStock(inventory)
        // then
        assertThat(warehouse).containsExactlyEntriesOf(inventory)
    }

    @Nested
    inner class FullWarehouseContext {

        @MockK
        private lateinit var knownArticle: Article

        @BeforeEach
        internal fun setUp() {
            warehouse.loadCurrentStock(mapOf(knownArticle to 5L))
        }

        @Test
        internal fun `can load current stock`() {
            // given
            val newArticle = mockk<Article>()
            val inventory = mapOf(
                newArticle to 1L
            )
            // when
            warehouse.loadCurrentStock(inventory)
            // then
            assertThat(warehouse).containsExactlyEntriesOf(inventory)
        }

        @Test
        internal fun `can reserve existing stock`() {
            // given
            val articleToReserve = knownArticle
            val quantityToReserve = 1L
            // when
            val reservation = warehouse.reserve(mapOf(articleToReserve to quantityToReserve))
            // then
            assertThat(reservation).isInstanceOf(OK::class.java)
        }

        @Test
        internal fun `cannot reserve unknown article`() {
            // given
            val articleToReserve = mockk<Article>()
            val quantityToReserve = 1L
            // when
            val reservation = warehouse.reserve(mapOf(articleToReserve to quantityToReserve))
            // then
            assertThat(reservation).isInstanceOf(Failure::class.java)
        }

        @Test
        internal fun `cannot reserve if not enough stock`() {
            // given
            val articleToReserve = knownArticle
            val quantityToReserve = 999L
            // when
            val reservation = warehouse.reserve(mapOf(articleToReserve to quantityToReserve))
            // then
            assertThat(reservation).isInstanceOf(Failure::class.java)
        }
    }
}
