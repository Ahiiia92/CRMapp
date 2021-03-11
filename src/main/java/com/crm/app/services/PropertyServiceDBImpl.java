package com.crm.app.services;

import com.crm.app.models.Property;
import com.crm.app.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PropertyServiceDBImpl implements PropertyService {
    @Autowired
    private PropertyRepository repo;

    @Override
    public List<Property> findAll() {
        return repo.findAll();
    }
}
