package cz.petrbalat.jpaspecification.dto

import cz.petrbalat.jpaspecification.entity.Product
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class StandartProductDto(val name: String? = null,
                         val weight: Double? = null,
                         val visible: Boolean = true
) : Specification<Product> {

    override fun toPredicate(root: Root<Product>, query: CriteriaQuery<*>, cb: CriteriaBuilder): Predicate?  {
        val predicates: MutableList<Predicate> = mutableListOf()

        val delPredicate = cb.isNull(root.get<Any>("deleted"))
        predicates.add(delPredicate)

        if (name != null) {
            val namePredicate = cb.like(root.get<String>("name"), "$name%")
            predicates.add(namePredicate)
        }

        if (weight != null) {
            val weightPath = root.get<Double>("weight")
            val weightNotNullPredicate = cb.isNotNull(weightPath)
            predicates.add(weightNotNullPredicate)

            val weightPredicate = cb.lt(weightPath, weight)
            predicates.add(weightPredicate)
        }

        val visiblePath = root.get<Boolean>("visible")
        if (visible) {
            val visiblePredicate = cb.isTrue(visiblePath)
            predicates.add(visiblePredicate)
        } else {
            val visiblePredicate = cb.isFalse(visiblePath)
            predicates.add(visiblePredicate)
        }

        if (predicates.isEmpty()) {
            return null
        }

        return cb.and(*predicates.toTypedArray())
    }

}


