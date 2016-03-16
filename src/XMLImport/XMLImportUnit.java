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

import scltsl.Unit;
import scltsl.Lesson;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLImportUnit extends DefaultHandler {
    
    protected Unit aUnit;
    protected boolean unit_id, unit_title, lesson_place_holder;
    protected int foundUnitID = -1;
    protected static ArrayList<Unit> units = new ArrayList<>();
    
    public XMLImportUnit(){
        unit_id = unit_title = lesson_place_holder = false;
        
    }
    
    //getter method for Unit list
    public ArrayList<Unit> getUnitList() {
        return units;
    }
    
    // getter method for list of unit names
    public ArrayList<String> getListOfUnitNames(){
        
        ArrayList<String> list = new ArrayList<>(10);
        
        for (int i = 0; i < units.size(); i++){
            list.add(units.get(i).getTitle());
        }
        
        return list;
    }
    
    public int searchForUnit(String unit_id){ // improve searching algortihm
        for(int i = 0; i < units.size(); i++){
            if(units.get(i).getUnitID().equals(unit_id))
                return i;
        }
        return -1;
    }
    
    public String searchForUnitName(String unit_id){ // improve searching algortihm
        for(int i = 0; i < units.size(); i++){
            if(units.get(i).getUnitID().equals(unit_id))
                return units.get(i).getTitle();
        }
        return "";
    }
    
    public Unit getUnit(String unit_title){
        for(int i = 0; i < units.size(); i++){
            if(units.get(i).getTitle().equalsIgnoreCase(unit_title))
                return units.get(i);
        }
        return null;
    }
    
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        
        if (qName.equalsIgnoreCase("unit")) {
            //initialize Unit object
            aUnit = new Unit();
        } else if (qName.equalsIgnoreCase("unit_id")) {
            unit_id = true; //A course id found 
        } else if (qName.equalsIgnoreCase("unit_title")) {
            unit_title = true; //A course title found
        } else if (qName.equalsIgnoreCase("lesson_place_holder")) {
            lesson_place_holder = true; //A unit place holder found
        }
    }
    
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //add Course object to Course list
        if (qName.equalsIgnoreCase("unit") && foundUnitID == -1) //search before adding
            units.add(aUnit);
    }
    
    
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        
        if (unit_id) {
            String uid = (new String(ch, start, length)).trim();
            foundUnitID = searchForUnit(uid);
            if(foundUnitID == -1)//search for uid.. if not found make use aUnit else edit the unit found
                aUnit.setUnitID(uid); 
            unit_id = false;
        } else if (unit_title) {
            String title = (new String(ch, start, length)).trim();
            if(foundUnitID == -1)
                aUnit.setTitle(title);
            else{
                units.get(foundUnitID).setTitle(title);
            }
            unit_title = false;
        } else if(lesson_place_holder) {
            String buff = (new String(ch, start, length)).trim();
            if(foundUnitID == -1)
                aUnit.addLessonPlaceHolder(buff);
            else{ //Must STill check if the lesson exists
                units.get(foundUnitID).addLessonPlaceHolder(buff);
            }
           /* boolean found = false;
            try{
            for(Lesson l: XMLImportLesson.lessons){
                if((l.getLessonID()).equals(buff)){
                    found = true;
                }
            }
            }catch(Exception e){System.out.println("Error");}
            if(!found){
                Lesson aLesson = new Lesson(); 
                aLesson.setLessonID(buff);
                XMLImportLesson.lessons.add(aLesson);
            }*/ lesson_place_holder = false;
        }
        
    }
}
