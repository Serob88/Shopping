package com.shopping.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @PrePersist
  public void prePersist() {
    this.createdDate = LocalDateTime.now();
  }

}
