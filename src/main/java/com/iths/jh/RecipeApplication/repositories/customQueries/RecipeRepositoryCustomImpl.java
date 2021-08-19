package com.iths.jh.RecipeApplication.repositories.customQueries;

import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.domain.User;
import com.iths.jh.RecipeApplication.utilities.SearchParams;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Recipe> findAllFetched(SearchParams searchParams) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> cq = cb.createQuery(Recipe.class);
        Root<Recipe> root = cq.from(Recipe.class);
        Fetch<Recipe, User> users = root.fetch("user");
//                .fetch("quantityPerIngredient")
//                .fetch("foodCategories");
        LinkedList<Predicate> predicateLinkedList = new LinkedList<>();
        for (String word : searchParams.getWords()) {
            System.out.println(word);

            predicateLinkedList.add(cb.like(
                    cb.lower(
                            root.get("title")),
                    "%" + word.toLowerCase() + "%"));
        }
        Predicate[] predArray = new Predicate[predicateLinkedList.size()];
        cq.where(cb.or(predicateLinkedList.toArray(predArray)));

        cq.distinct(true);
        TypedQuery<Recipe> q = entityManager.createQuery(cq);
        List<Recipe> recipes = q.getResultList();
        System.out.println(recipes);


//        Expression<String> parentExpression = recipe.get("title");
//        Predicate tagsPredicate = parentExpression.in(searchParams.getWords());
//        cq.where(tagsPredicate);
////        CriteriaBuilder.In<String> inClause = cb.in(recipe.get("title"));
//        searchParams.getWords().forEach(inClause::value);
//        cq.select(recipe).where(inClause);
//        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(cb.like(book.get("title"), "%" + title + "%"));
//        cq.where(predicates.toArray(new Predicate[0]));
        return recipes;
    }
}
