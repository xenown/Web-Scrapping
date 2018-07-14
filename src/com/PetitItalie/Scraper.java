package com.PetitItalie;

import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Scraper {
	static String category;
	static ChromeDriver driver;
	static WebDriverWait wait;
	static WebElement element;
	static FileWriter writer;

	public static void main(String[] args) throws IOException, InterruptedException {
		writer = new FileWriter("PetitItalie.txt");
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		driver = new ChromeDriver();
		String baseURL = "https://www.petiteitalie.com/en/stores";
		driver.get(baseURL);
		wait = new WebDriverWait(driver, 1);
		Thread.sleep(1000);
		int tabs = driver.findElements(By.xpath("//*[@id=\"page\"]/div/div[3]/div[1]/div/nav[1]/ul/li")).size();
		for (int i = 1; i <= tabs; i++) {
			element = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"page\"]/div/div[3]/div[1]/div/nav[1]/ul/li[" + i + "]")));
			category = element.getText();
			System.out.println("category: " + category);
			element.click();
			Thread.sleep(2000);
			int pages = driver.findElements(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/nav/center/ul/li"))
					.size();
			System.out.println(pages);
			if (pages != 0) {
				wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/nav/center/ul/li[1]"))).click();
				Thread.sleep(1000);
				for (int j = 2; j <= pages + 1; j++) {
					scan();
					Thread.sleep(500);
					if (j != pages + 1) {
						wait.until(ExpectedConditions.elementToBeClickable(
								By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/nav/center/ul/li[" + j + "]")))
								.click();
						Thread.sleep(1000);
					}
				}
			} else {
				scan();
			}
			JavascriptExecutor jsk = (JavascriptExecutor) driver;
			jsk.executeScript("window.scrollTo(0,0)", "");
		}
		writer.close();
	}

	public static void scan() throws IOException {
		int elements = driver.findElements(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li")).size();
		System.out.println(elements);
		for (int k = 1; k <= elements; k++) {
			System.out.println(k);
			element = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li[" + k
							+ "]/div/article/div[2]/div[2]/div[1]/div/h2")));

			String name = element.getText();
			System.out.println("name: " + name);
			int locationInfo = driver.findElements(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li[" + k
					+ "]/div/article/div[2]/div[2]/div[1]/div/p")).size();
			String address = "";
			String website = "";
			if (locationInfo == 1) {
				element = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li[" + k
								+ "]/div/article/div[2]/div[2]/div[1]/div/p")));
				address = element.getText();
				System.out.println("add: " + address);
			} else {
				element = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li[" + k
								+ "]/div/article/div[2]/div[2]/div[1]/div/p[1]")));
				address = element.getText();
				System.out.println("add: " + address);
				element = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li[" + k
								+ "]/div/article/div[2]/div[2]/div[1]/div/p[2]/a")));
				website = element.getAttribute("href");
				System.out.println("web: " + website);
			}
			String phone = "";
			try {
				element = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li[" + k
								+ "]/div/article/div[2]/div[2]/div[2]/div/p")));
				phone = element.getText();
				System.out.println(phone);
			} catch (Exception e) {
				System.out.println("No phone found");
			}
			String mail = "";
			try {
				element = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li[" + k
								+ "]/div/article/div[2]/div[2]/div[2]/div/a")));
				mail = element.getText();
				System.out.println(mail);
			} catch (Exception e) {
				System.out.println("No mail found");
			}
			String contact = "";
			try {
				wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li[" + k
								+ "]/div/article/div[2]/div[3]/button")))
						.click();

				int contacts = driver.findElements(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li[" + k
						+ "]/div/article/div[3]/div/aside/ul/li")).size();
				if (contacts == 1) {
					element = wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li[" + k
									+ "]/div/article/div[3]/div/aside/ul/li/a")));
					contact += element.getAttribute("href") + "\n";
				} else {
					for (int l = 1; l <= contacts; l++) {
						element = wait.until(ExpectedConditions
								.elementToBeClickable(By.xpath("//*[@id=\"page\"]/div/div[3]/div[2]/div/ul/div/li[" + k
										+ "]/div/article/div[3]/div/aside/ul/li[" + l + "]/a")));
						contact += element.getAttribute("href") + "\n";
					}
				}
				System.out.println(contact);
			} catch (Exception e) {
				System.out.println("No contact found");
			}
			Split.print(name, category, address, website, phone, mail, contact, writer);
			System.out.println("----------");
		}
	}
}
