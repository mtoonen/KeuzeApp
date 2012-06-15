package meine.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Meine Toonen
 */
@Entity
public class Groep implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String groepnaam;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroepnaam() {
        return groepnaam;
    }

    public void setGroepnaam(String groepnaam) {
        this.groepnaam = groepnaam;
    }

    public String toString(){
        return groepnaam;
    }

}
