package com.crm.app.controllers;

import com.crm.app.exceptions.ResourceNotFoundException;
import com.crm.app.models.Property;
import com.crm.app.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Property Controller to manage properties in the CRM
 * @author maroussiaarnault
 */

@RestController
@CrossOrigin
@RequestMapping("api/v1/")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    /**
     * Get all properties
     * @return all properties
     */

    @GetMapping("/properties")
    public ResponseEntity<List<Property>> getAllProperties() {
        return ResponseEntity.ok().body(propertyService.findAll());
    }

    /**
     *  Create a New Property
     * @param property
     * @return 201 created status
     */

    @PostMapping("/properties")
    public ResponseEntity<Void> createProperty(@RequestBody Property property) {
        Property propCreated = propertyService.createProperty(property);

        if (propCreated == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(propCreated.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Get a specific property by id
     * @param id
     * @return specific property
     * @throws ResourceNotFoundException
     */
    @GetMapping("/properties/{id}")
    public ResponseEntity<Property> getAProperty(@PathVariable Integer id) throws ResourceNotFoundException {
        Property property = propertyService.getPropertyById(id);
        return ResponseEntity.ok().body(property);
    }
}
