package org.wb.components.office;

import org.wb.gen.api.OfficesApi;
import org.wb.gen.model.Office;
import org.wb.gen.model.OfficeCreate;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class OfficesApiController implements OfficesApi {
    @Autowired
    OfficeRepository repo;

    @Override
    public ResponseEntity<List<Office>> getOffices() {
        var result = repo
                .findAll()
                .stream()
                .map(o -> new Office().id(o.getId()).name(o.getName()).address(o.getAddress()))
                .toList();

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> createOffice(@Valid OfficeCreate officeCreate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Office> getOffice(Long officeId) {
        var office = repo.findById(officeId).get();
        return ResponseEntity.ok(new Office().id(office.getId()).name(office.getName()).address(office.getAddress()));
    }

    @Override
    public ResponseEntity<Office> updateOffice(Long id, @Valid OfficeCreate officeCreate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteOffice(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
