package br.com.inatel.MyCatalog.controller;

import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.form.ShowForm;
import br.com.inatel.MyCatalog.model.rest.Show;
import br.com.inatel.MyCatalog.service.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Gabriel Pivoto
 * @since dec 2022
 */
@RestController
@RequestMapping("/show")
@AllArgsConstructor
public class ShowController {

    private ShowService showService;

    @Operation(summary = "Add a new show.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Show successfully added to database.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShowDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid personal score.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Show not found or doesn't exist.",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<ShowDto> addNewShow(@RequestBody @Valid ShowForm showForm){
        return ResponseEntity.created(null).body(showService.addNewShow(showForm));
    }

    @Operation(summary = "Find all shows or find shows by type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All shows",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShowDto.class)) })})
    @GetMapping
    public ResponseEntity<List<ShowDto>> findShows(
            @RequestParam(required = false) Optional<String> type) {
        return ResponseEntity.ok().body(showService.findShows(type));
    }

    @Operation(summary = "Get a show by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the show",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Show.class)) }),
            @ApiResponse(responseCode = "404", description = "Show not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ShowDto> findShow(@PathVariable int id) {
        return ResponseEntity.ok().body(showService.findShow(id));
    }

    @Operation(summary = "Update show.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show updated.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Show.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid personal score.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Show not found.",
                    content = @Content) })
    @PatchMapping
    public ResponseEntity<ShowDto> updateShow(@RequestBody ShowForm showForm){
        return ResponseEntity.ok().body(showService.updateShow(showForm));
    }

    @Operation(summary = "Delete a show by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Show deleted.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Show not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShow(@PathVariable int id){
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }

}
