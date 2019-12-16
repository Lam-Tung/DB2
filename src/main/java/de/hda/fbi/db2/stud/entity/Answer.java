package de.hda.fbi.db2.stud.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;


/**
 * Answer class.
 */
@Embeddable
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

  public boolean getIsCorrect() {
    return isCorrect;
  }

  public void setCorrect(boolean correct) {
    isCorrect = correct;
  }

  // Equals & hashCode
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Answer)) {
      return false;
    }
    Answer that = (Answer) o;
    return Objects.equals(getChoice(), that.getChoice()) &&
            Objects.equals(getIsCorrect(), that.getIsCorrect());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getChoice(), getIsCorrect());
  }
}
