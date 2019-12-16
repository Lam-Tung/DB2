package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Class which contains information about which questions were played.
 */
@Entity
@Table(name = "questionsplayed", schema = "db2p2")
public class QuestionsPlayed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int qpid;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "game_fk_g_id", referencedColumnName = "gid")
    private Game qpGame;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Question qpQuestion;
    private Map<Question, Boolean> questionsPlayed;

    public QuestionsPlayed() {}

    public QuestionsPlayed(Game qpGame, Map<Question, Boolean> questionsPlayed) {
        this.qpGame = qpGame;
        this.questionsPlayed = questionsPlayed;
    }

    public int getQpid() {
        return qpid;
    }

    public void setQpid(int qpid) {
        this.qpid = qpid;
    }

    public Game getQpGame() {
        return qpGame;
    }

    public void setQpGame(Game qpGame) {
        this.qpGame = qpGame;
    }

    public Map<Question, Boolean> getQuestionsPlayed() {
        return questionsPlayed;
    }

    public void setQuestionsPlayed(Map<Question, Boolean> questionsPlayed) {
        this.questionsPlayed = questionsPlayed;
    }
}
