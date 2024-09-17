//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pack.MProp.Constant;

public class MDialog {
    private static Alert alert;

    private MDialog() {
    }

    public static void createErrAlert(Exception err) {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle(MProp.getString(Constant.TITLE));
        alert.setHeaderText(err.getLocalizedMessage());
        alert.setContentText(MProp.getString(Constant.SUPPORT_TEXT));
        alert.showAndWait();
        err.printStackTrace();
    }

    public static void createWarningAlert(String message) {
        alert = new Alert(AlertType.WARNING);
        alert.setTitle(MProp.getString(Constant.TITLE));
        alert.setHeaderText(message);
        alert.setContentText(MProp.getString(Constant.SUPPORT_TEXT));
        alert.showAndWait();
    }

    public static void createInformationAlert(String message) {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(MProp.getString(Constant.TITLE));
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
