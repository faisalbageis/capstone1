package org.example.capston1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capston1.Model.Merchant;
import org.example.capston1.Service.MerchantService;
import org.example.capston1.api.apiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;
    @GetMapping("/get")
    public ResponseEntity<?> getMerchants(){
        ArrayList<Merchant> merchants = merchantService.getMerchants();
        return ResponseEntity.status(200).body(merchants);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean success = merchantService.addMerchants(merchant);

        if(success){
            return ResponseEntity.status(200).body(new apiResponse("Merchant added successfully"));
        }
        return ResponseEntity.status(400).body(new apiResponse("id is taken or already exist"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id,@RequestBody @Valid Merchant merchant,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean success = merchantService.updateMerchant(id,merchant);

        if(success){
            return ResponseEntity.status(200).body(new apiResponse("merchant updated successfully"));
        }
        return ResponseEntity.status(400).body(new apiResponse("id not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){
        boolean success = merchantService.deleteMerchant(id);
        if(success){
            return ResponseEntity.status(200).body(new apiResponse("merchant deleted successfully"));
        }
        return ResponseEntity.status(400).body(new apiResponse("id not found"));
    }

}
