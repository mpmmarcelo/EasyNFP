//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenManager {
    private final Stage window;
    private final Map<ScreenManager.ScreenType, Screen> screens;

    public ScreenManager(Stage stage) {
        this.window = stage;
        this.screens = new HashMap();
    }

    public void addScreen(ScreenManager.ScreenType screenType, Screen screen) {
        if (this.screens.containsKey(screenType)) {
            this.screens.replace(screenType, screen);
        } else {
            this.screens.put(screenType, screen);
        }

    }

    public Screen getScreen(ScreenManager.ScreenType screenType) {
        return (Screen)this.screens.get(screenType);
    }

    public void load(ScreenManager.ScreenType screenType) {
        Screen screen = (Screen)this.screens.get(screenType);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(screen.getFXML()));
        screen.setScreenManager(this);
        loader.setController(screen);

        try {
            Parent root = (Parent)loader.load();
            //root.getStylesheets().add(screen.getCSS()); https://stackoverflow.com/questions/26715454/javafx-css-does-not-work-in-my-project
            Scene scene = new Scene(root);

            scene.getStylesheets().add(this.getClass().getResource(screen.getCSS()).toExternalForm());

            this.window.setScene(scene);
        } catch (IOException var5) {
            MDialog.createErrAlert(var5);
        }

    }

    public void setSize(int width, int height) {
        this.window.setWidth((double)width);
        this.window.setHeight((double)height);
    }

    public Stage getWindow() {
        return this.window;
    }

    public void show() {
        this.window.show();
    }

    public static enum ScreenType {
        LOGIN,
        APP,
        TRAINEE;

        private ScreenType() {
        }
    }
}
