package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.stud.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * P3 class is an implementation of the Lab03Game.java.
 */
public class P3 extends Lab03Game {
    /**
     * This method should create a game. For this you have to do the following things:
     * - Ask for player
     * - Ask for categories
     * - Ask for maximum questions per category and choose questions
     * <p>
     * You do not have to play the game here. This should only create a game
     *
     * @return the game entity object. IMPORTANT: This has to be a entity.
     */
    @Override
    public Object createGame() {
        EntityManager em = lab02EntityManager.getEntityManager();
        List<Question> questionList = convertToQuestionList(lab01Data.getQuestions());
        List<Category> categoryList = convertToCategoryList(lab01Data.getCategories());


        Game game;
        return null;
    }

    /**
     * Here you have to play the game 'game'.
     *
     * @param game the game that should be played
     */
    @Override
    public void playGame(Object game) {

    }

    /**
     * This should create a game with the given arguments.
     * You do not have to read data from console.
     * The game should be create only with the given arguments.
     * You do not have to play the game here.
     *
     * @param playerName name of the player
     * @param questions  chosen questions
     * @return a game object
     */
    @Override
    public Object createGame(String playerName, List<Object> questions) {
        Game game = new Game((Player) getPlayer(playerName), questions);
        Object oGame = game;

        return oGame;
    }

    /**
     * Simulate a game play. You do not have to read anything from console.
     *
     * @param game given game
     */
    @Override
    public void simulateGame(Object game) {
    // TODO
    }

    /**
     * This should return the appropriate player for the given game.
     *
     * @param game given game for returning player
     * @return player for given game
     */
    @Override
    public Object getPlayer(Object game) {
        Game g = (Game) game;
        return g.getPlayer();
    }

    /**
     * Return the player entity with given name.
     *
     * @param name name of the player
     * @return the player entity
     */
    @Override
    public Object getPlayer(String name) {
        EntityManager em = lab02EntityManager.getEntityManager();
        Player pDatabase = em.find(Player.class, name);
        if (pDatabase != null) {
            return pDatabase;
        }

        return new Player(name);
    }

    /**
     * return the right answers of a played game.
     *
     * @param game given game
     * @return number of right answers
     */
    @Override
    public int getRightAnswers(Object game) {
        Game g = (Game) game;
        int rightAnswers = 0;

        Map<Question, Boolean> questionsPlayed = g.getQuestionsPlayed();
        for (java.util.Map.Entry<Question, Boolean> questionAnswerEntry : questionsPlayed.entrySet()) {
            if(questionAnswerEntry.getValue()) {
                rightAnswers++;
            }
        }

        return rightAnswers;
    }

    /**
     * persist the game object in the database.
     *
     * @param game given game
     */
    @Override
    public void persistGame(Object game) {
        EntityManager em = lab02EntityManager.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();
            Game g = (Game) game;
            //TODO g.settEnd(); // set tEnd
            em.persist(g);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    private List<Category> convertToCategoryList (List<Object> objectList) {
        List<Category> categoryList = new ArrayList<>();
        for (Object o: objectList) {
            Category c = (Category) o;
            categoryList.add(c);
        }

        return categoryList;
    }

    private List<Question> convertToQuestionList (List<Object> objectList) {
        List<Question> questionList = new ArrayList<>();
        for (Object o: objectList) {
            Question q = (Question) o;
            questionList.add(q);
        }

        return questionList;
    }
}
