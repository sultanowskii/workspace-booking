package org.wb.components.common;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface EntityMapper<T extends Entity, TDto, TListDto, TCreateDto, TUpdateDto> {
    TDto toDto(T entity);

    TListDto toListDto(T entity);

    @Mapping(target = "id", ignore = true)
    T fromCreateDto(TCreateDto createDto);

    @Mapping(target = "id", ignore = true)
    T fromUpdateDto(TUpdateDto updateDto);

    void update(@MappingTarget T entity, TUpdateDto updateDto);
}
