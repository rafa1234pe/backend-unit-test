package com.upc.TuCine.TuCine.service.impl;

import com.upc.TuCine.TuCine.dto.ActorDto;
import com.upc.TuCine.TuCine.dto.CategoryDto;

import com.upc.TuCine.TuCine.dto.save.Category.CategorySaveDto;

import com.upc.TuCine.TuCine.shared.exception.ValidationException;

import com.upc.TuCine.TuCine.model.Category;
import com.upc.TuCine.TuCine.repository.CategoryRepository;
import com.upc.TuCine.TuCine.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    CategoryServiceImpl(){
        this.modelMapper = new ModelMapper();
    }

    public CategoryDto EntityToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }

    public Category DtoToEntity(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
    }


    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::EntityToDto)
                .collect(Collectors.toList());
    }
    @Override
    public CategoryDto createCategory(CategorySaveDto categorySaveDto) {

        CategoryDto categoryDto = modelMapper.map(categorySaveDto, CategoryDto.class);

        validateCategory(categoryDto);
        existsCategoryByName(categoryDto.getName());

        Category category = DtoToEntity(categoryDto);
        return EntityToDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(Integer id, CategorySaveDto categorySaveDto) {
        CategoryDto categoryDto = modelMapper.map(categorySaveDto, CategoryDto.class);
        Category category = DtoToEntity(categoryDto);
        Category categoryUpdate = categoryRepository.findById(id).orElseThrow(() -> new ValidationException("No existe la categoria"));
        categoryUpdate.setName(category.getName());
        return EntityToDto(categoryRepository.save(categoryUpdate));
    }
    @Override
    public String deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ValidationException("No existe la categoria"));
        categoryRepository.delete(category);
        return "La categoria con nombre " + category.getName() + " ha sido eliminado";
    }

    private void validateCategory(CategoryDto category) {
        if (category.getName() == null || category.getName().isEmpty()) {
            throw new ValidationException("El nombre de la categoria es obligatorio");
        }
    }

    private void existsCategoryByName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new ValidationException("No se puede registrar la categoria, ya existe una con ese nombre");
        }
    }


}
