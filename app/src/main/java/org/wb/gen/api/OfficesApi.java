/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.wb.gen.api;

import org.wb.gen.model.Error;
import org.wb.gen.model.Office;
import org.wb.gen.model.OfficeCreateUpdate;
import org.springframework.data.domain.Pageable;
import org.springdoc.core.annotations.ParameterObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
@Validated
@Controller
@Tag(name = "offices", description = "the offices API")
public interface OfficesApi {

    /**
     * POST /offices : Create office
     *
     * @param officeCreateUpdate  (optional)
     * @return Success (status code 201)
     */
    @Operation(
        operationId = "createOffice",
        summary = "Create office",
        responses = {
            @ApiResponse(responseCode = "201", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Office.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/offices",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<Office> createOffice(
        @Parameter(name = "OfficeCreateUpdate", description = "") @Valid @RequestBody(required = false) OfficeCreateUpdate officeCreateUpdate
    );


    /**
     * DELETE /offices/{id} : Delete office
     *
     * @param id  (required)
     * @return Success (status code 204)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "deleteOffice",
        summary = "Delete office",
        responses = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/offices/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<Void> deleteOffice(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /offices/{id} : Get office
     *
     * @param id  (required)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "getOffice",
        summary = "Get office",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Office.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/offices/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<Office> getOffice(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /offices : Get offices
     * Get list of offices. Supported sort/search fields: &#x60;name&#x60;, &#x60;address&#x60; 
     *
     * @param searchFieldName  (optional)
     * @param searchString  (optional)
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getOffices",
        summary = "Get offices",
        description = "Get list of offices. Supported sort/search fields: `name`, `address` ",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Office.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/offices",
        produces = { "application/json" }
    )
    
    ResponseEntity<List<Office>> getOffices(
        @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
        @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
        @ParameterObject final Pageable pageable
    );


    /**
     * PUT /offices/{id} : Update office
     *
     * @param id  (required)
     * @param officeCreateUpdate  (optional)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "updateOffice",
        summary = "Update office",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Office.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/offices/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<Office> updateOffice(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
        @Parameter(name = "OfficeCreateUpdate", description = "") @Valid @RequestBody(required = false) OfficeCreateUpdate officeCreateUpdate
    );

}
