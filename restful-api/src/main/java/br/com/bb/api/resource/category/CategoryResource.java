package br.com.bb.api.resource.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Setter
public class CategoryResource extends ResourceSupport {

    @JsonIgnore
    private Long uniqueId;

    private String name;

}
