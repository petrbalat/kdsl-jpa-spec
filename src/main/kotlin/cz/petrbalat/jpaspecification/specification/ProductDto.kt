package cz.petrbalat.jpaspecification.specification

import cz.petrbalat.jpaspecification.dsl.dsl
import cz.petrbalat.jpaspecification.entity.Product
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class ProductDto(val name: String? = null,
                      val weight: Double? = null,
                      val visible: Boolean = true
                      ) : Specification<Product> {

    override fun toPredicate(root: Root<Product>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate? = dsl(root, query, criteriaBuilder) {
        add {
            Product::deleted.isNull()
        }

        if (name != null) {
            add {
                Product::name like "$name%"
            }
        }

        if (weight != null) {
            add {
                Product::weight.isNotNull()
            }
            add {
                Product::weight lt weight
            }
        }

        if (visible) {
            add {
                Product::visible.isTrue()
            }
        } else {
            add {
                Product::visible.isFalse()
            }
        }
    }

}


