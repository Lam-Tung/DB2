package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Category class.
 */
@Entity
@Table(name = "category", schema = "db2p2")
public class Category implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "cid")
  private int cid;

  @Column(unique = true)
  private String name;

  @ManyToMany(mappedBy = "catSelected")
  private List<Game> games;

  @OneToMany(mappedBy = "cat", cascade = CascadeType.PERSIST)
  private List<Question> questionlist;

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
      System.out.println("ID: " + q.getQid());
      System.out.println("Question: " + q.getChallenge());
      for (Answer a : q.getChoices()){
        System.out.println(a.getChoice() + " " + a.getIsCorrect());
      }
    }
  }

  public int getCid() {
    return cid;
  }

  public void setCid(int cid) {
    this.cid = cid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Game> getGames() {
    return games;
  }

  public void setGames(List<Game> games) {
    this.games = games;
  }

  public List<Question> getQuestionlist() {
    return questionlist;
  }

  public void setQuestionlist(List<Question> questionlist) {
    this.questionlist = questionlist;
  }

  // Equals & hashCode
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Category)) {
      return false;
    }
    Category that = (Category) o;
    return getCid() == that.getCid();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCid());
  }
}
