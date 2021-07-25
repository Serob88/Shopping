package com.shopping.repository;

import com.shopping.entity.User;
import java.util.Optional;
import javax.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(@Email String email);

}
