package quru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileParsingTest {
ClassLoader cl = FileParsingTest.class.getClassLoader();
    @Test
    void pdfParseTest() throws Exception{
open("https://docs.junit.org/5.0.0/user-guide");
        File downloadedPdf = $("a[href='index.pdf']").download();
        try (InputStream is = new FileInputStream(downloadedPdf)) {
            PDF content = new PDF(downloadedPdf);
            assertThat(content.author).contains("Sam Brannen");


        }
    }
    @Test
    void xlsParseTest() throws Exception {
       try (InputStream resourceAsStream = cl.getResourceAsStream("homeworkfile/filecsv.csv")) {
           XLS content = new XLS(resourceAsStream);
           assertThat(content.excel.getSheetAt(0).getRow(2).getCell(2).getStringCellValue());
       }
        }
        @Test
    void csvParseTest() throws Exception{
            try (
                    InputStream resource = cl.getResourceAsStream("homeworkfile/filecsv.csv");
                    CSVReader reader = new CSVReader(new InputStreamReader(resource))
            ){
                List<String[]> content = reader.readAll();
                assertThat(content.get(1)[0]).contains("Williams");
            }
        }
    @Test
    void zipParseTest() throws Exception{
        try (InputStream resource = cl.getResourceAsStream("homeworkfile/filetest.zip");
             ZipInputStream zis = new ZipInputStream(resource)){
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) !=null) {

                if (entry.getName().equals("filepdf.pdf")) {
                    PDF pdf = new PDF(zis);
                    assertThat(pdf.text).contains("Никакой полезной информации он не несёт.");
                }
                else if (entry.getName().equals("filecsv.csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(0)).contains("id;first_name;last_name;email;age;city;salary;joined");
                }
                else if (entry.getName().equals("filexlsx.xlsx")) {
                    XLS xls = new XLS(zis);
                    String cellValue = xls.excel.getSheetAt(0).getRow(2).getCell(2).getStringCellValue();
                    assertThat(cellValue).isEqualTo("Female");
                }
            }
        }
    }
    }

