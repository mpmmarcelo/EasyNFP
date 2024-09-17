//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import java.util.Iterator;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NFP {
    private StringProperty emitente;
    private IntegerProperty nNota;
    private StringProperty destinatario;
    private StringProperty operacao;
    private StringProperty dtEmissao;
    private ObservableList<Produto> produtos;
    private StringProperty observacao;
    private BooleanProperty baixada;

    public NFP() {
        this.emitente = new SimpleStringProperty();
        this.nNota = new SimpleIntegerProperty();
        this.destinatario = new SimpleStringProperty();
        this.operacao = new SimpleStringProperty();
        this.dtEmissao = new SimpleStringProperty();
        this.produtos = FXCollections.observableArrayList();
        this.observacao = new SimpleStringProperty();
        this.baixada = new SimpleBooleanProperty();
    }

    public NFP(NFP nfp) {
        this.emitente = new SimpleStringProperty(nfp.getEmitente());
        this.nNota = new SimpleIntegerProperty(nfp.getNNota());
        this.destinatario = new SimpleStringProperty(nfp.getDestinatario());
        this.operacao = new SimpleStringProperty(nfp.getOperacao());
        this.dtEmissao = new SimpleStringProperty(nfp.getDtEmissao());
        this.produtos = FXCollections.observableArrayList(nfp.produtosProperty());
        this.observacao = new SimpleStringProperty(nfp.getObservacao());
        this.baixada = new SimpleBooleanProperty(nfp.isBaixada());
    }

    public NFP(String emitente, int nNota, String destinatario, String operacao, String dtEmissao, List<Produto> produtos, String observacao, boolean baixada) {
        this.emitente = new SimpleStringProperty(emitente);
        this.nNota = new SimpleIntegerProperty(nNota);
        this.destinatario = new SimpleStringProperty(destinatario);
        this.operacao = new SimpleStringProperty(operacao);
        this.dtEmissao = new SimpleStringProperty(dtEmissao);
        this.produtos = FXCollections.observableArrayList(produtos);
        this.observacao = new SimpleStringProperty(observacao);
        this.baixada = new SimpleBooleanProperty(baixada);
    }

    public String getEmitente() {
        return (String)this.emitente.get();
    }

    public void setEmitente(String emitente) {
        this.emitente.set(emitente);
    }

    public StringProperty emitenteProperty() {
        return this.emitente;
    }

    public int getNNota() {
        return this.nNota.get();
    }

    public void setNNota(int nNota) {
        this.nNota.set(nNota);
    }

    public IntegerProperty nNotaProperty() {
        return this.nNota;
    }

    public String getDestinatario() {
        return (String)this.destinatario.get();
    }

    public void setDestinatario(String destinatario) {
        this.destinatario.set(destinatario);
    }

    public StringProperty destinatarioProperty() {
        return this.destinatario;
    }

    public String getOperacao() {
        return (String)this.operacao.get();
    }

    public void setOperacao(String operacao) {
        this.operacao.set(operacao);
    }

    public StringProperty operacaoProperty() {
        return this.operacao;
    }

    public String getDtEmissao() {
        return (String)this.dtEmissao.get();
    }

    public void setDtEmissao(String dtEmissao) {
        this.dtEmissao.set(dtEmissao);
    }

    public StringProperty dtEmissaoProperty() {
        return this.dtEmissao;
    }

    public void setProdutos(ObservableList<Produto> produtos) {
        this.produtos.setAll(produtos);
    }

    public ObservableList<Produto> produtosProperty() {
        return this.produtos;
    }

    public String getObservacao() {
        return (String)this.observacao.get();
    }

    public void setObservacao(String observacao) {
        this.observacao.set(observacao);
    }

    public StringProperty observacaoProperty() {
        return this.observacao;
    }

    public boolean isBaixada() {
        return this.baixada.get();
    }

    public void setBaixada(boolean baixada) {
        this.baixada.set(baixada);
    }

    public BooleanProperty baixadaProperty() {
        return this.baixada;
    }

    public void set(NFP nfp) {
        this.emitente.set(nfp.getEmitente());
        this.nNota.set(nfp.getNNota());
        this.destinatario.set(nfp.getDestinatario());
        this.operacao.set(nfp.getOperacao());
        this.dtEmissao.set(nfp.getDtEmissao());
        this.produtos.setAll(nfp.produtosProperty());
        this.observacao.set(nfp.getObservacao());
        this.baixada = new SimpleBooleanProperty(nfp.isBaixada());
    }

    public void clear() {
        this.emitente.set("");
        this.nNota.set(0);
        this.destinatario.set("");
        this.operacao.set("");
        this.dtEmissao.set("");
        this.produtos.clear();
        this.observacao.set("");
        this.baixada.set(false);
    }

    public String toString() {
        String pps = "";

        Produto p;
        for(Iterator iterator = this.produtos.iterator(); iterator.hasNext(); pps = pps + "\t" + p.getProduto() + " | " + p.getQuantidade() + " | " + p.getUnidade() + " | " + p.getValorTotal() + "\n") {
            p = (Produto)iterator.next();
        }

        return (String)this.emitente.get() + "\n" + this.nNota.get() + "\n" + (String)this.destinatario.get() + "\n" + (String)this.operacao.get() + "\n" + (String)this.dtEmissao.get() + "\n" + pps + (String)this.observacao.get() + "\n" + this.baixada.get();
    }
}
