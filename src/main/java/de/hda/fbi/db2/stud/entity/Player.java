package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Player class.
 */
@Entity
@Table(name = "player", schema = "db2p2")
public class Player {
    @Id
    @Column(unique = true)
    private String pName;

    @OneToOne(mappedBy = "player")
    private Game gameP;

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
