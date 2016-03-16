/*
 * 
 * 
 */
package scltsl;

import java.util.ArrayList;
import java.util.Date;

/**
 * This is the class of a complete Lesson
 * @version v1.3
 * @author Masilo and Muchenja and Chad 
 * Modified by George
 */
public class Lesson {
    
    /***************************************************/
    /**********************Properties*******************/
    /***************************************************/
    private String lessonID;
    private String lessonType;
    private String title;
    private Date date;
    private ArrayList<Step> steps;
    /***************************************************/
    /*********************End Properties****************/
    /***************************************************/
    
    
    /***************************************************/
    /***********************Constructor*****************/
    /***************************************************/
    
    /**
     * Default constructor with default values
     */
    public Lesson(){
        this.lessonID = "";
        this.title = "";
        this.lessonType = "";
        this.date = new Date();
        this.steps = new ArrayList<>(10);
    }
    
    /**
     * Create a new Lesson object
     * @param theLessonID The id of the Lesson
     * @param theTitle The title of the Lesson
     */
    public Lesson(String theLessonID, String theTitle){
        this.lessonID = theLessonID;
        this.title = theTitle;
        this.date = new Date(); // get back to this
        this.steps = new ArrayList<>(10);
    }
    
    /**
     * Create a new Lesson object
     * @param theLessonID The id of the Lesson
     * @param theTitle The title of the Lesson
     * @param theType The type of the Lesson
     */
    public Lesson(String theLessonID, String theTitle, String theType){
        this.lessonID = theLessonID;
        this.title = theTitle;
        this.lessonType = theType;
        this.date = new Date(); // get back to this
    }
    
    /****************************************************/
    /********************End Constructor*****************/
    /****************************************************/
    
    
    
    /****************************************************/
    /************************Methods*********************/
    /****************************************************/
    
    /**
     * Get the ID of the Lesson
     * @return The lessonID of the Lesson
     */
    public String getLessonID(){
        return this.lessonID;
    }
    
    /**
     * Set the id of this lesson
     * @param theLessonID the id of this lesson
     */
    public void setLessonID(String theLessonID){
        this.lessonID = theLessonID;
    }
    
    /**
     * Get the title of the Lesson
     * @return the title of the Lesson
     */
    public String getTitle(){
        return this.title;
    }
    
    /**
     * Set the title of this title
     * @param theTitle the title of this title
     */
    public void setTitle(String theTitle){
        this.title = theTitle;
    }
    
    /**
     * Get the type of the Lesson
     * @return the type of the Lesson
     */
    public String getType(){
        return this.lessonType;
    }
    
    /**
     * Set the title of this title
     * @param theType the type of the lesson
     */
    public void setType(String theType){
        this.lessonType = theType;
    }
    
    /**
     * get the date of when the Lesson was created
     * @return the date when the Lesson was created
     */
    public Date getDate(){ // come back to review this
        return this.date;
    }
    
    /**
     * Set the date the lesson was created
     * @param theDate the date the lesson was created
     */
    public void setDate(Date theDate){
        this.date = theDate;
    }
    
    /**
     * Get the total duration of the Lesson
     * @return the duration of the Lesson
     */
    public int getDuration(){
        return 22;
    }
    
    /**
     * Get the resource at the specified position from a given step
     * @param stepIndex the index of the step in the list of steps
     * @param resourceIndex the index of the step in the list of resources
     * @return the resource at the specified position from at the given step
     */
    public Resource getResource(int stepIndex, int resourceIndex){
        
        return steps.get(stepIndex).getResource(resourceIndex);
    }
    
    /**
     * Remove a resource at a specified position from a given step
     * @param stepIndex the index of the step in the list of steps
     * @param resourceIndex the index of the step in the list of resources
     */
    public void removeResource(int stepIndex, int resourceIndex){
        
        steps.get(stepIndex).removeResource(resourceIndex);
    }
    
    /**
     * Add a resource at the end of the given step
     * @param stepIndex the index of the step in the list of steps
     * @param resource the resource
     */
    public void addResource(int stepIndex, Resource resource){
        if (stepIndex >= steps.size()){
            addStep();
        }
        resource.setLessonVideoType(this.getType());
        steps.get(stepIndex).addResource(resource);
    }
    
    /**
     * Add a resource to the given step before the given resource position
     * @param stepIndex the index of the step in the list of steps
     * @param resourceIndex the index of the step in the list of resources
     * @param resource the resources
     */
    public void addResourceBefore(int stepIndex, int resourceIndex, Resource resource){
        if (stepIndex >= steps.size()){
            addStep();
        }
        resource.setLessonVideoType(this.getType());
        steps.get(stepIndex).addResourceAt(stepIndex, resource);
    }
    
