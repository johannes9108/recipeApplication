package com.iths.jh.RecipeApplication.utilities;

import com.iths.jh.RecipeApplication.domain.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParams {


    /**
     * Recipe SearchParams
     * @words
     * @categoryList
     * @ingredientList
     */
    // Higheest priority
    private Set<String> words;
    // Secondary priority
    private List<FoodCategory> categoryList;
    private Set<String> ingredientList;

    /*
    * @userName
    * */
    @NotNull
    private String searchQuery;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SearchParams{");
        sb.append("words=").append(words);
        sb.append(", categoryList=").append(categoryList);
        sb.append(", ingredientList=").append(ingredientList);
        sb.append(", searchQuery='").append(searchQuery).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public boolean isEmpty(){
        System.out.println(words + ":" + categoryList + ":" + ingredientList);
        return
                (words==null && categoryList == null && ingredientList == null) ||
                        (words.isEmpty() && categoryList.isEmpty() && ingredientList.isEmpty());
    }
}
