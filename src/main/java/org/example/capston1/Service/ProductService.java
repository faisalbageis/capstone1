package org.example.capston1.Service;

import lombok.RequiredArgsConstructor;
import org.example.capston1.Model.Category;
import org.example.capston1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {
    ArrayList<Product> products = new ArrayList<>();
    private final CategoryService categoryService;

    public ArrayList<Product> getProducts(){
        return products;
    }

    public int addProduct(Product product){

        for(Product i:products){
            if(i.getId().equalsIgnoreCase(product.getId())){
                return 1;
            }
        }
        for(Category i:categoryService.categories){
            if(product.getCategoryId().equalsIgnoreCase(i.getId())){
                products.add(product);
                return 2;
            }
        }
        return 0;
    }

    public int updateProduct(String id , Product product){
        boolean exist=false;
        for(Category i:categoryService.categories){
            if(product.getCategoryId().equalsIgnoreCase(i.getId())){
                exist=true;
            }
        }
        if(!exist){
            return 1;
        }

        for(int i =0;i<products.size();i++){
            if(products.get(i).getId().equalsIgnoreCase(id)){
                products.set(i,product);
                return 2;
            }
        }
        return 0;
    }

    public boolean deleteProduct(String id){
        for (Product i:products){
            if (i.getId().equalsIgnoreCase(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> searchByCategory(String category) {
        String categoryId = "";
        ArrayList<Product> found = new ArrayList<>();
        for (Category i : categoryService.categories) {
            if (i.getName().equalsIgnoreCase(category)) {
                categoryId = i.getId();
            }
        }
        for (Product i : products) {
            if (i.getCategoryId().equalsIgnoreCase(categoryId)) {
                found.add(i);
            }
        }
        return found;
    }




    public boolean discount(String productid,double percentage){
        for(Product i:products){
            if(i.getId().equalsIgnoreCase(productid)){
                i.setPrice(i.getPrice()*(1-(percentage/100.0)));
                return true;
            }
        }
        return false;
    }
}
