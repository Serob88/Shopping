package com.shopping.repository;

import com.shopping.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query(value = "SELECT avg(rate) FROM Review WHERE productId = :productId")
  Double findProductRate(Long productId);
}
