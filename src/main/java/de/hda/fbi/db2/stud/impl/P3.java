package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.stud.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.sql.Timestamp;
import java.util.*;

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
        List<Question> questionList = new ArrayList<>();
        List<Category> catSelected = new ArrayList<>();
        Scanner in = new Scanner(System.in, "UTF-8");
        Game newGame = new Game();

        System.out.println("Type in playername: ");
        // read playername and check if exists
        String playername = in.nextLine();
        Player dbplayer = (Player) getPlayer(playername);

        if (dbplayer == null) {
            System.out.println("Player does not exist, creating new one...");
            dbplayer = new Player(playername);
            em.persist(dbplayer);
        }

        // print categories
        printCategories();
        System.out.println("Choose first category (of at least 2): ");
        int cat = in.nextInt();
        Category current = findCategory(cat);
        catSelected.add(current);
        System.out.println(current.getName() + " added");

        System.out.println("Choose second category: ");
        cat = in.nextInt();
        current = findCategory(cat);
        catSelected.add(current);
        System.out.println(current.getName() + " added");

        System.out.println("Add another category?");
        System.out.println("1: yes");
        System.out.println("2: no");
        int addAnother = in.nextInt();

        boolean addCategory;

        if (addAnother == 1) {
            addCategory = true;
        } else {
            addCategory = false;
        }

        while (addCategory) {
            System.out.println("Choose another category:");
            cat = in.nextInt();
            current = findCategory(cat);
            catSelected.add(current);
            System.out.println(current.getName() + " added");
            System.out.println("Add another category?");
            System.out.println("1: yes");
            System.out.println("2: no");
            addAnother = in.nextInt();

            if (addAnother == 2) {
                addCategory = false;
            }
        }

        System.out.println("Choose max. questions per category: ");
        int maxQuestionsPerCategory = in.nextInt();

        // generate questionList
        for (Category c : catSelected) {
            List<Question> shuffledQuestionsFromCategory = c.getQuestionlist();
            Collections.shuffle(shuffledQuestionsFromCategory);
            int amtQuestions;

            if (c.getQuestionlist().size() < maxQuestionsPerCategory) {
                amtQuestions = c.getQuestionlist().size();
            } else {
                amtQuestions = maxQuestionsPerCategory;
            }

            for (int i = 0; i < amtQuestions; i++) {
                if (!questionList.contains(shuffledQuestionsFromCategory.get(i))) {
                    questionList.add(shuffledQuestionsFromCategory.get(i));
                }
            }
        }
        Collections.shuffle(questionList);

        newGame.setPlayer(dbplayer);
        newGame.setCatSelected(catSelected);
        newGame.setQuestionList(questionList);


        return newGame;
    }

    /**
     * Here you have to play the game 'game'.
     *
     * @param game the game that should be played
     */
    @Override
    public void playGame(Object game) {
        Game currentGame = (Game) game;
        Scanner in = new Scanner(System.in, "UTF-8");
        EntityManager em = lab02EntityManager.getEntityManager();
        EntityTransaction tx = null;
        Map<Question, Boolean> playerAnswers = new HashMap<>();
        QuestionsPlayed questionsPlayed = new QuestionsPlayed(currentGame, playerAnswers);

        // set start timestamp
        Date currentDate = new Date();
        currentGame.settStart(new Timestamp(currentDate.getTime()));
        // play the questions
        for (Question q : currentGame.getQuestionList()) {
            System.out.println(q.getChallenge());
            System.out.println("(1) " + q.getChoices().get(0).getChoice());
            System.out.println("(2) " + q.getChoices().get(1).getChoice());
            System.out.println("(3) " + q.getChoices().get(2).getChoice());
            System.out.println("(4) " + q.getChoices().get(3).getChoice());
            System.out.println("Your answer: ");
            int playerChoice = in.nextInt();

            if (q.getChoices().get(playerChoice - 1).getIsCorrect()) {
                System.out.println("Correct! Good job!");
            } else {
                System.out.println("Wrong answer! Correct answer is: "
                        + q.getCorrectChoice().getChoice());
            }

            // add to QuestionsPlayed
            boolean isCorrect = q.getChoices().get(playerChoice - 1).getIsCorrect();
            questionsPlayed.getQuestionsPlayed().put(q, isCorrect);
        }
        // set end timestamp
        currentGame.settEnd(new Timestamp(currentDate.getTime()));

        // persist game & player choices
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(questionsPlayed);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }


        // show stats
        System.out.println("Questions played: " + questionsPlayed.getQuestionsPlayed().size());
        System.out.println("Correct Answers: " + getRightAnswers(currentGame));

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

        return game;
    }

    /**
     * Simulate a game play. You do not have to read anything from console.
     *
     * @param game given game
     */
    @Override
    public void simulateGame(Object game) {
        EntityManager em = lab02EntityManager.getEntityManager();
        EntityTransaction tx = null;

        Random r = new Random();
        Game currentGame = (Game) game;
        // set start timestamp
        Date currentDate = new Date();
        currentGame.settStart(new Timestamp(currentDate.getTime()));
        Map<Question, Boolean> played = new HashMap<>();
        for (Object question: currentGame.getQuestionList()) {
            Question currentQuestion = (Question) question;
            List<Answer> answers = currentQuestion.getChoices();
            int selectedAnswer = r.nextInt(4);
            Boolean isCorrect = answers.get(selectedAnswer).getIsCorrect();
            played.put(currentQuestion, isCorrect);
        }
        // set end timestamp
        currentDate = new Date();
        currentGame.settEnd(new Timestamp(currentDate.getTime()));
        QuestionsPlayed qp = new QuestionsPlayed(currentGame, played);

        // persist game & player choices
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(qp);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
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
        EntityTransaction tx = null;
        Player result = null;
        try {
            result = (Player) em.createQuery(
                    "select p from Player p where p.pName = '" + name + "'").getSingleResult();
        } catch (NoResultException nre) {
            Player newPlayer = new Player(name);
            try {
                tx = em.getTransaction();
                tx.begin();
                em.persist(newPlayer);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
                throw e;
            }
            result = (Player) em.createQuery("select p from Player p where p.pName = '" +
                    name + "'").getSingleResult();

            return result;
        }

        return result;
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
        EntityManager em = lab02EntityManager.getEntityManager();
        QuestionsPlayed result = (QuestionsPlayed) em.createQuery(
                "select qp from QuestionsPlayed qp where qp.qpGame.gid = '" + g.getGid() + "'",
                QuestionsPlayed.class).getSingleResult();

        Map<Question, Boolean> questionsPlayed = result.getQuestionsPlayed();
        for (java.util.Map.Entry<Question, Boolean> questionAnswerEntry :
                questionsPlayed.entrySet()) {
            if (questionAnswerEntry.getValue()) {
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
//        EntityManager em = lab02EntityManager.getEntityManager();
//        EntityTransaction tx = null;
//        try {
//            tx = em.getTransaction();
//            tx.begin();
//            Game g = (Game) game;
//            em.persist(g);
//            tx.commit();
//        } catch (RuntimeException e) {
//            if (tx != null && tx.isActive()) {
//                tx.rollback();
//            }
//            throw e;
//        }
    }

    private void printCategories() {
        EntityManager em = lab02EntityManager.getEntityManager();
        List<Category> result = em.createQuery(
                "select m from Category m ", Category.class).getResultList();
        for (Category c : result) {
            System.out.println(c.getCid() + ". " + c.getName());
        }
    }

    private Category findCategory(int catID) {
        EntityManager em = lab02EntityManager.getEntityManager();
        List<Category> result = em.createQuery(
                "select m from Category m ", Category.class).getResultList();

        for (Category c: result) {
            if (c.getCid() == catID) {
                return c;
            }
        }
        return null;
    }
}
