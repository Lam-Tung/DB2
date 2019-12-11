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
  @Column(name = "questionid")
  private int qid;
  private String challenge;
  @ElementCollection
  @CollectionTable(
          name = "answers",
          schema = "db2p2",
          joinColumns = @JoinColumn(name = "QuestionID")
  )
  private List<Answer> choices;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private Category cat;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private Game gameQuest;

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

  public Answer getCorrectChoice() {
    for (Answer a : getChoices()) {
      if (a.getIsCorrect()) {
        return a;
      }
    }
    return null;
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
