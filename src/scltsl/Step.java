/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scltsl;

import java.util.ArrayList;

/**
 *
 * @author Masilo
 */
public class Step {
    
    private int number;
    private Resource resource;
    private ArrayList<Resource> resources;
    
    public Step(int step_number){
        
        this.number = step_number;
        resources = new ArrayList<>(5);
    }
    
    public int getNumber(){
        return this.number;
    }
    
    public void setNumber(int theNumber){
        this.number = theNumber;
    }
    
    public void addResourceAt(int index, Resource theResource){
        resources.add(index, theResource);
    }
    
    public void addResource(Resource theResource){
        resources.add(theResource);
    }
    
    public int getLastIndex(){
        return resources.size() - 1;
    }
    
    public int getResourceIndex(Resource theResource){
        return resources.indexOf(theResource);
    }
    
    public Resource getResource(int resourceIndex){
        return resources.get(resourceIndex);
    }
    
    public void removeResource(int resourceIndex){
        resources.remove(resourceIndex);
    }
    
    public ArrayList<Resource> getResources(){
        return resources;
    }
    
    public String toXML(boolean IRXML){
        
        String xml = "";
        if(IRXML){
            if (this.number == 1){ // lesson description make it screen tag
//                xml += "\n\t<lesson_description>";
            //    xml += "\n\t<screen>";
//                xml += "\n\t\t<lesson_description>";
 //               xml += "\n\t\t<screenID>"+Integer.toString(this.number)+"</screenID>";
                for (int i = 0; i < resources.size(); i++){
                    xml += "\n\t<screen>";
                    xml += "\n\t\t<screenID>"+Integer.toString(this.number+ i)+"</screenID>";
                    if (resources.get(i) == null){
                        xml += "\n\t\t<res></res>";
                    }
                    else {
                        xml += "\n\t\t"+resources.get(i).toXML();
                        //resources are the videos and images
                    }
                }
//                xml += "\n\t</lesson_description>";
                xml += "\n\t</screen";
            }
            else if (this.number == 2){
//                xml += "\n\t\t<task_description>";
               
                for (int i = 0; i < resources.size(); i++){
                     xml += "\n\t<screen>";
                     xml += "\n\t<screenID>task_description_"+i+"</screenID>";
                    if (resources.get(i) == null){
//                        xml += "\n\t\t\t<res></res>";
                        xml += "\n\t\t<res></res>";
                        
                    }
                    else {
                        //xml += "\n\t\t\t"+resources.get(i).toXML();
                        xml += "\n\t\t"+resources.get(i).toXML();
                    }
                     xml += "\n\t</screen>";
                }
//                xml += "\n\t\t</task_description>";
               
            }
            else {
//                xml += "\n\t\t<step_"+(this.number - 2)+">";
                xml += "\n\t<screen>";
                xml += "\n\t\t<screenID>step_"+(this.number -2)+"</screenID>";
                for (int i = 0; i < resources.size(); i++){
                    if (resources.get(i) == null){
//                        xml += "\n\t\t\t<res></res>";
                        xml += "\n\t\t<res></res>";

                    }
                    else {
//                        xml += "\n\t\t\t"+resources.get(i).toXML();
                        xml += "\n\t\t"+resources.get(i).toXML();
                    }
                }
//                xml += "\n\t\t</step_"+(this.number - 2) +">";
                xml += "\n\t</screen>";
            }
        }else{
            if (this.number == 1){ // lesson description
//                xml += "\n\t\t\t<lesson_description>";
//                xml += "\n\t<screen>";
//                xml += "\n\t\t<screenID>lesson_description_"+this.number+"</screenID>";
                for (int i = 0; i < resources.size(); i++){
                    xml += "\n\t<screen>";
                    xml += "\n\t\t<screenID>lesson_description_"+(this.number + i)+"</screenID>";
                    if (resources.get(i) == null){
//                        xml += "\n\t\t\t\t<res></res>";
                        xml += "\n\t\t<res></res>";
                    }
                    else {
//                        xml += "\n\t\t\t\t"+resources.get(i).toXML();
                        xml += "\n\t\t"+resources.get(i).toXML();
                    }
                    xml += "\n\t</screen>";
                }
//                xml += "\n\t\t\t</lesson_description>";
//                xml += "\n\t</screen>";
            }
            else if (this.number == 2){
//                xml += "\n\t\t\t\t<task_description>";
                xml += "\n\t<screen>";
                xml += "\n\t\t<screenID>task_description"+this.number+"</screenID>";
                for (int i = 0; i < resources.size(); i++){
                    if (resources.get(i) == null){
//                        xml += "\n\t\t\t\t\t<res></res>";
                         xml += "\n\t\t<res></res>";
                    }
                    else {
//                        xml += "\n\t\t\t\t\t"+resources.get(i).toXML();
                        xml += "\n\t\t"+resources.get(i).toXML();
                    }
                }
//                xml += "\n\t\t\t\t</task_description>";
                xml += "\n\t</screen>";
            }
            else {
//                xml += "\n\t\t\t\t<step_"+(this.number - 2)+">";
                xml += "\n\t<screen>";
                xml += "\n\t\t<screenID>step_"+(this.number - 2)+"</screenID>";
                for (int i = 0; i < resources.size(); i++){
                    if (resources.get(i) == null){
//                        xml += "\n\t\t\t\t\t<res></res>";
                         xml += "\n\t\t<res></res>";
                    }
                    else {
//                        xml += "\n\t\t\t\t\t"+resources.get(i).toXML();
                        xml += "\n\t\t"+resources.get(i).toXML();
                    }
                }
//                xml += "\n\t\t\t\t</step_"+(this.number - 2) +">";
                xml += "\n\t</screen>";
            }
        }
        return xml;
        
    }
    
