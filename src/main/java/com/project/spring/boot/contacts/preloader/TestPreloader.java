package com.project.spring.boot.contacts.preloader;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TestPreloader extends Preloader {

    private Stage preloaderStage;
    private Scene scene;

    private ProgressIndicator bar;



    @Override
    public void init() throws Exception {

        // показ окна загрузки
        Platform.runLater(() -> {
            Label title = new Label("Инициализация...");

            title.setTextAlignment(TextAlignment.CENTER);
            bar = new ProgressIndicator();
            bar.setPadding(new Insets(2));
            bar.setMaxSize(40,40);

            HBox root = new HBox(bar, title);

            root.setAlignment(Pos.CENTER);

            scene = new Scene(root, 200, 100);


        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.setScene(scene);
        preloaderStage.show();
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            if (((ProgressNotification) info).getProgress()==100){// окно загрузки исчезнет, если придет уведомление о 100%
                preloaderStage.hide();
            }
        }
    }


    // метод можно использовать, чтобы изменять статус загрузки..
//    @Override
//    public void handleStateChangeNotification(StateChangeNotification info) {
//        StateChangeNotification.Type type = info.getType();
//        switch (type) {
//            case BEFORE_LOAD:
//                break;
//            case BEFORE_INIT:
//                break;
//            case BEFORE_START:
//                break;
//        }
//    }

}
