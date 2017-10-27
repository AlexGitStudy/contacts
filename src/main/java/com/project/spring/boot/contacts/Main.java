package com.project.spring.boot.contacts;


import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main extends JavaFxSpringIntegrator {

    public static void main(String[] args) {
        launchSpringJavaFXApp(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        super.start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
