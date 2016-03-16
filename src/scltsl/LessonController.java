/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scltsl;

import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Muchenja Namumba, Masilo Mapaila and Chad Petersen
 */
public class LessonController extends AnchorPane implements Initializable {

    /**
     * *****Global Constants**********
     */
    private final int resourceXDistance = 110;
    private final int resourceYDistance = 110;
    private final int resourceWidth = 80;
    private final int resourceHeight = 80;
    private final int dropRegionLayoutX = 165;
    private final int dropRegionLayoutY = 80;
    private final int stepButtonLayoutX = 59;
    private final int stepButtonLayoutY = 323;
    private final ArrayList<String> imageExtensions = new ArrayList<String>() {
        {
            add("*.png");
            add("*.gif");
            add("*.jpg");
            add("*.jpeg");
            add("*.jpe");
            add("*.jfif");
            add("*.bmp");
        }
    };
    private final ArrayList<String> videoExtensions = new ArrayList<String>() {
        {
            add("*.mp4");
            add("*.avi");
            add("*.wmv");
            add("*.mpg");
            add("*.mpg2");
            add("*.mpeg");
            add("*.3gp");
            add("*.3gp2");
            add("*.3gpp");
            add("*.3gpp2");
        }
    };
    /**
     * *****Global Variables**********
     */
    static boolean trueOrFalse = false;
    private boolean creatingLesson = false;
    private int currentLessonIndex;
    final ImageView saveImage = new ImageView(new File("icons/save_1.png").toURI().toString());
    final ImageView playImage = new ImageView(new File("icons/play-icon.png").toURI().toString());
    
    
    private Lesson lesson;
    public ArrayList<Rectangle> stepDropRegions;
    public ArrayList<Label> stepLabels;
    public ArrayList<String> videoNames;
    public ArrayList<String> pictureNames;
    public ArrayList<Lesson> listOfLessons;
    public ArrayList<String> lessonNames;
    PreviewPopupController controller;
    MessagePopupController messageController;
    
    
    // default values
    private int numberOfSteps = 1;
    private int picCount = 0;
    private int vidCount = 0;
    private int lessonCount = 0;
    Rectangle firstStepDropRegion;
    
    @FXML
    private Label status;
    public Label firstStepLabel;
    public Label lessonDecription;
    public Label taskDecription;
    public Label taskSteps;
    public Button button;
    public Button newStep;
    public Button saveButton;
    public Button createLessonButton;
    public Button playButton;
    public AnchorPane lessonPane;
    public AnchorPane picturePane;
    public ScrollPane lessonScroll;
    public ListView videoList;
    public ComboBox lessonList;
    public ComboBox lessonTypeList;
    public Rectangle ldDropRegion;
    public Rectangle tdDropRegion;
    public Rectangle s1DropRegion;
    public TextField l_title;
    public TextField l_type;
    private SCLTSL application;

