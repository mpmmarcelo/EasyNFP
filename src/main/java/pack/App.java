//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import java.math.BigDecimal;
import java.util.Iterator;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import pack.MMoneyField.Pattern;

public class App implements Screen {
    @FXML
    private TextField obsField;
    @FXML
    private MNumberField emitenteField;
    @FXML
    private MNumberField destinatarioField;
    @FXML
    private MNumberField nNotaField;
    @FXML
    private MNumberField quantidadeField;
    @FXML
    private MMoneyField valorUnitField;
    @FXML
    private MMoneyField valorTotalField;
    @FXML
    private MComboBox operacaoBox;
    @FXML
    private MComboBox produtoBox;
    @FXML
    private MComboBox unidadeBox;
    @FXML
    private MNumberField dateField;
    @FXML
    private TableView<NFP> nfpsTableView;
    @FXML
    private TableView<Produto> productsTableView;
    @FXML
    private Button insertNFPButton;
    private final String fxmlDir = "AppFxml_1.fxml";
    private final String cssDir = "/pack/AppCss.css";
    private final User user;
    private final ObservableList<NFP> nfps;
    private ScreenManager screenManager;
    private Trainee trainee;
    private NFP nfpView;
    private Produto produtoView;
    private boolean active;

    public App(User user, ObservableList nfps) {
        this.user = user;
        this.nfps = nfps;
    }

