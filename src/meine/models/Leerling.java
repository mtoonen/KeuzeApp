package meine.models;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import meine.util.Encrypter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Meine Toonen
 */
@Entity
public class Leerling implements Serializable {

    @Transient
    private final Log log = LogFactory.getLog(this.getClass());
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String naam;
  /*  @OneToOne()
    @JoinColumn(name = "groep_id")
    private Groep groep;
    private String schooljaar;*/
    private String opmerkingen;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "leerling")
    private Set<LopendeTest> test;
    @OneToOne//(fetch=FetchType.EAGER)
    @JoinColumn(name = "gebruiker_id")
    private Gebruiker gebruiker;

    public Leerling() {
        gebruiker = new Gebruiker();
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
/*
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
*/
    public String getOpmerkingen() {
        return opmerkingen;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public Set<LopendeTest> getTest() {
        return test;
    }

    public void setTest(Set<LopendeTest> test) {
        this.test = test;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    @Override
    public String toString() {
        return naam;
    }
}
