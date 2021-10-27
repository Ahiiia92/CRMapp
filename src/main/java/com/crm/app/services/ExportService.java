package com.crm.app.services;

import javassist.bytecode.ByteArray;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public interface ExportService {
    void export(HttpServletResponse response) throws IOException;
}
