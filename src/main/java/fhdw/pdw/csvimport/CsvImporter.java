package fhdw.pdw.csvimport;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Klasse, um die Produkt CSV Datei in Java Data Transfer Objekte zu überführen
 */
@Service
public class CsvImporter {
  public <T> List<T> loadObjectList(Class<T> type, MultipartFile file) {
    try {
      CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
      CsvMapper mapper = new CsvMapper();
      MappingIterator<T> readValues =
          mapper.readerFor(type).with(bootstrapSchema).readValues(file.getBytes());
      return readValues.readAll();
    } catch (Exception e) {
      throw new RuntimeException("Error import csv file contents." + "\n" + e);
    }
  }
}
