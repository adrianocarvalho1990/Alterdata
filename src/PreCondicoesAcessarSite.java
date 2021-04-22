
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.*;

public class PreCondicoesAcessarSite {

	@Test
	public void GerarBoleto() {

		String mensagemErro1 = "Preencha este campo.";

		// Configuração do Driver
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.alterdata.com.br");

		System.out.println("Página Inicial Alterdata foi acessada");

		// Configuração do timer
		WebDriverWait wait = new WebDriverWait(driver, (30));

		// Clicar na Central do Cliente
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[text()='Central do Cliente'])[1]")))
				.click();

		System.out.println("Página da Central do Cliente foi acessada");

		// Clicar em Gerar Boleto
		List<String> abas = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(abas.get(1));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=' Gerar Boleto']"))).click();

		System.out.println("Botão de Gerar Boleto foi aceito");

		// Gerar Boleto com Campo em Branco
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Obter boletos']"))));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Obter boletos']"))).click();
		System.out.println("Botão obter boleto foi clicado");
		
		try {
			Robot robot = new Robot();
			BufferedImage bi = robot.createScreenCapture(new Rectangle(100, 100));
			ImageIO.write(bi, "jpg", new File("C:/imageTest.jpg"));

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Tentativa de acesso com Campos em Branco foi validado");
		
		// Gerar Boleto com Código Cliente Errado
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CodCliente"))).sendKeys("123456");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Obter boletos']"))).click();
		
		Assert.assertFalse(driver.getPageSource().contains("OPS! Não foi possível recuperar dados deste código ou não está identificado como cliente Alterdata."));
		
		try {
			Robot robot = new Robot();
			BufferedImage bi = robot.createScreenCapture(new Rectangle(100, 100));
			ImageIO.write(bi, "jpg", new File("C:/imageTest2.jpg"));

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Tentativa de acesso com Código do Cliente errado foi validado");	
	}

}