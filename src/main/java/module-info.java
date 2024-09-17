module pack.easynfp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;
    requires mongo.java.driver;
    requires java.logging;
    requires java.sql;
    requires org.controlsfx.controls;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.seleniumhq.selenium.support;
    requires io.github.bonigarcia.webdrivermanager;


    opens pack to javafx.fxml;
    exports pack;
}