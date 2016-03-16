/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scltsl;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Masilo
 */
public class PreviewPopupController implements Initializable {

    Stage dialogStage;
    boolean isPicture = false;
    Lesson lesson;
    File currentResourceFile;
    
    Timer lessonTimer;
    boolean pictureDurationDone = false;
    boolean lessonDone = false;
    int currentStepIndex = 0;
    int currentResourceIndex = 0;
    
    @FXML
    public AnchorPane playerPane;
    public Button playOrPause;
    public Label lessonTitle;
    public Label resourceTitle;
    public ImageView imageView;
    public MediaView mediaView;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void close(){
        mediaView.getMediaPlayer().stop();
    }
    
    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        
        //this.dialogStage.getScene().getRoot().getChildrenUnmodifiable().add(mediaView);
    }
    
    @FXML
    public void pauseOrPlay(ActionEvent event){
        //if (lesson.)
        if (mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING){
            mediaView.getMediaPlayer().pause();
        }
        else {
            mediaView.getMediaPlayer().play();
        }
    }
    
    @FXML
    public void stop(ActionEvent event){
        mediaView.getMediaPlayer().stop();
    }
    
    /**
     * Set the lesson to preview
     * @param l the lesson to preview
     */
    public void setLesson(Lesson l){
        lesson = l;
        lessonTitle.setText("Lesson Title: "+lesson.getTitle());
        currentResourceIndex = 0;
        currentStepIndex = 0;
        playerPane.getChildren().clear();
        
        try{
        if (!lesson.getResource(currentStepIndex, currentResourceIndex).isImage()){
                currentResourceFile = new File(lesson.getResource(currentStepIndex, currentResourceIndex).getAbsolutePath());
                final String MEDIA_URL = currentResourceFile.toURI().toString();
                Media media = new Media(MEDIA_URL);
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setCycleCount(1);

                // Add media display node to the scene graph
                mediaView = new MediaView(mediaPlayer);
                mediaView.setFitHeight(400);
                mediaView.setFitWidth(645);
                playerPane.getChildren().add(mediaView);
                resourceTitle.setText("Media Name: "+lesson.getResource(currentStepIndex, currentResourceIndex).getName());
            }
            else {
                currentResourceFile = new File(lesson.getResource(currentStepIndex, currentResourceIndex).getAbsolutePath());
                imageView.setImage(new Image(currentResourceFile.toURI().toString()));
                resourceTitle.setText("Media Name: "+lesson.getResource(currentStepIndex, currentResourceIndex).getName());
                playerPane.getChildren().add(imageView);
            }
        }catch(Exception e){
            
            if(e.toString().contains("MEDIA_UNAVAILABLE")){
                resourceTitle.setText("File: " + lesson.getResource(currentStepIndex, currentResourceIndex).getName() + ", can not be found. Please Check that the file"
                        + " exists.");
            }else if(e.toString().contains("MEDIA_UNSUPPORTED")) {
                resourceTitle.setText("File: " + lesson.getResource(currentStepIndex, currentResourceIndex).getName() + ", is not Supported.");
            }
                
                 
        }
        
    }
    
    /**
     * view the next resource
     */
    public void next(ActionEvent event){
        
        currentResourceIndex++;
        if (currentResourceIndex >= lesson.getSteps().get(currentStepIndex).getResources().size()){
            if (currentStepIndex < lesson.getSteps().size() - 1){
                currentStepIndex++;
                currentResourceIndex = 0;
            }
            else {
                currentResourceIndex--;
            }
        }
        
        playerPane.getChildren().clear();
        if (!(currentStepIndex >= lesson.getSteps().size()) && !(currentResourceIndex >= lesson.getSteps().get(currentStepIndex).getResources().size())){
            if (!lesson.getResource(currentStepIndex, currentResourceIndex).isImage()){
                currentResourceFile = new File(lesson.getResource(currentStepIndex, currentResourceIndex).getAbsolutePath());
                final String MEDIA_URL = currentResourceFile.toURI().toString();
                Media media = new Media(MEDIA_URL);
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setCycleCount(1);

                // Add media display node to the scene graph
                stop(event);
                mediaView = new MediaView(mediaPlayer);
                mediaView.setFitHeight(400);
                mediaView.setFitWidth(645);
                playerPane.getChildren().add(mediaView);
                resourceTitle.setText("Media Name: "+lesson.getResource(currentStepIndex, currentResourceIndex).getName());
            }
            else {
                currentResourceFile = new File(lesson.getResource(currentStepIndex, currentResourceIndex).getAbsolutePath());
                imageView.setImage(new Image(currentResourceFile.toURI().toString()));
                stop(event);
                resourceTitle.setText("Media Name: "+lesson.getResource(currentStepIndex, currentResourceIndex).getName());
                playerPane.getChildren().add(imageView);
            }
        }
    }
    
    public void previous(ActionEvent event){
        currentResourceIndex--;
        if (currentResourceIndex < 0){
            if (currentStepIndex > 0){
                currentStepIndex--;
                currentResourceIndex = lesson.getSteps().get(currentStepIndex).getResources().size()-1;
            }
            else {
                currentResourceIndex++;
            }
        }
        
        playerPane.getChildren().clear();
        if (!(currentStepIndex < 0) && !(currentResourceIndex < 0)){
            if (!lesson.getResource(currentStepIndex, currentResourceIndex).isImage()){
                currentResourceFile = new File(lesson.getResource(currentStepIndex, currentResourceIndex).getAbsolutePath());
                final String MEDIA_URL = currentResourceFile.toURI().toString();
                Media media = new Media(MEDIA_URL);
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setCycleCount(1);

                // Add media display node to the scene graph
                stop(event);
                mediaView = new MediaView(mediaPlayer);
                mediaView.setFitHeight(400);
                mediaView.setFitWidth(645);
                playerPane.getChildren().add(mediaView);
                resourceTitle.setText("Media Name: "+lesson.getResource(currentStepIndex, currentResourceIndex).getName());
            }
            else {
                currentResourceFile = new File(lesson.getResource(currentStepIndex, currentResourceIndex).getAbsolutePath());
                imageView.setImage(new Image(currentResourceFile.toURI().toString()));
                stop(event);
                resourceTitle.setText("Media Name: "+lesson.getResource(currentStepIndex, currentResourceIndex).getName());
                playerPane.getChildren().add(imageView);
            }
            
        }
    }
    
}
