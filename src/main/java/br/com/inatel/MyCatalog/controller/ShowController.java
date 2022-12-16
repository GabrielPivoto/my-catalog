package br.com.inatel.MyCatalog.controller;

import br.com.inatel.MyCatalog.mapper.Mapper;
import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.dto.ShowSimpleDto;
import br.com.inatel.MyCatalog.model.form.ShowForm;
import br.com.inatel.MyCatalog.service.ShowService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/show")
@AllArgsConstructor
public class ShowController {

    private ShowService showService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShowDto> addNewShow(@RequestBody @Valid ShowForm showForm){
        return ResponseEntity.created(null).body(showService.addNewShow(showForm));
    }

    @GetMapping
    public ResponseEntity<List<ShowDto>> findShows(
            @RequestParam(required = false) Optional<String> type) {
        return ResponseEntity.ok().body(showService.findShows(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowDto> findShow(@PathVariable int id) {
        return ResponseEntity.ok().body(showService.findShow(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ShowDto> deleteShow(@PathVariable int id){
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }

}
