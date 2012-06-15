package meine.models;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Meine Toonen
 */
@Entity
@Table(name="keuzemoment")
public class KeuzeMoment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="keuzemomentid")
    private Set<KeuzeMomentFoto> fotos;

    @OneToOne
    private KeuzeMomentFoto keuze;
    
    private Double tijd;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="lopendetestid")
    private LopendeTest lopendetest;

    private Integer plek;

    public Set<KeuzeMomentFoto> getFotos() {
        return fotos;
    }

    public void setFotos(Set<KeuzeMomentFoto> fotos) {
        this.fotos = fotos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KeuzeMomentFoto getKeuze() {
        return keuze;
    }

    public void setKeuze(KeuzeMomentFoto keuze) {
        this.keuze = keuze;
    }

    public Double getTijd() {
        return tijd;
    }

    public void setTijd(Double tijd) {
        this.tijd = tijd;
    }
    
    public LopendeTest getLopendetest() {
        return lopendetest;
    }

    public Integer getPlek() {
        return plek;
    }

    public void setPlek(Integer plek) {
        this.plek = plek;
    }

    public void setLopendetest(LopendeTest lopendetest) {
        this.lopendetest = lopendetest;
    }
}
