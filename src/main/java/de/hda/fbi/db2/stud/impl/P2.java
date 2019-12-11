package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
        List<Object> oql = lab01Data.getQuestions();
        List<Object> ocl = lab01Data.getCategories();

        // persist questions
        for (Object oquest : oql) {
            Question q = (Question) oquest;
            Category c = findQuestionCategory(q);
            q.setCat(c); // set category for FK relation
            em.persist(q);
        }

        // persist categories
        for (Object ocat : ocl) {
            Category c = (Category) ocat;
            em.persist(c);
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
