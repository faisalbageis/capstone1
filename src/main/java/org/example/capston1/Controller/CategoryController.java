package org.example.capston1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capston1.Model.Category;
import org.example.capston1.Service.CategoryService;
import org.example.capston1.api.apiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getcategories(){
        ArrayList<Category> categories = categoryService.getCategories();
        return ResponseEntity.status(200).body(categories);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

       boolean success = categoryService.addCategory(category);
        if(success){
            return ResponseEntity.status(200).body(new apiResponse("category added successfully"));

        }
        return ResponseEntity.status(400).body(new apiResponse("id is taken or already exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCatefory(@PathVariable String id,@RequestBody @Valid Category category ,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean success = categoryService.updateCategory(id,category);
        if(success){
            return ResponseEntity.status(200).body(new apiResponse("category updated successfully"));
        }
        return ResponseEntity.status(400).body(new apiResponse("id not found"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        boolean success = categoryService.deleteCategory(id);
        if(success){
            return ResponseEntity.status(200).body(new apiResponse("category deleted successfully"));
        }
        return ResponseEntity.status(400).body(new apiResponse("id not found"));
    }

}
