/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scltsl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import javafx.util.Callback;

/**
 *
 * @author Masilo and Chad Petersen
 */
public class CourseController extends AnchorPane implements Initializable {

    private final int lessonXDistance = 110;
    private final int lessonWidth = 80;
    private final int lessonHeight = 80;
    private final int dropRegionLayoutX = 166;
    private final int dropRegionLayoutY = 80;
    private SCLTSL application;
    private int lessonCount;
    private int currentCourseIndex;
    private int currentUnitIndex;
    private Unit currentUnit;
    private Course currentCourse;
    private boolean creatingUnit = false;
    private boolean creatingCourse = false;
    @FXML
    public ComboBox unitList;
    
    @FXML
    public Button Cancelbutton;
    @FXML
    public ComboBox courseList;
    public ListView typeOLessonList;
    public ListView typeSLessonList;
    public ListView typeELessonList;
    public ListView courseUnitsList;
    public ListView otherUnitsList;
    public Rectangle typeODropRegion;
    public Rectangle typeSDropRegion;
    public Rectangle typeEDropRegion;
    public AnchorPane unitPane;
    public TextField courseTitle;
    public TextField unitTitle;
    public Label sLabel;
    public Label oLabel;
    public Label eLabel;
    public AnchorPane MenuPane;
    CheckBoxListCell<String> test;

