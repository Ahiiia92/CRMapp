package com.crm.app.services;

import com.crm.app.models.Viewing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("Viewing Service")
public interface ViewingService {
    List<Viewing> getAllViewings();

    Viewing createViewing(Viewing viewing);
}
