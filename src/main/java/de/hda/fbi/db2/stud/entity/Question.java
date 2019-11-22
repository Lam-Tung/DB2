package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Question class.
 */
@Entity
@Table(name = "Question", schema = "db2p2")
public class Question {
  @Id
  private int did;
  private String challenge;
  @ElementCollection
  private ArrayList<Answer> choices;
  @ManyToOne
  private Category cat;

  public Question() {
  }

  public Question(int did, String challenge, ArrayList<Answer> choices) {
    this.did = did;
    this.challenge = challenge;
    this.choices = choices;
  }

  public int getID() {
    return did;
  }

  public void setID(int id) {
    this.did = id;
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
