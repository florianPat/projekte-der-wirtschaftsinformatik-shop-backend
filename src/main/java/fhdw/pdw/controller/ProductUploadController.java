package fhdw.pdw.controller;

import fhdw.pdw.csvimport.CsvImporter;
import fhdw.pdw.mapper.ProductMapper;
import fhdw.pdw.model.dto.ProductDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductUploadController {
  protected CsvImporter csvImporter;
  protected ProductMapper productMapper;

  public ProductUploadController(CsvImporter csvImporter, ProductMapper productMapper) {
    this.csvImporter = csvImporter;
    this.productMapper = productMapper;
  }

  @GetMapping("/import-product-csv")
  public String uploadProductCsvFileForm(Model model) {
    return "productCsvUploadForm";
  }

  @PostMapping("/import-product-csv")
  public String uploadProductCsvFile(
      @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
    assertFile(file);
    List<ProductDto> list = csvImporter.loadObjectList(ProductDto.class, file);
    productMapper.mapAndReplaceFrom(list);
    redirectAttributes.addFlashAttribute("message", "Import successfully!");
    return "redirect:/import-product-csv";
  }

  protected void assertFile(MultipartFile file) {
    if (file.isEmpty()) {
      throw new RuntimeException("The file was empty!");
    }
    String contentType = file.getContentType();
    if (null == contentType) {
      throw new RuntimeException("The content type could not be retrieved!");
    }
    if (!contentType.equals("text/csv")) {
      throw new RuntimeException("The file needs to be a CSV file!");
    }
  }
}
