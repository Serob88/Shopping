package com.shopping.repository;

import com.shopping.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("FROM Product p "
      + "WHERE lower(p.name) LIKE :name ")
  Page<Product> findByName(String name, Pageable pageable);


  @Query(value = "SELECT p FROM Product p JOIN p.reviews r "
      + "WHERE r.rate = :rate")
  Page<Product> findByRate(int rate, Pageable pageable);

}
