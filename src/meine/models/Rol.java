package meine.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 *
 * @author Meine Toonen
 */
@Entity
public class Rol implements Serializable {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Rollen rol;
    private Integer gebruiker_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rollen getRol() {
        return rol;
    }

    public void setRol(Rollen rol) {
        this.rol = rol;
    }

    public Integer getGebruiker_id() {
        return gebruiker_id;
    }

    public void setGebruiker_id(Integer gebruiker_id) {
        this.gebruiker_id = gebruiker_id;
    }

    public String toString(){
        return rol.getDescription();
    }
}