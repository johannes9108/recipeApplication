package com.iths.jh.RecipeApplication.repositories.customQueries;

import com.iths.jh.RecipeApplication.domain.FoodCategory;
import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.domain.User;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<Recipe> findAllFetched(SearchParams searchParams, Pageable pageable) {
//        PersistenceUtil util = Persistence.getPersistenceUtil();
//        boolean isObjectLoaded = util.isLoaded(recipe);
//        boolean isFieldLoaded = util.isLoaded(recipe, "address");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> cq = cb.createQuery(Recipe.class);
        Root<Recipe> root = cq.from(Recipe.class);
        Fetch<Recipe, User> users = root.fetch("user");
        LinkedList<Predicate> predicateLinkedList = new LinkedList<>();
        try {
            for (String word : searchParams.getWords()) {
                System.out.println(word);

                predicateLinkedList.add(cb.like(
                        cb.lower(
                                root.get("title")),
                        "%" + word.toLowerCase() + "%"));
            }
        } catch (Exception e) {
            System.err.println("Problem WORDS: " + e.getMessage());
        }
        try {
            for (FoodCategory category : searchParams.getCategoryList()) {
                predicateLinkedList.add(cb.like(cb.lower(root.join("foodCategories").get("name")), "%" + category.getName().toLowerCase() + "%"));
            }
        } catch (Exception e) {
            System.err.println("Problem FOODCATEGORY: " + e.getMessage());
        }
        try {
            Join<Recipe, String> joinedIngredients = root.join("ingredientEntries");
            for (String ingredient : searchParams.getIngredientList()) {
                predicateLinkedList.add(cb.like(cb.lower(joinedIngredients), "%" + ingredient.toLowerCase() + "%"));
            }
        } catch (Exception e) {
            System.err.println("Problem INGREDIENTS: " + e.getMessage());
        }

        try {
            Predicate[] predArray = new Predicate[predicateLinkedList.size()];
            cq.where(cb.or(predicateLinkedList.toArray(predArray)));

            TypedQuery<Recipe> q = null;
            System.out.println(pageable.getPageSize() + ":" + pageable.getPageNumber());
            cq.distinct(true);
            q = entityManager.createQuery(cq);
            q.setMaxResults(pageable.getPageSize());
            q.setFirstResult(pageable.getPageNumber());
            List<Recipe> recipes = q.getResultList();
            return new PageImpl<>(recipes, pageable, recipes.size());
        } catch (Exception e) {
            System.err.println("Resulting: " + e.getMessage());
        }


//        Expression<String> parentExpression = recipe.get("title");
//        Predicate tagsPredicate = parentExpression.in(searchParams.getWords());
//        cq.where(tagsPredicate);
////        CriteriaBuilder.In<String> inClause = cb.in(recipe.get("title"));
//        searchParams.getWords().forEach(inClause::value);
//        cq.select(recipe).where(inClause);
//        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(cb.like(book.get("title"), "%" + title + "%"));
//        cq.where(predicates.toArray(new Predicate[0]));
        return new PageImpl<>(new LinkedList<>());
    }
}
