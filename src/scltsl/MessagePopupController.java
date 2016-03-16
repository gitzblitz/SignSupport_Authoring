/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scltsl;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Masilo
 */
public class MessagePopupController implements Initializable {

    
    Stage dialogStage;
    boolean deleting;
    
    @FXML
    File icon;
    Text message;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    /**
     * Set the message for this pop-up box
     * @param theMessage the message
     */
    public void setMessage(String theMessage){
        message.setText(theMessage);
        //message.setWrappingWidth(280);
    }
    
    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * The icon of the message
     * @param theIcon the icon image file
     */
    public void setIcon(File theIcon){
        icon = theIcon;
    }
    
    /**
     * Set if this is a delete message box
     * @param isDelete true if its delete or false otherwise
     */
    public void setIsDelete(boolean isDelete){
        deleting = isDelete;
    }
    
    public void handleButtonAction(ActionEvent event){
        if (deleting){
            LessonController.trueOrFalse = true;
            dialogStage.close();
        }
        else {
            dialogStage.close();
        }
    }
    
    public void close(){
        LessonController.trueOrFalse = false;
        dialogStage.close();
    }
    
}