    public void initialize() {
        this.emitenteField.installMask("########-##");
        this.destinatarioField.autoApplyMask();
        this.operacaoBox.populateCombo(MProp.loadList("files/operacao.dat"));
        this.produtoBox.populateCombo(MProp.loadList("files/produto.dat"));
        this.unidadeBox.populateCombo(MProp.loadList("files/unidade.dat"));
        this.dateField.installMask("##/##/####");
        this.valorUnitField.asMoney(Pattern.THREE_DECIMAL);
        this.valorTotalField.asMoney(Pattern.TWO_DECIMAL);
        this.nfpView = new NFP();
        this.produtoView = new Produto();
        this.nfpView.emitenteProperty().bindBidirectional(this.emitenteField.textProperty());
        Bindings.bindBidirectional(this.nNotaField.textProperty(), this.nfpView.nNotaProperty(), new NumberStringConverter());
        this.nfpView.destinatarioProperty().bindBidirectional(this.destinatarioField.textProperty());
        this.nfpView.operacaoProperty().bindBidirectional(this.operacaoBox.valueProperty());
        this.nfpView.dtEmissaoProperty().bindBidirectional(this.dateField.textProperty());
        this.produtoView.produtoProperty().bindBidirectional(this.produtoBox.valueProperty());
        this.produtoView.unidadeProperty().bindBidirectional(this.unidadeBox.valueProperty());
        this.produtoView.quantidadeProperty().bindBidirectional(this.quantidadeField.textProperty());
        this.produtoView.valorTotalProperty().bindBidirectional(this.valorTotalField.textProperty());
        this.nfpView.observacaoProperty().bindBidirectional(this.obsField.textProperty());
        this.insertNFPButton.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            return this.nfpView.produtosProperty().isEmpty();
        }, new Observable[]{this.nfpView.produtosProperty()}));
        this.nfpsTableView.setItems(this.nfps);
        this.productsTableView.setItems(this.nfpView.produtosProperty());
        this.nfpsTableView.setRowFactory(new Callback<TableView<NFP>, TableRow<NFP>>() {
            public TableRow<NFP> call(TableView<NFP> tableView2) {
                final TableRow<NFP> row = new TableRow();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        int index = row.getIndex();
                        if (index >= 0 && index < App.this.nfpsTableView.getItems().size() && App.this.nfpsTableView.getSelectionModel().isSelected(index) && event.getButton().equals(MouseButton.PRIMARY)) {
                            App.this.nfpsTableView.getSelectionModel().clearSelection();
                            App.this.productsTableView.getSelectionModel().clearSelection();
                            App.this.nfpView.clear();
                            App.this.produtoView.clear();
                            event.consume();
                        }

                    }
                });
                return row;
            }
        });
        this.nfpsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    App.this.nfpView.set((NFP) App.this.nfpsTableView.getSelectionModel().getSelectedItem());
                }

            }
        });
        this.productsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    App.this.produtoView.set((Produto) App.this.productsTableView.getSelectionModel().getSelectedItem());
                }

            }
        });
        this.bindTableColumns();
        this.nfpsTableView.setContextMenu(this.nfpsRightClickMenu());
        this.productsTableView.setContextMenu(this.productsRightClickMenu());
        this.trainee = new Trainee(this.user, this.nfps);
    }

    @FXML
    void executeAction(ActionEvent event) {
        Task task = new Task<Boolean>() {
            protected Boolean call() throws Exception {
                App.this.active = MDB.checkUser(App.this.user.getPlainUsername());
                if (App.this.active) {
                    App.this.trainee.execute();
                }
                return null;
            }

            protected void succeeded() {
                if (App.this.active) {
                    String[] usernameSplit = App.this.trainee.getUsername().split(" ");
                    MDialog.createInformationAlert("Parabéns " + usernameSplit[0] + " você acabou de baixar " + App.this.trainee.getCounter() + " nota(s) com EasyNFP!");
                    MDB.sumBaixadas(App.this.trainee.getCounter());
                    App.this.trainee.setCounter(0);
                } else {
                    MDialog.createWarningAlert("Usuario não habilitado para executar esta função");
                }

            }
        };
        (new Thread(task)).start();
    }

    @FXML
    void insertProductAction(ActionEvent event) {
        if (!this.valorUnitField.getText().isEmpty()) {
            BigDecimal qnt = new BigDecimal(this.quantidadeField.getText().replace(".", "").replace(",", "."));
            BigDecimal total = qnt.multiply(new BigDecimal(this.valorUnitField.getText().replace(".", "").replace(",", ".")));
            this.valorTotalField.setText(total.setScale(2, 4).toString().replace(".", ","));
        }

        this.nfpView.produtosProperty().add(new Produto(this.produtoView));
        this.produtoBox.requestFocus();
    }

    @FXML
    void insertAction(ActionEvent event) {
        this.nfpView.setBaixada(false);
        this.insertNFP(new NFP(this.nfpView));
        this.nfpView.produtosProperty().clear();
    }

    public void insertNFP(NFP nfp) {
        Iterator var2 = this.nfps.iterator();

        NFP n;
        do {
            if (!var2.hasNext()) {
                this.nfps.add(nfp);
                this.nextNota();
                return;
            }

            n = (NFP) var2.next();
        } while (nfp.getNNota() != n.getNNota() || !nfp.getEmitente().equals(n.getEmitente()));

        this.nfps.set(this.nfps.indexOf(n), nfp);
        this.nextNota();
    }

    private void nextNota() {
        int nn = ((NFP) this.nfps.get(this.nfps.size() - 1)).getNNota();
        ++nn;
        this.nNotaField.setText(nn + "");
        this.nNotaField.requestFocus();
    }

    private void bindTableColumns() {
        ObservableList<TableColumn<NFP, ?>> columns = this.nfpsTableView.getColumns();
        ((TableColumn) columns.get(0)).setCellValueFactory(new PropertyValueFactory("emitente"));
        ((TableColumn) columns.get(1)).setCellValueFactory(new PropertyValueFactory("nNota"));
        ((TableColumn) columns.get(2)).setCellValueFactory(new PropertyValueFactory("destinatario"));
        ((TableColumn) columns.get(3)).setCellValueFactory(new PropertyValueFactory("operacao"));
        ((TableColumn) columns.get(4)).setCellValueFactory(new PropertyValueFactory("dtEmissao"));
        TableColumn<NFP, Boolean> col = (TableColumn) columns.get(5);
        col.setCellValueFactory((param) -> {
            return ((NFP) param.getValue()).baixadaProperty();
        });
        col.setCellFactory(CheckBoxTableCell.forTableColumn(col));
        ObservableList<TableColumn<Produto, ?>> columns2 = this.productsTableView.getColumns();
        ((TableColumn) columns2.get(0)).setCellValueFactory(new PropertyValueFactory("produto"));
        ((TableColumn) columns2.get(1)).setCellValueFactory(new PropertyValueFactory("unidade"));
        ((TableColumn) columns2.get(2)).setCellValueFactory(new PropertyValueFactory("quantidade"));
        ((TableColumn) columns2.get(3)).setCellValueFactory(new PropertyValueFactory("valorTotal"));
    }

    private ContextMenu nfpsRightClickMenu() {
        MenuItem update = new MenuItem("alterar");
        update.setOnAction((e) -> {
            this.nfpView.set((NFP) this.nfpsTableView.getSelectionModel().getSelectedItem());
        });
        MenuItem delete = new MenuItem("deletar");
        delete.setOnAction((e) -> {
            this.nfps.remove(this.nfpsTableView.getSelectionModel().getSelectedItem());
            this.nfpView.produtosProperty().clear();
        });
        MenuItem deleteBaixadas = new MenuItem("deletar notas baixadas");
        deleteBaixadas.setOnAction((e) -> {
            this.nfps.removeIf((nfp) -> {
                return nfp.isBaixada();
            });
        });
        MenuItem deleteAll = new MenuItem("deletar tudo");
        deleteAll.setOnAction((e) -> {
            this.nfps.clear();
        });
        MenuItem baixadaTrue = new MenuItem("baixada (V)");
        baixadaTrue.setOnAction((e) -> {
            ((NFP) this.nfps.get(this.nfpsTableView.getSelectionModel().getSelectedIndex())).setBaixada(true);
        });
        MenuItem baixadaFalse = new MenuItem("baixada (  )");
        baixadaFalse.setOnAction((e) -> {
            ((NFP) this.nfps.get(this.nfpsTableView.getSelectionModel().getSelectedIndex())).setBaixada(false);
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().addAll(new MenuItem[]{delete, deleteBaixadas, deleteAll, baixadaTrue, baixadaFalse});
        menu.setOnShowing((event) -> {
            if (this.nfpsTableView.getSelectionModel().getSelectedItem() == null) {
                menu.getItems().forEach((item) -> {
                    item.setDisable(true);
                });
            } else {
                menu.getItems().forEach((item) -> {
                    item.setDisable(false);
                });
            }

        });
        return menu;
    }

    private ContextMenu productsRightClickMenu() {
        MenuItem update = new MenuItem("alterar");
        update.setOnAction((e) -> {
            this.produtoView.set((Produto) this.productsTableView.getSelectionModel().getSelectedItem());
        });
        MenuItem delete = new MenuItem("deletar");
        delete.setOnAction((e) -> {
            if (this.nfps.contains(this.nfpsTableView.getSelectionModel().getSelectedItem())) {
                ((NFP) this.nfps.get(this.nfpsTableView.getSelectionModel().getSelectedIndex())).produtosProperty().remove(this.productsTableView.getSelectionModel().getSelectedItem());
            }

            this.nfpView.produtosProperty().remove(this.productsTableView.getSelectionModel().getSelectedItem());
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().addAll(new MenuItem[]{delete});
        menu.setOnShowing((event) -> {
            if (this.productsTableView.getSelectionModel().getSelectedItem() == null) {
                menu.getItems().forEach((item) -> {
                    item.setDisable(true);
                });
            } else {
                menu.getItems().forEach((item) -> {
                    item.setDisable(false);
                });
            }

        });
        return menu;
    }

    public boolean areFieldsEmpty() {
        return this.emitenteField.getText().trim().isEmpty() || this.emitenteField.getText().length() != 11 || this.destinatarioField.getText().trim().isEmpty() || this.nNotaField.getText().trim().isEmpty() || this.quantidadeField.getText().trim().isEmpty() || this.valorTotalField.getText().trim().isEmpty() || this.operacaoBox.getSelectionModel().isEmpty() || this.produtoBox.getSelectionModel().isEmpty() || this.unidadeBox.getSelectionModel().isEmpty() || this.dateField.getText().trim().isEmpty();
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
