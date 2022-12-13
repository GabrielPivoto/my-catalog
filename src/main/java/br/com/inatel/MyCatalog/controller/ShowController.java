package br.com.inatel.MyCatalog.controller;

import br.com.inatel.MyCatalog.adapter.Adapter;
import br.com.inatel.MyCatalog.model.dto.ShowDto;
import br.com.inatel.MyCatalog.model.form.ShowForm;
import br.com.inatel.MyCatalog.service.ShowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/show")
public class ShowController {

    private Adapter adapter;
    private ShowService showService;

    public ShowController(Adapter adapter, ShowService showService){
        this.adapter = adapter;
        this.showService = showService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShowDto> addNewShow(@RequestBody @Valid ShowForm showDto){
        return showService.addNewShow(showDto);
    }

    @GetMapping("/{title}")
    public ResponseEntity<ShowDto> findShowById(@PathVariable String title){
        return showService.findShowById(title);
    }

}
