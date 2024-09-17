//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import io.github.bonigarcia.wdm.WebDriverManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Trainee {
    private User user;
    private ObservableList<NFP> nfps;
    private final String url = "https://www.sefanet.pr.gov.br/sefanetv2/segSefanet/segLogin.asp";
    private Wait wait;
    private WebDriver driver;
    private int counter = 0;
    private String username = "";
    private HashMap<String, String> storedCfops;

    public Trainee(User user) {
        this.user = user;
        nfps = FXCollections.observableArrayList();
    }

    public Trainee(User user, ObservableList<NFP> nfps) {
        this.user = user;
        this.nfps = nfps;
    }

    public void addNFP(NFP nfp){
        nfps.add(nfp);
    }

    public void execute() {
        this.storedCfops = new HashMap<>();
        this.connect(url);
        this.login(this.user);
        this.gotoInclusao();
        this.nfps.forEach((nfp) -> {
            if (!nfp.isBaixada()) {
                this.insertNFP(nfp);
            }

        });
        this.driver.close();
    }

    public void baixarCanceladas(String cadpro, String txtCancelada, Integer... nnotas) {
        this.connect(url);
        this.login(this.user);
        this.gotoInclusao();
        Arrays.asList(nnotas).forEach((nota) -> {
            this.findElement(By.id("fpro_CadProd")).sendKeys(new CharSequence[]{cadpro});
            this.findElement(By.id("faidf_NrNF")).sendKeys(new CharSequence[]{nota.toString()});
            WebElement radioBox = this.driver.findElement(By.id("opcao1"));
            radioBox.sendKeys(new CharSequence[]{Keys.ENTER});
            radioBox.sendKeys(new CharSequence[]{Keys.RIGHT});
            this.findClickableElement(By.name("btnContinuar")).click();
            this.findElement(By.name("motivo")).sendKeys(new CharSequence[]{txtCancelada});
            this.findClickableElement(By.name("btnContinuar")).click();
            System.out.println(this.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[3]/table[2]/tbody/tr[2]/td")).getText());
            this.findClickableElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[3]/center/a")).click();
            this.findElement(By.id("fpro_CadProd")).clear();
        });
    }

    public void baixarEmBranco(String cadpro, Integer... nnotas) {
        this.connect(url);
        this.login(this.user);
        this.gotoInclusao();
        Arrays.asList(nnotas).forEach((nota) -> {
            this.findElement(By.id("fpro_CadProd")).sendKeys(new CharSequence[]{cadpro});
            this.findElement(By.id("faidf_NrNF")).sendKeys(new CharSequence[]{nota.toString()});
            List<WebElement> radioBox = this.driver.findElements(By.id("opcao1"));
            ((WebElement)radioBox.get(2)).click();
            this.findClickableElement(By.name("btnContinuar")).click();
            this.driver.switchTo().alert().accept();
            this.driver.switchTo().alert().accept();
            this.findClickableElement(By.xpath("//*[@id=\"frmPrestacaoContas\"]/center[2]/a")).click();
        });
        this.driver.close();
    }

    private void connect(String url) {
        //https://www.youtube.com/watch?v=lcc9ihfNyWc
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        //https://bonigarcia.dev/webdrivermanager/
        //https://sqa.stackexchange.com/questions/41928/how-to-autoupdate-chrome-driver-in-selenium
        //https://stackoverflow.com/questions/64397766/have-webdrivermanager-download-to-specific-directory
        WebDriverManager.chromedriver().cachePath("ChromeDriver").avoidOutputTree().setup();

        this.driver = new ChromeDriver(options);

        File file = new File("chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

        //this.driver = new ChromeDriver(options);
        this.driver.get(url);
        this.driver.manage().timeouts().pageLoadTimeout(15L, TimeUnit.SECONDS);
        this.wait = (new FluentWait(this.driver)).pollingEvery(Duration.ofMillis(100L)).withTimeout(Duration.ofSeconds(15L)).ignoring(NoSuchElementException.class).withMessage("Explicit Wait ERROR: Componente não encontrado");
    }

    private void login(User user) {
        this.findElement(By.name("txtUsuario")).sendKeys(new CharSequence[]{user.getPlainUsername()});
        this.findElement(By.name("txtSenha")).sendKeys(new CharSequence[]{(CharSequence)user.passwordProperty().get()});
        this.findElement(By.id("btnSubmit")).click();
    }

    private void gotoInclusao() {
        this.wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("topo")));
        this.username = this.findElement(By.xpath("/html/body/table/tbody/tr[2]/td/div/table/tbody/tr/td/p/font/strong")).getText();
        this.findElement(By.id("divoCMenu0_2")).click();
        this.driver.switchTo().defaultContent();
        this.wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("contSEFANET")));
        this.findElement(By.cssSelector("a")).click();
        this.findElement(By.xpath("//a[contains(text(),'Prestação de Contas')]")).click();
        this.findElement(By.xpath("//a[contains(text(),'Inclusão')]")).click();
    }

    private void insertNFP(NFP nfp) {
        try {
            this.findElement(By.id("fpro_CadProd")).sendKeys(new CharSequence[]{nfp.getEmitente()});
            this.findElement(By.id("faidf_NrNF")).sendKeys(new CharSequence[]{nfp.getNNota() + ""});
            this.findClickableElement(By.name("btnContinuar")).click();
            boolean baixada = this.driver.findElements(By.xpath("/html/body/div[1]/table/tbody/tr/td[3]/center/table/tbody/tr[2]/td[2]")).size() > 0;
            if (!baixada) {
                ((JavascriptExecutor)this.driver).executeScript("contador = 3;", new Object[0]);
                switch(nfp.getDestinatario().replaceAll("[^\\d]", "").length()) {
                    case 10:
                        this.findElement(By.id("eCadICMS")).sendKeys(new CharSequence[]{nfp.getDestinatario()});
                        break;
                    case 11:
                        this.findElement(By.id("eCPF")).sendKeys(new CharSequence[]{nfp.getDestinatario()});
                    case 12:
                    case 13:
                    default:
                        break;
                    case 14:
                        this.findElement(By.id("eCNPJ")).sendKeys(new CharSequence[]{nfp.getDestinatario()});
                }

                this.findElement(By.name("btnVerificar")).click();
                this.findElement(By.xpath("//*[@id=\"frmPrestacaoContas\"]/table[2]/tbody/tr[8]/td[2]/span/div/input[1]")).click();
                WebElement cfopbrute = this.findElement(By.xpath("/html/body/div[1]"));
                List<WebElement> cfops = cfopbrute.findElements(By.tagName("div"));
                if (this.storedCfops.containsKey(nfp.getOperacao())) {
                    this.findElement(By.xpath((String)this.storedCfops.get(nfp.getOperacao()))).click();
                } else {
                    Predicate<WebElement> predicate = (e) -> {
                        return e.getText().substring(0, 4).contentEquals(nfp.getOperacao().substring(0, 4));
                    };
                    WebElement cfop = (WebElement)cfops.stream().filter(predicate).findFirst().get();
                    this.storedCfops.put(nfp.getOperacao(), this.generateXPATH(cfop, ""));
                    cfop.click();
                }

                this.findElement(By.name("DtEmissao")).sendKeys(new CharSequence[]{nfp.getDtEmissao()});
                this.driver.switchTo().frame("frmProdutoNovo");
                nfp.produtosProperty().forEach((produto) -> {
                    this.dirtySleep(1500L);
                    this.findElement(By.xpath("//*[@id=\"frmDadosProduto\"]/table/tbody/tr[1]/td[2]/span/div/input[1]")).sendKeys(new CharSequence[]{produto.getProduto()});
                    this.dirtySleep(1000L);
                    Select selUnidade = new Select(this.driver.findElement(By.id("eUnidade")));
                    selUnidade.selectByVisibleText(produto.getUnidade());
                    this.findElement(By.id("eQtde")).sendKeys(new CharSequence[]{produto.getQuantidade()});
                    this.findElement(By.id("eValorTotal")).sendKeys(new CharSequence[]{produto.getValorTotal()});
                    this.findElement(By.name("btnGravar")).click();
                });
                this.driver.switchTo().parentFrame();
                this.findElement(By.name("obs")).sendKeys(new CharSequence[]{nfp.getObservacao()});
                this.findClickableElement(By.xpath("//*[@id=\"frmPrestacaoContas\"]/table[2]/tbody/tr[22]/td[2]/input[1]")).click();
                this.findClickableElement(By.name("btnContinuar")).click();
                System.out.println(this.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[3]/table[2]/tbody/tr[2]/td")).getText());
                nfp.setBaixada(true);
                ++this.counter;
                this.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[3]/center/a")).click();
            } else {
                System.out.println(this.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[3]/center/table/tbody/tr[2]/td[2]")).getText());
                nfp.setBaixada(true);
                this.findClickableElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[3]/center/table/tbody/tr[3]/td/a")).click();
            }

            this.findElement(By.id("fpro_CadProd")).clear();
        } catch (Exception var7) {
            var7.printStackTrace();
            Alert alert = this.driver.switchTo().alert();
            System.out.println(alert.getText());
            alert.accept();
            this.driver.close();
            this.execute();
        }

    }

    public int getCounter() {
        return this.counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getUsername() {
        return this.username;
    }

    private WebElement findElement(By by) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return this.driver.findElement(by);
    }

    private WebElement findClickableElement(By by) {
        this.wait.until(ExpectedConditions.elementToBeClickable(by));
        return this.driver.findElement(by);
    }

    private String generateXPATH(WebElement childElement, String current) {
        String childTag = childElement.getTagName();
        if (childTag.equals("html")) {
            return "/html[1]" + current;
        } else {
            WebElement parentElement = childElement.findElement(By.xpath(".."));
            List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
            int count = 0;

            for(int i = 0; i < childrenElements.size(); ++i) {
                WebElement childrenElement = (WebElement)childrenElements.get(i);
                String childrenElementTag = childrenElement.getTagName();
                if (childTag.equals(childrenElementTag)) {
                    ++count;
                }

                if (childElement.equals(childrenElement)) {
                    return this.generateXPATH(parentElement, "/" + childTag + "[" + count + "]" + current);
                }
            }

            return null;
        }
    }

    public void dirtySleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException var4) {
            Logger.getLogger(Trainee.class.getName()).log(Level.SEVERE, (String)null, var4);
        }

    }
}
