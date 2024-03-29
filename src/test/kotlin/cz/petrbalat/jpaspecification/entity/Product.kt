package cz.petrbalat.jpaspecification.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

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
