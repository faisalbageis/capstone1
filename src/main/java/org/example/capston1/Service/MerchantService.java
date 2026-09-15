package org.example.capston1.Service;

import org.example.capston1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {
    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }


    public boolean addMerchants(Merchant merchant) {
        for (Merchant i : merchants) {
            if (i.getId().equalsIgnoreCase(merchant.getId())) {
                return false;
            }
        }
        merchants.add(merchant);
        return true;
    }

    public boolean updateMerchant(String id,Merchant merchant){
        for(int i=0;i<merchants.size();i++){
            if(merchants.get(i).getId().equalsIgnoreCase(id)){
                merchants.set(i,merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(String id){
        for(Merchant i:merchants){
            if(i.getId().equalsIgnoreCase(id)){
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }
}
