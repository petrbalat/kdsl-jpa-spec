package cz.petrbalat.jpaspecification.specification

import cz.petrbalat.jpaspecification.entity.Product
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

data class ProductDto(val name: String? = null,
                      val weight: Double? = null) : Specification<Product> {

    override fun toPredicate(product: Root<Product>, query: CriteriaQuery<*>, cb: CriteriaBuilder): Predicate? {
        val predicates = mutableListOf<Predicate>()

        if (name != null) {
            val predicate = cb.like(product.get<String>(Product::name.name), "$name%")
            predicates.add(predicate)
        }

        if (weight != null) {
            val predicate = cb.equal(product.get<String>(Product::weight.name), weight)
            predicates.add(predicate)
        }

        return cb.and(*predicates.toTypedArray())
    }

}