    public void setApp(SCLTSL application) {

        // set main application
        this.application = application;

        // refresh list of courses, units and lessons
        refreshCourseList();
        refreshUnitList();
        refreshLessonList();
        
        courseList.getSelectionModel().select(SCLTSL.lastCourseIndex);
        unitList.getSelectionModel().select(SCLTSL.lastUnitIndex);
        Cancelbutton.setVisible(false);
        /* set drag-and-drop events */
        typeODropRegion.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragEntered(event, typeODropRegion);
            }
        });
        typeODropRegion.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragExited(event, typeODropRegion);
            }
        });
        typeSDropRegion.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragEntered(event, typeSDropRegion);
            }
        });
        typeSDropRegion.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragExited(event, typeSDropRegion);
            }
        });
        typeEDropRegion.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragEntered(event, typeEDropRegion);
            }
        });
        typeEDropRegion.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleOnDragExited(event, typeEDropRegion);
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void selectUnit(ActionEvent event) {
        
        // clear workspace
        clearWorkSpace();

        if (unitList.getSelectionModel().isSelected(0)) {
            currentUnitIndex = 0;
            Cancelbutton.setVisible(true);
            createUnit();
        } else {
            
            // load unit data
            Cancelbutton.setVisible(true);
            //Cancelbutton.setVisible(false);
            SCLTSL.lastUnitIndex = unitList.getSelectionModel().getSelectedIndex();
            try{
            currentUnit = application.unitHandler.getUnit((String) unitList.getSelectionModel().getSelectedItem());
            currentUnitIndex = unitList.getSelectionModel().getSelectedIndex();
            for (int i = 0; i < currentUnit.getLessonPlaceHolders().size(); i++) {

                String lessonName = application.lessonHandler.getLessonList().
                        get(application.lessonHandler.searchForLesson(currentUnit.getLessonPlaceHolders().get(i))).getTitle();

                final Text s = new Text(lessonName);
                s.setWrappingWidth(lessonWidth);

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

                String lessonType = application.lessonHandler.getLessonList().
                        get(application.lessonHandler.searchForLesson(currentUnit.getLessonPlaceHolders().get(i))).getType();
                if (lessonType.toLowerCase().charAt(0) == 'o') {
                    s.setLayoutX(typeODropRegion.getLayoutX());
                    s.setLayoutY(typeODropRegion.getLayoutY());
                    nextLesson(typeODropRegion);
                } else if (lessonType.toLowerCase().charAt(0) == 's') {
                    s.setLayoutX(typeSDropRegion.getLayoutX());
                    s.setLayoutY(typeSDropRegion.getLayoutY());
                    nextLesson(typeSDropRegion);
                } else if (lessonType.toLowerCase().charAt(0) == 'e') {
                    s.setLayoutX(typeEDropRegion.getLayoutX());
                    s.setLayoutY(typeEDropRegion.getLayoutY());
                    nextLesson(typeEDropRegion);
                }

                unitPane.getChildren().add((Text) s);
            }
        }catch(Exception e){}
        }
    }

    private void clearWorkSpace() {
        unitPane.getChildren().clear();
        unitPane.getChildren().add(oLabel);
        unitPane.getChildren().add(eLabel);
        unitPane.getChildren().add(sLabel);

        // reset positions
        typeEDropRegion.setLayoutX(165);
        typeODropRegion.setLayoutX(165);
        typeSDropRegion.setLayoutX(165);
        typeEDropRegion.setLayoutY(355);
        typeODropRegion.setLayoutY(39);
        typeSDropRegion.setLayoutY(197);


        unitPane.getChildren().add(typeEDropRegion);
        unitPane.getChildren().add(typeODropRegion);
        unitPane.getChildren().add(typeSDropRegion);
    }

    private class SelectableUnit {

        private SimpleBooleanProperty selected;
        private String unitID;
        private String unitName;

        public SelectableUnit(String id, String name) {
            this.selected = new SimpleBooleanProperty(false);
            this.unitID = id;
            this.unitName = name;
        }

        public boolean getSelected() {
            return this.selected.get();
        }

        public void setSelected(boolean selected) {
            this.selected = new SimpleBooleanProperty(selected);
        }

        public String getName() {
            return this.unitName;
        }

        public String getID() {
            return this.unitID;
        }

        public SimpleBooleanProperty selectedProperty() {
            return selected;
        }

        @Override
        public String toString() {
            return this.unitName;
        }
    }
    
    public void selectCourse(ActionEvent event) {
        
        // checkbox callbacks
        Callback<SelectableUnit, ObservableValue<Boolean>> getProperty = new Callback<SelectableUnit, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(SelectableUnit p) {
                return p.selectedProperty();
            }
        };
        Callback<ListView<SelectableUnit>, ListCell<SelectableUnit>> forListView = CheckBoxListCell.forListView(getProperty);
        
        if (courseList.getSelectionModel().isSelected(0)) {
            
            // clear course units list
            courseUnitsList.getItems().clear();
            
            currentCourseIndex = 0;

            // populate other units list
            ObservableList<SelectableUnit> otherUnits = FXCollections.observableArrayList();
            for (Unit unit : application.unitHandler.getUnitList()) {
                otherUnits.add(new SelectableUnit(unit.getUnitID(), unit.getTitle()));
            }
            otherUnitsList.setItems(otherUnits);
            otherUnitsList.setCellFactory(forListView);
            
            createCourse();
        } else {
            // load unit data
            SCLTSL.lastCourseIndex = courseList.getSelectionModel().getSelectedIndex();
            try{
            currentCourse = application.courseHandler.getCourse((String) courseList.getSelectionModel().getSelectedItem());
            currentCourseIndex = courseList.getSelectionModel().getSelectedIndex();
             // view the selected course units
            ObservableList<SelectableUnit> units = FXCollections.observableArrayList();
            for (String unitID : currentCourse.getUnitPlaceHolders()) {
                units.add(new SelectableUnit(unitID, application.unitHandler.searchForUnitName(unitID)));
            }
            
            courseUnitsList.setItems(units);
            
            courseUnitsList.setCellFactory(forListView);
            
            otherUnitsList.getItems().clear();
            // populate other units list
            ObservableList<SelectableUnit> otherUnits = FXCollections.observableArrayList();
            for (Unit unit : application.unitHandler.getUnitList()) {
                if(!currentCourse.getUnitPlaceHolders().contains(unit.getUnitID()))
                    otherUnits.add(new SelectableUnit(unit.getUnitID(), unit.getTitle()));
               
            }
            otherUnitsList.setItems(otherUnits);
            otherUnitsList.setCellFactory(forListView);
            }catch(Exception e){}
        }
    }

    private void createUnit() {
        currentUnit = new Unit();

        // set id
        int id;
        try{
            id = Integer.parseInt(application.unitHandler.getUnitList().get(application.unitHandler.getUnitList().size() - 1).getUnitID()) + 1;
        }catch(ArrayIndexOutOfBoundsException e){
            id = 1;
        }
        
        String unitID = "" + id;
        if (id < 10) {
            unitID = "0" + id;
        }
        currentUnit.setUnitID(unitID);

        creatingUnit = true;
        unitList.setVisible(false);
        unitTitle.setVisible(true);
        Cancelbutton.setVisible(true);
        refreshUnitList();
        
    }

    private void createCourse() {
        currentCourse = new Course();
  
        // set id
        int id;
        try{
                id = Integer.parseInt(application.courseHandler.getCourseList().get(application.courseHandler.getCourseList().size() - 1).getId()) + 1;
        }catch(Exception e){
            id = 1;
        }
        String courseID = "" + id;
        if (id < 10) {
            courseID = "0" + id;
        }
        currentCourse.setId(courseID);
        
        creatingCourse = true;
        courseList.setVisible(false);
        courseTitle.setVisible(true);
        currentCourse.setTitle(courseTitle.getText());
        
        refreshCourseList();
  
    }
    
    public boolean editCourse(){
        // checkbox callbacks
        boolean change = false;
        if(currentCourse == null){
            currentCourse = new Course();
            int id = Integer.parseInt(application.courseHandler.getCourseList().get(application.courseHandler.getCourseList().size() - 1).getId()) + 1;
            String courseID = "" + id;
            if (id < 10) {
                courseID = "0" + id;
        }
        currentCourse.setId(courseID);
        }
            
        Callback<SelectableUnit, ObservableValue<Boolean>> getProperty = new Callback<SelectableUnit, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(SelectableUnit p) {
                return p.selectedProperty();
            }
        };
        Callback<ListView<SelectableUnit>, ListCell<SelectableUnit>> forListView = CheckBoxListCell.forListView(getProperty);
        
       for(Object unit: otherUnitsList.getItems()){
            if(((SelectableUnit) unit).getSelected()){
                currentCourse.addUnitPlaceHolder(((SelectableUnit) unit).getID());
                change = true;
            }
        }
       
        for(Object unit: courseUnitsList.getItems()){
            if(((SelectableUnit) unit).getSelected()){
                currentCourse.getUnitPlaceHolders().remove(((SelectableUnit) unit).getID());
                change = true;
            }
        }
        
        // view the selected course units
        ObservableList<SelectableUnit> units = FXCollections.observableArrayList();
        for (String unitID : currentCourse.getUnitPlaceHolders()) {
            units.add(new SelectableUnit(unitID, application.unitHandler.searchForUnitName(unitID)));
        }

        courseUnitsList.setItems(units);

        courseUnitsList.setCellFactory(forListView);

        otherUnitsList.getItems().clear();
        // populate other units list
        ObservableList<SelectableUnit> otherUnits = FXCollections.observableArrayList();
        for (Unit unit : application.unitHandler.getUnitList()) {
            if(!currentCourse.getUnitPlaceHolders().contains(unit.getUnitID()))
                otherUnits.add(new SelectableUnit(unit.getUnitID(), unit.getTitle()));

        }
        otherUnitsList.setItems(otherUnits);
        otherUnitsList.setCellFactory(forListView);
        
        
        return change;
    }
    
   public void setCourse(Course theCourse) {
        if (theCourse == null) {
            currentCourse = new Course();
        } else {
            currentCourse = theCourse;
        }
    }
    public void saveCourse() { 
        boolean edit = editCourse();
        int index = currentCourseIndex;
        if ((!courseTitle.getText().isEmpty() && !isDuplicate(courseTitle.getText(), true)) || (edit)) {
                // save course
                creatingCourse = true;
                try {
                    if(!courseTitle.getText().isEmpty())
                        currentCourse.setTitle(courseTitle.getText());
                    if(!isDuplicate(currentCourse.getTitle(), true))
                        application.courseHandler.getCourseList().add(currentCourse);
                    
                    PrintWriter writer = new PrintWriter(SCLTSL.courseDirPath+"course_"+currentCourse.getId()+".xml");
                    writer.println(currentCourse.getXML(true));
                    writer.flush();
                    index = application.courseHandler.getCourseList().indexOf(application.courseHandler.getCourse(currentCourse.getTitle())) + 1;
                    writer.close();
                    
                    JOptionPane.showMessageDialog(null, "Course \"" + currentCourse.getTitle() + "\" Successfully Saved.");
                } catch (FileNotFoundException ex) {
                    System.out.println("File was not Found");
                }
                creatingCourse = false;
                courseTitle.clear();
                courseTitle.setVisible(false);
                courseList.setVisible(true);
                refreshCourseList();
                courseList.getSelectionModel().select(index);
            }
    }

    public void saveUnit() {
        int index = currentUnitIndex;
        if (!unitTitle.getText().isEmpty()) {
            if (!isDuplicate(unitTitle.getText(), false)) {
                
                // save unit to course
                creatingUnit = true;
                try {
                    currentUnit.setTitle(unitTitle.getText());
                    application.unitHandler.getUnitList().add(currentUnit);
                    
                    PrintWriter writer = new PrintWriter(SCLTSL.unitDirPath+"unit_"+currentUnit.getUnitID()+".xml");
                    writer.println(currentUnit.getXML(true,true));
                    
                    index = application.unitHandler.getUnitList().indexOf(application.unitHandler.getUnit(currentUnit.getTitle())) + 1;
                    
                    writer.flush();
                    
                    writer.close();
                    
                    JOptionPane.showMessageDialog(null, "Unit \"" + currentUnit.getTitle() + "\" Successfully Saved.");
                } catch (FileNotFoundException ex) {
                    System.out.println("File was not Found");
                }


                
                creatingUnit = false;
                unitTitle.clear();
                unitTitle.setVisible(false);
                unitList.setVisible(true);
                refreshUnitList();
                
                unitList.getSelectionModel().select(index);
                
                try{
                    Callback<SelectableUnit, ObservableValue<Boolean>> getProperty = new Callback<SelectableUnit, ObservableValue<Boolean>>() {
                        @Override
                        public ObservableValue<Boolean> call(SelectableUnit p) {
                            return p.selectedProperty();
                        }
                    };
                    Callback<ListView<SelectableUnit>, ListCell<SelectableUnit>> forListView = CheckBoxListCell.forListView(getProperty);
                            // populate other units list
                    ObservableList<SelectableUnit> otherUnits = FXCollections.observableArrayList();
                    for (Unit unit : application.unitHandler.getUnitList()) {
                        if(!currentCourse.getUnitPlaceHolders().contains(unit.getUnitID()))
                            otherUnits.add(new SelectableUnit(unit.getUnitID(), unit.getTitle()));

                    }
                    otherUnitsList.setItems(otherUnits);
                    otherUnitsList.setCellFactory(forListView);
                }catch(Exception e){}
          }
        }
        
        else if (!unitList.getSelectionModel().isSelected(0) && !creatingUnit) {
            
                // save unit to course
                creatingUnit = true;
                try {
                    
                    PrintWriter writer = new PrintWriter(SCLTSL.unitDirPath+"unit_"+currentUnit.getUnitID()+".xml");
                    writer.println(currentUnit.getXML(true,true));
                    
                    index = application.unitHandler.getUnitList().indexOf(application.unitHandler.getUnit(currentUnit.getTitle())) + 1;
                    
                    writer.flush();
                    
                    writer.close();
                    
                    JOptionPane.showMessageDialog(null, "Unit \"" + currentUnit.getTitle() + "\" Successfully Saved.");
                } catch (FileNotFoundException ex) {
                    System.out.println("File was not Found");
                }


                
                creatingUnit = false;
                unitTitle.clear();
                unitTitle.setVisible(false);
                unitList.setVisible(true);
                refreshUnitList();
                
                unitList.getSelectionModel().select(index);
                
                try{
                    Callback<SelectableUnit, ObservableValue<Boolean>> getProperty = new Callback<SelectableUnit, ObservableValue<Boolean>>() {
                        @Override
                        public ObservableValue<Boolean> call(SelectableUnit p) {
                            return p.selectedProperty();
                        }
                    };
                    Callback<ListView<SelectableUnit>, ListCell<SelectableUnit>> forListView = CheckBoxListCell.forListView(getProperty);
                            // populate other units list
                    ObservableList<SelectableUnit> otherUnits = FXCollections.observableArrayList();
                    for (Unit unit : application.unitHandler.getUnitList()) {
                        if(!currentCourse.getUnitPlaceHolders().contains(unit.getUnitID()))
                            otherUnits.add(new SelectableUnit(unit.getUnitID(), unit.getTitle()));

                    }
                    otherUnitsList.setItems(otherUnits);
                    otherUnitsList.setCellFactory(forListView);
                }catch(Exception e){}
          
        }
    }
    
    public void cancelUnitCreation(){
        unitTitle.clear();
        unitTitle.setVisible(false);
        unitList.setVisible(true);
        //Cancelbutton.setVisible(false);
        refreshUnitList();
    }
