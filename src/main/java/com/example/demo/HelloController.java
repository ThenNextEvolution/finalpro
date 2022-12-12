package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.demo.client.thisone;
import static com.example.demo.client2.thisone2;

public class HelloController implements Initializable {
    public Pane back;
    public VBox vbox;
   @FXML
   private ListView<Message> listView;
   @FXML
   private ListView<String> listViews;
    @FXML
    private Label welcomeText;
    @FXML
    private  TextFlow mes_view=new TextFlow();
    @FXML
    private TextArea inmes;
    @FXML
    private Stage room;
    @FXML
    private Button Send;
    public static String se;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private MediaView mid;

    @FXML private Button play,pause,reset;

    private  File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private ListView<Message> listCell;
    public static  ObservableList<Message> mes =
            FXCollections.observableArrayList();
    public static final ObservableList<String> names =
            FXCollections.observableArrayList();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        file =new File("src/vids/mayahiga.mp4");
        media =new Media(file.toURI().toString());
        mediaPlayer =new MediaPlayer(media);
        //mid.setMediaPlayer(mediaPlayer);
        Message message1 = new Message(1,"JOhn","get em");
        ListCell<Message> cel = new ListCell<>();
        cel.startEdit();
        cel.setText("gang");
        //cel.update
        cel.commitEdit(message1);
        mes.add(message1);
        ListCell<Message> kap = new ListCell<>();
        kap.setText("anng");
       // mes.add(kap);
        names.addAll( "Adam", "Alex", "Alfred", "Albert");
        listViews.getItems().addAll(names);

        //cel.updateListView(cel.getListView());
        //listView.setItems(mes);
        //listView.refresh();
        listView.getItems().addAll(mes);

        listView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> messageListView) {
                return new ListCell<>(){
                    @Override
                    public void updateItem(Message message, boolean empty) {
                        super.updateItem(message, empty);
                        if (empty || message == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            //setText(message.name + "/" + message.message);
                            GridPane grid = new GridPane();


                            grid.getStylesheets().add("com/example/demo/style.css");
                            grid.getStyleClass().add("grid");
                            grid.setHgap(10);
                            grid.setVgap(4);
                            grid.setPadding(new Insets(0,10,0,10));

                            Label icon = new Label();
                            Image img = new Image("C:\\Users\\eyita\\IdeaProjects\\demo\\src\\imgs\\cool-neon-blue-profile-picture-u9y9ydo971k9mdcf.jpg");
                            ImageView view = new ImageView(img);
                            view.setFitHeight(40);
                            view.setPreserveRatio(true);
                            icon.setGraphic(view);
                            grid.add(icon,0,0,1,2);



                            Label mess= new Label();
                            mess.setText(message.name);
                            setGraphic(grid);
                            mess.getStyleClass().add("name");
                            grid.add(mess,1,0,1,1);

                            Label sub= new Label();
                            sub.setText(message.message);
                            setGraphic(grid);
                            sub.getStyleClass().add("mes");
                            grid.add(sub,1,1);
                        }
                    }
                };
            }


        });
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setOnMouseClicked(new EventHandler<Event>(){
            @Override
            public void handle(Event event){
                ObservableList<Message> cell = listView.getSelectionModel().getSelectedItems();
                try {
                    System.out.println(cell.get(0));
                    switchrooms(cell,listView.getScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
    public void makemes(Message me){
        mes.add(me);
    }

    public void startplay(){
    mediaPlayer.play();
    }
    public void pausemed(){
    mediaPlayer.pause();
    }
    public void resetmed(){
        if (mediaPlayer.getStatus() != MediaPlayer.Status.READY)
    mediaPlayer.seek(Duration.seconds(0.0));
    }
    public void switchrooms(ObservableList<Message> cell, Scene roo) throws IOException {
        room = (Stage) roo.getRoot().getScene().getWindow();


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chatview.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load(), 600, 600);


        room.setScene((scene1));
        room.setTitle(cell.get(0).name);
        Text hold2 = new Text(cell.get(0).message);


        mes_view.getChildren().add(hold2);



        makeconn();
        room.show();

    }
    protected void makeconn() throws IOException {
        Message message = new Message(2,"mary","hope this works");
        Socket socketc =new Socket("localhost",9000);
        client2 me = new client2(socketc,"hold");
    }

    @FXML
    protected void sendmessage(ActionEvent event) throws IOException {
        se = inmes.getText();
        inmes.setText("");






    }
    public static Text holddd;

    public  void print(Text text){
        mes_view.getChildren().add(thisone2);
        //System.out.println(mes_view.getChildren().get(0));

    }



}