    public int getlastResourceIndex(int stepIndex){
        if (!steps.isEmpty()){
            if (stepIndex == steps.size()){
                return -1;
            }
            return steps.get(stepIndex).getLastIndex();
        }
        
        return -1;
    }
    
    /**
     * Swap the two resources
     * @param a the first resource
     * @param b the second resource
     */
    public void swapResources(Resource a, Resource b){
       
    }
    
    public void addStep(){
        steps.add(new Step(steps.size()+1));       
    }
    
    public void addStepAt(int position){
        steps.add(position-1, new Step(position));
        
        // increment step numbers
        for (int i = position; i < steps.size(); i++){
            steps.get(i).setNumber(i+1);
        }
    }
    
    public ArrayList<Step> getSteps(){
        return this.steps;
    }
    
    /**
     * Get the string representation of this Lesson
     * @return the string representation of Lesson 
     */
    @Override
    public String toString(){
        return ""+lessonID;
    }
    
    /**
     * Get the XML representation of the Lesson
     * @param XMLHeader Do you want to include the XML Header?
     * @return the XLM representation of the Lesson
     */
    public String getXML(boolean XMLHeader, boolean IRXML){
        String xml = "";
        if(XMLHeader)
            xml += "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
        else if(IRXML){
            xml += "\n<lesson lesson_title=\""+this.getTitle()+"\" lesson_id=\""+this.getLessonID()+"\" lesson_type=\""+this.getType()+"\">";
//            xml += "\n\t<lesson_id>" + this.getLessonID() + "</lesson_id>";
//            xml += "\n\t<lesson_type>" + this.getType() + "</lesson_type>";
//            xml += "\n\t<lesson_title>" + this.getTitle() + "</lesson_title>";

            for (int i = 0; i < steps.size(); i++){

                if (i == 1){
//                    xml += "\n\t<task>";
                     xml += "";
                     
                }

                xml += steps.get(i).toXML(true);
                // toXML is a function in the Resource class
            }
            if (steps.size() > 1){
//                xml += "\n\t</task>";
                 xml += "";
            }

            xml += "\n</lesson>\n";
        }
        else{
            //xml += "\n\t\t<lesson>";
//            xml += "\n\t\t<lesson name= '"+this.getTitle()+"' id='"+this.getLessonID()+"'>";
            xml += "\n\t<lesson lesson_title=\""+this.getTitle()+"\" lesson_id=\""+this.getLessonID()+"\" lesson_type=\""+this.getType()+"\">";
//            xml += "\n\t\t\t<lesson_id>" + this.getLessonID() + "</lesson_id>";
//            xml += "\n\t\t\t<lesson_type>" + this.getType() + "</lesson_type>";
//            xml += "\n\t\t\t<lesson_title>" + this.getTitle() + "</lesson_title>";

            for (int i = 0; i < steps.size(); i++){

                if (i == 1){
//                    xml += "\n\t\t\t<task>";
                    xml += "";
                    
                }

                xml += steps.get(i).toXML(false);

            }
            if (steps.size() > 1){
//                xml += "\n\t\t\t</task>";
                xml += "";
            }

            xml += "\n\t\t</lesson>";
        }
        return xml;
    }
    
     /**
     * Get the XML representation of the Lesson Local for saving
     * @param XMLHeader Do you want to include the XML Header?
     * @return the XLM representation of the Lesson
     */
    public String getLocalXML(boolean XMLHeader){
        String xml = "";
        if(XMLHeader)
            xml += "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
//        xml += "\n<lesson>";
        xml += "\n<lesson lesson_title=\""+this.getTitle()+"\" lesson_id=\""+this.getLessonID()+"\" lesson_type=\""+this.getType()+"\">";
//        xml += "\n\t<lesson_id>" + this.getLessonID() + "</lesson_id>";
//        xml += "\n\t<lesson_type>" + this.getType() + "</lesson_type>";
//        xml += "\n\t<lesson_title>" + this.getTitle() + "</lesson_title>";
        
        for (int i = 0; i < steps.size(); i++){
            
            if (i == 1){
            //    xml += "\n\t<task>";
//                xml += "\n\t<screen>";
            }
            
            xml += steps.get(i).getLocalXML();
            
        }
        if (steps.size() > 1){
//            xml += "\n\t</screen>";
        }
        
        xml += "\n</lesson>\n";

        return xml;
    }
    
    /****************************************************/
    /********************End Methods*********************/
    /****************************************************/
}