//    Delete a unit from the current course
    public void deleteUnit() {
        int index = currentCourseIndex;
        System.out.println(index);
        if (!unitList.getSelectionModel().isEmpty() && !creatingUnit) {
            currentUnit.getUnitID();
            creatingUnit = true;
            int check = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete Unit \"" + currentUnit.getTitle()+"\"?");
            creatingUnit = false;
            if(check == 0){
                 try{
                    File file = new File(SCLTSL.unitDirPath+"unit_"+currentUnit.getUnitID()+".xml");
                    
                    if(!file.delete())
                        System.out.println("File could not be deleted.");
                    

                } catch (Exception ex) {
                    System.out.println("File was not Found");
                }
                 
                 for(Course courses: application.courseHandler.getCourseList()){
                     if(courses.getUnitPlaceHolders().contains(currentUnit.getUnitID())){
                         
                         for(int i = 0; i < courses.getUnitPlaceHolders().size(); i++){
                             courses.getUnitPlaceHolders().remove(currentUnit.getUnitID());
                         }
                         
                         // save course
                            try {
                                PrintWriter writer = new PrintWriter(SCLTSL.courseDirPath+"course_"+courses.getId()+".xml");
                                writer.println(courses.getXML(true));
                                writer.flush();

                                writer.close();

                            } catch (FileNotFoundException ex) {
                                System.out.println("File was not Found");
                            }
                            refreshCourseList();
                     }
                 }
                application.courseHandler.getCourseList().get(0).getUnitPlaceHolders().contains(currentUnit.getUnitID());
                application.unitHandler.getUnitList().remove(currentUnit);
                unitTitle.clear();
                unitTitle.setVisible(false);
                unitList.setVisible(true);
                refreshUnitList();
                courseList.getSelectionModel().select(index);
            }
            }
        }
    

    public boolean isDuplicate(String title, boolean course) {
        boolean isDublicate = false;

        if (course) {
            if (application.courseHandler.getListOfCourseNames().contains(title)) {
                isDublicate = true;
            }
        } else {
            if (application.unitHandler.getListOfUnitNames().contains(title)) {
                isDublicate = true;
            }
        }

        return isDublicate;
    }
    
     public void exportCourse() {
         
          if (!courseTitle.getText().isEmpty()) {
            if (!isDuplicate(courseTitle.getText(), true)) {
                
                // save course
                try {
                    currentCourse.setTitle(courseTitle.getText());
                    application.courseHandler.getCourseList().add(currentCourse);
                    
                    PrintWriter writer = new PrintWriter(SCLTSL.courseDirPath+"course_"+currentCourse.getId()+".xml");
                    writer.println(currentCourse.getXML(true));
                    writer.flush();

                    writer.close();
                    
                } catch (FileNotFoundException ex) {
                    System.out.println("File was not Found");
                }
                creatingCourse = false;
                courseTitle.clear();
                courseTitle.setVisible(false);
                courseList.setVisible(true);
                refreshCourseList();
            }
        }
        try {

            PrintWriter writer = new PrintWriter("src/XMLFiles/Export/"+currentCourse.getTitle()+".xml");
            
            writer.println(currentCourse.exportCourse(false));
            writer.flush();

            writer.close();
            
            PrintWriter IRXMLwriter = new PrintWriter("src/XMLFiles/Export/"+currentCourse.getTitle()+".irxml");
            
            IRXMLwriter.println(currentCourse.exportCourse(true));
            IRXMLwriter.flush();

            IRXMLwriter.close();
            
            JOptionPane.showMessageDialog(null, "Course \"" + currentCourse.getTitle() + "\" Successfully Exported.");
        } catch (FileNotFoundException ex) {
            System.out.println("File was not Found");
        }
            
    }
    
    @FXML
    public void createLesson(){
        openLesson("");
    }
    
    @FXML
    public void openLesson(String lessonID) {
        if (lessonID.isEmpty()) {
            application.createLesson(null);
        } else {
            Lesson lesson = application.lessonHandler.getLessonList().get(application.lessonHandler.searchForLesson(lessonID));
            application.createLesson(lesson);
        }
    }

    /**
     * Refresh the list of lessons on the gui
     */
    private void refreshLessonList() {

        if (application.lessonHandler.getLessonList().size() > lessonCount) {
            lessonCount = application.lessonHandler.getLessonList().size();
            ObservableList<String> typeOLessons = FXCollections.observableArrayList(application.lessonHandler.getListOfTypeOLessonNames());
            ObservableList<String> typeSLessons = FXCollections.observableArrayList(application.lessonHandler.getListOfTypeSLessonNames());
            ObservableList<String> typeELessons = FXCollections.observableArrayList(application.lessonHandler.getListOfTypeELessonNames());
            typeOLessonList.setItems(typeOLessons);
            typeSLessonList.setItems(typeSLessons);
            typeELessonList.setItems(typeELessons);
        }
    }

    private void refreshUnitList() {
        ObservableList<String> units = FXCollections.observableArrayList(application.unitHandler.getListOfUnitNames());
        units.add(0, "New Unit");
        //unitList.setItems(units);
        //Added
        unitList.getItems().clear();
        unitList.setItems(units);
    }

    private void refreshCourseList() {
        ObservableList<String> courses = FXCollections.observableArrayList(application.courseHandler.getListOfCourseNames());
        courses.add(0, "New Course");
        //courseList.setItems(courses);
        //Added
        courseList.getItems().clear();
        courseList.setItems(courses);
    }

    /**
     * Move widgets to make space for the next lesson
     */
    private void nextLesson(DragEvent event) {

        // move drop region to the right
        Rectangle dropRegion = (Rectangle) event.getSource();
        dropRegion.setLayoutX(dropRegion.getLayoutX() + lessonXDistance);

        // update content panel size and scrollbar
        double prefWidth = unitPane.getPrefWidth();
        double region = dropRegion.getLayoutX() + dropRegion.getWidth();
        unitPane.setPrefWidth(region > prefWidth ? region + 30 : prefWidth);

    }

    /**
     * Move widgets to make space for the next lesson
     */
    private void nextLesson(Rectangle dropRegion) {

        // move drop region to the right
        //Rectangle dropRegion = (Rectangle) event.getSource();
        dropRegion.setLayoutX(dropRegion.getLayoutX() + lessonXDistance);

        // update content panel size and scrollbar
        double prefWidth = unitPane.getPrefWidth();
        double region = dropRegion.getLayoutX() + dropRegion.getWidth();
        unitPane.setPrefWidth(region > prefWidth ? region + 30 : prefWidth);

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

        if (obj instanceof ListView && ((ListView) obj).getItems().size() != 0) { // a lesson
            // allow the copy transfer mode 
            Clipboard db = ((ListView) obj).startDragAndDrop(TransferMode.COPY_OR_MOVE);

            // put the name of video on dragboard
            ClipboardContent content = new ClipboardContent();
            try {
                content.putString((String) ((ListView) obj).getSelectionModel().getSelectedItem());
                db.setContent(content);
            } catch (Exception ex) {
            }
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

        boolean drop = true;


        // get the lesson type dropped
        /*if (event.getGestureSource() == typeOLessonList && event.getGestureTarget() == typeODropRegion) {
         drop = true;
         } else if (event.getGestureSource() == typeSLessonList && event.getGestureTarget() == typeSDropRegion) {
         drop = true;
         } else if (event.getGestureSource() == typeELessonList && event.getGestureTarget() == typeEDropRegion) {
         drop = true;
         }*/

        if (drop) {

            if (event.getGestureSource() != event.getGestureTarget() && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
        }

        event.consume();
    }

    /**
     * Drag-and-drop gesture entered here
     *
     * @param event the drag event
     * @param obj the target object
     */
    public void handleOnDragEntered(DragEvent event, Object obj) {

        // show the user that it is an actual gesture target
        boolean drop = false;


        // get the lesson type dropped
        if (event.getGestureSource() == typeOLessonList && obj == typeODropRegion) {
            drop = true;
        } else if (event.getGestureSource() == typeSLessonList && obj == typeSDropRegion) {
            drop = true;
        } else if (event.getGestureSource() == typeELessonList && obj == typeEDropRegion) {
            drop = true;
        }

        if (drop) {

            if (obj != event.getGestureTarget()
                    && event.getDragboard().hasString()) {
                if (obj instanceof Rectangle) {
                    ((Rectangle) obj).setFill(Paint.valueOf("blue"));
                } else if (obj instanceof Text) {
                    ((Text) obj).setFill(Color.BLUE);
                }
            }
        }

        event.consume();
    }

    /**
     * Drag-and-drop gesture exited from here
     *
     * @param event the drag event
     * @param obj the target object
     */
    public void handleOnDragExited(DragEvent event, Object obj) {
        /* mouse moved away, remove the graphical cues */
        boolean drop = false;


        // get the lesson type dropped
        if (event.getGestureSource() == typeOLessonList && obj == typeODropRegion) {
            drop = true;
        } else if (event.getGestureSource() == typeSLessonList && obj == typeSDropRegion) {
            drop = true;
        } else if (event.getGestureSource() == typeELessonList && obj == typeEDropRegion) {
            drop = true;
        }

        if (drop) {

            if (obj instanceof Rectangle) {
                ((Rectangle) obj).setFill(Paint.valueOf("transparent"));
            } else if (obj instanceof Text) {
                ((Text) obj).setFill(Color.BLACK);
            }

        }

        event.consume();
    }

    /**
     * Drag dropped
     *
     * @param event the drag event
     */
    public void handleOnDragDropped(DragEvent event) {

        // if there is string data on dragboard, read it and use it */
        Dragboard db = event.getDragboard();
        boolean drop = true;


        // get the lesson type dropped
        char lessonType = 'x';
        if (event.getGestureSource() == typeOLessonList && event.getGestureTarget() == typeODropRegion) {
         lessonType = 'o';
         drop = true;
         } else if (event.getGestureSource() == typeSLessonList && event.getGestureTarget() == typeSDropRegion) {
         lessonType = 's';
         drop = true;
         } else if (event.getGestureSource() == typeELessonList && event.getGestureTarget() == typeEDropRegion) {
         lessonType = 'e';
         drop = true;
         }

        if (drop) {

            boolean success = false;
            if (event.getGestureTarget() instanceof Rectangle) {
                if (db.hasString()) { // // a lesson dropped

                    String lessonName = db.getString();
                    final Text s = new Text(lessonName);
                    s.setWrappingWidth(lessonWidth);
                    s.setLayoutX(((Rectangle) event.getGestureTarget()).getLayoutX());
                    s.setLayoutY(((Rectangle) event.getGestureTarget()).getLayoutY());

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


                    unitPane.getChildren().add((Text) s);
                    
                    // update unit object
                    String lessonID = application.lessonHandler.getLessonID(lessonName, lessonType);
                    try{
                        currentUnit.addLessonPlaceHolder(lessonID);
                    }catch(Exception e){
                        currentUnit = new Unit();
                        int id = Integer.parseInt(application.unitHandler.getUnitList().get(application.unitHandler.getUnitList().size() - 1).getUnitID()) + 1;
                        String unitID = "" + id;
                        if (id < 10) {
                            unitID = "0" + id;
                        }
                        currentUnit.setUnitID(unitID);
                        currentUnit.addLessonPlaceHolder(lessonID);
                        //Text box should open to allow to type a name
                    }
                    
                    //application.unitHandler.getUnitList().get(currentUnitIndex).addLessonPlaceHolder(lessonID);

                    success = true;
                }
                nextLesson(event);
            }

            // let the source know whether the string was successfully
            // transferred and used
            event.setDropCompleted(success);
        }

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
}
