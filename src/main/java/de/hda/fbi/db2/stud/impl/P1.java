package de.hda.fbi.db2.stud.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.stud.entity.Answer;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;

/**
 * P1 class is an implementation of the Lab01Data.java.
 */
public class P1 extends Lab01Data {
  private List<Object> questions = new ArrayList<>();
  private HashMap<String, Object> categories = new HashMap<>();

  /**
   * Return all questions.
   *
   * @return questions
   */
  @Override
  public List<Object> getQuestions() {
    return questions;
  }

  /**
   * Return all categories.
   *
   * @return categories
   */
  @Override
  public List<Object> getCategories() {
    ArrayList<Object> cats = new ArrayList<>();
    for (java.util.Map.Entry<String, Object> stringObjectEntry : categories.entrySet()) {
      Category c = (Category) stringObjectEntry.getValue();
      cats.add(c);
    }

    return cats;
  }

  /**
   * Save the csv data in appropriate objects.
   *
   * @param additionalCsvLines is the csv data
   */
  @Override
  public void loadCsvFile(List<String[]> additionalCsvLines) {
    for (String[] s: additionalCsvLines.subList(1, additionalCsvLines.size())) {
      // s[0] = ID, s[1] = challenge, s[2] = answer1, s[3] = answer2,s[4] = answer3, s[5] = answer4
      // s[6] = position of solution ==> set answer bool, s[7] = category
      int id = Integer.parseInt(s[0]);
      String challenge = s[1];
      String answer1 = s[2];
      String answer2 = s[3];
      String answer3 = s[4];
      String answer4 = s[5];
      int solution = Integer.parseInt(s[6]);
      String category = s[7];

      // creating answers
      Answer a1 = new Answer(answer1, false);
      Answer a2 = new Answer(answer2, false);
      Answer a3 = new Answer(answer3, false);
      Answer a4 = new Answer(answer4, false);

      // set solution
      switch (solution) {
        case 1: a1.setCorrect(true); break;
        case 2: a2.setCorrect(true); break;
        case 3: a3.setCorrect(true); break;
        case 4: a4.setCorrect(true); break;
        default:
          throw new IllegalStateException("Unexpected value: " + solution);
      }

      // create question
      ArrayList<Answer> choices = new ArrayList<>();
      choices.add(a1);
      choices.add(a2);
      choices.add(a3);
      choices.add(a4);
      Question q = new Question(id, challenge, choices);
      questions.add(q);

      // create category or if exist add question to category
      Category c = checkCategoryExistence(category);
      if (c != null) {
        c.getQuestionlist().add(q);
      } else {
        Category newCat = new Category(category);
        newCat.getQuestionlist().add(q);
        categories.put(category, newCat);   //put instead of add + added key
      }
    }

    // print Questions
    //printMap(categories);
  }

  /**
   * checks if category already exists.
   * @param cat the String to find a belonging category
   * @return the category or null
   */
  private Category checkCategoryExistence(String cat) {
    if (categories.containsKey(cat)) {
      return (Category) categories.get(cat);  //is cast save?
    } else {
      return null;
    }
  }

  /**
   * @param map the map to be printed
   */
  private void printMap(HashMap map) {
    for (Object o : map.entrySet()) {
      HashMap.Entry pair = (HashMap.Entry) o;
      Category c = (Category) pair.getValue();
      c.printQuestions();
    }
  }
}
