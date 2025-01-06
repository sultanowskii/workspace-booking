package org.wb.components.workplace;

import org.springframework.stereotype.Service;
import org.wb.components.common.EntityService;
import org.wb.gen.model.WorkplaceCreate;
import org.wb.gen.model.WorkplaceUpdate;

@Service
public class WorkplaceService extends
        EntityService<Workplace, org.wb.gen.model.Workplace, org.wb.gen.model.Workplace, WorkplaceCreate, WorkplaceUpdate> {
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
}
