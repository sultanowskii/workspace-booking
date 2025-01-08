/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.wb.gen.api;

import org.wb.gen.model.Error;
import org.springframework.data.domain.Pageable;
import org.springdoc.core.annotations.ParameterObject;
import org.wb.gen.model.Workplace;
import org.wb.gen.model.WorkplaceCreate;
import org.wb.gen.model.WorkplaceUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@Tag(name = "workplaces", description = "the workplaces API")
public interface WorkplacesApi {

    /**
     * POST /workplaces : Create workplace
     *
     * @param workplaceCreate  (required)
     * @return Success (status code 201)
     *         or Invalid request (status code 400)
     */
    @Operation(
        operationId = "createWorkplace",
        summary = "Create workplace",
        responses = {
            @ApiResponse(responseCode = "201", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Workplace.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        },
        security = {
            @SecurityRequirement(name = "bearerTokenAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/workplaces",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<Workplace> createWorkplace(
        @Parameter(name = "WorkplaceCreate", description = "", required = true) @Valid @RequestBody WorkplaceCreate workplaceCreate
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
        },
        security = {
            @SecurityRequirement(name = "bearerTokenAuth")
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
        },
        security = {
            @SecurityRequirement(name = "bearerTokenAuth")
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
     * Get list of workplaces. Supported sort/search fields: &#x60;numberOfMonitors&#x60; 
     *
     * @param roomId Room ID (required)
     * @param searchFieldName  (optional)
     * @param searchString  (optional)
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getWorkplaces",
        summary = "Get workplaces",
        description = "Get list of workplaces. Supported sort/search fields: `numberOfMonitors` ",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Workplace.class)))
            })
        },
        security = {
            @SecurityRequirement(name = "bearerTokenAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/workplaces",
        produces = { "application/json" }
    )
    
    ResponseEntity<List<Workplace>> getWorkplaces(
        @NotNull @Parameter(name = "roomId", description = "Room ID", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "roomId", required = true) Long roomId,
        @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
        @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
        @ParameterObject final Pageable pageable
    );


    /**
     * PUT /workplaces/{id} : Update workplace
     *
     * @param id  (required)
     * @param workplaceUpdate  (required)
     * @return Success (status code 200)
     *         or Invalid request (status code 400)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "updateWorkplace",
        summary = "Update workplace",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Workplace.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        },
        security = {
            @SecurityRequirement(name = "bearerTokenAuth")
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
        @Parameter(name = "WorkplaceUpdate", description = "", required = true) @Valid @RequestBody WorkplaceUpdate workplaceUpdate
    );

}
