package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;

/**
 * Question class
 */
public class Question {

  private int id;
  private String challenge;
  private ArrayList<Answer> choices;

  public Question() {
  }

  public Question(int id, String challenge, ArrayList<Answer> choices) {
    this.id = id;
    this.challenge = challenge;
    this.choices = choices;
  }

  public int getID() {
    return id;
  }

  public void setID(int id) {
    this.id = id;
  }

  public String getChallenge() {
    return challenge;
  }

  public void setChallenge(String challenge) {
    this.challenge = challenge;
  }

  public ArrayList<Answer> getChoices() {
    return choices;
  }

  public void setChoices(ArrayList<Answer> choices) {
    this.choices = choices;
  }
}
