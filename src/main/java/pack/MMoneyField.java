//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.FormatStringConverter;

public class MMoneyField extends TextField {
    public MMoneyField() {
    }

    public String getPlainText() {
        return this.getText().replaceAll("[^\\d]", "");
    }

    public void asMoney(MMoneyField.Pattern pattern) {
        UnaryOperator<Change> simpleNumberFilter = (change) -> {
            String text = change.getText();
            if (!text.equals(".") && !text.equals(",")) {
                if (text.length() == 1 && !text.matches("\\d")) {
                    change.setText("");
                }
            } else {
                change.setText(",");
            }

            return change;
        };
        TextFormatter tf = new TextFormatter(new FormatStringConverter(new DecimalFormat(pattern.getPattern(), new DecimalFormatSymbols(new Locale("pt", "BR")))), (Object)null, simpleNumberFilter);
        this.setTextFormatter(tf);
    }

    public static enum Pattern {
        TWO_DECIMAL("#,##0.00"),
        THREE_DECIMAL("#,##0.000");

        private final String pattern;

        private Pattern(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return this.pattern;
        }
    }
}
