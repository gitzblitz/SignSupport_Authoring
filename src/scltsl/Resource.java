/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scltsl;

/**
 * This is the class of a Resource that is added to a lesson 
 * @version v1.1
 * @author Masilo and Muchenja
 */



public class Resource {
    
    public enum ResourceType{
        IMAGE, VIDEO;
    }
    
    /***************************************************/
    /**********************Properties*******************/
    /***************************************************/
    private final String relativeImageUrl = "/shared_images/";
    private final String relativeVideoUrl = "/video/";
    private String name;
    private ResourceType type;
    private String lessonVideoType;
    private String absolutePath;
    
    /*******************************************************/
    /**********************End Properties*******************/
    /*******************************************************/
    
    
    
    /***************************************************/
    /***********************Constructor*****************/
    /***************************************************/
    
    public Resource(){
    }
    
    /**
     * The constructor to create an object with specific properties
     * @param theResourceID the id of the resource
     * @param theName the name of the resource
     */
    public Resource(String theName){
        this.name = theName;
    }
    
     /**
     * The constructor to create an object with specific properties
     * @param theName the name of the resource
     * @param resType the resource type
     */
    public Resource(String theName, ResourceType resType){
        this.name = theName;
        this.type = resType;
    }
    
     /**
     * The constructor to create an object with specific properties
     * @param theName the name of the resource
     * @param resType the resource type
     * @param theAbsolutePath the absolute path of the resource
     */
    public Resource(String theName, String theAbsolutePath, ResourceType resType){
        this.name = theName;
        this.type = resType;
        this.absolutePath = theAbsolutePath;
    }
    
    /**
     * Copy constructor of the resource object
     * @param theResource the resource to make a deep copy of
     */
    public Resource(Resource theResource){
        this.name = theResource.getName();
        this.type = theResource.getType();
    }
    
    /****************************************************/
    /********************End Constructor*****************/
    /****************************************************/
    
    
    
    /****************************************************/
    /************************Methods*********************/
    /****************************************************/
    
    /**
     * Get the type of the resource
     * @return the type of the resource
     */
    public ResourceType getType(){
        return this.type;
    }

    /**
     * Set the type of the resource
     * @param type the type of the resource
     */
    public void setType(ResourceType type) {
        this.type = type;
    }
    
    public String getAbsolutePath(){
        return this.absolutePath;
    }
    
    public void setAbsolutePath(String theAbsolutePath){
        this.absolutePath = theAbsolutePath;
    }
    
    public boolean isImage(){
        return (this.type == ResourceType.IMAGE);
    }
    
    public void setLessonVideoType(String lessonType){
        this.lessonVideoType = lessonType;
    }
    
    public String getLessonVideoType(){
        return this.lessonVideoType;
    }
    
    /**
     * Get the name of the resource
     * @return the name of the resource
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Set the name of the resource
     * @param theName the name of the resource
     */
    public void setName(String theName){
        this.name = theName;
    }
   
    /**
     * Get the string representation of this resource
     * @return the string representation
     */
    @Override
    public String toString(){
        return "";
    }
    
    /**
     * Get the XML representation of this resource
     * @return the XML representation
     */
    public String toXML(){
        
        String xml = "";
        
        if (this.type == ResourceType.IMAGE){
            xml = "<image>"+this.relativeImageUrl+this.getName()+".png</image>";
        }
        else if (this.type == ResourceType.VIDEO){
            xml = "<video>"+this.relativeVideoUrl+this.getName()+".mp4</video>";
            xml += "\n\t\t<vid_caption>"+this.getName() + "</vid_caption>";
        }
        
        return xml;
    } 
    
     /**
     * Get the XML representation of this resource
     * @return the XML representation
     */
    public String getLocalXML(){
        
        String xml = "";
        
        if (this.type == ResourceType.IMAGE){
            xml = "<image>"+this.getAbsolutePath()+"</image>";
        }
        else if (this.type == ResourceType.VIDEO){
//            xml += "<video>"+this.getAbsolutePath()+"</video>";
             xml = "<video>"+this.getAbsolutePath()+"</video>";
            xml += "\n<vid_caption>"+this.getName()+"</vid_caption>";
        }
        
        return xml;
    } 
    
    /****************************************************/
    /********************End Methods*********************/
    /****************************************************/
}
