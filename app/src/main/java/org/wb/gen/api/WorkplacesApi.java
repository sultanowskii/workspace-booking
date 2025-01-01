/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.wb.gen.api;

import org.wb.gen.model.Error;
import org.wb.gen.model.Workplace;
import org.wb.gen.model.WorkplaceCreateUpdate;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-01T12:50:04.846966322+03:00[Europe/Moscow]", comments = "Generator version: 7.10.0")
@Validated
@Controller
@Tag(name = "workplaces", description = "the workplaces API")
public interface WorkplacesApi {

    /**
     * POST /workplaces : Create workplace
     *
     * @param workplaceCreateUpdate  (optional)
     * @return Success (status code 201)
     */
    @Operation(
        operationId = "createWorkplace",
        summary = "Create workplace",
        responses = {
            @ApiResponse(responseCode = "201", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/workplaces",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<Void> createWorkplace(
        @Parameter(name = "WorkplaceCreateUpdate", description = "") @Valid @RequestBody(required = false) WorkplaceCreateUpdate workplaceCreateUpdate
    );


    /**
     * DELETE /workplaces/{id} : Delete workplace
     *
     * @param id  (required)
     * @return Success (status code 201)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "deleteWorkplace",
        summary = "Delete workplace",
        responses = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/workplaces/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<Void> deleteWorkplace(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /workplaces/{id} : Get workplace
     *
     * @param id  (required)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "getWorkplace",
        summary = "Get workplace",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Workplace.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/workplaces/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<Workplace> getWorkplace(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /workplaces : Get workplaces
     *
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getWorkplaces",
        summary = "Get workplaces",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Workplace.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/workplaces",
        produces = { "application/json" }
    )
    
    ResponseEntity<List<Workplace>> getWorkplaces(
        
    );


    /**
     * PUT /workplaces/{id} : Update workplace
     *
     * @param id  (required)
     * @param workplaceCreateUpdate  (optional)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "updateWorkplace",
        summary = "Update workplace",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Workplace.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/workplaces/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<Workplace> updateWorkplace(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
        @Parameter(name = "WorkplaceCreateUpdate", description = "") @Valid @RequestBody(required = false) WorkplaceCreateUpdate workplaceCreateUpdate
    );

}