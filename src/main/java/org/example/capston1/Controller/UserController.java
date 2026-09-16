package org.example.capston1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capston1.Model.Merchant;
import org.example.capston1.Model.User;
import org.example.capston1.Service.UserService;
import org.example.capston1.api.apiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.naming.spi.ResolveResult;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        ArrayList<User> users = userService.getUsers();
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user , Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean success = userService.addUser(user);

        if(success){
            return ResponseEntity.status(200).body(new apiResponse("user added successfully"));
        }
        return ResponseEntity.status(400).body(new apiResponse("id is taken or already exist"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,@RequestBody @Valid User user,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean success = userService.updateUser(id,user);

        if(success){
            return ResponseEntity.status(200).body(new apiResponse("user updated successfully"));
        }
        return ResponseEntity.status(400).body(new apiResponse("id not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        boolean success = userService.deleteUser(id);
        if(success){
            return ResponseEntity.status(200).body(new apiResponse("user deleted successfully"));
        }
        return ResponseEntity.status(400).body(new apiResponse("id not found"));
    }

    @PutMapping("/bay/{pid}/{mid}/{uid}")
    public ResponseEntity<?> bay(@PathVariable String pid,@PathVariable String mid,@PathVariable String uid){
        int success = userService.buy(uid, pid, mid);

        if(success==0){
            return ResponseEntity.status(400).body(new apiResponse("user id not found"));
        } else if (success ==1) {
            return ResponseEntity.status(400).body(new apiResponse("product id not found"));
        } else if (success==2) {
            return ResponseEntity.status(400).body(new apiResponse("merchant id not found"));
        }else if(success==3){
            return ResponseEntity.status(400).body(new apiResponse("out of stock"));
        }else if(success==4){
            return ResponseEntity.status(400).body(new apiResponse("you dont have balance"));
        }else {
            return ResponseEntity.status(200).body(new apiResponse("bay complete successfully"));
        }

    }





    @PutMapping("/bayGift/{userid}/{productid}/{otherUserid}")
    public ResponseEntity<?> bayGift(@PathVariable String userid,@PathVariable String productid,@PathVariable String otherUserid){
        int success = userService.bayGift(userid, productid, otherUserid);

        if(success ==0){
            return ResponseEntity.status(400).body(new apiResponse("user id not found"));
        }else if(success ==1){
            return ResponseEntity.status(400).body(new apiResponse("product id not found"));
        } else if (success ==2) {
            return ResponseEntity.status(400).body(new apiResponse("other user id not found"));
        }else if(success ==3){
            return ResponseEntity.status(400).body(new apiResponse("product out of stock"));
        } else if (success ==4) {
            return ResponseEntity.status(400).body(new apiResponse("you dont have the balance"));
        }else {
            return ResponseEntity.status(200).body(new apiResponse("success your gift is on the way"));
        }
    }

    @PutMapping("/refund/{userid}/{productid}/{merchantid}")
    public ResponseEntity<?> refund(@PathVariable String userid,@PathVariable String productid ,@PathVariable String merchantid){
        int success = userService.refund(userid, productid, merchantid);

        if(success ==0){
            return ResponseEntity.status(400).body(new apiResponse("user id not found"));
        } else if (success ==1) {
            return ResponseEntity.status(400).body(new apiResponse("product id not found"));
        }else if (success==2){
            return ResponseEntity.status(400).body(new apiResponse("merchant id not found"));
        } else if (success==3) {
            return ResponseEntity.status(200).body(new apiResponse("refund complete successfully"));
        }else if(success ==4) {
            return ResponseEntity.status(400).body(new apiResponse("id not found"));
        }else{
            return ResponseEntity.status(400).body(new apiResponse("sorry you dont perches this product"));
        }
    }

    @PutMapping("/compensation/{adminid}/{amount}/{userid}")
    public ResponseEntity<?> compensation(@PathVariable String adminid,@PathVariable double amount,@PathVariable String userid){
        int success = userService.compensation(adminid, amount, userid);

        if(success==0){
            return ResponseEntity.status(400).body(new apiResponse("unauthorised admin id "));
        } else if (success==1) {
            return ResponseEntity.status(200).body(new apiResponse("compensation added successfully"));
        }else {
            return ResponseEntity.status(400).body(new apiResponse("user id not found"));
        }
    }
}
