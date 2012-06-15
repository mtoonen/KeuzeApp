package meine.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import meine.util.Plaats;

/**
 *
 * @author Meine Toonen
 */
@Entity
@Table(name="keuzemomentfoto")
public class KeuzeMomentFoto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="fotoid")
    private Foto foto;

    @Enumerated(EnumType.STRING)
    private Plaats positie;
    
    @OneToOne
    @JoinColumn(name="keuzemomentid")
    private KeuzeMoment keuzemoment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Plaats getPositie() {
        return positie;
    }

    public void setPositie(Plaats positie) {
        this.positie = positie;
    }

    public KeuzeMoment getKeuzemoment() {
        return keuzemoment;
    }

    public void setKeuzemoment(KeuzeMoment keuzemoment) {
        this.keuzemoment = keuzemoment;
    }
}
