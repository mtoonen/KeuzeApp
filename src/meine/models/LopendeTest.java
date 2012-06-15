/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package meine.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import meine.util.Status;

/**
 *
 * @author Meine Toonen
 */
@Entity
@Table(name="lopendetest")
public class LopendeTest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Leerling leerling;

    @ManyToOne
    private Test test;
    
    private Integer keuzemomenten;

    private Integer fotosperkeuzemoment;
    
    private Integer tijdlimiet;

    private Boolean leerlinguitslag;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="lopendetestid")
    @OrderBy(value="plek")
    private Set<KeuzeMoment> keuzemoment;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date einddatum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Leerling getLeerling() {
        return leerling;
    }

    public void setLeerling(Leerling leerling) {
        this.leerling = leerling;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Integer getFotosperkeuzemoment() {
        return fotosperkeuzemoment;
    }

    public void setFotosperkeuzemoment(Integer fotosperkeuzemoment) {
        this.fotosperkeuzemoment = fotosperkeuzemoment;
    }

    public Integer getKeuzemomenten() {
        return keuzemomenten;
    }

    public void setKeuzemomenten(Integer keuzemomenten) {
        this.keuzemomenten = keuzemomenten;
    }
    public Integer getTijdlimiet() {
        if(tijdlimiet == null){
            return 0;
        }
        return tijdlimiet;
    }

    public void setTijdlimiet(Integer tijdlimiet) {
        this.tijdlimiet = tijdlimiet;
    }

    public Boolean getLeerlinguitslag() {
        return leerlinguitslag;
    }

    public void setLeerlinguitslag(Boolean leerlinguitslag) {
        this.leerlinguitslag = leerlinguitslag;
    }

    public Set<KeuzeMoment> getKeuzemoment() {
        return keuzemoment;
    }

    public void setKeuzemoment(Set<KeuzeMoment> keuzemoment) {
        this.keuzemoment = keuzemoment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getEinddatum() {
        return einddatum;
    }

    public void setEinddatum(Date einddatum) {
        this.einddatum = einddatum;
    }


    @Override
    public String toString(){
        return "Test: " + test.getNaam() + ", Leerling: " + leerling;
    }

}
