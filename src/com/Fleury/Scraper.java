package com.Fleury;

import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Scraper {
	public static void main(String[] args) throws IOException, InterruptedException {
		FileWriter writer = new FileWriter("FleuryFile.txt");
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		String baseURL = "https://promenadefleury.com/";
		driver.get(baseURL);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement element;

		int num1 = driver.findElements(By.xpath("//*[@id=\"main\"]/div[3]/div/div")).size();
		for (int row = 1; row <= num1; row++) {
			int num2 = driver.findElements(By.xpath("//*[@id=\"main\"]/div[3]/div/div[" + row + "]/a")).size();
			for (int col = 1; col <= num2; col++) {
				try {
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"popmake-1881\"]/button")))
							.click();
				} catch (Exception e) {

				}

				element = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//*[@id=\"main\"]/div[3]/div/div[" + row + "]/a[" + col + "]")));
				String category = element.getText();
				System.out.println(category);
				element.click();

				int num3 = driver
						.findElements(By.xpath("//*[@class=\"tab-pane entreprises row-eq-height active\"]/div")).size();
				System.out.println("num3: " + num3);
				for (int i = 1; i <= num3; i++) {
					wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//*[@class=\"tab-pane entreprises row-eq-height active\"]/div[" + i + "]/a")))
							.click();
					String name = driver.getTitle();
					System.out.println(name);

					String contact = "";
					for (int j = 1; j <= 3; j++) {
						element = wait.until(ExpectedConditions.elementToBeClickable(
								By.xpath("//*[@id=\"main\"]/div[1]/div[2]/div/div[3]/div[2]/div[" + j + "]")));
						contact += element.getText() + "\n";
					}

					int num4 = driver.findElements(By.xpath("//*[@id=\"main\"]/div[1]/div[2]/div/div[3]/div[2]/a"))
							.size();
					for (int j = 1; j <= num4; j++) {
						if (num4 != 1) {
							if (j == 2) {
								contact += "email:";
							}
							element = driver.findElement(
									By.xpath("//*[@id=\"main\"]/div[1]/div[2]/div/div[3]/div[2]/a[\" + j + \"]"));
						} else {
							element = driver
									.findElement(By.xpath("//*[@id=\"main\"]/div[1]/div[2]/div/div[3]/div[2]/a"));
						}
						contact += element.getText() + "\n";
					}
					try {
						element = wait.until(ExpectedConditions.elementToBeClickable(
								By.xpath("//*[@id=\"main\"]/div[1]/div[2]/div/div[3]/div[3]/div/ul/li/a")));
						contact += "facebook:" + element.getAttribute("href");
					} catch (Exception e) {
						System.out.println("skip");
					}
					System.out.println(contact);
					System.out.println("--------------");
					Split.print(name, category, contact, writer);
					driver.navigate().back();
				}
				driver.navigate().back();
			}

		}
		writer.close();
	}
}
