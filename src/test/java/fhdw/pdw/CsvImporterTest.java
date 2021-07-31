package fhdw.pdw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fhdw.pdw.csvimport.CsvImporter;
import fhdw.pdw.model.dto.ProductDto;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
public class CsvImporterTest extends AbstractFunctionalTestCase {
  @Autowired protected CsvImporter csvImporter;

  @Test
  public void testCsvDtoMapping() {
    String fileContent =
        "name,category,producer,amount,price,allergens,cover\n"
            + "Evian Wasser,Wasser,Evian,\"6 x 0.5 L\",6.50,\"2, 3, 10\",https://ein-Link.de\n"
            + "Evian Wasser,Wasser,Evian,6 x 1 L,14.00,,https://noch-ein-link.de";
    byte[] bytes = fileContent.getBytes(StandardCharsets.UTF_8);

    MockMultipartFile file =
        new MockMultipartFile("Getraenkeliste.csv", "Getraenkeliste.csv", "text/csv", bytes);

    List<ProductDto> dtos = csvImporter.loadObjectList(ProductDto.class, file);

    assertEquals(2, dtos.size());
    ProductDto dto = dtos.get(0);

    assertEquals("Evian Wasser", dto.getName());
    assertEquals("Wasser", dto.getCategory());
    assertEquals(6, dto.getNumberOfContainer());
    assertEquals(0.5f, dto.getAmount());
    assertEquals("L", dto.getUnitTitle());
    assertEquals(6.5f, dto.getPrice());
    assertEquals("2, 3, 10", dto.getAllergens());
    assertEquals("https://ein-Link.de", dto.getCover());
  }
}
