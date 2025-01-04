package org.wb.components.common;

import org.mapstruct.MappingTarget;

public interface EntityMapper<T extends Entity, TDto, TCreateDto, TUpdateDto> {
    TDto toDto(T entity);

    T fromCreateDto(TCreateDto createDto);

    T fromUpdateDto(TUpdateDto updateDto);

    void update(@MappingTarget T entity, TUpdateDto updateDto);
}
