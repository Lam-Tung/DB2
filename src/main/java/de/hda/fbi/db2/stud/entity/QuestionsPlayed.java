package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    @JoinColumn(name = "game_fk_gid", referencedColumnName = "gid")
    private Game qpGame;

    @ElementCollection
    @MapKeyColumn(name = "Question")
    @Column(name = "value")
    @CollectionTable(name = "Map", schema = "db2p2", joinColumns = @JoinColumn(name = "qpid"))
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

    // Equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionsPlayed)) {
            return false;
        }
        QuestionsPlayed that = (QuestionsPlayed) o;
        return getQpid() == that.getQpid();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQpid());
    }
}
