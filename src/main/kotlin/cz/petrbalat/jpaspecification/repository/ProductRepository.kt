package cz.petrbalat.jpaspecification.repository

import cz.petrbalat.jpaspecification.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional
interface ProductRepository : JpaRepository<Product, Int>, JpaSpecificationExecutor<Product> {
}
