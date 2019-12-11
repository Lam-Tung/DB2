package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * P2 class is an implementation of the Lab02EntityManager.java.
 */
public class P2 extends Lab02EntityManager {
    /**
     * There you have to persist the data in the database.
     */
    @Override
    public void persistData() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = null;
        List<Object> oql = lab01Data.getQuestions();
        List<Object> ocl = lab01Data.getCategories();

        // set category for FK relation
        for (Object oquest : oql) {
            Question q = (Question) oquest;
            Category c = findQuestionCategory(q);
            q.setCat(c);
        }

        // persist categories
        for (Object ocat : ocl) {
            try {
                tx = em.getTransaction();
                tx.begin();
                Category c = (Category) ocat;
                if(em.find(Category.class, c.getCid()) == null) {
                    em.persist(c);
                }
                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
                throw e;
            }
        }

        em.close();

    }

    /**
     * Return a valid EntityManager.
     *
     * @return EntityManager
     */
    @Override
    public EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresPU");
        EntityManager em = emf.createEntityManager();

        return em;
    }

    private List<Category> convertToCategoryList (List<Object> objectList) {
        List<Category> categoryList = new ArrayList<>();
        for (Object o: objectList) {
            Category c = (Category) o;
            categoryList.add(c);
        }

        return categoryList;
    }

    private Category findQuestionCategory (Question q) {
        List<Object> ocl = lab01Data.getCategories();
        List<Category> cl = convertToCategoryList(ocl);
        Category result;
        for (Category c : cl) {
            if (c.getQuestionlist().contains(q)) {
                result = c;
                return result;
            }
        }

        return null;
    }
}
