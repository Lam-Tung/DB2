package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab04MassData;
import de.hda.fbi.db2.stud.entity.*;


import javax.persistence.EntityManager;
import java.util.*;

/**
 * Implementation of Lab04MassData.
 */
public class P4 extends Lab04MassData {
    final String LEXICON = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890abcdefghijklmnopqrstuvwxyz";
    final java.util.Random RANDOM = new java.util.Random();
    final int PLAYER_SIZE = 10000;
    final int GAMES_PER_PLAYER = 100;
    final int AMT_CATEGORY = 6;
    final int QUESTION_PER_CATEGORY = 3;
    private Map<Player, List<QuestionsPlayed>> thingsToPersist;

    @Override
    public void createMassData() {
        // 10 k player unterschiedliche namen
        // jeweils 100 games
        // zeitraum mind. 2 wochen
        // zwischen 10 und 20 fragen
        thingsToPersist = new HashMap<>();
        List<Player> players = generatePlayers();

    }

    // generate players
    private List<Player> generatePlayers() {
        Map<String, Player> players = new HashMap<>();
        List<Player> result = new ArrayList<>();
       while(players.size() < PLAYER_SIZE) {
           String name = getRandomName();
           if(!players.containsKey(name)) {
               Player p = new Player(name);
               players.put(name, p);
               result.add(p);
           }
       }
       return result;
    }

    // generate a random String which functions as player name
    private String getRandomName() {
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = RANDOM.nextInt(11) + 5;
            for(int i = 0; i < length; i++) {
                builder.append(LEXICON.charAt(RANDOM.nextInt(LEXICON.length())));
            }
        }
        return builder.toString();
    }

    // generates a list of games with random categories and questions for each player from the given player list
    private Map<Player, List<Game>> generateGamesForPlayers (List<Player> players) {
        EntityManager em = lab02EntityManager.getEntityManager();
        List<Category> categoryList = em.createQuery(
                "select c from Category c ", Category.class).getResultList();
        Map<Player, List<Game>> result = new HashMap<>();

        for (Player p : players) {
            List<Game> gameList = new ArrayList<>();
            while (gameList.size() < GAMES_PER_PLAYER) {
                List<Category> randomCategories = selectRandomCategories(categoryList);
                List<Question> randomQuestions = selectRandomQuestions(randomCategories);
                gameList.add(new Game(p, randomCategories, randomQuestions));
            }
            result.put(p, gameList);
        }
        return result;
    }

    // returns a random category list from the given category list
    private List<Category> selectRandomCategories(List<Category> categoryList) {
        List<Category> result = new ArrayList<>();
        for (int i = 0; i < AMT_CATEGORY; i++) {
            result.add(categoryList.get(RANDOM.nextInt(categoryList.size())));
        }
        return result;
    }

    // returns a shuffled question list of all the categories in the given category list
    private List<Question> selectRandomQuestions(List<Category> categoryList) {
        List<Question> result = new ArrayList<>();
        for (Category c : categoryList) {
            List<Question> shuffledQuestionsFromCategory = c.getQuestionlist();
            Collections.shuffle(shuffledQuestionsFromCategory);
            int amtQuestions;

            if (c.getQuestionlist().size() < QUESTION_PER_CATEGORY) {
                amtQuestions = c.getQuestionlist().size();
            } else {
                amtQuestions = QUESTION_PER_CATEGORY;
            }

            for (int i = 0; i < amtQuestions; i++) {
                if (!result.contains(shuffledQuestionsFromCategory.get(i))) {
                    result.add(shuffledQuestionsFromCategory.get(i));
                }
            }
        }
        Collections.shuffle(result);
        return result;
    }

    // simulates all games of players and returns a list of QuestionsPlayed (to be persited)
    private List<QuestionsPlayed> simulateGames(Map<Player, List<Game>> playersUnplayedGames) {
        List<QuestionsPlayed> result = new ArrayList<>();
        Iterator<Map.Entry<Player, List<Game>>> it = playersUnplayedGames.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Player, List<Game>> pair = (Map.Entry<Player, List<Game>>) it.next();
            List<Game> gamesToSimulate = pair.getValue();
            for (Game g : gamesToSimulate) {
                Map<Question, Boolean> playerChoices = new HashMap<>();
                Date startDate = getRandomDate();
                g.settStart(startDate);
                for (Question q: g.getQuestionList()) {
                    List<Answer> answers = q.getChoices();
                    int selectedAnswer = RANDOM.nextInt(4);
                    Boolean isCorrect = answers.get(selectedAnswer).getIsCorrect();
                    playerChoices.put(q, isCorrect);
                }
                g.settEnd(startDate); //TODO +5 min oder so
                QuestionsPlayed questionsPlayed = new QuestionsPlayed(g, playerChoices);
                result.add(questionsPlayed);
            }
        }
        return result;
    }

    private Date getRandomDate() {
        return null;
    }

}
