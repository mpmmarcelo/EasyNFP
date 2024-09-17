//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class MComboBox extends ComboBox {
    private AutoCompletionBinding auto;

    public MComboBox() {
    }

    public void populateCombo(List<String> items) {
        this.setItems(FXCollections.observableArrayList(items));
        this.auto = TextFields.bindAutoCompletion(this.getEditor(), this.getItems());
        //https://stackoverflow.com/questions/53695304/autocompletionbinding-cannot-access-class-com-sun-javafx-event-eventhandlermanag
        //https://stackoverflow.com/questions/50938383/how-to-set-jvm-arguments-in-intellij-idea
        this.getEditor().textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue value, Object oldValue, Object newValue) {
                if (MComboBox.this.isShowing()) {
                    MComboBox.this.hide();
                }

            }
        });
    }
}
