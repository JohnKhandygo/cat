package warehouse.impl

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import warehouse.Article

class MapBasedWarehouseTest {

    private lateinit var warehouse: MapBasedWarehouse

    @BeforeEach
    internal fun setUp() {
        warehouse = MapBasedWarehouse(mutableMapOf())
    }

    @Test
    internal fun `can load stock into warehouse`() {
        // given
        val inventory = mapOf(
            mockk<Article>() to 1UL
        )
        // when
        warehouse.loadCurrentStock(inventory)
        // then
        assertThat(warehouse).containsAllEntriesOf(inventory)
    }
}
