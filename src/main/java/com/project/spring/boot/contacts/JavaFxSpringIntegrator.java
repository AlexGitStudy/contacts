

/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

        package com.project.spring.boot.contacts;
        import com.sun.javafx.application.LauncherImpl;
        import javafx.application.Application;
        import javafx.application.Preloader;
        import javafx.stage.Stage;
        import org.springframework.boot.SpringApplication;
        import org.springframework.context.ConfigurableApplicationContext;
        import com.project.spring.boot.contacts.preloader.TestPreloader;

        import java.util.Arrays;

// класс для "соединения" javafx со spring
public abstract class JavaFxSpringIntegrator extends Application {

    private static String[] savedArgs;// аргументы при старте (если они есть)

    // spring контекст для приложения - его нужно связать с javafx контекстом
    private ConfigurableApplicationContext applicationContext;


    @Override
    public void start(Stage primaryStage) throws Exception {

        applicationContext = SpringApplication.run(getClass(), savedArgs);

        // главный момент - "присоединяем" экземпляр Application (который стартует javafx приложение) к контексту Spring
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);

		printBeans();

        // уведомить прелоадер, что загрузка прошла полностью (чтобы скрыть окно инициализации)
        LauncherImpl.notifyPreloader(this,  new Preloader.ProgressNotification(100));
    }

    // печать всех spring бинов
    private void printBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
    }

    // старт javafx приложения
    protected static void launchSpringJavaFXApp(Class<? extends JavaFxSpringIntegrator> appClass, String[] args) {
        JavaFxSpringIntegrator.savedArgs = args;
        // стартуем javafx приложение с прелоадером (окном инициализации)
        LauncherImpl.launchApplication(appClass, TestPreloader.class, args);
    }
}
