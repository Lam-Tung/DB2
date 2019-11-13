package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;

/**
 * Category class.
 */
public class Category {
  private String name;
  private ArrayList<Question> questionlist;

  public Category(){}

  public Category(String name) {
    this.name = name;
    this.questionlist = new ArrayList<>();
  }


  /**
   * Print all questions with answers.
   */
  public void printQuestions() {
    System.out.println("Category : " + name);
    for (Question q : this.questionlist){
      System.out.println("ID: " + q.getID());
      System.out.println("Question: " + q.getChallenge());
      for (Answer a : q.getChoices()){
        System.out.println(a.getChoice() + " " + a.isCorrect());
      }
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<Question> getQuestionlist() {
    return questionlist;
  }

  public void setQuestionlist(ArrayList<Question> questionlist) {
    this.questionlist = questionlist;
  }
}
