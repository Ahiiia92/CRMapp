package com.crm.app.services;

import javassist.bytecode.ByteArray;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ExportService {
    ByteArray generateXlsxReport();
    ByteArray generateXlsReport() throws IOException;
}
