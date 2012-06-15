package meine.models;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Meine Toonen
 */
@Entity
public class Gebruiker implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String naam;

    private String wachtwoord;

    private String salt;

    @OneToMany()
    @JoinColumn(name="gebruiker_id")
    private Set<Rol> rollen;

    @OneToOne()
    @JoinColumn(name="groep_id")
    private Groep groep;

    private String schooljaar;

    public Gebruiker() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return naam;
    }

    public Set<Rol> getRollen() {
        return rollen;
    }

    public void setRollen(Set<Rol> rollen) {
        this.rollen = rollen;
    }

    public void addRol(Rol rol){
        this.rollen.add(rol);
    }

    public Groep getGroep() {
        return groep;
    }

    public void setGroep(Groep groep) {
        this.groep = groep;
    }

    public String getSchooljaar() {
        return schooljaar;
    }

    public void setSchooljaar(String schooljaar) {
        this.schooljaar = schooljaar;
    }

    public boolean isInRole(Rollen rol){
        for (Rol gebRol : rollen) {
            if(gebRol.getRol().toString().equals(rol.toString())){
                return true;
            }
        }
        return false;
    }
}