    public String getLocalXML(){
        
        String xml = "";
        
        if (this.number == 1){ // lesson description
//            xml += "\n\t<lesson_description>";
//            xml += "\n\t<screen>";
//            xml += "\n\t\t<screenID>lesson_description_"+this.number+"</screenID>";
            
            for (int i = 0; i < resources.size(); i++){
                int count = 0;
                 xml += "\n\t<screen>";
                 xml += "\n\t\t<screenID>lesson_description_"+(this.number + i)+"</screenID>";
                if (resources.get(i) == null){
                    xml += "\n\t\t<res></res>";
                }
                else if((resources.get(i).getType() == Resource.ResourceType.VIDEO) && count <=1) {
//                   only add one video per tag
                    xml += "\n\t\t"+resources.get(i).getLocalXML();
                    count++;
                }else{
                    xml += "\n\t\t"+resources.get(i).getLocalXML();
                }
                 xml += "\n\t</screen>";
            }
//            xml += "\n\t</lesson_description>";
//            xml += "\n\t</screen>";
        }
        else if (this.number == 2){
//            xml += "\n\t\t<task_description>";
//            xml += "\n\t<screen>";
//            xml += "\n\t\t<screenID>task_description_"+this.number+"</screenID>";
            for (int i = 0; i < resources.size(); i++){
                 xml += "\n\t<screen>";
                 xml += "\n\t\t<screenID>task_description_"+i+"</screenID>";
                if (resources.get(i) == null){
//                    xml += "\n\t\t\t<res></res>";
                    xml += "\n\t\t<res></res>";
                }
                else {
//                    xml += "\n\t\t\t"+resources.get(i).getLocalXML();
                    xml += "\n\t\t"+resources.get(i).getLocalXML();
                }
                xml += "\n\t</screen>";
            }
//            xml += "\n\t\t</task_description>";
//            xml += "\n\t</screen>";
        }
        else {
//            xml += "\n\t\t<step_"+(this.number - 2)+">";
            xml += "\n\t<screen>";
            xml += "\n\t\t<screenID>step_"+(this.number - 2)+"</screenID>";
            for (int i = 0; i < resources.size(); i++){
                if (resources.get(i) == null){
//                    xml += "\n\t\t\t<res></res>";
                    xml += "\n\t\t<res></res>";
                }
                else {
//                    xml += "\n\t\t\t"+resources.get(i).getLocalXML();
                     xml += "\n\t\t"+resources.get(i).getLocalXML();
                }
            }
//            xml += "\n\t\t</step_"+(this.number - 2) +">";
            xml += "\n\t</screen>";
            
        }
        
        return xml;
        
    }
}
