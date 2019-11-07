package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;

public class Question {

  private int ID;
  private String challenge;
  private ArrayList<Answer> choices;

  public Question() {
  }

  public Question(int ID, String challenge, ArrayList<Answer> choices, int solution, String type) {
    this.ID = ID;
    this.challenge = challenge;
    this.choices = choices;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public String getChallenge() {
    return challenge;
  }

  public void setChallenge(String challenge) {
    this.challenge = challenge;
  }
}
