package com.example.product_inventory.Controller;

import com.example.product_inventory.DTO.ProductRequestDTO;
import com.example.product_inventory.DTO.ProductResponseDTO;
import com.example.product_inventory.Entity.Product;
import com.example.product_inventory.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService productService;
  @GetMapping("getAll")
    public List<ProductResponseDTO> getAllProducts() {
      return productService.getAllProducts();
  }

  @PostMapping("create")
  public ProductResponseDTO createProduct( @RequestBody ProductRequestDTO productRequestDTO) throws Exception{
      return productService.createProduct(productRequestDTO);
  }
  @PutMapping("update/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) throws Exception{
      return productService.updateProduct(id,productRequestDTO);
  }
  @DeleteMapping("delete/{id}")
    public String deleteProduct(@PathVariable Long id) throws Exception{
     productService.deleteProduct(id);
     return "Product with id : " + id + " Soft deleted successfully";

  }
  @GetMapping("getById/{Id}")
    public ProductResponseDTO getProductById(@PathVariable Long Id) throws Exception{
      return productService.getProductById(Id);
  }
}
