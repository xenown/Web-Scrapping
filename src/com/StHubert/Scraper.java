package com.StHubert;

import java.io.FileWriter;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Scraper {
	private static WebDriver driver;
	private static String baseUrl;

	public static void main(String[] args) throws Exception {
		String csvFile = "StHubert.txt";
		FileWriter writer = new FileWriter(csvFile);

		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "http://maplaza.ca/en/directory/directory-category/";
		driver.get(baseUrl);
		WebDriverWait wait = new WebDriverWait(Scraper.driver, 5);

		int num1 = driver.findElements(By.xpath("//div[@id='repertoire']/div")).size();
		System.out.println("num1: " + num1);
		for (int i = 2; i <= num1; i++) {
			int num2 = driver.findElements(By.xpath("//*[@id=\"repertoire\"]/div[" + i + "]/a")).size();
			System.out.println("num2: " + num2);
			for (int j = 1; j <= num2; j++) {
				WebElement element = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"repertoire\"]/div[" + i + "]/a[" + j + "]")));
				String type = element.getAttribute("text");
				System.out.println("Category: " + type);
				element.click();
				int num3 = driver.findElements(By.xpath("//div[@id='repertoire']/div[2]/div")).size();
				System.out.println("num3: " + num3);
				String path = "//*[@id=\"repertoire\"]/div[2]/div[";
				if (num3 == 0) {
					JavascriptExecutor jsx = (JavascriptExecutor) driver;
					int previous;
					do {
						previous = num3;
						jsx.executeScript("window.scrollBy(0,5000)", "");
						Thread.sleep(2500);
						num3 = driver.findElements(By.xpath("//div[@id='repertoire']/div/div[2]/div")).size();
					} while (num3 != previous);
					jsx.executeScript("window.scrollTo(0,0)", "");

					System.out.println("num3: " + num3);
					path = "//*[@id=\"repertoire\"]/div/div[2]/div[";
				}
				for (int k = 1; k <= num3; k++) {
					try {
						driver.findElement(By.xpath(path + k + "]/div/div"));
						wait.until(ExpectedConditions.elementToBeClickable(By.xpath(path + k + "]/div"))).click();
						WebElement element2;
						try {
							element = wait.until(ExpectedConditions.elementToBeClickable(
									By.xpath("//*[@id=\"detailledInfo\"]/div/div/div[1]/div[2]/div[2]/h2")));
							element2 = wait.until(ExpectedConditions.elementToBeClickable(
									By.xpath("//*[@id=\"detailledInfo\"]/div/div/div[1]/div[2]/div[2]/p[2]")));
						} catch (Exception e) {
							element = wait.until(ExpectedConditions.elementToBeClickable(
									By.xpath("//*[@id=\"detailledInfo\"]/div/div/div[1]/div[2]/div[1]/h2")));
							element2 = wait.until(ExpectedConditions.elementToBeClickable(
									By.xpath("//*[@id=\"detailledInfo\"]/div/div/div[1]/div[2]/div[1]/p[2]")));
						}
						String name = element.getText();
						System.out.println("Name: " + name);
						String contact = element2.getText();
						System.out.println("Contact: " + contact);
						System.out.println("------");
						Split.print(name, type, contact, writer);
					} catch (Exception e) {
						System.out.println("skip");
					}
				}
				driver.navigate().back();
			}
		}
		writer.flush();
		writer.close();
		driver.close();
		System.out.println("FINISHED!");
		System.exit(0);
	}
}
