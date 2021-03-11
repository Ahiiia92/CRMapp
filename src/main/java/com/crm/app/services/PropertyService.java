package com.crm.app.services;

import com.crm.app.models.Property;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public interface PropertyService {
    List<Property> findAll();
}
