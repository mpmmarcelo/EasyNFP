//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.openqa.selenium.interactions.SourceType;
import pack.MProp.Constant;
import pack.ScreenManager.ScreenType;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ENFP extends Application {
    private ScreenManager sc;
    private User user;
    private ObservableList<NFP> nfps;

    public ENFP() {
    }

    public void start(Stage stage) {
        this.sc = new ScreenManager(stage);
        this.user = new User(MProp.getString(Constant.USER), MProp.getString(Constant.PASS));
        //this.user = new User(); //PARA LOGIN
        this.nfps = FXCollections.observableArrayList();
        this.sc.setSize(MProp.getInteger(Constant.WINDOW_WIDTH), MProp.getInteger(Constant.WINDOW_HEIGHT));
        this.sc.addScreen(ScreenType.LOGIN, new Login(this.user));
        this.sc.addScreen(ScreenType.APP, new App(this.user, this.nfps));
        this.sc.getWindow().setTitle("EasyNFP 0.9.8 MPM | 2019-2024");

        this.sc.load(ScreenType.APP);
        this.sc.show();
    }


    public static void main(String[] args) {
        launch(new String[0]);
/*
        //Trainee trainee = new Trainee(new User(MProp.getString(Constant.USER), MProp.getString(Constant.PASS)));
        //trainee.baixarCanceladas("9529578560","nota extraviada segundo Boletim de Ocorrencia 622479/2023",1805,1980,2179);
        //trainee.baixarCanceladas("9529575979","nota extraviada segundo Boletim de Ocorrencia 622492/2023",590, 675);
        //trainee.baixarCanceladas("9538828332","nota extraviada segundo Boletim de Ocorrencia 172976/2023",36,37,38,39,40);

        //Trainee trainee = new Trainee(new User(MProp.getString(Constant.USER), MProp.getString(Constant.PASS)));
        //trainee.baixarEmBranco("9510742769",1956,2036,2040,2042,2054,2083,2086,2087,2088,2089,2093,2097,2110,2164,2171,2178,2180);


        Trainee trainee = new Trainee(new User(MProp.getString(Constant.USER), MProp.getString(Constant.PASS)));

        SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();

        int nNota = 3403; // numero primeira nota
        String dt = "14/11/2023"; //data
        List<Integer> notasList = new ArrayList<>(Arrays.asList(


                14595,14835,15045,14955,14745,14955,14790,14505,15085,14055,14565,14685,14835,14985,14745,14445,14655,13995,14580,14715,14790,13980,14595,14660,14704,14505,15465

        ));

        for (Integer nota : notasList) {
            ArrayList<Produto> produtos = new ArrayList<>();
            BigDecimal bigQnt = new BigDecimal(nota);
            produtos.add(new Produto(
                    "OVOS DE GALINHA ( PARA INCUBAÇAO)", //produto
                    "Unidade", //unidade
                    nota.toString(),
                    bigQnt.multiply(new BigDecimal("0.018")).setScale(2, RoundingMode.HALF_UP).toString().replace(".", ","))); //adicionar ,00

            NFP nfp = new NFP(
                    "9529578560", //cadpro
                    nNota,
                    "9062245007", //destinatario
                    "5949-Outra Saida De Mercadoria Ou Prestacao De Servico Nao Especificado", //cfop
                    dt, //data de emissao
                    produtos, //como produtos reseta a cada nota, nao vai adicionando mais
                    "",
                    false);

            trainee.addNFP(nfp);


            //https://intellipaat.com/community/9285/how-can-i-increment-a-date-by-one-day-in-java
            //if(nNota == 930 || nNota == 936){ //incrementar data na proxima nota (pegar ultima do bloco)
            if (nNota % 2 != 0) { //se for IMPAR a proxima nota vem com a nova data
                try {
                    c.setTime(sdt.parse(dt));
                    c.add(Calendar.DATE, 1);
                    dt = sdt.format(c.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            nNota++;
        }

        trainee.execute();
        System.out.println(trainee.getCounter());
        MDB.sumBaixadas(trainee.getCounter());
        trainee.setCounter(0);

        System.exit(0);
*/
    }
}
