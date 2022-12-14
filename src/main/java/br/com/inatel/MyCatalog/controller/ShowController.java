package br.com.inatel.MyCatalog.controller;

import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.form.ShowForm;
import br.com.inatel.MyCatalog.service.ShowService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/show")
@AllArgsConstructor
public class ShowController {

    private ShowService showService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShowDto> addNewShow(@RequestBody @Valid ShowForm showDto){
        return showService.addNewShow(showDto);
    }

//    @GetMapping("{type}/details")
//    public ResponseEntity<List<ShowDto>> findAllShowsWithDetails(@PathVariable String type){
//        return showService.findAllShowsWithDetails(type);
//    }
//
//    @GetMapping
//    public ResponseEntity<?> findShowByTitle(@RequestParam(required = false) Optional<String> title){
//        return showService.findShowByTitle(title);
//    }

    @GetMapping
    public ResponseEntity<?> findShows(
            @RequestParam(required = false) Optional<String> title,
            @RequestParam(required = false) Optional<String> type){
        return showService.findShows(title,type);
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<ShowDto> deleteShow(@PathVariable String title){
        return showService.deleteShow(title);
    }

}
