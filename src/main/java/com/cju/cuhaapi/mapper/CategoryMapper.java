package com.cju.cuhaapi.mapper;

import com.cju.cuhaapi.domain.post.entity.Category;
import com.cju.cuhaapi.domain.post.dto.CategoryDto.CategoryRequest;
import com.cju.cuhaapi.domain.post.dto.CategoryDto.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category categoryRequestToEntity(CategoryRequest request);
    CategoryResponse entityToCategoryResponse(Category category);
}
