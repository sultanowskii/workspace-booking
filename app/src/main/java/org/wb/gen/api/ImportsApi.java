/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.wb.gen.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
@Validated
@Controller
@Tag(name = "imports", description = "the imports API")
public interface ImportsApi {

    /**
     * POST /imports : Import room
     * Import room (layout)
     *
     * @param file  (optional)
     * @return 
     */
    @Operation(
        operationId = "importRoom",
        summary = "Import room",
        description = "Import room (layout)",
        responses = {
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/imports",
        consumes = { "multipart/form-data" }
    )
    
    ResponseEntity<Void> importRoom(
        @Parameter(name = "file", description = "") @RequestPart(value = "file", required = false) MultipartFile file
    );

}
