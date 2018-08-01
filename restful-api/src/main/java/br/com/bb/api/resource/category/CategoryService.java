package br.com.bb.api.resource.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResource> getCollection() {
        return convertEntityToDomain(categoryRepository.findAll());
    }

    public CategoryResource getResourceById(String id) {
        return convertEntityToDomain(categoryRepository.findOne(Long.valueOf(id)));
    }

    public List<CategoryResource> getResourceWithNameContainingPatternMostTimes(String pattern) {
        List<CategoryResource> categories = new ArrayList<>();

        if (!isEmpty(pattern)) {
            categories = convertEntityToDomain(categoryRepository.findWithNameContainingPatternMostTimes(pattern.toLowerCase()));
        }

        return categories;
    }

    private List<CategoryResource> convertEntityToDomain(List<CategoryEntity> categoryEntities) {
        List<CategoryResource> categories = new ArrayList<>(categoryEntities.size());
        categoryEntities.forEach(categoryEntity -> categories.add(convertEntityToDomain(categoryEntity)));

        return categories;
    }

    private CategoryResource convertEntityToDomain(CategoryEntity categoryEntity) {
        categoryEntity = categoryEntity == null ? new CategoryEntity() : categoryEntity;
        CategoryResource categoryResource = new CategoryResource();
        categoryResource.setUniqueId(categoryEntity.getId());
        categoryResource.setName(categoryEntity.getName());
        return categoryResource;
    }

}
