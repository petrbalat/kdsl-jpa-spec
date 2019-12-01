package cz.petrbalat.jpaspecification.specification

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@DslMarker
annotation class CriteriaBuilderDsl

fun <T : Any> dsl(root: Root<T>, query: CriteriaQuery<*>, cb: CriteriaBuilder, init: DslCriteriaBuilder<T>.() -> Unit): Predicate? {
    val builder = DslCriteriaBuilder<T>(root, query, cb)
    builder.init()
    return builder.toPredicate()
}
