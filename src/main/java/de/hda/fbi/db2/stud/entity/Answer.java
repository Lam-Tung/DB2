package de.hda.fbi.db2.stud.entity;

import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Answer class.
 */
@Embeddable
@Table(name = "Answer", schema = "db2p2")
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
