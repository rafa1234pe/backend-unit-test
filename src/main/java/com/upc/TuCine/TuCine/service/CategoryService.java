package com.upc.TuCine.TuCine.service;

import com.upc.TuCine.TuCine.dto.CategoryDto;
import com.upc.TuCine.TuCine.dto.save.Category.CategorySaveDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories();
    CategoryDto createCategory(CategorySaveDto categorySaveDto);

    CategoryDto updateCategory(Integer id, CategorySaveDto categorySaveDto);

    String deleteCategory(Integer id);
}
