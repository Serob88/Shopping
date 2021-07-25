package com.shopping.repository;


import com.shopping.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findFirstByUserIdAndActive(final Long userId, final boolean active);

  @Modifying
  @Query("UPDATE RefreshToken rt SET rt.active = false WHERE rt.user.id = :userId")
  void invalidateByUserId(final Long userId);

  Optional<RefreshToken> findByToken(final String token);

}
