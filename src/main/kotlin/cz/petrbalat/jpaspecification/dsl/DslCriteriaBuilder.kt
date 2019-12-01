package cz.petrbalat.jpaspecification.dsl

import javax.persistence.criteria.*
import kotlin.reflect.KProperty

@CriteriaBuilderDsl
class DslCriteriaBuilder<T : Any>(//private val rootClazz: KClass<T>,
        val root: Root<T>,
        val query: CriteriaQuery<*>,
        val cb: CriteriaBuilder) {

    private val predicates: MutableList<Predicate> = mutableListOf()

    fun toPredicate(): Predicate? {
        if (predicates.isEmpty()) {
            return null
        }

        return cb.and(*predicates.toTypedArray())
    }

    fun add(createPredicate: () -> Predicate) {
        predicates.add(createPredicate())
    }


///////////
//extension infix fun for Expression

    fun Expression<Boolean>.isTrue(): Predicate = cb.isTrue(this)

    fun Expression<Boolean>.isFalse(): Predicate = cb.isFalse(this)

    infix fun Expression<String>.like(pattern: String): Predicate = cb.like(this, pattern)

    infix fun <R : Any?> Expression<R>.equal(obj: Any): Predicate = cb.equal(this, obj)

    infix fun <R : Any?> Expression<R>.notEqual(obj: Any): Predicate = cb.notEqual(this, obj)

    infix fun <R : Number?> Expression<R>.gt(number: Number): Predicate = cb.gt(this, number)
    infix fun <R : Number?> Expression<R>.ge(number: Number): Predicate = cb.ge(this, number)
    infix fun <R : Number?> Expression<R>.lt(number: Number): Predicate = cb.lt(this, number)
    infix fun <R : Number?> Expression<R>.le(number: Number): Predicate = cb.le(this, number)

    ///////////
//extension infix fun for KProperty
///////////
    private val <R> KProperty<R>.rootPath: Path<R>
        get() = root.get<R>(this.name)


    fun KProperty<Boolean>.isTrue(): Predicate = rootPath.isTrue()

    fun KProperty<Boolean>.isFalse(): Predicate = rootPath.isFalse()

    fun KProperty<*>.isNull(): Predicate = rootPath.isNull()

    fun KProperty<*>.isNotNull(): Predicate = rootPath.isNotNull()

    infix fun KProperty<String>.like(pattern: String): Predicate = rootPath.like(pattern)

    infix fun <R : Any?> KProperty<R>.equal(obj: Any): Predicate = rootPath.equal(obj)
    infix fun <R : Any?> KProperty<R>.notEqual(obj: Any): Predicate = rootPath.notEqual(obj)

    infix fun <R : Number?> KProperty<R>.gt(number: Number): Predicate = rootPath.gt(number)
    infix fun <R : Number?> KProperty<R>.ge(number: Number): Predicate = rootPath.ge(number)
    infix fun <R : Number?> KProperty<R>.lt(number: Number): Predicate = rootPath.lt(number)
    infix fun <R : Number?> KProperty<R>.le(number: Number): Predicate = rootPath.le(number)
}
