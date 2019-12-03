package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Player class.
 */
@Entity
@Table(name = "player", schema = "db2p2")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PID")
    private int pid;

    @Column(unique = true)
    private String pName;

    @OneToMany(mappedBy = "player")
    private List<Game> gamesPlayed;

    public Player () {
    };

    public Player (String pName) {
        this.pName = pName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<Game> getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(List<Game> gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    // Equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        Player that = (Player) o;
        return getpName().equals(that.getpName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getpName());
    }
}
