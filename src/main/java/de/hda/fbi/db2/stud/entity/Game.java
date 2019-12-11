package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Game class.
 */
@Entity
@Table(name = "game", schema = "db2p2")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int gid;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Player player;

    @Temporal(TemporalType.DATE)
    private Date tStart;

    @Temporal(TemporalType.DATE)
    private Date tEnd;

    @ManyToMany
    private List<Category> catSelected;

    @OneToMany(mappedBy = "gameQuest", cascade = CascadeType.PERSIST)
    private List<Question> questionList;

    private Map<Question, Boolean> questionsPlayed;

    public Game () {
    };

    public Game (Player player, List<Object> questionList) {
        this.player = player;
        List<Question> ql = new ArrayList<>();
        for (Object o : questionList) {
            Question q = (Question) o;
            ql.add(q);
        }
        this.questionList = ql;
    }

    public Game (Player player, List<Category> catSelected, List<Question> questionList) {
        this.player = player;
        this.catSelected = catSelected;
        this.questionList = questionList;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date gettStart() {
        return tStart;
    }

    public void settStart(Timestamp tStart) {
        this.tStart = tStart;
    }

    public Date gettEnd() {
        return tEnd;
    }

    public void settEnd(Timestamp tEnd) {
        this.tEnd = tEnd;
    }

    public List<Category> getCatSelected() {
        return catSelected;
    }

    public void setCatSelected(List<Category> catSelected) {
        this.catSelected = catSelected;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public Map<Question, Boolean> getQuestionsPlayed() {
        return questionsPlayed;
    }

    public void setQuestionsPlayed(Map<Question, Boolean> questionsPlayed) {
        this.questionsPlayed = questionsPlayed;
    }

    // Equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game)) {
            return false;
        }
        Game that = (Game) o;
        return getGid() == that.getGid();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGid());
    }
}
