/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package meine.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Meine Toonen
 */
@Entity
public class Test implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String naam;

    @OneToMany(mappedBy="test")
    private Set<Foto> foto;

    public Set<Foto> getFoto() {
        return foto;
    }

    public void setFoto(Set<Foto> foto) {
        this.foto = foto;
    }

    public void addFotos(Foto fotoArg){
        if(foto == null){
            foto = new HashSet<Foto>();
        }

        foto.add(fotoArg);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String toString(){
        return naam;
    }
}
