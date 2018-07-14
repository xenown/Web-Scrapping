package com.StRoch;

import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Scraper {
	public static FileWriter writer;
	public static ChromeDriver driver;
	public static WebDriverWait wait;
	public static WebElement element;
	static String category;

	public static void main(String[] args) throws IOException, InterruptedException {
		writer = new FileWriter("StRoch.txt");
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		driver = new ChromeDriver();
		String baseURL = "http://www.stroch.com/en/explore";
		driver.get(baseURL);
		wait = new WebDriverWait(driver, 2);
		JavascriptExecutor jsk = (JavascriptExecutor) driver;
		String last = "";
		Thread.sleep(7000);
		for (int i = 1; i <= 12; i++) {

			jsk.executeScript("window.scrollBy(0,500)", "");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"categorie-aucun\"]"))).click();
			jsk.executeScript("window.scrollTo(0,0)", "");
			element = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"carte-interactive-categories\"]/li[" + i + "]/a")));
			category = element.getText();
			System.out.println(category);
			element.click();

			while (true) {
				for (int j = 5; j >= 1; j--) {
					System.out.println(j);
					Thread.sleep(1000);
				}
				try {
					int lastrow = driver.findElements(By.xpath("//*[@id=\"fiche-client\"]/div")).size();
					element = wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//*[@id=\"fiche-client\"]/div[" + lastrow + "]/div[1]")));
					String info = element.getText();

					if (!info.equals(last)) {
						System.out.println(info);
						last = info;
						element = wait.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath("//*[@id=\"fiche-client\"]/div[" + lastrow + "]/div[1]/div")));
						String tel = element.getText();
						System.out.println(tel);
						jsk.executeScript("window.scrollBy(0,500);", "");
						String medias = "";
						int media = driver
								.findElements(By.xpath("//*[@id=\"fiche-client\"]/div[" + lastrow + "]/div[3]/a"))
								.size();
						if (media == 1) {
							element = wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//*[@id=\"fiche-client\"]/div[" + lastrow + "]/div[3]/a")));
							medias = element.getAttribute("href");
						} else {
							for (int k = 1; k <= media; k++) {
								element = wait.until(ExpectedConditions.visibilityOfElementLocated(By
										.xpath("//*[@id=\"fiche-client\"]/div[" + lastrow + "]/div[3]/a[" + k + "]")));
								medias += element.getAttribute("href") + "\n";
							}
						}
						System.out.println(medias);
						Split.print(info, category, medias, tel, writer);
						System.out.println("----------------");
					} else {
						System.out.println("finished page");
						break;
					}
				} catch (Exception e) {
					System.out.println("Not open");
				}
			}
		}

		System.out.println("FINISHED!");
		writer.close();
	}
}
