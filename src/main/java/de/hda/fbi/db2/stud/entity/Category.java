package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Category class.
 */
@Entity
@Table(name = "Category", schema = "db2p2")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_category")
  private int cid;
  @Column(unique = true)
  private String name;
  private ArrayList<Question> questionlist;

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
      System.out.println("ID: " + q.getID());
      System.out.println("Question: " + q.getChallenge());
      for (Answer a : q.getChoices()){
        System.out.println(a.getChoice() + " " + a.getIsCorrect());
      }
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(mappedBy = "cat", cascade = {CascadeType.PERSIST})
  public ArrayList<Question> getQuestionlist() {
    return questionlist;
  }

  public void setQuestionlist(ArrayList<Question> questionlist) {
    this.questionlist = questionlist;
  }

  public int getCid() {
    return cid;
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
