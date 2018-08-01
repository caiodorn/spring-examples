package br.com.bb.api.resource.category;

import br.com.bb.api.resource.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "CATEGORY")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE,
            mappedBy = "categoryEntity",
            orphanRemoval = true)
    private List<Product> products;

}
