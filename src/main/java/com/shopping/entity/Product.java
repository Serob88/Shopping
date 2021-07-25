package com.shopping.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "sp_product")
public class Product extends BaseEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private Double price;

  @Column(name = "category")
  private String category;

  @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "productId")
  private List<Review> reviews;

}
