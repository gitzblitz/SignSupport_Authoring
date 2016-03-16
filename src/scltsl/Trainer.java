/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scltsl;

/**
 * This is the class of a video object that is added to a lesson 
 * @version v1.1
 * @author Masilo and Muchenja
 */
public class Trainer {
    /***************************************************/
    /**********************Properties*******************/
    /***************************************************/
    
    private String trainerID;
    private String name;
    private String surname;
    private String password;
    
    /***************************************************/
    /*********************End Properties****************/
    /***************************************************/
    
    
    
    /***************************************************/
    /***********************Constructor*****************/
    /***************************************************/
    
    /**
     * The constructor for creating a specific trainer object
     * @param theTrainerID the id of the trainer
     * @param theName the first name of the trainer
     * @param theSurname the second name of the trainer
     * @param thePassword the password of the trainer
     */
    public Trainer(String theTrainerID, String theName, String theSurname, String thePassword){
            this.trainerID = theTrainerID;
            this.name = theName;
            this.surname = theSurname;
            this.password = thePassword;
    }
    
    /****************************************************/
    /********************End Constructor*****************/
    /****************************************************/
    
    
    
    /****************************************************/
    /************************Methods*********************/
    /****************************************************/
    
    /**
     * Get the id of the trainer
     * @return the id of the trainer
     */
    public String getTrainerID(){
        return this.trainerID;
    }
    
    /**
     * Set the id of the trainer
     * @param theTrainerID the id of the trainer
     */
    public void setTrainerID(String theTrainerID){
        this.trainerID = theTrainerID;
    }
    
    /**
     * Get the first name of the trainer
     * @return the first name
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Set the first name of the trainer
     * @param theName the first name o the trainer
     */
    public void setName(String theName){
        this.name = theName;
    }
    
    /**
     * Get the second name of the trainer
     * @return the second name of the trainer
     */
    public String getSurname(){
        return this.surname;
    }
    
    /**
     * Set the second name of the trainer
     * @param theSurname the second name of the trainer
     */
    public void setSurname(String theSurname){
        this.surname = theSurname;
    }
    
    /**
     * Get the password of the trainer
     * @return the password
     */
    public String getPassword(){
        return  this.password;
    }
    
    /**
     * Set the password of the trainer
     * @param thePassword the password
     */
    public void setPassword(String thePassword){
        this.password = thePassword;
    }
    
    /**
     * Get the string representation of the trainer
     * @return the string representation of the trainer
     */
    @Override
    public String toString(){
        return "";
    }
    
    public void createLesson(){
        
    }
    
    public void deleteLesson(String lessonID){
        
    }
    
    public void saveLesson(){
        
    }
    
    /****************************************************/
    /********************End Methods*********************/
    /****************************************************/
}
