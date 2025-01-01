/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.wb.gen.api;

import org.wb.gen.model.Employee;
import org.wb.gen.model.EmployeeCreate;
import org.wb.gen.model.EmployeeUpdate;
import org.wb.gen.model.Error;
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
@Tag(name = "employees", description = "the employees API")
public interface EmployeesApi {

    /**
     * POST /employees : Create employee
     *
     * @param employeeCreate  (optional)
     * @return Success (status code 201)
     */
    @Operation(
        operationId = "createEmployee",
        summary = "Create employee",
        responses = {
            @ApiResponse(responseCode = "201", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/employees",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<Void> createEmployee(
        @Parameter(name = "EmployeeCreate", description = "") @Valid @RequestBody(required = false) EmployeeCreate employeeCreate
    );


    /**
     * DELETE /employees/{id} : Delete employee
     *
     * @param id  (required)
     * @return Success (status code 204)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "deleteEmployee",
        summary = "Delete employee",
        responses = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/employees/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<Void> deleteEmployee(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /employees/{id} : Get employee
     *
     * @param id  (required)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "getEmployee",
        summary = "Get employee",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/employees/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<Employee> getEmployee(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /employees : Get employees
     *
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getEmployees",
        summary = "Get employees",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Employee.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/employees",
        produces = { "application/json" }
    )
    
    ResponseEntity<List<Employee>> getEmployees(
        
    );


    /**
     * PUT /employees/{id} : Update employee
     *
     * @param id  (required)
     * @param employeeUpdate  (optional)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "updateEmployee",
        summary = "Update employee",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/employees/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<Employee> updateEmployee(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
        @Parameter(name = "EmployeeUpdate", description = "") @Valid @RequestBody(required = false) EmployeeUpdate employeeUpdate
    );

}
