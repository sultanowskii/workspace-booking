package org.wb.components.offices;

import org.wb.gen.api.OfficesApi;
import org.wb.gen.model.Office;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${openapi.workplaceBooking.base-path:}")
public class OfficesApiController implements OfficesApi {
    @Override
    public ResponseEntity<Office> getOffice(Integer officeId) {
        return ResponseEntity.ok(new Office().id(0).name("test").address("moscow"));
    }

    @Override
    public ResponseEntity<List<Office>> getOffices() {
        return ResponseEntity.ok(List.of(new Office().id(0).name("test").address("moscow")));
    }
}
