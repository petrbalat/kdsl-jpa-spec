# Kotlin DSL for spring data JpaSpecification 

Imagine that you have jpa entity Product, repository and dto for request params   
(see [Product](src/main/kotlin/cz/petrbalat/jpaspecification/entity/Product.kt)
)

```kotlin
@Entity
@Table(name = "PRODUCTS")
class Product {

    @Id
    var id: Int = 0

    lateinit var name: String

    var weight: Double? = null

    var visible: Boolean = true

    var deleted: LocalDateTime? = null
}

@Repository
@Transactional
interface ProductRepository : JpaRepository<Product, Int>, JpaSpecificationExecutor<Product> {
}


data class ProductDto(val name: String? = null,
                      val weight: Double? = null,
                      val visible: Boolean = true
                      )
@RestController
@RequestMapping("/api/product")
class ProductApiController(private val repository: ProductRepository) {

    @GetMapping
    fun all(dto: ProductDto, pageable: Pageable): Page<Product> = repository.findAll(dto, pageable)

}
 
```


### With standart spring Specification<Product>(see [StandartProductDto](src/main/kotlin/cz/petrbalat/jpaspecification/specification/StandartProductDto.kt)): 

```kotlin
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
```

### With kdsl-jpa-spec dkl library (see [ProductDto](src/main/kotlin/cz/petrbalat/jpaspecification/specification/ProductDto.kt)):
```kotlin
data class ProductDto(val name: String? = null,
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
```
## How to use


Gradle:
```
repositories {
	jcenter()
}
...

implementation "cz.petrbalat:kdsl-jpa-spec:0.1.0"
```

Maven:
```xml
<dependency>
    <groupId>cz.petrbalat</groupId>
    <artifactId>kdsl-jpa-spec</artifactId>
    <version>0.1.0</version>
</dependency>


...

<repository>
    <id>kdsl-jpa-spec</id>
    <url>https://jcenter.bintray.com/</url>
    <snapshots>
        <enabled>false</enabled>
        <updatePolicy>always</updatePolicy>
    </snapshots>
</repository>
```
