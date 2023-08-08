package school.hei.haapi.service.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@NoArgsConstructor
public class FilePdfUtils {

  public File byteArrayToFile(byte[] pdfBytes, String name) {
    try (OutputStream out = new FileOutputStream(name+".pdf")) {
      out.write(pdfBytes);
      return new File(name+".pdf");
    } catch (IOException e) {
      throw new RuntimeException("Error writing PDF to file", e);
    }
  }


}
