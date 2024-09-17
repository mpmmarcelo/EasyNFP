//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Produto {
    private StringProperty produto;
    private StringProperty unidade;
    private StringProperty quantidade;
    private StringProperty valorTotal;

    public Produto() {
        this.produto = new SimpleStringProperty();
        this.unidade = new SimpleStringProperty();
        this.quantidade = new SimpleStringProperty();
        this.valorTotal = new SimpleStringProperty();
    }

    public Produto(Produto produto) {
        this();
        this.set(produto);
    }

    public Produto(String produto, String unidade, String quantidade, String valorTotal) {
        this();
        this.produto.set(produto);
        this.unidade.set(unidade);
        this.quantidade.set(quantidade);
        this.valorTotal.set(valorTotal);
    }

    public void set(Produto produto) {
        this.produto.set(produto.getProduto());
        this.unidade.set(produto.getUnidade());
        this.quantidade.set(produto.getQuantidade());
        this.valorTotal.set(produto.getValorTotal());
    }

    public void clear() {
        this.produto.set("");
        this.unidade.set("");
        this.quantidade.set("");
        this.valorTotal.set("");
    }

    public String getProduto() {
        return (String)this.produto.get();
    }

    public void setProduto(String produto) {
        this.produto.set(produto);
    }

    public StringProperty produtoProperty() {
        return this.produto;
    }

    public String getUnidade() {
        return (String)this.unidade.get();
    }

    public void setUnidade(String unidade) {
        this.unidade.set(unidade);
    }

    public StringProperty unidadeProperty() {
        return this.unidade;
    }

    public String getQuantidade() {
        return (String)this.quantidade.get();
    }

    public void setQuantidade(String quantidade) {
        this.quantidade.set(quantidade);
    }

    public StringProperty quantidadeProperty() {
        return this.quantidade;
    }

    public String getValorTotal() {
        return (String)this.valorTotal.get();
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal.set(valorTotal);
    }

    public StringProperty valorTotalProperty() {
        return this.valorTotal;
    }
}
