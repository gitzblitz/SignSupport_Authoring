/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scltsl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import XMLImport.XMLImportUnit;
import XMLImport.XMLImportLesson;

/**
 *
 * @author Masilo and Chad Petersen
 */
public class Course {
    
    private String course_id;
    private String title;
    private ArrayList<String> units;
    private ArrayList<String> lessons; // load list of lessons
    
    //Default constructor for guest teacher
    public Course() {
        course_id = null;
        title = null;
        units = new ArrayList<>();
        lessons = new ArrayList<>();
    }
    
    public Course(String name) {
        this.title = name;
    }
    
    //Mutators
    public void setTitle(String t) {
        title = t;
    }
    
    public void setId(String t) {
        course_id = t;
    }
    
    public void addUnitPlaceHolder(String unitId){
        units.add(unitId);
    }
    public void addLessonPlaceHolder(String lessonID){
        lessons.add(lessonID);
    }
    //Accessors
    public String getTitle() {
        return title;
    }
    
    public String getId() {
        return course_id;
    }
    
    public ArrayList<String> getUnitPlaceHolders(){
        return units;
    }
    public ArrayList<String>getLessonPlaceHolders(){
        return lessons;
    }
    
    public String getXML(boolean IRXML) {
      String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
       if(IRXML){
          xml += "\n<course xmlns=\"http://www.w3.org/2005/Atom\" course_id=\""+this.course_id+"\" course_title=\""+this.title+"\">"
               + "\n\t<course_id>" + this.course_id + "</course_id>" 
               + "\n\t<course_title type=\"text\">" + this.title + "</course_title>";
           for(String uid: this.getUnitPlaceHolders())
                 xml += "\n\t<unit_place_holder>" + uid + "</unit_place_holder>" ;
           xml += "\n</course>\n";
           return xml;
       }
       else{
           xml += "\n<course xmlns=\"http://www.w3.org/2005/Atom\" course_id=\""+this.course_id+"\" course_title=\""+this.title+"\">"; 
//               + "\n\t<course_title type=\"text\">" + this.title + "</course_title>";
           XMLImportUnit unitHandler = new XMLImportUnit();
             for(String uid: getUnitPlaceHolders())
                 xml += unitHandler.getUnitList().get(Integer.parseInt(uid) - 1).getXML(false,false);
             return xml + "\n</course>\n";
       }
       
    }
        
    public String exportCourse(boolean IRXML) {
      String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
       if(IRXML){
           Set<String> uniqueUnits = new HashSet<>(this.getUnitPlaceHolders());
           Set<String> uniqueLessons = new HashSet<>();
           
           XMLImportUnit unitHandler = new XMLImportUnit();
           
           for(Unit unit : unitHandler.getUnitList()){
               if(uniqueUnits.contains(unit.getUnitID()))
                   uniqueLessons.addAll(unit.getLessonPlaceHolders());
           }
               
           XMLImportLesson lessonHandler = new XMLImportLesson();
             for(String lid: uniqueLessons)
                xml += lessonHandler.getLessonList().get(lessonHandler.searchForLesson(lid)).getXML(false, true);
           
           
             for(String uid: uniqueUnits)
                 xml += unitHandler.getUnitList().get(unitHandler.searchForUnit(uid)).getXML(false,true);
             
          xml += "\n<course xmlns=\"http://www.w3.org/2005/Atom\" course_id=\""+this.course_id+"\" course_title=\""+this.title+"\">"
               + "\n\t<course_id>" + this.course_id + "</course_id>" 
               + "\n\t<course_title type=\"text\">" + this.title + "</course_title>";
           for(String uid: this.getUnitPlaceHolders())
                 xml += "\n\t<unit_place_holder>" + uid + "</unit_place_holder>" ;
           xml += "\n</course>";
           return xml;
       }
       else{
           xml += "\n<course xmlns=\"http://www.w3.org/2005/Atom\" course_id=\""+this.course_id+"\" course_title=\""+this.title+"\">"; 
//               + "\n\t<course_title type=\"text\">" + this.title + "</course_title>";
           XMLImportUnit unitHandler = new XMLImportUnit();
             for(String uid: getUnitPlaceHolders())
                 xml += unitHandler.getUnitList().get(unitHandler.searchForUnit(uid)).getXML(false,false);
             return xml + "\n</course>";
       }
       
    }
    
}
