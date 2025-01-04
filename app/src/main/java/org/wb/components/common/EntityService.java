package org.wb.components.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.wb.components.common.paging.Paginator;
import org.wb.components.common.paging.SmartPage;
import org.wb.components.user.UserService;

import jakarta.transaction.Transactional;

public abstract class EntityService<T extends Entity, TDto, TCreateDto, TUpdateDto> {
    @Autowired
    protected Repository<T> repo;
    @Autowired
    protected EntityMapper<T, TDto, TCreateDto, TUpdateDto> mapper;
    @Autowired
    protected UserService userService;

    protected abstract boolean isOwnershipValid(T entity);

    protected Specification<T> additionalSpec() {
        return (root, query, builder) -> {
            // always true
            return builder.conjunction();
        };
    }

    public List<TDto> getAll(Specification<T> specification,
            Paginator paginator) {
        var result = repo
                .findAll(additionalSpec().and(specification), paginator.getSort());

        var paged = new SmartPage<>(result, paginator);

        return paged
                .map(e -> mapper.toDto(e))
                .toList();
    }

    @Transactional
    public TDto create(TCreateDto createDto) {
        var e = mapper.fromCreateDto(createDto);
        var createdEmployee = repo.save(e);
        return mapper.toDto(createdEmployee);
    }

    public TDto get(long id) {
        var employee = repo.findById(id).orElse(null); // TODO: error
        return mapper.toDto(employee);
    }

    @Transactional
    public TDto update(long id, TUpdateDto updateDto) {
        var entity = repo
                .findById(id)
                .orElse(null); // TODO: error

        if (!isOwnershipValid(entity)) {
            // TODO: error
            return null;
        }

        mapper.update(entity, updateDto);
        repo.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public void delete(long id) {
        var entity = repo.findById(id).orElse(null); // TODO: error

        if (!isOwnershipValid(entity)) {
            // TODO: error
            return;
        }

        repo.deleteById(id);
    }
}
