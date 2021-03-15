package com.crm.app.services;

import com.crm.app.exceptions.ResourceNotFoundException;
import com.crm.app.models.Property;
import com.crm.app.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PropertyServiceDBImpl implements PropertyService {
    @Autowired
    private PropertyRepository repo;

    @Override
    public List<Property> findAll() {
        return repo.findAll();
    }

    @Override
    public Property createProperty(Property property) {
        return repo.save(property);
    }

    @Override
    public Property getPropertyById(Integer id) throws ResourceNotFoundException {
        Optional<Property> optionalProperty = repo.findById(id);
        if (optionalProperty.isPresent()) {
            return optionalProperty.get();
        } else {
            throw new ResourceNotFoundException("Property not found");
        }
    }

    @Override
    public Property editProperty(Integer id, Property property) throws ResourceNotFoundException {
        Optional<Property> optProperty = repo.findById(id);
        if (optProperty.isPresent()) {
            return  repo.save(property);
        } else {
            throw new ResourceNotFoundException("Property not found");
        }
    }

    @Override
    public void deleteProperty(Integer id) throws ResourceNotFoundException {
        Optional<Property> optionalProperty = repo.findById(id);
        if (optionalProperty.isPresent()) {
            repo.delete(optionalProperty.get());
        } else {
            throw new ResourceNotFoundException("Property not found");
        }
    }
}
