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
import scltsl.Lesson;
import scltsl.Resource;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLImportLesson extends DefaultHandler {

    protected Lesson aLesson;
    protected boolean lesson_id, lesson_type, lesson_title, lesson_description,
            task_description, stepAtN, video, image, screen, screenID;
    protected int foundLessonID = -1;
    protected int stepN = 0;
//    protected String id, type, title;
    protected static ArrayList<Lesson> lessons = new ArrayList<>();

    public XMLImportLesson() {
        lesson_id = lesson_type = lesson_title = screen = screenID = lesson_description = task_description = stepAtN = video = image = false;
    }

    //getter method for Lesson list
    public ArrayList<Lesson> getLessonList() {
        return lessons;
    }

    // getter method for list of lesson names
    public ArrayList<String> getListOfLessonNames() {

        ArrayList<String> list = new ArrayList<>(10);

        for (int i = 0; i < lessons.size(); i++) {
            list.add(lessons.get(i).getTitle());
        }

        return list;
    }

    // getter method for list of lesson names
    public ArrayList<String> getListOfTypeOLessonNames() {

        ArrayList<String> list = new ArrayList<>(10);

        for (int i = 0; i < lessons.size(); i++) {
            if (!(lessons.get(i).getType().isEmpty()) && lessons.get(i).getType().toLowerCase().charAt(0) == 'o') {
                list.add(lessons.get(i).getTitle());
            }
        }

        return list;
    }

    // getter method for list of lesson names
    public ArrayList<String> getListOfTypeSLessonNames() {

        ArrayList<String> list = new ArrayList<>(10);

        for (int i = 0; i < lessons.size(); i++) {
            if (!(lessons.get(i).getType().isEmpty()) && lessons.get(i).getType().toLowerCase().charAt(0) == 's') {
                list.add(lessons.get(i).getTitle());
            }
        }

        return list;
    }

    // getter method for list of lesson names
    public ArrayList<String> getListOfTypeELessonNames() {

        ArrayList<String> list = new ArrayList<>(10);

        for (int i = 0; i < lessons.size(); i++) {
            if (!(lessons.get(i).getType().isEmpty()) && lessons.get(i).getType().toLowerCase().charAt(0) == 'e') {
                list.add(lessons.get(i).getTitle());
            }
        }

        return list;
    }

    public int searchForLesson(String lesson_id) { // improve searching algortihm
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getLessonID().equals(lesson_id)) {
                return i;
            }
        }
        return -1;
    }

    public String getLessonID(String lesson_title, char lesson_type) { // improve searching algortihm
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getType().toLowerCase().charAt(0) == lesson_type
                    && lessons.get(i).getTitle().equalsIgnoreCase(lesson_title)) {
                return lessons.get(i).getLessonID();
            }
        }
        return "";
    }
    
    public Lesson getLesson(String lesson_title){
        for(int i = 0; i < lessons.size(); i++){
            if(lessons.get(i).getTitle().equalsIgnoreCase(lesson_title))
                return lessons.get(i);
        }
        return null;
    }
    
    public String getBiggestLessonID(){
        String highest = "0";
        
        for(int i = 0; i < lessons.size(); i++){
            if(Integer.parseInt(lessons.get(i).getLessonID()) > Integer.parseInt(highest))
                highest = lessons.get(i).getLessonID();
        }
        if (Integer.parseInt(highest) < 10) {
            highest = "0" + Integer.parseInt(highest);
        }
        return highest;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        int attributeLength = attributes.getLength();
        if (qName.equalsIgnoreCase("lesson")) {
            //initialize Lesson object
            aLesson = new Lesson();
            String id = attributes.getValue("lesson_id");
            String type = attributes.getValue("lesson_type");
            String title = attributes.getValue("lesson_title");
            System.out.println(id + " " + type + " "+ title);
            foundLessonID = searchForLesson(id);
            if(foundLessonID == -1){
                aLesson.setTitle(title);
                aLesson.setType(type);
                aLesson.setLessonID(id);
            }else{
                lessons.get(foundLessonID).setType(type);
                lessons.get(foundLessonID).setTitle(title);
                lessons.get(foundLessonID).setLessonID(id);
            }
        }else if (qName.equalsIgnoreCase("screen")){
            screen = true;
        }else if (qName.equalsIgnoreCase("screenID")){
         screenID = true;   
        }
//        else if (qName.equalsIgnoreCase("lesson_id")) {
//            lesson_id = true; //A lesson id found 
//        }
//        else if (qName.equalsIgnoreCase("lesson_type")) {
//            lesson_type = true; //A lesson title found
//        }
//        else if (qName.equalsIgnoreCase("lesson_title")) {
//            lesson_title = true; //A lesson title found
//        }
//        else if (qName.equalsIgnoreCase("lesson_description")) {
//            stepN = 0;
//            lesson_description = true; //A lesson description found
//        } else if (qName.equalsIgnoreCase("task_description")) {
//            stepN = 1;
//            task_description = true; //A task description found
//        } else if (qName.toLowerCase().contains("step_")) {
//            try{ //Check if Steps tag has a valid number at the end eg. "step_4"
//                stepN = Integer.parseInt(qName.substring(qName.indexOf("_") + 1).replaceAll("^0+(?=\\d+$)", "")) + 1;
//                stepAtN = true; //Remember that Step_1 is stored in ArrayList at [0 -> 1-1]
//            }catch(NumberFormatException e){/*not a legal step, shouldnt enter this*/}
//        } 
        else if (qName.equalsIgnoreCase("image")) {
            image = true; //An image found
        } else if (qName.equalsIgnoreCase("video")) {
            video = true; //A video found
        }
    }
    
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //add Course object to Course list
        if (qName.equalsIgnoreCase("lesson") && foundLessonID == -1) 
            lessons.add(aLesson);
    }
    
    
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
 
