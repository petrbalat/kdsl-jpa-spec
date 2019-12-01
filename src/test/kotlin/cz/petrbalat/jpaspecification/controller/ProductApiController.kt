package cz.petrbalat.jpaspecification.controller

import cz.petrbalat.jpaspecification.entity.Product
import cz.petrbalat.jpaspecification.repository.ProductRepository
import cz.petrbalat.jpaspecification.dto.ProductDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/product")
class ProductApiController(private val repository: ProductRepository) {

    @GetMapping
    fun all(dto: ProductDto, pageable: Pageable): Page<Product> = repository.findAll(dto, pageable)

}
