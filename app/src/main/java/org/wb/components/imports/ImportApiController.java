package org.wb.components.imports;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.wb.gen.api.ImportsApi;

@Controller
@RequestMapping("${openapi.workspaceBooking.base-path:}")
public class ImportApiController implements ImportsApi {
    @Autowired
    protected ImportService service;

    @Override
    public ResponseEntity<Void> importRoom(MultipartFile file) {
        try {
            service.extractAndCreateRoom(file);
        } catch (IOException e) {
            System.out.println(e);
        }
        return ResponseEntity.ok().build();
    }
}
