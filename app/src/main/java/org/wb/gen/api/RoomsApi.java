/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.10.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.wb.gen.api;

import org.wb.gen.model.Error;
import org.wb.gen.model.Room;
import org.wb.gen.model.RoomCreateUpdate;
import org.wb.gen.model.RoomWithWalls;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
@Validated
@Controller
@Tag(name = "rooms", description = "the rooms API")
public interface RoomsApi {

    /**
     * POST /rooms : Create room
     *
     * @param roomCreateUpdate  (optional)
     * @return Success (status code 201)
     */
    @Operation(
        operationId = "createRoom",
        summary = "Create room",
        responses = {
            @ApiResponse(responseCode = "201", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/rooms",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<Void> createRoom(
        @Parameter(name = "RoomCreateUpdate", description = "") @Valid @RequestBody(required = false) RoomCreateUpdate roomCreateUpdate
    );


    /**
     * DELETE /rooms/{id} : Delete room
     *
     * @param id  (required)
     * @return Success (status code 201)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "deleteRoom",
        summary = "Delete room",
        responses = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/rooms/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<Void> deleteRoom(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /rooms/{id} : Get room
     *
     * @param id  (required)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "getRoom",
        summary = "Get room",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RoomWithWalls.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/rooms/{id}",
        produces = { "application/json" }
    )
    
    ResponseEntity<RoomWithWalls> getRoom(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * GET /rooms : Get rooms
     *
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getRooms",
        summary = "Get rooms",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Room.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/rooms",
        produces = { "application/json" }
    )
    
    ResponseEntity<List<Room>> getRooms(
        
    );


    /**
     * PUT /rooms/{id} : Update room
     *
     * @param id  (required)
     * @param roomCreateUpdate  (optional)
     * @return Success (status code 200)
     *         or Resource Not Found (status code 404)
     */
    @Operation(
        operationId = "updateRoom",
        summary = "Update room",
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RoomWithWalls.class))
            }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/rooms/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<RoomWithWalls> updateRoom(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
        @Parameter(name = "RoomCreateUpdate", description = "") @Valid @RequestBody(required = false) RoomCreateUpdate roomCreateUpdate
    );

}
