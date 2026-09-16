package org.example.capston1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capston1.Model.Product;
import org.example.capston1.Service.ProductService;
import org.example.capston1.api.apiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProducts(){
        ArrayList<Product> products = productService.getProducts();
        return ResponseEntity.status(200).body(products);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        int success = productService.addProduct(product);

        if(success==1){
            return ResponseEntity.status(400).body(new apiResponse("id is taken or already exist"));
        } else if (success ==0) {
            return ResponseEntity.status(400).body(new apiResponse("category id not found"));
        }
        return ResponseEntity.status(200).body(new apiResponse("product added successfully"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id,@RequestBody @Valid Product product,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        int success = productService.updateProduct(id,product);

        if(success==1){
            return ResponseEntity.status(400).body(new apiResponse("category id not found"));
        } else if (success==0) {
            return ResponseEntity.status(400).body(new apiResponse("id not found"));
        }

            return ResponseEntity.status(200).body(new apiResponse("product updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        boolean success = productService.deleteProduct(id);

        if(success){
            return ResponseEntity.status(200).body(new apiResponse("product deleted successfully"));
        }
        return ResponseEntity.status(400).body(new apiResponse("id not found"));
    }

    @GetMapping("/searchByCategory/{category}")
    public ResponseEntity<?> searchByCategory(@PathVariable String category){
        ArrayList<Product> found = productService.searchByCategory(category);

        if(found.isEmpty()){
            return ResponseEntity.status(400).body(new apiResponse("there is no product with this category"));
        }


        return ResponseEntity.status(200).body(found);
    }

    @PutMapping("/discount/{productid}/{percentage}")
    public ResponseEntity<?> discount(@PathVariable String productid,@PathVariable double percentage){
        boolean success = productService.discount(productid, percentage);

        if(success){
            return ResponseEntity.status(200).body(new apiResponse("discount applied successfully"));
        }
        return ResponseEntity.status(400).body(new apiResponse("product id not found"));
    }
}
