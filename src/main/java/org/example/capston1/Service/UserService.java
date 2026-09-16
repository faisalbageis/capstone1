package org.example.capston1.Service;

import lombok.RequiredArgsConstructor;
import org.example.capston1.Model.Merchant;
import org.example.capston1.Model.MerchantStock;
import org.example.capston1.Model.Product;
import org.example.capston1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<String[]> perchesditems = new ArrayList<>();
    private final MerchantService merchantService;
    private final ProductService productService;
    private final MerchantStockService merchantStockService;
    public ArrayList<User> getUsers(){
        return users;
    }

    public boolean addUser(User user) {
        for (User i : users) {
            if (i.getId().equalsIgnoreCase(user.getId())) {
                return false;
            }
        }
        users.add(user);
        return true;
    }

    public boolean updateUser(String id,User user){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getId().equalsIgnoreCase(id)){
                users.set(i,user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id){
        for(User i:users){
            if(i.getId().equalsIgnoreCase(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public int buy(String userid,String productid,String merchantid) {
        boolean us = false;
        for (User i : users) {
            if (i.getId().equalsIgnoreCase(userid)) {
                us = true;
            }
        }

        boolean pr = false;
        for (Product i : productService.products) {
            if (i.getId().equalsIgnoreCase(productid)) {
                pr = true;
            }
        }

        boolean mr = false;
        for (Merchant i : merchantService.merchants) {
            if (i.getId().equalsIgnoreCase(merchantid)) {
                mr = true;
            }
        }

        if (!us) {
            return 0;
        }
        if (!pr) {
            return 1;
        }
        if (!mr) {
            return 2;
        }

        boolean exist = false;
        for (MerchantStock i : merchantStockService.merchantStocks) {
            if (i.getProductId().equalsIgnoreCase(productid) && i.getMerchantId().equalsIgnoreCase(merchantid)) {
                if (i.getStock() > 0) {
                    i.setStock(i.getStock() - 1);
                    exist = true;
                }
            }
        }

        if (!exist) {
            return 3;
        }
        double price = 0;
        for (Product i : productService.products) {
            if (i.getId().equalsIgnoreCase(productid)) {
                price = i.getPrice();
            }
        }

        for (User i : users) {
            if (i.getId().equalsIgnoreCase(userid)) {
                if (price > i.getBalance()) {
                    return 4;
                } else {
                    i.setBalance(i.getBalance() - price);
                }
            }

        }
        String[] prechesd = {productid , userid};
        perchesditems.add(prechesd);
      return 5;
    }

    public int bayGift(String userid,String productid, String otherUserid ){
        boolean us = false;
        for (User i : users) {
            if (i.getId().equalsIgnoreCase(userid)) {
                us = true;
            }
        }

        boolean pr = false;
        for (Product i : productService.products) {
            if (i.getId().equalsIgnoreCase(productid)) {
                pr = true;
            }
        }

        boolean ou = false;
        for (User i : users) {
            if (i.getId().equalsIgnoreCase(otherUserid)) {
                ou = true;
            }
        }

        if (!us) {
            return 0;
        }
        if (!pr) {
            return 1;
        }
        if (!ou) {
            return 2;
        }
        boolean exist = false;
        double price =0.0;
        for(Product i:productService.products){
            if(i.getId().equalsIgnoreCase(productid)){
                for(MerchantStock y:merchantStockService.merchantStocks){
                    if(y.getProductId().equalsIgnoreCase(productid)){
                        if(y.getStock()>0){
                            y.setStock(y.getStock()-1);
                            exist =true;
                        }
                    }
                }
                price = i.getPrice();
            }
        }

        if(!exist){
            return 3;
        }

        for(User i : users){
            if(i.getId().equalsIgnoreCase(userid)){
                if(i.getBalance()>price){
                    i.setBalance(i.getBalance()-price);
                }else {
                    return 4;
                }
            }
        }
        String[] prechesd = {productid , userid};
        perchesditems.add(prechesd);
        return 5;
    }

    public int refund(String userid,String productid ,String merchantid){
        boolean ui = false;
        for(User i:users){
            if(i.getId().equalsIgnoreCase(userid)){
                ui=true;
            }
        }

        boolean pi = false;
        double price = 0;
        for(Product i:productService.products){
            if(i.getId().equalsIgnoreCase(productid)){
                price = i.getPrice();
                pi=true;
            }
        }

        boolean mi=false;
        for(Merchant i: merchantService.merchants){
            if(i.getId().equalsIgnoreCase(merchantid)){
                mi = true;
            }
        }

        if(!ui){
            return 0;
        }

        if(!pi){
            return 1;
        }

        if(!mi){
            return 2;
        }
        boolean purchased =false;
        for(int i =0;i<perchesditems.size();i++){
            if(perchesditems.get(i)[0].equalsIgnoreCase(productid)&&perchesditems.get(i)[1].equalsIgnoreCase(userid)){
                purchased = true;
            }
        }

        if(!purchased){
            return 5;
        }


        for(User i:users){
            if(i.getId().equalsIgnoreCase(userid)){
                i.setBalance(i.getBalance()+price);
                return 3;
            }
        }
        for(MerchantStock i:merchantStockService.merchantStocks){
            if(i.getMerchantId().equalsIgnoreCase(merchantid)&&i.getProductId().equalsIgnoreCase(productid)){
                i.setStock(i.getStock()+1);
            }
        }

        return 4;
    }

    public int compensation(String adminid,double amount , String userid){
      boolean authorised=false;
        for(User i:users){
            if(i.getId().equalsIgnoreCase(adminid)){
                if(i.getRole().equalsIgnoreCase("admin")){
                    authorised=true;
                }
            }
        }

        if(!authorised){
            return 0;
        }

        for(User i: users){
            if(i.getId().equalsIgnoreCase(userid)){
                i.setBalance(i.getBalance()+amount);
                return 1;
            }
        }
        return 2;
    }

}
