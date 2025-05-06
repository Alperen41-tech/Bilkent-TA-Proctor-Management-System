package com.cs319group3.backend.Services;

import java.io.IOException;

public interface ExcelService {

    public byte[] generateExcelFromTemplate() throws IOException;
}
