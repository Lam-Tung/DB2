package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

/**
 * Question class.
 */
@Entity
@Table(name = "question", schema = "db2p2")
public class Question {
  @Id
  private int qid;
  private String challenge;
  @ElementCollection
  @CollectionTable(name = "CHOICE")
  private List<Answer> choices;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private Category cat;

  public Question() {}

  public Question(int qid, String challenge, ArrayList<Answer> choices) {
    this.qid = qid;
    this.challenge = challenge;
    this.choices = choices;
  }

  public int getID() {
    return qid;
  }

  public void setID(int id) {
    this.qid = id;
  }

  public String getChallenge() {
    return challenge;
  }

  public void setChallenge(String challenge) {
    this.challenge = challenge;
  }

  public List<Answer> getChoices() {
    return choices;
  }

  public void setChoices(ArrayList<Answer> choices) {
    this.choices = choices;
  }

  public Category getCat() {
    return cat;
  }

  public void setCat(Category cat) {
    this.cat = cat;
  }

  // Equals & hashCode
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Question)) {
      return false;
    }
    Question that = (Question) o;
    return getID() == that.getID();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getID());
  }
}
