package com.shopping.entity;

import com.shopping.entity.enums.RoleType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;


@Data
@Entity
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "sp_role")
public class Role extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private RoleType type;

}
