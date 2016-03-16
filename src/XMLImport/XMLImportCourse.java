/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XMLImport;

/**
 *
 * @author Chad Petersen
 */

import scltsl.Course;
import scltsl.Unit;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLImportCourse extends DefaultHandler {
    
    protected Course aCourse;
    protected boolean course_id, course_title, unit_place_holder; 
    protected static ArrayList<Course> courses = new ArrayList<>();
    
    public XMLImportCourse(){
        course_id = course_title = unit_place_holder = false;
    }
    
    //getter method for Course list
    public ArrayList<Course> getCourseList() {
        return courses;
    }
    
    // getter method for list of course names
    public ArrayList<String> getListOfCourseNames(){
        
        ArrayList<String> list = new ArrayList<>(10);
        
        for (int i = 0; i < courses.size(); i++){
            list.add(courses.get(i).getTitle());
        }
        
        return list;
    }
    
    public Course getCourse(String course_title){
        for(int i = 0; i < courses.size(); i++){
            if(courses.get(i).getTitle().equalsIgnoreCase(course_title))
                return courses.get(i);
        }
        return null;
    }
    
        public int searchForCourse(String course_id) { // improve searching algortihm
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId().equals(course_id)) {
                return i;
            }
        }
        return -1;
    }
    
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        
        if (qName.equalsIgnoreCase("course")) {
            //initialize Employee object
            aCourse = new Course();
        } else if (qName.equalsIgnoreCase("course_id")) {
            course_id = true; //A course id found 
        } else if (qName.equalsIgnoreCase("course_title")) {
            course_title = true; //A course title found
        } else if (qName.equalsIgnoreCase("unit_place_holder")) {
            unit_place_holder = true; //A unit place holder found
        }
    }
    
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //add Course object to Course list
        if (qName.equalsIgnoreCase("course")) 
            courses.add(aCourse);
    }
    
    
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
 
        if (course_id) {
            aCourse.setId((new String(ch, start, length)).trim());
            course_id = false;
        } else if (course_title) {
            aCourse.setTitle((new String(ch, start, length)).trim());
            course_title = false;
        } else if(unit_place_holder) {
            String buff = (new String(ch, start, length)).trim();
            aCourse.addUnitPlaceHolder(buff);
            /*boolean found = false;
            try{
            for(Unit u: XMLImportUnit.units){
                if((u.getUnitID()).equals(buff)){
                    found = true;
                }
            } 
            }catch(Exception e){System.out.println("Error");}
            if(!found){
                Unit aUnit = new Unit(); 
                aUnit.setUnitID(buff);
                XMLImportUnit.units.add(aUnit);
            }*/ unit_place_holder = false;
        }
        
    }
}
