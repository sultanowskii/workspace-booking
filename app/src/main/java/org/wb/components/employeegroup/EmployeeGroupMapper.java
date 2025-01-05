package org.wb.components.employeegroup;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityMapper;
import org.wb.gen.model.EmployeeGroupCreateUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EmployeeGroupMapper implements
                EntityMapper<EmployeeGroup, org.wb.gen.model.EmployeeGroup, EmployeeGroupCreateUpdate, EmployeeGroupCreateUpdate> {

}
