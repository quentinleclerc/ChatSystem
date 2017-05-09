package view;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import communication.*;

public class ConversationView extends VBox {

    private ObservableList<Node> discussion = FXCollections.observableArrayList();

    private ScrollPane messageScroller;

    public ConversationView(){
        super(5);
        setupElements();
    }

    private void setupElements(){
        setupMessageDisplay();
        getChildren().setAll(messageScroller);
        setPadding(new Insets(5));
    }

    private void setupMessageDisplay(){
        VBox messageContainer = new VBox(5);
        //messageContainer.setPrefHeight(getMaxHeight());
        messageContainer.setPrefHeight(2000);
        String style = "-fx-border-color: white;\n" +
                "-fx-border-insets: 0;\n" +
                "-fx-border-width: 0;\n" +
                "-fx-border-style: dashed;\n";
        messageContainer.setStyle(style);

        // messageContainer.setStyle("-fx-background-color: #FFFFFF;");
        Bindings.bindContentBidirectional(discussion, messageContainer.getChildren());

        messageScroller = new ScrollPane(messageContainer);
        messageScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        messageScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messageScroller.setFitToHeight(true);
        messageScroller.prefWidthProperty().bind(messageContainer.prefWidthProperty().subtract(5));
        messageScroller.setFitToWidth(true);

        messageScroller.vvalueProperty().bind(messageContainer.heightProperty());

        //Make the scroller scroll to the bottom when a new message is added
        /*
        discussion.addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                if(change.wasAdded()){
                    messageScroller.setVvalue(messageScroller.getVmax());
                }
            }
        });
        */
    }

    public void addMessage(Message message, BubbleText.SpeechDirection leftorright) {
        discussion.add(new BubbleText(message.getData(), leftorright));
    }

}