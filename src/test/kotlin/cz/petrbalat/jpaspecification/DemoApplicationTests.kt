package cz.petrbalat.jpaspecification

import cz.petrbalat.jpaspecification.entity.Product
import cz.petrbalat.jpaspecification.repository.ProductRepository
import cz.petrbalat.jpaspecification.specification.ProductDto
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
		assertEquals(4, products.size)
	}

	@Test
	fun findByName() {
		val dto = ProductDto(name = "2")
		val products: List<Product> = repository.findAll(dto)
		assertEquals(2, products.single().id)
	}

	@Test
	fun findByVisible() {
		val dto = ProductDto(visible = false)
		val products: List<Product> = repository.findAll(dto)
		assertEquals(3, products.single().id)
	}

	@Test
	fun findByWeight() {
		val dto = ProductDto(weight = 1.3)
		val products: List<Product> = repository.findAll(dto)
		assertEquals(1, products.single().id)
	}

}
