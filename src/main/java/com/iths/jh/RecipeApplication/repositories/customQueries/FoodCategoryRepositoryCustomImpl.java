package com.iths.jh.RecipeApplication.repositories.customQueries;

import com.iths.jh.RecipeApplication.domain.FoodCategory;
import com.iths.jh.RecipeApplication.domain.Ingredient;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class FoodCategoryRepositoryCustomImpl implements FoodCategoryRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<FoodCategory> findAllFiltered(SearchParams searchParams, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FoodCategory> cq = cb.createQuery(FoodCategory.class);
        Root<FoodCategory> root = cq.from(FoodCategory.class);

        try {
            cq.where(cb.like(cb.lower(root.get("name")), "%" + searchParams.getSearchQuery().toLowerCase() + "%"));
            TypedQuery<FoodCategory> q = null;
            System.out.println(pageable.getPageSize() + ":" + pageable.getPageNumber());
            cq.distinct(true);
            q = entityManager.createQuery(cq);
            q.setMaxResults(pageable.getPageSize());
            q.setFirstResult(pageable.getPageNumber());
            List<FoodCategory> ingredients = q.getResultList();
            System.err.println(ingredients.size());

            return new PageImpl<>(ingredients, pageable, ingredients.size());
        } catch (Exception e) {
            System.err.println("Resulting: " + e.getMessage());
        }

        return new PageImpl<>(new LinkedList<>());
    }
}
