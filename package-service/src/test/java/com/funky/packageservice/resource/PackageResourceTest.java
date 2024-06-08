package com.funky.packageservice.resource;

import com.funky.packageservice.service.PackageServiceImpl;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PackageResource.class)
public class PackageResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PackageServiceImpl packageServiceImpl;

    @Mock
    private Workbook mockWorkbook;

    @BeforeEach
    public void setUp() {
        // Setup code, if any
    }

    @Test
    public void testDownloadExcel() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(packageServiceImpl.getWorkbook()).thenReturn(mockWorkbook);
        when(packageServiceImpl.fileName()).thenReturn("orders_20_May.xlsx");
        doNothing().when(mockWorkbook).write(outputStream);

        mockMvc.perform(get("/package/download-excel"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders_20_May.xlsx"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .andExpect(content().bytes(outputStream.toByteArray()));

        verify(packageServiceImpl, times(1)).getWorkbook();
        verify(packageServiceImpl, times(1)).fileName();
        verify(mockWorkbook, times(1)).write(any(ByteArrayOutputStream.class));
    }

    @Test
    public void testSaveExcel() throws Exception {
        when(packageServiceImpl.getWorkbook()).thenReturn(mockWorkbook);
        when(packageServiceImpl.fileName()).thenReturn("orders_20_May.xlsx");
        doNothing().when(mockWorkbook).write(any(FileOutputStream.class));

        mockMvc.perform(post("/package/save-excel"))
                .andExpect(status().isOk())
                .andExpect(content().string("Excel file saved successfully in path"));

        verify(packageServiceImpl, times(1)).getWorkbook();
        verify(packageServiceImpl, times(1)).fileName();
        verify(mockWorkbook, times(1)).write(any(FileOutputStream.class));
    }

    @Test
    public void testDownloadExcelWithIOException() throws Exception {
        when(packageServiceImpl.getWorkbook()).thenReturn(mockWorkbook);
        doThrow(new IOException("Failed to create the Excel file")).when(mockWorkbook).write(any(ByteArrayOutputStream.class));

        mockMvc.perform(get("/package/download-excel"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(""));

        verify(packageServiceImpl, times(1)).getWorkbook();
    }

    @Test
    public void testSaveExcelWithIOException() throws Exception {
        when(packageServiceImpl.getWorkbook()).thenReturn(mockWorkbook);
        when(packageServiceImpl.fileName()).thenReturn("orders_20_May.xlsx");
        doThrow(new IOException("Failed to save Excel file")).when(mockWorkbook).write(any(FileOutputStream.class));

        mockMvc.perform(post("/package/save-excel"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to save Excel file."));

        verify(packageServiceImpl, times(1)).getWorkbook();
    }
}
