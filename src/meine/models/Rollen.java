/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package meine.models;

/**
 *
 * @author Meine Toonen
 */
public enum Rollen {
    BEHEERDER("Beheerder"),
    LERAAR("Leraar"),
    LEERLING("Leerling");

    private String description;

    Rollen(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public String toString(){
        return description;
    }

}