    public void setApp(SCLTSL application) {
        this.application = application;
        
        getListOfResources();
        refreshResourceList();
        refreshLessonList();
        refreshLessonTypeList();
        // set lesson type list
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("O");
        types.add("S");
        types.add("E");
        lessonTypeList.setItems(types);
         
//        lessonTypeList.valueProperty().addListener(new ChangeListener<String>() {
//
//            @Override
//            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//               
//            }
//        }
//        );
        // set button icons
        /*saveImage.setFitHeight(32);
        playImage.setFitHeight(32);
        saveImage.setFitWidth(32);
        playImage.setFitWidth(32);
        saveButton.setGraphic(saveImage);
        playButton.setGraphic(playButton);*/
        

        stepDropRegions = new ArrayList<>(10);
        stepLabels = new ArrayList<>(10);
        firstStepDropRegion = s1DropRegion;

        /* set drag-and-drop events */
        ldDropRegion.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragEntered(event, ldDropRegion);
            }
        });
        ldDropRegion.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragExited(event, ldDropRegion);
            }
        });
        tdDropRegion.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragEntered(event, tdDropRegion);
            }
        });
        tdDropRegion.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragExited(event, tdDropRegion);
            }
        });
        s1DropRegion.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragEntered(event, s1DropRegion);
            }
        });
        s1DropRegion.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragExited(event, s1DropRegion);
            }
        });
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    
    /**
     * Draw the gui representation of the selected lesson
     *
     * @param event the combobox selection event
     */
    public void selectLesson(ActionEvent event) {

        // clear workspace
        clearWorkSpace();

        if (lessonList.getSelectionModel().isSelected(0)) {
            currentLessonIndex = 0;
            newLesson(event);
        } else {

            try {

                // get the lesson selected
                lesson = application.lessonHandler.getLesson((String) lessonList.getSelectionModel().getSelectedItem());

                // get the type of the lesson selected
                if (lesson.getType().toLowerCase().charAt(0) == 'o') {
                    lessonTypeList.getSelectionModel().select(0);
                } else if (lesson.getType().toLowerCase().charAt(0) == 's') {
                    lessonTypeList.getSelectionModel().select(1);
                } else if (lesson.getType().toLowerCase().charAt(0) == 'e') {
                    lessonTypeList.getSelectionModel().select(2);
                }
                currentLessonIndex = lessonList.getSelectionModel().getSelectedIndex();

                // draw gui representation of lesson selected
                for (int i = 0; i < lesson.getSteps().size(); i++) {
                    if (i > 2) { // other steps
                        newStep(event);
                    }
                    for (int j = 0; j < lesson.getSteps().get(i).getResources().size(); j++) {

                        // get the resource
                        Resource res = lesson.getSteps().get(i).getResources().get(j);

                        Node s;
                        if (res.isImage()) {
                            //s = new ImageView(new File("images/Photo.jpg").toURI().toString());
                            
                            s = new ImageView(new File(res.getAbsolutePath()).toURI().toString());
                            ((ImageView) s).setFitHeight(resourceHeight);
                            ((ImageView) s).setFitWidth(resourceWidth);
                        } else {
                            s = new Text(res.getName());
                            ((Text) s).setWrappingWidth(resourceWidth);
                        }

                        setDragEvents(s);
                        s.setId(i + "_" + (lesson.getlastResourceIndex(i) + 1));

                        if (i == 0) {
                            s.setLayoutX(ldDropRegion.getLayoutX());
                            s.setLayoutY(ldDropRegion.getLayoutY());
                            nextResource(ldDropRegion);
                        } else if (i == 1) {
                            s.setLayoutX(tdDropRegion.getLayoutX());
                            s.setLayoutY(tdDropRegion.getLayoutY());
                            nextResource(tdDropRegion);
                        } else if (i == 2) {
                            s.setLayoutX(s1DropRegion.getLayoutX());
                            s.setLayoutY(s1DropRegion.getLayoutY());
                            nextResource(s1DropRegion);
                        } else if (stepDropRegions.size() > 0 && numberOfSteps > 1 && i > 2) {
                            Rectangle rec = stepDropRegions.get(numberOfSteps - 2);
                            s.setLayoutX(rec.getLayoutX());
                            s.setLayoutY(rec.getLayoutY());
                            nextResource(rec);
                        }

                        if (res.isImage()) {
                            lessonPane.getChildren().add((ImageView) s);
                        } else {
                            lessonPane.getChildren().add((Text) s);
                        }

                    }
                }
            } catch (Exception e) {
            }
        }
    }
    
    private void setDragEvents(final Node s) {
        s.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                handleOnDragDetected(t);
            }
        });
        s.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                handleOnDragOver(t);
            }
        });
        s.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                handleOnDragEntered(t, s);
            }
        });
        s.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                handleOnDragExited(t, s);
            }
        });
        s.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                handleOnDragDropped(t);
            }
        });
    }
    
    
    private void clearWorkSpace() {
        // remove step drop regions
        for (Object obj : stepDropRegions) {
            lessonPane.getChildren().remove(obj);
        }
        stepDropRegions.clear();

        // remove step labels
        for (Object obj : stepLabels) {
            lessonPane.getChildren().remove(obj);
        }
        stepLabels.clear();

        // reset step count
        numberOfSteps = 1;

        // remove resources
        ArrayList toDeleteResources = new ArrayList();
        for (Object obj : lessonPane.getChildren()) {
            if (obj instanceof Text || obj instanceof ImageView) {
                toDeleteResources.add(obj);
            }
        }
        for (Object obj : toDeleteResources) {
            lessonPane.getChildren().remove(obj);
        }


        // update scroll bars
        lessonPane.setPrefHeight(428.0);
        lessonPane.setPrefWidth(667.0);

        // reposition widgets
        l_title.setText("");
        //l_type.setText("");
        ldDropRegion.setLayoutX(dropRegionLayoutX);
        ldDropRegion.setLayoutY(23.0);
        tdDropRegion.setLayoutX(dropRegionLayoutX);
        tdDropRegion.setLayoutY(133.0);
        s1DropRegion.setLayoutX(dropRegionLayoutX);
        s1DropRegion.setLayoutY(243.0);
        newStep.setLayoutX(stepButtonLayoutX);
        newStep.setLayoutY(stepButtonLayoutY);
    }
    
    public void selectLessonType(ActionEvent event) {
      
//        String type = (String) lessonTypeList.getSelectionModel().getSelectedItem().toString();
//       
//         System.out.println("Type: "+ type);
//        if (type == null){
//            System.out.println("No item selected. Type = "+ type);
//        }else{
//            lesson.setType(type);
//            System.out.println("The selected item is: "+ type);
//        }
   
//        lesson.setType(type);
//        lesson.setType(lessonTypeList.getSelectionModel().getSelectedItem().toString());

    lessonTypeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(t != null){
                    System.out.println("The value of t is: " + t);
                }else if (t1 != null){
                     System.out.println("t1 value is = " + t1);
                }else{
                    System.out.println("t=" + t + " t1="+ t1);
                }
                
               lesson.setType(t);
//                lesson.setType(t1);
//                t1 = lessonTypeList.getValue().toString();
//                 System.out.println("changed value is " + t1);
//                lesson.setType(t1);
            }
        }
        
        );
    }
    
    public void createCourse(ActionEvent event) {
        application.createCourseOrUnit();
    }
    
    @FXML
    public void backToCourse(){
        openCourse("");
    }
    
    @FXML
    public void openCourse(String courseID) {
        if (courseID.isEmpty()) {
            application.createCourseOrUnit();
        } else {
           Course course = application.courseHandler.getCourseList().get(application.courseHandler.searchForCourse(courseID));
        }
    }

    private void refreshLessonList() {
        lessonCount = application.lessonHandler.getLessonList().size();
        ObservableList<String> lessons = FXCollections.observableArrayList(application.lessonHandler.getListOfLessonNames());
        lessons.add(0, "New Lesson");
        //Added
        lessonList.getItems().clear();
        lessonList.setItems(lessons);
    }
    
        private void refreshLessonTypeList() {        
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add(0,"O");
        types.add(1,"S");
        types.add(2,"E");
     
        lessonTypeList.setItems(types);
       
    }

    public void setLesson(Lesson theLesson) {
        if (theLesson == null) {
            lesson = new Lesson();
        } else {
            lesson = theLesson;
        }
    }

    public boolean isLesson(String name) {
        String ext = "*" + name.substring(name.lastIndexOf("."));

        if (ext.equals("*.xml")) {
            return true;
        }

        return false;
    }

    @FXML
    private void newStep(ActionEvent event) {

        // create step drop region
        final Rectangle newRegion = new Rectangle(resourceWidth, resourceHeight);
        newRegion.setLayoutX(dropRegionLayoutX); 
        newRegion.setLayoutY(243.0 + ((numberOfSteps)*91));
        //newRegion.setLayoutY(dropRegionLayoutY + ((numberOfSteps + 2) * resourceYDistance));
        newRegion.setEffect(firstStepDropRegion.getEffect());
        newRegion.setArcHeight(firstStepDropRegion.getArcHeight());
        newRegion.setArcWidth(firstStepDropRegion.getArcWidth());
        newRegion.setFill(firstStepDropRegion.getFill());
        newRegion.setSmooth(firstStepDropRegion.smoothProperty().get());
        newRegion.setStroke(firstStepDropRegion.getStroke());
        newRegion.setStrokeDashOffset(firstStepDropRegion.getStrokeDashOffset());
        newRegion.setStrokeLineCap(firstStepDropRegion.getStrokeLineCap());
        newRegion.setId("" + (numberOfSteps + 2));

        // add the OnDragOver event handler
        newRegion.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragOver(event);
            }
        });

        // add the OnDragEntered event handler
        newRegion.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragEntered(event, newRegion);
            }
        });

        // add the OnDragExited event handler
        newRegion.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragExited(event, newRegion);
            }
        });

        // add the OnDragDropped event handler
        newRegion.setOnDragDropped(new EventHandler<DragEvent>() { // set event handler
            @Override
            public void handle(DragEvent event) {
                handleOnDragDropped(event);
            }
        });
        stepDropRegions.add(newRegion);

        // create step lable
        Label newLabel = new Label("Step " + (numberOfSteps + 1) + ":");
        newLabel.setLayoutX(firstStepLabel.getLayoutX());
        newLabel.setLayoutY(263.0 + ((numberOfSteps)*91));
        newLabel.setFont(firstStepLabel.getFont());
        stepLabels.add(newLabel);

        // add widgets to gui
        lessonPane.getChildren().add(stepLabels.get(numberOfSteps - 1));
        lessonPane.getChildren().add(stepDropRegions.get(numberOfSteps - 1));

        // reposition new step button
        newStep.setLayoutY(323.0 + ((numberOfSteps)*91));

        // increment step count
        numberOfSteps++;

        // add empty step slot in lesson
        //lesson.addResourceBefore(null, null);

        // update content panel size and scrollbar
        double prefHight = lessonPane.getPrefHeight();
        double stepButton = newStep.getLayoutY() + newStep.getHeight();
        lessonPane.setPrefHeight(stepButton > prefHight ? stepButton + 30 : prefHight);
    }
    
     public void deleteLesson() {
        
        if (!lessonList.getSelectionModel().isEmpty() && !creatingLesson) {
            lesson.getLessonID();
            creatingLesson = true;
            int check = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete Lesson \"" + lesson.getTitle()+"\"?");
            creatingLesson = false;
            if(check == 0){
                 try{
                     File file = null;
                    if(lesson.getType().toLowerCase().charAt(0) == 'o')
                        file = new File(SCLTSL.lessonRootDirPath + SCLTSL.lessonOTypeDirPath + "lesson_"+lesson.getLessonID()+".xml");
                    else if(lesson.getType().toLowerCase().charAt(0) == 's')
                        file = new File(SCLTSL.lessonRootDirPath + SCLTSL.lessonSTypeDirPath + "lesson_"+lesson.getLessonID()+".xml");
                    else if(lesson.getType().toLowerCase().charAt(0) == 'e')
                        file = new File(SCLTSL.lessonRootDirPath + SCLTSL.lessonETypeDirPath + "lesson_"+lesson.getLessonID()+".xml");
                    
                    
                    if(!file.delete())
                        System.out.println("File could not be deleted.");
                    

                } catch (Exception ex) {
                    System.out.println("File was not Found");
                }
                 
                 for(Unit units: application.unitHandler.getUnitList()){
                     if(units.getLessonPlaceHolders().contains(lesson.getLessonID())){
                         
                         for(int i = 0; i < units.getLessonPlaceHolders().size(); i++){
                             units.getLessonPlaceHolders().remove(lesson.getLessonID());
                         }
                         
                         // save unit
                            try {
                                PrintWriter writer = new PrintWriter(SCLTSL.unitDirPath+"unit_"+units.getUnitID() +".xml");
                                writer.println(units.getXML(true, true));
                                writer.flush();

                                writer.close();

                            } catch (FileNotFoundException ex) {
                                System.out.println("File was not Found");
                            }
                           // refreshUnitList();
                     }
                 }
                application.unitHandler.getUnitList().get(0).getLessonPlaceHolders().contains(lesson.getLessonID());
                application.lessonHandler.getLessonList().remove(lesson);
                l_title.clear();
                l_type.clear();
                l_title.setVisible(false);
                lessonList.setVisible(true);
                refreshLessonList();
                refreshLessonTypeList();
            }
            }
        }

    @FXML
    /**
     * Handle the drag detected event
     *
     * @param event the mouse event
     */
    public void handleOnDragDetected(MouseEvent event) {
        // drag was detected, start drag-and-drop gesture
        final Object obj = event.getSource();

        if (obj instanceof ImageView) { // an image resource
            // allow the copy transfer mode 
            Clipboard db = ((ImageView) obj).startDragAndDrop(TransferMode.COPY_OR_MOVE);

            // put the image and name of image on dragboard
            ClipboardContent content = new ClipboardContent();
            content.putImage(((ImageView) obj).getImage());
            content.putString(((ImageView) obj).getId());
            db.setContent(content);
        } else if (obj instanceof ListView) { // a video resource
            // allow the copy transfer mode 
            Clipboard db = ((ListView) obj).startDragAndDrop(TransferMode.COPY_OR_MOVE);

            // put the name of video on dragboard
            ClipboardContent content = new ClipboardContent();
            content.putString(((ListView) obj).getSelectionModel().getSelectedIndex() + ":|:" + (String) ((ListView) obj).getSelectionModel().getSelectedItem());
            db.setContent(content);
        } else if (obj instanceof Text) {
            // allow the copy transfer mode 
            Clipboard db = ((Text) obj).startDragAndDrop(TransferMode.COPY_OR_MOVE);

            // put the name of video on dragboard
            ClipboardContent content = new ClipboardContent();
            content.putString((String) ((Text) obj).getText());
            db.setContent(content);
        }

        event.consume();
    }
    
    
    /**
     * Handle the onDragOver event
     *
     * @param event the drag event
     */
    public void handleOnDragOver(DragEvent event) {
        /* data is dragged over here */
        //System.out.println("drag over");

        if (event.getGestureSource() != event.getGestureTarget()
                && (event.getDragboard().hasImage() || event.getDragboard().hasString())) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    /**
     * Drag-and-drop gesture entered here
     *
     * @param event
     * @param obj
     */
    public void handleOnDragEntered(DragEvent event, Object obj) {

        // show the user that it is an actual gesture target
        if (obj != event.getGestureTarget()
                && (event.getDragboard().hasImage() || event.getDragboard().hasString())) {
            if (obj instanceof Rectangle) {
                ((Rectangle) obj).setFill(Paint.valueOf("blue"));
            } else if (obj instanceof ImageView) {
                ((ImageView) obj).setOpacity(0.5);
            } else if (obj instanceof Text) {
                ((Text) obj).setFill(Color.BLUE);
            }
        }

        event.consume();
    }

    public void handleOnDragExited(DragEvent event, Object obj) {
        /* mouse moved away, remove the graphical cues */
        if (obj instanceof Rectangle) {
            ((Rectangle) obj).setFill(Paint.valueOf("transparent"));
        } else if (obj instanceof ImageView) {
            ((ImageView) obj).setOpacity(1);
        } else if (obj instanceof Text) {
            ((Text) obj).setFill(Color.BLACK);
        }

        event.consume();
    }

    /**
     * Drag dropped
     *
     * @param event the drag event
     */
    public void handleOnDragDropped(DragEvent event) {

        // if there is an image or string data on dragboard, read it and use it */
        Dragboard db = event.getDragboard();

        boolean success = false;
        if (event.getGestureTarget() instanceof Rectangle) {
            if (db.hasImage() && db.hasString()) { // image dropped

                // get the step number
                int index;
                if (event.getGestureTarget().equals(ldDropRegion)) {
                    index = 0;
                } else if (event.getGestureTarget().equals(tdDropRegion)) {
                    index = 1;
                } else if (event.getGestureTarget().equals(s1DropRegion)) {
                    index = 2;
                } else {
                    index = Integer.parseInt(((Rectangle) event.getGestureTarget()).getId());
                }

                // new picture resource dropped
                final ImageView s = new ImageView(db.getImage());
                s.setFitHeight(resourceHeight);
                s.setFitWidth(resourceWidth);
                s.setLayoutX(((Rectangle) event.getGestureTarget()).getLayoutX());
                s.setLayoutY(((Rectangle) event.getGestureTarget()).getLayoutY());
                //s.setId(String.valueOf(index));

                s.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        handleOnDragDetected(t);
                    }
                });
                s.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent t) {
                        handleOnDragOver(t);
                    }
                });
                s.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent t) {
                        handleOnDragEntered(t, s);
                    }
                });
                s.setOnDragExited(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent t) {
                        handleOnDragExited(t, s);
                    }
                });
                s.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent t) {
                        handleOnDragDropped(t);
                    }
                });

                s.setId(index + "_" + (lesson.getlastResourceIndex(index) + 1));
                lessonPane.getChildren().add((ImageView) s);
                lesson.addResource(index, new Resource(getResourceName(pictureNames.get(Integer.parseInt(db.getString()))),
                        pictureNames.get(Integer.parseInt(db.getString())), Resource.ResourceType.IMAGE));

                success = true;
            } else if (db.hasString() && !db.hasImage()) {

                // get the step number
                int index;
                if (event.getGestureTarget().equals(ldDropRegion)) {
                    index = 0;
                } else if (event.getGestureTarget().equals(tdDropRegion)) {
                    index = 1;
                } else if (event.getGestureTarget().equals(s1DropRegion)) {
                    index = 2;
                } else {
                    index = Integer.parseInt(((Rectangle) event.getGestureTarget()).getId());
                }

                // new video resource dropped
                //Video vid = new Video(10, index, db.getString(), null, null);
                final Text s = new Text(db.getString().substring(db.getString().indexOf(":|:") + 3));
                s.setWrappingWidth(resourceWidth);
                s.setLayoutX(((Rectangle) event.getGestureTarget()).getLayoutX());
                s.setLayoutY(((Rectangle) event.getGestureTarget()).getLayoutY());


                s.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        handleOnDragDetected(t);
                    }
                });
                s.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent t) {
                        handleOnDragOver(t);
                    }
                });
                s.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent t) {
                        handleOnDragEntered(t, s);
                    }
                });
                s.setOnDragExited(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent t) {
                        handleOnDragExited(t, s);
                    }
                });
                s.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent t) {
                        handleOnDragDropped(t);
                    }
                });
                System.out.println(index);
                //vid.setGuiResource(s);
                s.setId(index + "_" + (lesson.getlastResourceIndex(index) + 1));
                lessonPane.getChildren().add((Text) s);
                lesson.addResource(index, new Resource(db.getString().substring(db.getString().indexOf(":|:") + 3),
                        videoNames.get(Integer.parseInt(db.getString().substring(0, db.getString().indexOf(":|:")))),
                        Resource.ResourceType.VIDEO));

                success = true;
            }
            nextResource(event);
        } else if (event.getGestureTarget() instanceof ImageView) {
            if (db.hasImage() && db.hasString()) {
                //Resource source = lesson.getResource(((ImageView) event.getGestureSource()).getId().substring(0, vidCount));
                /*if (source == null){ // a new resource being dropped
                    
                 }
                 else { // existing resource --> swap the resources
                    
                 }*/
                success = true;
            } else if (db.hasString() && !db.hasImage()) {
                //Resource source = lesson.find(event.getGestureSource());
                /*if (source == null){ // a new resource being dropped
                    
                 }
                 else { // existing resource --> swap the resources
                    
                 }*/
                success = false;
            }
        } else if (event.getGestureTarget() instanceof Text) {
        }

        // let the source know whether the string was successfully
        // transferred and used
        event.setDropCompleted(success);

        event.consume();
    }


    @FXML
    /**
     * Handle the onDragDone event
     *
     * @param event the drag event
     */
    public void handleOnDragDone(DragEvent event) {
        /* the drag-and-drop gesture ended */

        // if the data was copied successfully, finalize
        event.consume();
    }

    @FXML
    /**
     * Move widgets to make space for the next resource
     */
    private void nextResource(DragEvent event) {

        // move drop region to the right
        Rectangle dropRegion = (Rectangle) event.getSource();
        dropRegion.setLayoutX(dropRegion.getLayoutX() + resourceXDistance);

        // update content panel size and scrollbar
        double prefWidth = lessonPane.getPrefWidth();
        double region = dropRegion.getLayoutX() + dropRegion.getWidth();
        lessonPane.setPrefWidth(region > prefWidth ? region + 30 : prefWidth);

    }
    
        /**
     * Move widgets to make space for the next resource
     */
    private void nextResource(Rectangle dropRegion) {

        // move drop region to the right
        //Rectangle dropRegion = (Rectangle) event.getSource();
        dropRegion.setLayoutX(dropRegion.getLayoutX() + resourceXDistance);

        // update content panel size and scrollbar
        double prefWidth = lessonPane.getPrefWidth();
        double region = dropRegion.getLayoutX() + dropRegion.getWidth();
        lessonPane.setPrefWidth(region > prefWidth ? region + 30 : prefWidth);
    }

    /**
     * Get a list of resources from the system local directory
     */
    public void getListOfResources() {
        // get all files in the video textfile
        Scanner input;
        videoNames = new ArrayList<>();
        pictureNames = new ArrayList<>();
        try {

            // get list of video resources
            if ((new File(SCLTSL.videoDirPath + "videolist.txt")).exists()) {
                input = new Scanner(new FileInputStream(SCLTSL.videoDirPath + "videolist.txt"));
                while (input.hasNextLine()) {
                    String s = input.nextLine();
                    if((new File(s)).exists())
                        videoNames.add(s);
                }
                input.close();
            }

            // get list of image resources
            if ((new File(SCLTSL.imageDirPath + "imagelist.txt")).exists()) {
                input = new Scanner(new FileInputStream(SCLTSL.imageDirPath + "imagelist.txt"));
                while (input.hasNextLine()) {
                    String s = input.nextLine();
                    if((new File(s)).exists())
                        pictureNames.add(s);
                }
                input.close();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading resources");
        }

    }

    /**
     * Get the name of the resource from the absolute path
     *
     * @param absoluteResourcePathName the absolute path of the resource file
     * @return the name of the resource
     */
    private String getResourceName(String absoluteResourcePathName) {

        String name = absoluteResourcePathName.substring(absoluteResourcePathName.lastIndexOf("\\") + 1, absoluteResourcePathName.indexOf("."));

        return name;

    }
    
    
    public void refreshResourceList() {

        if (videoNames.size() > vidCount) {
            ObservableList<String> videos = FXCollections.observableArrayList();
            for (int i = 0; i < videoNames.size(); i++) {
                videos.add(getResourceName(videoNames.get(i)));
            }
            videoList.setItems(videos);
            vidCount = videoNames.size();
        }

        if (pictureNames.size() > picCount) {
            picCount = pictureNames.size();
            for (int i = 1; i <= picCount; i++) {

                final ImageView newImage = new ImageView(new File(pictureNames.get(i - 1)).toURI().toString());
                newImage.setId(String.valueOf(i - 1));

                newImage.setFitHeight(90);
                newImage.setFitWidth(90);
                if (i % 2 == 0) {
                    newImage.setLayoutX(117);
                    newImage.setLayoutY(7 + 100 * (((int) i / 2) - 1));
                } else {
                    newImage.setLayoutX(7);
                    newImage.setLayoutY(7 + 100 * (((int) (i + 1) / 2) - 1));
                }

                // add OnDragDetected event handlers
                newImage.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        handleOnDragDetected(event);
                    }
                });

                // add OnDragDone event handler
                newImage.setOnDragDone(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        handleOnDragDone(event);
                    }
                });

                picturePane.getChildren().add(newImage);

                // update horizontal scroll
                if (newImage.getLayoutY() + 90 > picturePane.getPrefHeight()) {
                    picturePane.setPrefHeight(newImage.getLayoutY() + 100);
                }
            }
        }
    }


    /**
     * create a new lesson
     */
    public void newLesson(ActionEvent event) {

        status.setText("NEW LESSON");

        // create empty lesson
        lesson = new Lesson();

        //id some how buggy
        int id = Integer.parseInt(application.lessonHandler.getBiggestLessonID()) + 1;
        String lessonID = "" + id;
        System.out.println(lessonID);
        if (id < 10) {
            lessonID = "0" + id;
        }
        lesson.setLessonID(lessonID);

        creatingLesson = true;
        lessonList.setVisible(false);
        l_title.setVisible(true);

        refreshLessonList();

        // update lesson
        status.setText("READY");
    }

    
    /**
     * Save lesson as a *.xml file
     */
    public void saveLesson() {
        int index = currentLessonIndex;
        try {
            if (!(l_title.getText().isEmpty()) && !(lessonTypeList.getSelectionModel().isEmpty()) && !isDuplicate(l_title.getText(), true)) {
                lesson.setTitle(l_title.getText());
                lesson.setType(lessonTypeList.getSelectionModel().getSelectedItem().toString());
                application.lessonHandler.getLessonList().add(lesson);
                
                PrintWriter writer = new PrintWriter(SCLTSL.lessonRootDirPath + lessonTypeList.getSelectionModel().getSelectedItem().toString() + "/lesson_" + lesson.getLessonID() + ".xml");
                System.out.println(l_title.getText() + ", " + lessonTypeList.getSelectionModel().getSelectedItem().toString());
                
                index = application.lessonHandler.getLessonList().indexOf(application.lessonHandler.getLesson(lesson.getTitle())) + 1;
                
                writer.println(lesson.getLocalXML(true));
                writer.flush();

                writer.close();
                JOptionPane.showMessageDialog(null, "Lesson \"" + lesson.getTitle() + "\" Successfully Saved.");
                //refreshLessonList();
                //refreshLessonTypeList();
            }else if(!(l_title.getText().isEmpty()) && !(lessonTypeList.getSelectionModel().isEmpty())){
//                else if(!(l_title.getText().isEmpty()) && !(lessonTypeList.getSelectionModel().isEmpty()) && !isDuplicate(l_title.getText(),false)){
              // update the lesson based on w
//                String lesson_name l_title.g;
               lesson = application.lessonHandler.getLessonList().get(index);
               lesson.setTitle(l_title.getText());
               lesson.setType(lessonTypeList.getSelectionModel().getSelectedItem().toString());
               application.lessonHandler.getLessonList().add(lesson);
//               if((lesson.getTitle().equalsIgnoreCase(l_title.getText())) && (lesson.getType().equalsIgnoreCase(lessonTypeList.getSelectionModel().getSelectedItem().toString()))){
                   
                PrintWriter writer = new PrintWriter(SCLTSL.lessonRootDirPath + lessonTypeList.getSelectionModel().getSelectedItem().toString() + "/lesson_" + lesson.getLessonID() + ".xml");
                System.out.println(l_title.getText() + ", " + lessonTypeList.getSelectionModel().getSelectedItem().toString());
                
//                index = application.lessonHandler.getLessonList().indexOf(application.lessonHandler.getLesson(lesson.getTitle())) + 1;
                
                writer.println(lesson.getLocalXML(true));
                writer.flush();

                writer.close();
                JOptionPane.showMessageDialog(null, "Lesson \"" + lesson.getTitle() + "\" Successfully Saved.");
//               }
//                lesson_name = application.lessonHandler.getLesson(l_title.getText()).toString();
                
            }
            else { // lesson title and lesson type not filled in
                //showMessagePopup("Incomplete Information", "Please make sure the lesson title and type are filled in", false, null);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        l_title.clear();
        
        lessonTypeList.setVisible(true);
        lessonList.setVisible(true);
        l_title.setVisible(false);
        
        refreshLessonList();
        refreshLessonTypeList();
       
        lessonList.getSelectionModel().select(index);
        

    }
    
    public boolean isDuplicate(String title, boolean lesson) {
        boolean isDublicate = false;

        if (lesson && !title.isEmpty()) {
            if (application.lessonHandler.getListOfLessonNames().contains(title)) {
                isDublicate = true;
            }
        } else if(!title.isEmpty()){
            if (application.unitHandler.getListOfUnitNames().contains(title)) {
                isDublicate = true;
            }
        }

        return isDublicate;
    }
    
    public void saveUnit() {
        int index = currentLessonIndex;
        if (!l_title.getText().isEmpty()) {
            if (!isDuplicate(l_title.getText(), false)) {
                
                // save unit to course
                creatingLesson = true;
                try {
                    lesson.setTitle(l_title.getText());
                    lesson.setType(lessonTypeList.getSelectionModel().getSelectedItem().toString());
                    application.lessonHandler.getLessonList().add(lesson);
                    
                    PrintWriter writer = new PrintWriter(SCLTSL.lessonRootDirPath + lessonTypeList.getSelectionModel().getSelectedItem().toString() + "/lesson_" + lesson.getLessonID() + ".xml");
                    writer.println(lesson.getLocalXML(true));
                    
                    index = application.lessonHandler.getLessonList().indexOf(application.lessonHandler.getLesson(lesson.getTitle())) + 1;
                    
                    writer.flush();
                    
                    writer.close();
                    
                    JOptionPane.showMessageDialog(null, "Unit \"" + lesson.getTitle() + "\" Successfully Saved.");
                } catch (FileNotFoundException ex) {
                    System.out.println("File was not Found");
                }


                
                creatingLesson = false;
                l_title.clear();
                l_title.setVisible(false);
                lessonList.setVisible(true);
                refreshLessonList();
                
                lessonList.getSelectionModel().select(index);

          }
        }
        
        else if (!lessonList.getSelectionModel().isSelected(0) && !creatingLesson) {
            
                // save unit to course
                creatingLesson = true;
                try {
                    
                    PrintWriter writer = new PrintWriter(SCLTSL.lessonRootDirPath + lessonTypeList.getSelectionModel().getSelectedItem().toString() + "/lesson_" + lesson.getLessonID() + ".xml");
                    writer.println(lesson.getLocalXML(true));
                    
                    index = application.lessonHandler.getLessonList().indexOf(application.lessonHandler.getLesson(lesson.getTitle())) + 1;
                    
                    writer.flush();
                    
                    writer.close();
                    
                    JOptionPane.showMessageDialog(null, "Unit \"" + lesson.getTitle() + "\" Successfully Saved.");
                } catch (FileNotFoundException ex) {
                    System.out.println("File was not Found");
                }


                
                creatingLesson = false;
                l_title.clear();
                l_title.setVisible(false);
                lessonList.setVisible(true);
                refreshLessonList();
                
                lessonList.getSelectionModel().select(index);
                
        }
    }
    

    /**
     * Upload multiple files to the system
     *
     * @param event the event of the button
     */
    public void uploadFile(ActionEvent event) {

        // update status
        status.setText("UPLOADING...");

        // open file chhoser to get abstract files
        FileChooser fuploadDialog = new FileChooser();
        fuploadDialog.setTitle("Upload a resource");
        
        ObservableList<String> all = FXCollections.observableArrayList(imageExtensions);
        all.addAll(videoExtensions);
        fuploadDialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", all));
        
        fuploadDialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", imageExtensions));
        fuploadDialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", videoExtensions));
        
        List<File> uploadedFiles = fuploadDialog.showOpenMultipleDialog(null);

        // copy file from source to appropriate local resource directory
        try {
            if (uploadedFiles != null) {
                for (File uploadedFile : uploadedFiles) {
                    if (isPicture(uploadedFile.getName())) {
                        File imageListFile = new File(SCLTSL.imageDirPath + "imagelist.txt");
                        boolean justCreated = false;

                        if (!imageListFile.exists()) {
                            imageListFile.createNewFile();
                            justCreated = true;
                        }
                        //imageListFile.

                        try {
                            FileWriter fw = new FileWriter(imageListFile.getAbsolutePath(), true);
                            BufferedWriter bw = new BufferedWriter(fw);

                            //Files.copy(uploadedFile.toPath(), (new File(SCLTSL.imageDirPath + uploadedFile.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
                            if (!pictureNames.contains(uploadedFile.getAbsolutePath())) {

                                if (!justCreated) {
                                    bw.newLine();
                                }

                                bw.write(uploadedFile.getAbsolutePath());
                                bw.close();
                                pictureNames.add(uploadedFile.getAbsolutePath());
                            }
                        } catch (Exception ex) {
                        }
                    } else if (isVideo(uploadedFile.getName())) {
                        File videoListFile = new File(SCLTSL.videoDirPath + "videolist.txt");
                        boolean justCreated = false;

                        if (!videoListFile.exists()) {
                            videoListFile.createNewFile();
                            justCreated = true;
                        }

                        try {
                            FileWriter fw = new FileWriter(videoListFile.getAbsolutePath(), true);
                            BufferedWriter bw = new BufferedWriter(fw);

                            //Files.copy(uploadedFile.toPath(), (new File(SCLTSL.videoDirPath + uploadedFile.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
                            if (!videoNames.contains(uploadedFile.getAbsolutePath())) {

                                if (!justCreated) {
                                    bw.newLine();
                                }

                                bw.write(uploadedFile.getAbsolutePath());
                                bw.close();
                                videoNames.add(uploadedFile.getAbsolutePath());
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
                //System.out.println("copied");
                refreshResourceList();
                //showMessagePopup("Uploading Files Complete", "The files were uploaded succesfully", false, null);

            }
        } catch (Exception e) {
            //System.out.println("error uploading files");
        }

        // update status
        status.setText("READY");
    }
    
    
    /**
     * Check if the file is a picture
     *
     * @param picName the name of the file
     * @return True if its a picture or False otherwise
     */
    private boolean isPicture(String picName) {
        String ext = "*" + picName.substring(picName.lastIndexOf("."));

        if (imageExtensions.contains(ext)) {
            return true;
        }

        return false;
    }

    /**
     * Check if the file is a video
     *
     * @param vidNamethe name of the file
     * @return True if its a video or False otherwise
     */
    private boolean isVideo(String vidName) {
        String ext = "*" + vidName.substring(vidName.lastIndexOf("."));

        if (videoExtensions.contains(ext)) {
            return true;
        }

        return false;
    }


       @FXML
    public void showMessagePopup(String title, String theMessage, boolean isDelete, File icon) {
        // Load the fxml file and create a new statge for the popup
        try {
            FXMLLoader loader = new FXMLLoader(SCLTSL.class.getResource("messagePopup.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(SCLTSL.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller
            messageController = loader.getController();
            messageController.setDialogStage(dialogStage);
            messageController.setIsDelete(isDelete);
            messageController.setMessage(theMessage);

            dialogStage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    messageController.close();
                }
            });

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            //dialogStage.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void previewLesson() {
        // Load the fxml file and create a new statge for the popup
        try {
            FXMLLoader loader = new FXMLLoader(SCLTSL.class.getResource("previewPopup.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Play Lesson");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(SCLTSL.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller
            controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setLesson(lesson);

            dialogStage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    //controller.close();
                }
            });

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
