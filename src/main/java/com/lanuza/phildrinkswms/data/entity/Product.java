package com.lanuza.phildrinkswms.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "product")
public class Product{
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @NotEmpty(message="Field cannot be empty")
    private String code;
    @NotEmpty(message="Field cannot be empty")
    private String product;
    @NotEmpty(message="Field cannot be empty")
    private String price;

}
