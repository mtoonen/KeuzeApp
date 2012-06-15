/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package meine.util;

import java.util.Iterator;
import java.util.Set;
import meine.models.Gebruiker;
import meine.models.Rol;
import meine.models.Rollen;

/**
 *
 * @author Meine Toonen
 */
public class AuthenticatorRealm {
    private Gebruiker gebruiker;
    private static AuthenticatorRealm realm = null;

    private AuthenticatorRealm(){

    }

    public static AuthenticatorRealm getInstance(){
        if(realm == null){
            realm = new AuthenticatorRealm();
        }
        return realm;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public void nullifyAuthentication(){
        this.gebruiker = null;
    }

    public boolean isUserInRole(Rollen rol){
        if(gebruiker == null){
            return false;
        }

        Set<Rol> rollen  = gebruiker.getRollen();
        for (Iterator<Rol> it = rollen.iterator(); it.hasNext();) {
            Rol rol1 = it.next();
            Rollen rolEnum = rol1.getRol();
            if(rolEnum.equals(rol)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the user is in one or more of the supplied Rollen. Returns true if it one (but NOT only one)
     * @param allowedRoles
     * @return
     */
    public boolean isUserInRoles(Set<Rollen> allowedRoles){
        
        for (Iterator<Rollen> it = allowedRoles.iterator(); it.hasNext();) {
            Rollen rol = it.next();
            if(isUserInRole(rol)){
                return true;
            }
        }
        return false;
    }

}
