package org.wb.components.office;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Service;
import org.wb.components.common.EntityMapper;
import org.wb.gen.model.OfficeCreateUpdate;

@Service
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OfficeMapper
                implements
                EntityMapper<Office, org.wb.gen.model.Office, org.wb.gen.model.Office, OfficeCreateUpdate, OfficeCreateUpdate> {

}
