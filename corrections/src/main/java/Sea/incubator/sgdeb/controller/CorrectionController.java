package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.dto.CorrectionDto;
import Sea.incubator.sgdeb.service.CorrectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.itextpdf.text.*;
@RestController
@RequestMapping("/corrections")
public class CorrectionController {
     private final CorrectionService correctionService;

    public CorrectionController(CorrectionService correctionService) {
        this.correctionService = correctionService;
    }

    @PostMapping("/add/")
    @Operation(summary = "Create a new correction", description = "Creates a new correction of participe and updates this participe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Correction created successfully"),
            @ApiResponse(responseCode = "404", description = "Not found of participe or examen to correct")
    })
    public ResponseEntity<CorrectionDto> createCorrection(
            @RequestBody CorrectionDto correctionDto,
            @Parameter(description = "The ID of the exam") @RequestParam UUID examen_id,
            @Parameter(description = "The ID of the participe") @RequestParam UUID participe_id) {
        CorrectionDto correction = correctionService.createCorrection(correctionDto, examen_id, participe_id);
        if (correction != null)
            return new ResponseEntity<>(correction, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a correction by ID", description = "Retrieves a correction for the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correction retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Correction not found")
    })
    public ResponseEntity<CorrectionDto> getCorrection(@PathVariable UUID id) {
        CorrectionDto correction = correctionService.getCorrection(id);
        if (correction != null)
            return new ResponseEntity<>(correction, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{anonymat}/anonymat")
    @Operation(summary = "Get a correction by anonymat", description = "Retrieves a correction for the given anonymat value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correction retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Correction not found")
    })
    public ResponseEntity<CorrectionDto> getCorrection(@PathVariable Integer anonymat) {
        CorrectionDto correction = correctionService.getCorrection(anonymat);
        if (correction != null)
            return new ResponseEntity<>(correction, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/change/{participe_id}")
    @Operation(summary = "Update a correction", description = "Updates a correction with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correction updated successfully"),
            @ApiResponse(responseCode = "404", description = "Correction not found")
    })
    public ResponseEntity<CorrectionDto> updateCorrection(
            @RequestBody CorrectionDto correctionDto,
            @PathVariable UUID id,
            @PathVariable UUID participe_id) {
        CorrectionDto correction = correctionService.updateCorrection(correctionDto, id, participe_id);
        if (correction != null)
            return new ResponseEntity<>(correction, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/change-status")
    @Operation(summary = "Update the status of a correction", description = "Updates the status of a correction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correction status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Correction not found")
    })
    public ResponseEntity<CorrectionDto> updateStatus(
            @RequestBody CorrectionDto correctionDto,
            @PathVariable UUID id) {
        CorrectionDto correction = correctionService.updateStatut(correctionDto, id);
        if (correction != null)
            return new ResponseEntity<>(correction, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete a correction", description = "Deletes a correction by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Correction not found")
    })
    public ResponseEntity<String> deleteCorrection(@PathVariable UUID id) {
        Boolean isDeleted = correctionService.deleteCorrection(id);
        if (isDeleted)
            return new ResponseEntity<>("{\"message\":\"La suppression est réussie\"}", HttpStatus.OK);
        else
            return new ResponseEntity<>("{\"message\":\"La suppression a échoué\"}", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all corrections", description = "Retrieves a list of all corrections.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Corrections retrieved successfully"),
    })
    public ResponseEntity<List<CorrectionDto>> getAllCorrection() {
        return new ResponseEntity<>(correctionService.getCorrectionList(), HttpStatus.OK);
    }
}
