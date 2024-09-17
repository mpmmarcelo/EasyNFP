//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pack.ScreenManager.ScreenType;

public class Login implements Screen {
    private final String fxmlDir = "LoginFxml.fxml";
    private final String cssDir = "/pack/LoginCss.css";
    private final User user;
    private ScreenManager screenManager;
    private BooleanProperty busy;
    @FXML
    MNumberField userField;
    @FXML
    TextField passField;
    @FXML
    Button loginBtn;
    @FXML
    Label waitLabel;

    @FXML
    private void signinAction(ActionEvent e) {
        Task loginTask = new Task<Boolean>() {
            protected Boolean call() {
                return MDB.checkUser(Login.this.user.getPlainUsername());
            }

            protected void succeeded() {
                Login.this.busy.setValue(false);
                if ((Boolean)this.getValue()) {
                    Login.this.screenManager.load(ScreenType.APP);
                } else {
                    MDialog.createWarningAlert("Usuario não encontrado. Por favor verifique sua conexão com a internet e tente novamente. \nCaso nao possua um usuario cadastrado entre em contato com um de nossos revendedores.");
                }

            }
        };
        if (!this.userField.getText().isEmpty() && !this.passField.getText().isEmpty()) {
            this.busy.set(true);
            (new Thread(loginTask)).start();
        } else {
            MDialog.createWarningAlert("Existem campos vazios ou incorretos.");
        }

    }

    public Login(User user) {
        this.user = user;
    }

    public void initialize() {
        this.user.usernameProperty().bind(this.userField.textProperty());
        this.user.passwordProperty().bind(this.passField.textProperty());
        this.userField.installMask("###.###.###-##");
        this.busy = new SimpleBooleanProperty();
        this.loginBtn.disableProperty().bind(this.busy);
        this.waitLabel.visibleProperty().bind(this.busy);
    }

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public String getFXML() {
        return fxmlDir;
    }

    public String getCSS() {
        return cssDir;
    }
}
