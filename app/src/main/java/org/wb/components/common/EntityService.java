package org.wb.components.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.wb.components.common.paging.Paginator;
import org.wb.components.common.paging.SmartPage;
import org.wb.components.error.exception.NotFoundException;
import org.wb.components.error.exception.PermissionDeniedException;
import org.wb.components.user.UserService;

import jakarta.transaction.Transactional;

public abstract class EntityService<T extends Entity, TDto, TCreateDto, TUpdateDto> {
    @Autowired
    protected Repository<T> repo;
    @Autowired
    protected EntityMapper<T, TDto, TCreateDto, TUpdateDto> mapper;
    @Autowired
    protected UserService userService;

    protected abstract boolean isListAllowed();

    protected abstract boolean isCreateAllowed();

    protected abstract boolean isReadAllowed(T entity);

    protected abstract boolean isUpdateAllowed(T entity);

    protected abstract boolean isDeleteAllowed(T entity);

    protected abstract String entityName();

    protected Specification<T> additionalSpec() {
        return (root, query, builder) -> {
            // always true
            return builder.conjunction();
        };
    }

    public List<TDto> getAll(Specification<T> specification,
            Paginator paginator) {
        if (!isListAllowed()) {
            throw new PermissionDeniedException("You can't get " + entityName() + " list");
        }
        var result = repo.findAll(additionalSpec().and(specification), paginator.getSort());

        var paged = new SmartPage<>(result, paginator);

        return paged
                .map(e -> mapper.toDto(e))
                .toList();
    }

    @Transactional
    public TDto create(TCreateDto createDto) {
        if (!isCreateAllowed()) {
            throw new PermissionDeniedException("You can't create " + entityName());
        }
        var entity = mapper.fromCreateDto(createDto);
        var createdEntity = repo.save(entity);
        return mapper.toDto(createdEntity);
    }

    public TDto get(long id) {
        var entity = repo
                .findById(id)
                .orElseThrow(() -> new NotFoundException(entityName() + " with id=" + id + " not found"));
        if (!isReadAllowed(entity)) {
            throw new PermissionDeniedException("You can't access this " + entityName());
        }
        return mapper.toDto(entity);
    }

    @Transactional
    public TDto update(long id, TUpdateDto updateDto) {
        var entity = repo
                .findById(id)
                .orElseThrow(() -> new NotFoundException(entityName() + " with id=" + id + " not found"));

        if (!isUpdateAllowed(entity)) {
            throw new PermissionDeniedException("You can't change this " + entityName());
        }

        mapper.update(entity, updateDto);
        repo.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public void delete(long id) {
        var entity = repo
                .findById(id)
                .orElseThrow(() -> new NotFoundException(entityName() + " with id=" + id + " not found"));

        if (!isDeleteAllowed(entity)) {
            throw new PermissionDeniedException("You can't delete this " + entityName());
        }

        repo.deleteById(id);
    }
}
