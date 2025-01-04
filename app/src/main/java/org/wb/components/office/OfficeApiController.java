package org.wb.components.office;

import org.wb.gen.api.OfficesApi;
import org.wb.gen.model.Office;
import org.wb.gen.model.OfficeCreateUpdate;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class OfficeApiController implements OfficesApi {
    @Autowired
    OfficeRepository repo;

    @Override
    public ResponseEntity<List<Office>> getOffices(
            @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
            @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
            @ParameterObject final Pageable pageable) {
        var result = repo
                .findAll()
                .stream()
                .map(o -> new Office().id(o.getId()).name(o.getName()).address(o.getAddress()))
                .toList();

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Office> createOffice(@Valid OfficeCreateUpdate officeCreateUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Office> getOffice(Long officeId) {
        var office = repo.findById(officeId).get();
        return ResponseEntity.ok(new Office().id(office.getId()).name(office.getName()).address(office.getAddress()));
    }

    @Override
    public ResponseEntity<Office> updateOffice(Long id, @Valid OfficeCreateUpdate officeCreateUpdate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteOffice(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
