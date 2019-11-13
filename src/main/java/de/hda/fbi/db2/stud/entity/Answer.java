package de.hda.fbi.db2.stud.entity;

/**
 * Answer class
 */
public class Answer {
  private String choice;
  private boolean isCorrect;

  public Answer() {}

  public Answer(String choice, boolean isCorrect) {
    this.choice = choice;
    this.isCorrect = isCorrect;
  }

  public String getChoice() {
    return choice;
  }

  public void setChoice(String choice) {
    this.choice = choice;
  }

  public boolean isCorrect() {
    return isCorrect;
  }

  public void setCorrect(boolean correct) {
    isCorrect = correct;
  }
}
