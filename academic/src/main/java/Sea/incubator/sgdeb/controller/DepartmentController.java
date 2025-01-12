package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.dto.DepartementDTO;
import Sea.incubator.sgdeb.service.DepartementService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/academics/department")
public class DepartmentController {
    private  final DepartementService departementService;

    public DepartmentController(DepartementService departementService) {
        this.departementService = departementService;
    }

    @PostMapping("/add")
    @Operation(summary = "Create a new Departement", description = "Creates a new Departement.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departement created successfully")
    })
    public ResponseEntity<DepartementDTO> createDepartment(
            @RequestBody DepartementDTO departementDTO) {

        DepartementDTO departementDTO1 = departementService.createDepartement(departementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(departementDTO1);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all Departements", description = "Retrieves a list of all Departements.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departements retrieved successfully")
    })
    public ResponseEntity<List<DepartementDTO>> getDepartmentList() {
        return ResponseEntity.status(HttpStatus.OK).body(departementService.getDapartmentListMarkedAsNotDeleted());
    }

    @GetMapping("/{nom}/department")
    @Operation(summary = "Get Departement by name", description = "Retrieves a Departement by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departement retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Departement not found")
    })
    public ResponseEntity<DepartementDTO> getDepartmentByName(
            @Parameter(description = "Name of the Departement to retrieve") @PathVariable String nom) {

        if (departementService.getDepartement(nom) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(departementService.getDepartement(nom));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/get")
    @Operation(summary = "Get Departement by ID", description = "Retrieves a Departement by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departement retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Departement not found")
    })
    public ResponseEntity<DepartementDTO> getDepartmentById(
            @Parameter(description = "ID of the Departement to retrieve") @PathVariable UUID id) {

        if (departementService.getDepartement(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(departementService.getDepartement(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}/change")
    @Operation(summary = "Update an existing Departement", description = "Updates an existing Departement.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departement updated successfully"),
            @ApiResponse(responseCode = "404", description = "Departement not found")
    })
    public ResponseEntity<DepartementDTO> updateDepartment(
            @RequestBody DepartementDTO departementDTO,
            @Parameter(description = "ID of the Departement to update") @PathVariable UUID id) {

        if (departementService.getDepartement(id) != null) {
            DepartementDTO newDepartment = departementService.updateDepartement(departementDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body(newDepartment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete a Departement", description = "Deletes an existing Departement by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departement deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Departement not found")
    })
    public ResponseEntity<String> deleteDepartment(
            @Parameter(description = "ID of the Departement to delete") @PathVariable UUID id) {

        if (departementService.deleteDepartement(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\" : \"The operation of deleting was successful\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Department does not exist\"}");
        }
    }
}
