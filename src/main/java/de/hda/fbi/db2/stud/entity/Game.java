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

    @ManyToOne
    private Player player;

    @Temporal(TemporalType.DATE)
    private Date tStart;

    @Temporal(TemporalType.DATE)
    private Date tEnd;

    @ManyToMany
    private List<Category> catSelected;

    private List<Question> questionList;

    @OneToMany(targetEntity = QuestionsPlayed.class, mappedBy = "qpGame")
    private List<QuestionsPlayed> questionsPlayed;

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

    public List<QuestionsPlayed> getQuestionsPlayed() {
        return questionsPlayed;
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
        Date startdate = new Date(tStart.getTime());
        return startdate;
    }

    public void settStart(Date tStart) {
        this.tStart = new Date(tStart.getTime());
    }

    public Date gettEnd() {
        Date enddate = new Date(tEnd.getTime());
        return enddate;
    }

    public void settEnd(Date tEnd) {
        this.tEnd = new Date(tEnd.getTime());
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
