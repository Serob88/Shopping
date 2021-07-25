package com.shopping.entity;

import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"refreshTokens", "roles"})
@Table(name = "sp_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Email
  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "birthday")
  private Date birthday;

  @Column(name = "is_blocked")
  private boolean isBlock;

  @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<RefreshToken> refreshTokens;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "sp_users_roles",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")}
  )
  private List<Role> roles;

}
