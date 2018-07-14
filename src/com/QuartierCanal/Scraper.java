package com.QuartierCanal;

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
		writer = new FileWriter("QuartierC.txt");
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		driver = new ChromeDriver();
		String baseURL = "http://www.quartierducanal.com/repertoire.html";
		driver.get(baseURL);
		wait = new WebDriverWait(driver, 5);

		int columns = driver.findElements(By.xpath("//*[@id=\"yoo-zoo\"]/div[2]/div")).size();
		System.out.println("columns: " + columns);
		for (int i = 1; i <= columns; i++) {
			int categories = driver.findElements(By.xpath("//*[@id=\"yoo-zoo\"]/div[2]/div[" + i + "]/div")).size();
			System.out.println("categories: " + categories);
			for (int j = 1; j <= categories; j++) {
				element = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//*[@id=\"yoo-zoo\"]/div[2]/div[" + i + "]/div[" + j + "]/h2/a")));
				category = element.getText();
				System.out.println(category);
				int subcategories = driver
						.findElements(By.xpath("//*[@id=\"yoo-zoo\"]/div[2]/div[" + i + "]/div[" + j + "]/ul/li"))
						.size();
				if (subcategories == 0) {
					wait.until(ExpectedConditions.elementToBeClickable(
							By.xpath("//*[@id=\"yoo-zoo\"]/div[2]/div[" + i + "]/div[" + j + "]/h2/a"))).click();
					scan();
				} else {
					for (int k = 1; k <= subcategories; k++) {
						wait.until(ExpectedConditions.elementToBeClickable(By
								.xpath("//*[@id=\"yoo-zoo\"]/div[2]/div[" + i + "]/div[" + j + "]/ul/li[" + k + "]/a")))
								.click();
						scan();
					}
				}
			}
		}
		System.out.println("FINISHED!");
		writer.close();
	}

	private static void scan() throws IOException {
		while (true) {
			int rows = driver.findElements(By.xpath("//*[@id=\"yoo-zoo\"]/div[3]/div")).size();
			for (int l = 1; l <= rows; l++) {
				try {
					for (int m = 1; m <= 2; m++) {
						wait.until(ExpectedConditions.elementToBeClickable(
								By.xpath("//*[@id=\"yoo-zoo\"]/div[3]/div[" + l + "]/div[" + m + "]/div/h2/a")))
								.click();
						scan2();
					}
				} catch (Exception e) {
					System.out.println("No second element or its on next div");
				}
			}
			try {
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText("> "))).click();
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("No next");
				break;
			}
		}
		driver.navigate().to("http://www.quartierducanal.com/repertoire.html");
	}

	private static void scan2() throws IOException {
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"yoo-zoo\"]/div/div[1]/h1")));
		String name = element.getText();
		System.out.println(name);
		String contact = "";
		int last = driver.findElements(By.xpath("//*[@id=\"yoo-zoo\"]/div/div[1]/div/div[1]/ul/li")).size();
		element = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id=\"yoo-zoo\"]/div/div[1]/div/div[1]/ul/li[1]")));
		contact += element.getText() + "\n";
		element = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id=\"yoo-zoo\"]/div/div[1]/div/div[1]/ul/li[" + last + "]")));
		contact += element.getText() + "\n";
		int e = driver.findElements(By.xpath("//*[@id=\"yoo-zoo\"]/div/div[1]/div/div[2]/ul/li")).size();
		if (e != 1) {
			for (int n = 1; n <= e; n++) {
				element = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"yoo-zoo\"]/div/div[1]/div/div[2]/ul/li[" + n + "]")));
				contact += element.getText() + "\n";
			}
		} else {
			element = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"yoo-zoo\"]/div/div[1]/div/div[2]/ul/li")));
			contact += element.getText() + "\n";
		}
		System.out.println(contact);
		System.out.println("------------");
		Split.print(name, category, contact, writer);
		driver.navigate().back();
	}
}
