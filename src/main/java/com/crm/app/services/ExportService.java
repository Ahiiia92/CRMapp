package com.crm.app.services;

import com.crm.app.models.Contact;
import javassist.bytecode.ByteArray;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public interface ExportService {
    void export(HttpServletResponse response, List<Contact> listContacts) throws IOException;
}
