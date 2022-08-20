package com.crm.app.controllers;

import com.crm.app.exceptions.ResourceNotFoundException;
import com.crm.app.models.Property;
import com.crm.app.services.PropertyService;
import com.crm.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserService userService;

    /**
     * Get all properties
     * @return all properties
     */

    @GetMapping("/properties")
    public ResponseEntity<List<Property>> getAllProperties() {
        userService.getCurrentUser();
        return ResponseEntity.ok().body(propertyService.findAll());
    }

    /**
     *  Create a New Property
     * @param property
     * @return 201 created status
     */

    @PostMapping("/properties")
    public ResponseEntity<Void> createProperty(@RequestBody Property property) {
        userService.getCurrentUser();
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
        userService.getCurrentUser();
        Property property = propertyService.getPropertyById(id);
        return ResponseEntity.ok().body(property);
    }

    /**
     * Update a property
     * @param id
     * @param property
     * @return 202 Status Code
     * @throws ResourceNotFoundException
     */

    @PutMapping("/properties/{id}")
    public ResponseEntity<Void> updateProperty(@PathVariable Integer id, @RequestBody Property property) throws ResourceNotFoundException {
        userService.getCurrentUser();
        Property propUpdated = propertyService.editProperty(id, property);
        return ResponseEntity.accepted().build();
    }

    /**
     * Delete a Property by Id
     * @param id
     * @return Key/value pair with "deleted : true"
     * @throws ResourceNotFoundException
     */

    @DeleteMapping("/properties/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProperty(@PathVariable Integer id) throws ResourceNotFoundException {
        userService.getCurrentUser();
        propertyService.deleteProperty(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok().body(response);
    }
}
