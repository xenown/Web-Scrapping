package com.DistrictCentral;

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
		writer = new FileWriter("DistrictCentral.txt");
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		driver = new ChromeDriver();
		String baseURL = "https://district-central.ca/repertoire/magasinage-et-decouverte/";
		driver.get(baseURL);
		wait = new WebDriverWait(driver, 1);
		int tabs = driver.findElements(By.xpath("/html/body/div[2]/div/div/div[1]/ul/li")).size();
		for (int i = 2; i <= tabs; i++) {
			element = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("/html/body/div[2]/div/div/div[1]/ul/li[" + i + "]")));
			category = element.getText();
			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("/html/body/div[2]/div/div/div[1]/ul/li[" + i + "]/a"))).click();
			int boxes = driver.findElements(By.xpath("/html/body/div[2]/div/div/div[2]/div")).size();
			if (boxes != 1) {
				for (int j = 1; j <= boxes; j++) {
					wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath("/html/body/div[2]/div/div/div[2]/div[" + j + "]/div/div[1]/a"))).click();
					element = wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("/html/body/div[2]/div/div/div[2]/div[1]/h1")));
					String name = element.getText();
					System.out.println(name);

					int rows = driver.findElements(By.xpath("/html/body/div[4]/div/div[2]/div/ul/li")).size();
					String contact = "";
					for (int k = 1; k <= rows; k++) {
						element = wait.until(ExpectedConditions
								.elementToBeClickable(By.xpath("/html/body/div[4]/div/div[2]/div/ul/li[" + k + "]")));
						contact += element.getText() + "\n";
					}
					System.out.println(contact);
					Split.print(name, category, contact, writer);
					System.out.println("------------");
					driver.navigate().back();
				}
			} else {
				wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("/html/body/div[2]/div/div/div[2]/div/div/div[1]/a"))).click();
				element = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("/html/body/div[2]/div/div/div[2]/div[1]/h1")));
				String name = element.getText();
				System.out.println(name);
				int rows = driver.findElements(By.xpath("/html/body/div[4]/div/div[2]/div/ul/li")).size();
				String contact = "";
				for (int k = 1; k <= rows; k++) {
					element = wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("/html/body/div[4]/div/div[2]/div/ul/li[" + k + "]")));
					contact += element.getText() + "\n";
				}
				System.out.println(contact);
				Split.print(name, category, contact, writer);
				System.out.println("----------");
				driver.navigate().back();
			}
			JavascriptExecutor jsk = (JavascriptExecutor) driver;
			jsk.executeScript("window.scrollTo(0,0)", "");
		}
		System.out.println("FINISHED!");
		writer.close();
	}
}