//        if (lesson_id) {
//            String lid = (new String(ch, start, length)).trim();
//            foundLessonID = searchForLesson(lid);
//            if(foundLessonID == -1)
//                aLesson.setLessonID(lid);
//            lesson_id = false; 
//        } else if (lesson_type) {
//            String type = (new String(ch, start, length)).trim();
//            if(foundLessonID == -1)
//                aLesson.setType(type);
//            else{
//                lessons.get(foundLessonID).setType(type);   
//            }
//            lesson_type = false;
//        } else if (lesson_title) {
//            String title = (new String(ch, start, length)).trim();
//            if(foundLessonID == -1)
//                aLesson.setTitle(title);
//            else{
//                lessons.get(foundLessonID).setTitle(title);
//            }
//            lesson_title = false;
//        }
        if(screen){
            screen = false;
        }else if(screenID){    
            String screen_id = (new String (ch,start,length)).trim();
            if(screen_id.toLowerCase().contains("lesson_description")){
                stepN = 0;
            }else if (screen_id.toLowerCase().contains("task_description")){
                stepN = 1;
            }else if(screen_id.toLowerCase().contains("step_")){
                try{ //Check if Steps tag has a valid number at the end eg. "step_4"
//                stepN = Integer.parseInt(screen_id.substring(screen_id.indexOf("_") + 1).replaceAll("^0+(?=\\d+$)", "")) + 1;
                  stepN = Integer.parseInt(screen_id.substring(screen_id.indexOf("_") + 1).replaceAll("^0+(?=\\d+$)", ""));
                stepAtN = true; //Remember that Step_1 is stored in ArrayList at [0 -> 1-1]
            }catch(NumberFormatException e){/*not a legal step, shouldnt enter this*/}
            }
            screenID = false;
        }
        else if (image){
            String path = (new String(ch, start, length)).trim();
            String name = path.substring(path.lastIndexOf("\\") + 1);
            Resource aResource = new Resource(name,path, Resource.ResourceType.IMAGE);
            if(foundLessonID == -1){
                System.out.println(aResource.getName() + " at step " + stepN);
                aLesson.addResource(stepN, aResource);
            }else{
                System.out.println(aResource.getName() + " at step " + stepN);
                lessons.get(foundLessonID).addResource(stepN, aResource);
            }
            image = false;
        }  else  if (video){
            String path = (new String(ch, start, length)).trim();
            String name = path.substring(path.lastIndexOf("\\") + 1);
            Resource aResource = new Resource(name, path, Resource.ResourceType.VIDEO);
            if(foundLessonID == -1){
                aResource.setLessonVideoType(aLesson.getType());
                  System.out.println(aResource.getName() + " at step " + stepN);
                aLesson.addResource(stepN, aResource);
            }
            else{
                aResource.setLessonVideoType(lessons.get(foundLessonID).getType());
                 System.out.println(aResource.getName() + " at step " + stepN);
                lessons.get(foundLessonID).addResource(stepN, aResource);
            } 
            video = false;
        } 
//        else if (lesson_description) {
//            /*String name = (new String(ch, start, length)).trim();
//            Resource aResource = new Resource();
//            aResource.setName(name);
//            if(foundLessonID == -1)
//                aLesson.addResource(0,aResource);
//            else{
//                lessons.get(foundLessonID).addResource(0,aResource);
//            }*/
//            lesson_description = false;
//        }else if(task_description){
//            task_description = false;
//        } 
//        else if (stepAtN) { //The Resources type!!!
//           /* String name = (new String(ch, start, length)).trim();
//            Resource aResource = new Resource();
//            aResource.setName(name);
//            if(foundLessonID == -1)
//                aLesson.addResource(stepN,aResource);
//            else{
//                lessons.get(foundLessonID).addResource(stepN,aResource);
//            }*/
//            stepAtN = false;
//        }
    }
}
