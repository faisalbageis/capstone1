package org.example.capston1.Service;

import lombok.RequiredArgsConstructor;
import org.example.capston1.Model.Merchant;
import org.example.capston1.Model.MerchantStock;
import org.example.capston1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    private final MerchantService merchantService;
    private  final ProductService productService;
    public ArrayList<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }

    public int addMerchentStock(MerchantStock merchantStock){
        for(MerchantStock i:merchantStocks){
            if(i.getId().equalsIgnoreCase(merchantStock.getId())){
                return 0;
            }
        }
        boolean pr = false;
        for(Product i:productService.products){
            if(i.getId().equalsIgnoreCase(merchantStock.getProductId())){
                pr=true;
            }
        }

        boolean mr=false;
        for(Merchant i:merchantService.merchants){
            if(i.getId().equalsIgnoreCase(merchantStock.getMerchantId())){
                mr=true;
            }
        }

        if(pr&&mr){
            merchantStocks.add(merchantStock);
            return 1;
        }

        if(!pr){
            return 2;
        }else {
            return 3;
        }
    }

    public int updateMerchantStock(String id , MerchantStock merchantStock){
        boolean pr = false;
        for(Product i:productService.products){
            if(i.getId().equalsIgnoreCase(merchantStock.getProductId())){
                pr=true;
            }
        }

        boolean mr=false;
        for(Merchant i:merchantService.merchants){
            if(i.getId().equalsIgnoreCase(merchantStock.getMerchantId())){
                mr=true;
            }
        }

        if(pr&&mr){
            for(int i =0;i<merchantStocks.size();i++){
                if(merchantStocks.get(i).getId().equalsIgnoreCase(id)){
                    merchantStocks.set(i,merchantStock);
                    return 0;
                }
            }
        }
        if(!pr){
            return 1;
        }else if(!mr) {
            return 2;
        }
        return 3;
    }

    public boolean deleteMarchentStock(String id){
        for(MerchantStock i:merchantStocks){
            if(i.getId().equalsIgnoreCase(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean addToStock(String productid , String meerchantId,int amount){
        for(MerchantStock i:merchantStocks){
            if(i.getProductId().equalsIgnoreCase(productid)&&i.getMerchantId().equalsIgnoreCase(meerchantId)){
                i.setStock(i.getStock()+amount);
                return true;
            }
        }
        return false;
    }


    public int repricing(String productid ,String merchantid, int newPrice){
        boolean valid = false;
        for(MerchantStock i: merchantStocks){
            if(i.getProductId().equalsIgnoreCase(productid)&&i.getMerchantId().equalsIgnoreCase(merchantid)){
                valid = true;
            }
        }

        if(valid) {
            for (Product i : productService.products) {
                if (i.getId().equalsIgnoreCase(productid)) {
                    if (i.getPrice() != newPrice) {
                        i.setPrice(newPrice);
                        return 0;
                    } else {
                        return 1;
                    }
                }
            }
        }
        return 2;
    }

}
