package com.example.product_inventory.Service;

import com.example.product_inventory.DTO.ProductRequestDTO;
import com.example.product_inventory.DTO.ProductResponseDTO;
import com.example.product_inventory.Entity.Product;
import com.example.product_inventory.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;


    public List<ProductResponseDTO> getAllProducts() {
        //get all active products from DB
            List<Product>activeProducts=repository.findByActiveTrue();
            //create empty new list for responseDTO
        List<ProductResponseDTO>productResponseDTOS=new ArrayList<>();
        //loop through each product(entity) and convert to response DTO
        for(Product product:activeProducts){
            productResponseDTOS.add(convertEntityToResponseDTO(product));
        }
        return productResponseDTOS;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO request) throws Exception {
        validateProduct(request);
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        product.setActive(true);
        product.setCreatedAt(LocalDate.now());
        product.setUpdatedAt(LocalDate.now());

        Product savedProduct = repository.save(product);
      return convertEntityToResponseDTO(savedProduct);

    }

    public ProductResponseDTO updateProduct(Long id ,ProductRequestDTO productRequest) throws Exception {
        validateProduct(productRequest);
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new Exception("Product with id : + " +id + " is not found"));
        if(!existingProduct.getActive()){
            throw new Exception("Product with id : + "+ id + " is inActive");
        }
        existingProduct.setName(productRequest.getName());
        existingProduct.setCategory(productRequest.getCategory());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setUpdatedAt(LocalDate.now());
       Product updatedProduct= repository.save(existingProduct);
        return convertEntityToResponseDTO(updatedProduct);
    }

    public void deleteProduct(Long id)throws Exception{
       Product productToDelete =repository.findById(id)
               .orElseThrow(() ->
                       new RuntimeException("Product with id " + id + " not found"));
             productToDelete.setActive(false);
             productToDelete.setUpdatedAt(LocalDate.now());
             repository.save(productToDelete);
    }

    public ProductResponseDTO getProductById(Long id) throws Exception {
        Product product= repository.findById(id).orElseThrow(()-> new Exception("Product with id : + "+ id + " is not found"));

        if(!product.getActive()){
            throw new Exception("Product with id : + "+ id + " is inActive");
        }
        return convertEntityToResponseDTO(product);
    }

    //check user input validation
    public void validateProduct(ProductRequestDTO requestDto) throws Exception {
        if (requestDto.getName() == null || requestDto.getName().trim().isEmpty()) {
            throw new Exception("Product name cannot be empty");

        }
        if (requestDto.getCategory() == null || requestDto.getCategory().trim().isEmpty()) {
            throw new Exception("Product Category cannot be empty");

        }
        if (requestDto.getPrice() == null || requestDto.getPrice() <= 0) {
            throw new Exception("Product price must be greater than zero");

        }
        if (requestDto.getQuantity() == null || requestDto.getQuantity() < 0) {
            throw new Exception("Product price cannot be null or less than zero");

        }
    }

    //Helper method to convert Entity to response DTO
    public ProductResponseDTO convertEntityToResponseDTO(Product product) {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setCategory(product.getCategory());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setQuantity(product.getQuantity());
        responseDTO.setActive(product.getActive());
        responseDTO.setCreatedAt(product.getCreatedAt());
        responseDTO.setUpdatedAt(product.getUpdatedAt());
        return responseDTO;

    }


}

