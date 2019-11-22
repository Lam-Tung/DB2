package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

/**
 * P2 class is an implementation of the Lab02EntityManager.java.
 */
public class P2 extends Lab02EntityManager {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    /**
     * There you have to persist the data in the database.
     */
    @Override
    public void persistData() {
        getEntityManager();
        //List<> ql =

    }

    /**
     * Return a valid EntityManager.
     *
     * @return EntityManager
     */
    @Override
    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("postgresPU");
        em = emf.createEntityManager();

        return em;
    }
}
