//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private StringProperty username;
    private StringProperty password;

    public User() {
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
    }

    public User(String usern, String passw) {
        this();
        this.username.setValue(usern);
        this.password.setValue(passw);
    }

    public StringProperty usernameProperty() {
        return this.username;
    }

    public String getPlainUsername() {
        return ((String)this.username.get()).replaceAll("[^\\d]", "");
    }

    public StringProperty passwordProperty() {
        return this.password;
    }
}
