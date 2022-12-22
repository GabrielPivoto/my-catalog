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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/show")
@AllArgsConstructor
public class ShowController {

    private ShowService showService;

    @Operation(summary = "Post a show by its title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Show added to database",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Show.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Show not found",
                    content = @Content) })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShowDto> addNewShow(@RequestBody @Valid ShowForm showForm){
        return ResponseEntity.created(null).body(showService.addNewShow(showForm));
    }

    @Operation(summary = "Find all shows or find shows by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the shows",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Show.class)) })})
    @GetMapping
    public ResponseEntity<List<ShowDto>> findShows(
            @RequestParam(required = false) Optional<String> type) {
        return ResponseEntity.ok().body(showService.findShows(type));
    }

    @Operation(summary = "Get a show by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the show",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Show.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Show not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ShowDto> findShow(@PathVariable int id) {
        return ResponseEntity.ok().body(showService.findShow(id));
    }

    @Operation(summary = "Delete a show by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the show",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Show.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Show not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable int id){
        showService.deleteShow(id);
        return new ResponseEntity<>("The show has been deleted!",HttpStatus.NO_CONTENT);
    }

}
