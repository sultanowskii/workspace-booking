/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.wb.gen.api;

import org.wb.gen.model.EmployeeGroup;
import org.wb.gen.model.EmployeeGroupCreateUpdate;
import org.wb.gen.model.Error;
import org.springframework.data.domain.Pageable;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "employeeGroups", description = "the employeeGroups API")
public interface EmployeeGroupsApi {

    /**
     * POST /employeeGroups : Create employee group
     *
     * @param employeeGroupCreateUpdate  (required)
     * @return Success (status code 201)
     *         or Invalid request (status code 400)
     *         or Permission denied (status code 403)
     */
    @Operation(
        operationId = "createEmployeeGroup",
        summary = "Create employee group",
        responses = {
            @ApiResponse(responseCode = "201", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeGroup.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            }),
            @ApiResponse(responseCode = "403", description = "Permission denied", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        },
        security = {
            @SecurityRequirement(name = "bearerTokenAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/employeeGroups",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<EmployeeGroup> createEmployeeGroup(
        @Parameter(name = "EmployeeGroupCreateUpdate", description = "", required = true) @Valid @RequestBody EmployeeGroupCreateUpdate employeeGroupCreateUpdate
    );


    /**
     * DELETE /employeeGroups/{id} : Delete employee group
     *
     * @param id  (required)
     * @return Success (status code 204)
     *         or Permission denied (status code 403)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "deleteEmployeeGroup",
        summary = "Delete employee group",
        responses = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Permission denied", content = {
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
        method = RequestMethod.DELETE,
        value = "/employeeGroups/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<Void> deleteEmployeeGroup(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /employeeGroups/{id} : Get employee group
     *
     * @param id  (required)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "getEmployeeGroup",
        summary = "Get employee group",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeGroup.class))
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
        value = "/employeeGroups/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<EmployeeGroup> getEmployeeGroup(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /employeeGroups : Get employee groups
     * Get list of employee groups. Supported sort/search fields: &#x60;name&#x60; 
     *
     * @param searchFieldName  (optional)
     * @param searchString  (optional)
     * @param officeId  (optional)
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getEmployeeGroups",
        summary = "Get employee groups",
        description = "Get list of employee groups. Supported sort/search fields: `name` ",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EmployeeGroup.class)))
            })
        },
        security = {
            @SecurityRequirement(name = "bearerTokenAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/employeeGroups",
        produces = { "application/json" }
    )
    
    ResponseEntity<List<EmployeeGroup>> getEmployeeGroups(
        @Parameter(name = "searchFieldName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchFieldName", required = false) String searchFieldName,
        @Size(min = 1) @Parameter(name = "searchString", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "searchString", required = false) String searchString,
        @Parameter(name = "officeId", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "officeId", required = false) Long officeId,
        @ParameterObject final Pageable pageable
    );


    /**
     * PUT /employeeGroups/{id} : Update employee group
     *
     * @param id  (required)
     * @param employeeGroupCreateUpdate  (required)
     * @return Success (status code 200)
     *         or Invalid request (status code 400)
     *         or Permission denied (status code 403)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "updateEmployeeGroup",
        summary = "Update employee group",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeGroup.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            }),
            @ApiResponse(responseCode = "403", description = "Permission denied", content = {
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
        value = "/employeeGroups/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<EmployeeGroup> updateEmployeeGroup(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
        @Parameter(name = "EmployeeGroupCreateUpdate", description = "", required = true) @Valid @RequestBody EmployeeGroupCreateUpdate employeeGroupCreateUpdate
    );

}
