/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scltsl;

import java.util.ArrayList;
import XMLImport.XMLImportLesson;

/**
 *
 * @author Masilo and Chad Petersen
 */
public class Unit {
   
    private String title;
    private String unitType;
    private String unitID;
    private ArrayList<String> lessons = new ArrayList<>();
    
    //Default constructor for the XML parser    
    public Unit(){
        this.title = "Unknown";
        this.unitID = null;
    }
    
    //parameterised constructor for creating a unit with a title
     public Unit(String u_title, String u_id) {
        this.title = u_title;
        this.unitID = u_id;
    }
     
    public void setUnitID(String id){
        unitID = id;
    }
    
    public void setTitle(String t){
        title = t;
    }
    
    public void setUnitType(String type){
        unitType = type;
    }
    
    public void addLessonPlaceHolder(String lessonId){
        lessons.add(lessonId);
    }
     
    public String getUnitID() {
        return unitID;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getUnitType() {
        return unitType;
    }
  
    public ArrayList<String> getLessonPlaceHolders(){
        return lessons;
    }
    
     public String getXML(boolean XMLHeader, boolean IRXML) {
        String xml = "";
        if(XMLHeader)
            xml += "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        
        if(IRXML){
            xml += "\n<unit>"
                + "\n\t<unit_title type=\""+"text"+"\">"+this.title+"</unit_title>"
                + "\n\t<unit_id>"+this.unitID+"</unit_id>";
             for(String lid: this.getLessonPlaceHolders())
                   xml += "\n\t<lesson_place_holder>" + lid + "</lesson_place_holder>" ;
             xml += "\n</unit>\n"; 
             
             
             return xml; 
        }
        else{
            xml += "\n\t<unit unit_title=\""+this.title+"\" unit_id=\""+this.unitID+"\">";
//                + "\n\t\t<unit_title type=\""+"text"+"\">"+this.title+"</unit_title>"
//                + "\n\t\t<unit_id>"+this.unitID+"</unit_id>";
            XMLImportLesson lessonHandler = new XMLImportLesson();
            for(String lid: this.getLessonPlaceHolders())
                xml += lessonHandler.getLessonList().get(lessonHandler.searchForLesson(lid)).getXML(XMLHeader,false);
            return xml + "\n\t</unit>"; 
        }
    }
    //Return the unit id
    @Override
    public String toString() {
        return ""+ unitID;
    }
    
}
