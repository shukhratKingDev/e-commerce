package com.company.ecommerce.repository;

import com.company.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
//collectionResourceRel---name of json entry
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

}
