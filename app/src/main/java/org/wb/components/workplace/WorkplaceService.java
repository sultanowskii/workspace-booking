package org.wb.components.workplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityService;
import org.wb.components.error.exception.PermissionDeniedException;
import org.wb.components.workplace.visual.WorkplaceVisualRepository;
import org.wb.gen.model.WorkplaceCreate;
import org.wb.gen.model.WorkplaceUpdate;

import jakarta.transaction.Transactional;

@Service
public class WorkplaceService extends
        EntityService<Workplace, org.wb.gen.model.Workplace, org.wb.gen.model.Workplace, WorkplaceCreate, WorkplaceUpdate> {
    @Autowired
    protected WorkplaceVisualRepository visualRepo;

    @Override
    protected boolean isListAllowed() {
        return true;
    }

    @Override
    protected boolean isCreateAllowed() {
        return isCurrentUserAdmin();
    }

    @Override
    protected boolean isReadAllowed(Workplace workplace) {
        return true;
    }

    @Override
    protected boolean isUpdateAllowed(Workplace workplace) {
        return isCurrentUserAdmin();
    }

    @Override
    protected boolean isDeleteAllowed(Workplace workplace) {
        return isCurrentUserAdmin();
    }

    @Override
    protected String entityName() {
        return "workplace";
    }

    @Override
    @Transactional
    public org.wb.gen.model.Workplace create(WorkplaceCreate createDto) {
        if (!isCreateAllowed()) {
            throw new PermissionDeniedException("You can't create " + entityName());
        }
        var workplace = mapper.fromCreateDto(createDto);
        workplace.setVisual(null);
        var createdWorkplace = repo.save(workplace);

        var workplaceVisual = mapper.fromCreateDto(createDto).getVisual();
        workplaceVisual.setWorkplace(createdWorkplace);
        workplaceVisual.setWorkplaceId(createdWorkplace.getId());
        createdWorkplace.setVisual(workplaceVisual);
        var createdWorkplaceVisual = visualRepo.save(workplaceVisual);

        createdWorkplace.setVisual(createdWorkplaceVisual);

        return mapper.toDto(createdWorkplace);
    }

    @Override
    public org.wb.gen.model.Workplace update(long id, WorkplaceUpdate updateDto) {
        var workplace = getRaw(id);

        if (!isUpdateAllowed(workplace)) {
            throw new PermissionDeniedException("You can't change this " + entityName());
        }

        mapper.update(workplace, updateDto);
        workplace.getVisual().setWorkplace(workplace);
        workplace.getVisual().setWorkplaceId(workplace.getId());
        var updatedWorkplace = repo.save(workplace);
        return mapper.toDto(updatedWorkplace);
    }

    @Override
    @Transactional
    public void delete(long id) {
        var workplace = getRaw(id);

        if (!isDeleteAllowed(workplace)) {
            throw new PermissionDeniedException("You can't delete this " + entityName());
        }

        visualRepo.delete(workplace.getVisual());
        repo.deleteById(id);
    }
}
