package org.example.capston1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capston1.Model.MerchantStock;
import org.example.capston1.Service.MerchantService;
import org.example.capston1.Service.MerchantStockService;
import org.example.capston1.api.apiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/marchentStock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;
    @GetMapping("/get")
    public ResponseEntity<?> getMarchentStocks(){
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStocks();
        return ResponseEntity.status(200).body(merchantStocks);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addMarchentStock(@RequestBody @Valid MerchantStock merchantStock , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        int success = merchantStockService.addMerchentStock(merchantStock);

        if(success ==0 ){
            return ResponseEntity.status(400).body(new apiResponse("id is taken or already exist"));
        }else if(success==1){
            return ResponseEntity.status(200).body(new apiResponse("merchantStock added successfully"));
        }else if(success==2){
            return ResponseEntity.status(400).body(new apiResponse("product id not valid"));
        }else {
            return ResponseEntity.status(400).body(new apiResponse("Merchant id invalid"));
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id , @RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        int success = merchantStockService.updateMerchantStock(id,merchantStock);

        if(success ==0 ){
            return ResponseEntity.status(200).body(new apiResponse("merchantStock updated successfully"));
        }else if(success==1){
            return ResponseEntity.status(400).body(new apiResponse("product id not valid"));
        }else if(success==2){
            return ResponseEntity.status(400).body(new apiResponse("Merchant id not valid"));
        }else {
           return ResponseEntity.status(400).body(new apiResponse("id not found"));
        }
    }
    @DeleteMapping("/delet/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id){
        boolean success = merchantStockService.deleteMarchentStock(id);

        if(success){
            return ResponseEntity.status(200).body(new apiResponse("MerchantStock deleted Successfully"));
        }

        return ResponseEntity.status(400).body(new apiResponse("id not found"));
    }

    @PutMapping("/updateStock/{productid}/{Merchantid}/{amount}")
    public ResponseEntity<?> addTodStock(@PathVariable String productid,@PathVariable String Merchantid ,@PathVariable int amount){
        boolean success = merchantStockService.addToStock(productid, Merchantid, amount);

        if(success){
            return ResponseEntity.status(200).body(new apiResponse("stock updated successfully"));
        }
        return ResponseEntity.status(400).body(new apiResponse("product id not found or not authorised to update"));
    }


}
