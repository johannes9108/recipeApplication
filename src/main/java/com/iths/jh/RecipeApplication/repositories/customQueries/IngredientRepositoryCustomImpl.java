package com.iths.jh.RecipeApplication.repositories.customQueries;

import com.iths.jh.RecipeApplication.domain.Ingredient;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

public class IngredientRepositoryCustomImpl implements IngredientRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;



    @Override
    public Page<Ingredient> findAllFiltered(SearchParams searchParams, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ingredient> cq = cb.createQuery(Ingredient.class);
        Root<Ingredient> root = cq.from(Ingredient.class);

        try {
            cq.where(cb.like(cb.lower(root.get("name")), "%" + searchParams.getSearchQuery().toLowerCase() + "%"));
            TypedQuery<Ingredient> q = null;
            System.out.println(pageable.getPageSize() + ":" + pageable.getPageNumber());
            cq.distinct(true);
            q = entityManager.createQuery(cq);
            q.setMaxResults(pageable.getPageSize());
            q.setFirstResult(pageable.getPageNumber());
            List<Ingredient> ingredients = q.getResultList();
            System.err.println(ingredients.size());

            return new PageImpl<>(ingredients, pageable, ingredients.size());
        } catch (Exception e) {
            System.err.println("Resulting: " + e.getMessage());
        }

        return new PageImpl<>(new LinkedList<>());
    }
}
