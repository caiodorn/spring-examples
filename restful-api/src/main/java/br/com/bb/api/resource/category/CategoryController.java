package br.com.bb.api.resource.category;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.util.StringUtils.isEmpty;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public HttpEntity<List<CategoryResource>> getCategories(@RequestParam(value = "withNameContainingPatternMostTimes", required = false) String pattern) {
        List<CategoryResource> categories;

        if (isEmpty(pattern)) {
            categories = categoryService.getCollection();
        } else {
            categories = categoryService.getResourceWithNameContainingPatternMostTimes(pattern);
        }

        categories.forEach(category -> category.add(linkTo(getClass()).slash(category.getUniqueId()).withSelfRel()));

        return new ResponseEntity<>(categories, categories.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<CategoryResource> getCategory(@PathVariable("id") String id) {
        CategoryResource category = categoryService.getResourceById(id);
        category.add(linkTo(getClass()).slash(category.getUniqueId()).withSelfRel());

        return new ResponseEntity<>(category, category.getUniqueId() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

}
