package com.shopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sp_review")
public class Review extends BaseEntity {

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @JoinColumn(name = "product_id", nullable = false)
  private Long productId;

  @Column(name = "comment")
  private String comment;

  @Column(name = "rate")
  private int rate;

}
