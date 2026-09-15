package org.example.capston1.Service;

import org.example.capston1.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service

public class CategoryService {
    ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getCategories(){
        return categories;
    }

    public boolean addCategory(Category category){
        for(Category i :categories){
            if(i.getId().equalsIgnoreCase(category.getId())){
                return false;
            }
        }
        categories.add(category);
        return true;
    }

    public boolean updateCategory(String id,Category category){
        for(int i=0;i<categories.size();i++){
            if(categories.get(i).getId().equalsIgnoreCase(id)){
                categories.set(i,category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id){
        for(Category i:categories){
            if(i.getId().equalsIgnoreCase(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
}
