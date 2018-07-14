package com.VieuxQuebec;

import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
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
		writer = new FileWriter("VieuxQuebec.txt");
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		driver = new ChromeDriver();
		String baseURL = "https://www.sdcvieuxquebec.com/en/parcourir-la-sdc";
		driver.get(baseURL);
		wait = new WebDriverWait(driver, 1);
		driver.manage().window().maximize();

		int categories = driver.findElements(By.xpath("//*[@id=\"shops\"]/div[1]/div[1]/div/div/div[1]/ul/li")).size();
		for (int i = 1; i <= categories; i++) {
			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"shops\"]/div[1]/div[1]/div/div/div[2]/button"))).click();
			element = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//*[@id=\"shops\"]/div[1]/div[1]/div/div/div[1]/ul/li[" + i + "]/button")));
			category = element.getText();
			System.out.println("CATEGORY: " + category);
			element.click();

			Thread.sleep(500);

			int elements = driver.findElements(By.xpath("//*[@id=\"shops\"]/div[1]/div[2]/div/div/div/ul/li")).size();
			for (int j = 1; j <= elements; j++) {
				wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//*[@id=\"shops\"]/div[1]/div[2]/div/div/div/ul/li[" + j + "]"))).click();
				element = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"shops\"]/article/div/header/div/h1")));
				String name = element.getText();
				System.out.println(name);
				element = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"shops\"]/article/div/header/div/address")));
				String address = element.getText();
				System.out.println(address);
				int rows = driver.findElements(By.xpath("//*[@id=\"shops\"]/article/div/header/div/p/span")).size();
				String tel = "";
				try {
					element = wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//*[@id=\"shops\"]/article/div/header/div/p/span[1]/a")));
					tel = element.getText();
					System.out.println(tel);
				} catch (Exception e) {
					System.out.println("No tel found");
				}
				String website = "";
				try {
					element = wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//*[@id=\"shops\"]/article/div/header/div/p/span[" + rows + "]/a")));
					website = element.getAttribute("href");
					System.out.println(website);
				} catch (Exception e) {
					System.out.println("No website found");
				}
				String contact = "";
				int socialmeds = driver.findElements(By.xpath("//*[@id=\"shops\"]/article/div/header/div/ul/li"))
						.size();
				for (int k = 1; k <= socialmeds; k++) {
					try {
						element = wait.until(ExpectedConditions.elementToBeClickable(
								By.xpath("//*[@id=\"shops\"]/article/div/header/div/ul/li[" + k + "]/a")));
						contact += element.getAttribute("href") + "\n";
					} catch (Exception e) {
						System.out.println("skip");
					}
				}
				System.out.println(contact);
				System.out.println("--------------");
				Split.print(name, category, address, website, tel, contact, writer);
			}
		}
		System.out.println("FINISHED!");
		writer.close();
	}
}
