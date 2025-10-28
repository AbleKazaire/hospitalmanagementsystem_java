/*
 * This package is for serializing objects.
 * These objects can be sent between a client
 * and the server.
 */



import java.io.Serializable;

/**
 *
 * @author Dr. Nabaasa Evarist
 * the Lecturer and examiner
 */
public class SerializableClass implements Serializable{
    // instance variables declaration
    String regno,surname, age, sex;
    
    // flags to control actions int the object
    boolean flagSave,flagFind, flagDelete;
    /**
     * the constructor for 
     * initializing the variables
    */
    public SerializableClass(){
        regno="";
        surname = "";
        age = "";
        sex = "";
        
        flagSave=false;
        flagFind = false;
        flagDelete = false;
    }
}
