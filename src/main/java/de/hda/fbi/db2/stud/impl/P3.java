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
        EntityTransaction tx = null;
        List<Question> questionList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();
        Map<Question, Boolean> questionsPlayed = new HashMap<>();
        Scanner in = new Scanner(System.in);
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
        categoryList.add(current);
        System.out.println(current.getName() + " added");

        System.out.println("Choose second category: ");
        cat = in.nextInt();
        current = findCategory(cat);
        categoryList.add(current);
        System.out.println(current.getName() + " added");

        System.out.println("Add another category?");
        System.out.println("1: yes");
        System.out.println("2: no");
        int addAnother = in.nextInt();

        boolean addCategory;

        if(addAnother == 1) {
            addCategory = true;
        } else {
            addCategory = false;
        }

        while (addCategory) {
            System.out.println("Choose another category:");
            cat = in.nextInt();
            current = findCategory(cat);
            categoryList.add(current);
            System.out.println(current.getName() + " added");
            System.out.println("Add another category?");
            System.out.println("1: yes");
            System.out.println("2: no");
            addAnother = in.nextInt();

            if(addAnother == 2) {
                addCategory = false;
            }
        }

        System.out.println("Choose max. questions per category: ");
        int maxQuestionsPerCategory = in.nextInt();

        // generate questionList
        for (Category c : categoryList) {
            List<Question> shuffledQuestionsFromCategory = c.getQuestionlist();
            Collections.shuffle(shuffledQuestionsFromCategory);
            int amtQuestions;

            if (c.getQuestionlist().size() < maxQuestionsPerCategory) {
                amtQuestions = c.getQuestionlist().size();
            } else {
                amtQuestions = maxQuestionsPerCategory;
            }

            for (int i = 0; i < amtQuestions; i++) {
                questionList.add(shuffledQuestionsFromCategory.get(i));
            }
        }

        newGame.setPlayer(dbplayer);
        newGame.setCatSelected(categoryList);
        newGame.setQuestionList(questionList);
        newGame.setQuestionsPlayed(questionsPlayed);

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
        Scanner in = new Scanner(System.in);
        EntityManager em = lab02EntityManager.getEntityManager();

        // set start timestamp
        Date currentDate = new Date();
        long currentTime = currentDate.getTime();
        Timestamp currentTs = new Timestamp(currentTime);
        currentGame.settStart(currentTs);
        // play the questions
        for (Question q : currentGame.getQuestionList()) {
            System.out.println(q.getChallenge());
            System.out.println("(1) " + q.getChoices().get(0).getChoice());
            System.out.println("(2) " + q.getChoices().get(1).getChoice());
            System.out.println("(3) " + q.getChoices().get(2).getChoice());
            System.out.println("(4) " + q.getChoices().get(3).getChoice());
            System.out.println("Your answer: ");
            int playerChoice = in.nextInt();

            if (q.getChoices().get(playerChoice-1).getIsCorrect()) {
                System.out.println("Correct! Good job!");
            } else {
                System.out.println("Wrong answer! Correct answer is: " + q.getCorrectChoice().getChoice());
            }

            // add to QuestionsPlayed with the player choices
            currentGame.getQuestionsPlayed().put(q, q.getChoices().get(playerChoice-1).getIsCorrect());
        }
        // set end timestamp
        currentDate = new Date();
        currentTime = currentDate.getTime();
        currentTs = new Timestamp(currentTime);
        currentGame.settEnd(currentTs);

        // persist game
        persistGame(currentGame);

        // show stats
        System.out.println("Questions played: " + currentGame.getQuestionsPlayed().size());
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
        Random r = new Random();
        Game currentGame = (Game)game;
        Map<Question, Boolean> played = new HashMap<>();
        for( Object question: currentGame.getQuestionList() ) {
            Question currentQuestion = (Question) question;
            List<Answer> answers = currentQuestion.getChoices();
            int selectedAnswer = r.nextInt(4);
            Boolean isCorrect = answers.get(selectedAnswer).getIsCorrect();
            played.put(currentQuestion, isCorrect);
        }
        ((Game) game).setQuestionsPlayed(played);
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

        Player result = null;
        try {
            result = (Player) em.createQuery("select p from Player p where p.pName = '" + name + "'").getSingleResult();
        } catch (NoResultException nre) {
            return new Player(name);
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
        em.persist(game);
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

    private void printCategories() {
        List<Category> categoryList = convertToCategoryList(lab01Data.getCategories());
        for (int i = 0; i < categoryList.size(); i++) {
            System.out.println(categoryList.get(i).getCid() + ". " + categoryList.get(i).getName() + "   ");
        }
    }

    private Category findCategory(int catID) {
        List<Category> categoryList = convertToCategoryList(lab01Data.getCategories());

        for (Category c: categoryList) {
            if (c.getCid() == catID) {
                return c;
            }
        }
        return null;
    }
}
