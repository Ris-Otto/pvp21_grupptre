package com.Controlmatic.PoS_System;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<CustomerWindowApplication.StageReadyEvent> {

    @Value("classpath:/CashierAndCustomerWindow.fxml")
    Resource UI;
    private ApplicationContext applicationContext;
    private String applicationTitle;

    public StageInitializer(ApplicationContext applicationContext, @Value("${spring.application.ui.title}") String applicationTitle) {
        this.applicationContext = applicationContext;
        this.applicationTitle = applicationTitle;
    }

    @Override
    public void onApplicationEvent(CustomerWindowApplication.StageReadyEvent stageReadyEvent){
        try {
            FXMLLoader loader = new FXMLLoader(UI.getURL());
            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent parent = loader.load();
            Stage stage = stageReadyEvent.getStage();
            stage.setTitle(applicationTitle);
            stage.setScene(new Scene(parent, 800, 600));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
