package cz.petrbalat.jpaspecification

import cz.petrbalat.jpaspecification.entity.Product
import cz.petrbalat.jpaspecification.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.*

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	lateinit var repository: ProductRepository

	@Test
	fun findAll() {
		val products: List<Product> = repository.findAll()
		assertEquals(2, products.size)
	}

}
