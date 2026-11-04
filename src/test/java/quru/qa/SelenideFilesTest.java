package quru.qa;

import com.codeborne.selenide.ex.FileNotDownloadedError;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelenideFilesTest {
    @Test
    void selenideDownloadTest() throws Exception {
        open("https://github.com/selenide/selenide-appium/blob/main/README.md");
        File downloadFile = $("[data-testid='raw-button']").download();
        try (InputStream is = new FileInputStream(downloadFile)) {
            byte[] bytes = is.readAllBytes();
            String textContent = new String(bytes, StandardCharsets.UTF_8);
            assertThat(textContent).contains("Now you will need to use dependency \"com.codeborne:selenide-appium:6.15.0\" or higher.");

        }
    }
    @Test
    void uploadFile () {
        open("https://demoqa.com/upload-download");
        $("input[type='file']").uploadFromClasspath("homeworkfile/images.jpg");
        System.out.println();


    }












}

