package com.crm.app.controllers;

import com.crm.app.exceptions.NoViewingException;
import com.crm.app.models.Viewing;
import com.crm.app.services.ViewingService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "Endpoints for Creating, Retrieving, Updating and Deleting of Viewings.",
        tags = {"viewing"})
@RestController
@CrossOrigin
@RequestMapping("api/v1/contacts/{id}/")
public class ViewingController {
    private final ViewingService viewingService;

    public ViewingController(ViewingService viewingService) { this.viewingService = viewingService; }

    @ApiOperation(value = "Retrieving all viewings", tags = { "viewings" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = List.class)
    })
    @GetMapping("/viewings")
    public ResponseEntity<List<Viewing>> getViewings() {
        List<Viewing> viewings = viewingService.getAllViewings();
        return ResponseEntity.ok().body(viewings);
    }

    @PostMapping("/viewings")
    public ResponseEntity<Map<String, Boolean>> createViewing(
            @ApiParam("Viewing to add.")
            @RequestBody Viewing viewing) {
        Viewing viewingCreated = viewingService.createViewing(viewing);
        Map<String, Boolean> response = new HashMap<>();
        if (viewingCreated == null) throw new NoViewingException("The viewing with id " + viewing.getId() + "can't be found.");

        response.put("Viewing created", Boolean.TRUE);
        return ResponseEntity.status(201).body(response);
    }

    // TODO: Create the SHOW Method for Viewing so that we can create a note
}
