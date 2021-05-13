package com.crm.app.services;

import com.crm.app.models.Viewing;
import com.crm.app.repositories.ViewingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViewingServiceDBImpl implements ViewingService {
    @Autowired
    private ViewingRepository repo;

    @Override
    public List<Viewing> getAllViewings() {
        return repo.findAll();
    }

    @Override
    public Viewing createViewing(Viewing viewing) {
        return repo.save(viewing);
    }
}
