package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;

public class Category {
  private String name;
  private ArrayList<Question> questionlist;

  public Category(){}

  public Category(String name, ArrayList<Question> questionlist) {
    this.name = name;
    this.questionlist = questionlist;
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
