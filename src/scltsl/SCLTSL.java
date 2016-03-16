package scltsl;

/**
 *
 * @author Muchenja Namumba, Masilo Mapaila and Chad Petersen
 */
import java.io.*;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;

import XMLImport.*;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class SCLTSL extends Application {

    /*****Global Variables********/
    static String videoDirPath = "src/videos/";
    static String imageDirPath = "src/images/";
    static String courseDirPath = "src/XMLFiles/Courses/";
    static String unitDirPath = "src/XMLFiles/Units/";
    static String lessonRootDirPath = "src/XMLFiles/Lessons/";
    static String lessonOTypeDirPath = "o/";
    static String lessonSTypeDirPath = "s/";
    static String lessonETypeDirPath = "e/";
    static int lastCourseIndex = -1;
    static int lastUnitIndex = -1;
    static int lastLessonIndex = -1;
    
    
    
    SAXParserFactory saxParserFactory;
    SAXParser saxParser;
    XMLImportCourse courseHandler;
    XMLImportUnit unitHandler;
    XMLImportLesson lessonHandler;
    
    
    // local variables
    private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 1125.0;
    private final double MINIMUM_WINDOW_HEIGHT = 690.0;

    @Override
    public void start(Stage primaryStage) {

        /* create the local system directories*/
        File videoDir = new File(videoDirPath);
        File imageDir = new File(imageDirPath);
        File courseDir = new File(courseDirPath);
        File unitDir = new File(unitDirPath);
        File lessonRootDir = new File(lessonRootDirPath);
        File lessonOTypeDir = new File(lessonRootDirPath+lessonOTypeDirPath);
        File lessonSTypeDir = new File(lessonRootDirPath+lessonSTypeDirPath);
        File lessonETypeDir = new File(lessonRootDirPath+lessonETypeDirPath);

        // create video directory
        if (!(videoDir.exists())) {
            videoDir.mkdir();
        }

        // create image directory
        if (!(imageDir.exists())) {
            imageDir.mkdir();
        }

        // create course directory
        if (!(courseDir.exists())) {
            courseDir.mkdir();
        }

        // create unit directory
        if (!(unitDir.exists())) {
            unitDir.mkdir();
        }

        // create lesson root directory
        if (!(lessonRootDir.exists())) {
            lessonRootDir.mkdir();
        }

        // create lesson O type directory
        if (!(lessonOTypeDir.exists())) {
            lessonOTypeDir.mkdir();
        }

        // create lesson S type directory
        if (!(lessonSTypeDir.exists())) {
            lessonSTypeDir.mkdir();
        }

        // create lesson E type directory
        if (!(lessonETypeDir.exists())) {
            lessonETypeDir.mkdir();
        }

        // import courses, units and lessons
        saxParserFactory = SAXParserFactory.newInstance();
        try {
            saxParser = saxParserFactory.newSAXParser();
            courseHandler = new XMLImportCourse();
            unitHandler = new XMLImportUnit();
            lessonHandler = new XMLImportLesson();

            // import courses
            File[] listOfCourseFiles = courseDir.listFiles();
            for (int i = 0; i < listOfCourseFiles.length; i++){
                saxParser.parse(new File(courseDirPath+listOfCourseFiles[i].getName()), courseHandler);
            }

            // import units
            File[] listOfUnitFiles = unitDir.listFiles();
            for (int i = 0; i < listOfUnitFiles.length; i++){
                saxParser.parse(new File(unitDirPath+listOfUnitFiles[i].getName()), unitHandler);
            }

            // import lessons
            File[] listOfLessonFiles = lessonOTypeDir.listFiles();
            for (int i = 0; i < listOfLessonFiles.length; i++){
                saxParser.parse(new File(lessonRootDirPath+lessonOTypeDirPath+listOfLessonFiles[i].getName()), lessonHandler);
            }
            listOfLessonFiles = lessonSTypeDir.listFiles();
            for (int i = 0; i < listOfLessonFiles.length; i++){
                saxParser.parse(new File(lessonRootDirPath+lessonSTypeDirPath+listOfLessonFiles[i].getName()), lessonHandler);
            }
            listOfLessonFiles = lessonETypeDir.listFiles();
            for (int i = 0; i < listOfLessonFiles.length; i++){
                saxParser.parse(new File(lessonRootDirPath+lessonETypeDirPath+listOfLessonFiles[i].getName()), lessonHandler);
            }
        
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.err.println("Could not import the xml files : " + ex.getMessage());
        }

        // create the stage
        try {
            stage = primaryStage;
            stage.setTitle("Supporting Computer Literacy Training In Sign Language");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            stage.setResizable(false);
            //stage.getIcons().add(new Image("src/scltsl/icons/Logo.PNG"));
            gotoCourseCreation();
            primaryStage.show();

        } catch (Exception ex) {
            System.err.println("Application could not be started : " + ex.getMessage());
            System.exit(0);
        }
    }

    public void createLesson(Lesson theLesson) {
        gotoLessonCreation(theLesson);
    }

    public void createCourseOrUnit() {
        gotoCourseCreation();
    }
    
    public void createCourseOrUnit(Course c) {
        gotoCourseCreation(c);
    }

    private void gotoCourseCreation() {

        try {

            CourseController course = (CourseController) replaceSceneContent("Course.fxml");
            course.setApp(this);

        } catch (Exception ex) {
            System.out.println("error");
        }
    }
    
    private void gotoCourseCreation(Course c) {

        try {

            CourseController course = (CourseController) replaceSceneContent("Course.fxml");
            course.setApp(this);
            course.setCourse(c);

        } catch (Exception ex) {
            System.out.println("error");
        }
    }
//    Load the lesson creation window
    private void gotoLessonCreation(Lesson theLesson) {
        try {
            LessonController lesson = (LessonController) replaceSceneContent("Lesson.fxml");
            lesson.setApp(this);
            lesson.setLesson(theLesson);
        } catch (Exception ex) {
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = SCLTSL.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(SCLTSL.class.getResource(fxml));
        AnchorPane page;
        
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT);
        //stage.getIcons().add(new Image("src/scltsl/icons/Logo.PNG"));
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(SCLTSL.class, (java.lang.String[]) null);
    }
}