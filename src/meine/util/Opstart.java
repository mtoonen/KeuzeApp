package meine.util;

import java.io.File;
import meine.app.Beginscherm;

/**
 *
 * @author Meine Toonen
 */
public class Opstart {

    public static File f = new File("img/logo.jpg");
   // private static WindowManager wm = new WindowManager();
    public static void main(String[] args) {
        MyDb m = new MyDb();
        
        Beginscherm b = new Beginscherm();
        b.setVisible(true);
    }
    
}
