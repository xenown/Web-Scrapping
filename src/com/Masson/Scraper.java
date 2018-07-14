package com.Masson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Scraper {
	public static void main(String[] args) throws IOException, InterruptedException {
		FileWriter writer = new FileWriter("MassonFile.txt");

		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		String baseURL = "http://www.promenademasson.com/en/";
		driver.get(baseURL);
		WebDriverWait wait = new WebDriverWait(driver, 5);

		int num1 = driver.findElements(By.xpath("//*[@id=\"post-8\"]/header/div[3]/a")).size();
		System.out.println(num1);
		for (int i = 1; i <= num1; i++) {
			boolean extra = false;
			WebElement element = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"post-8\"]/header/div[3]/a[" + i + "]")));
			WebElement element2 = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"post-8\"]/header/div[3]/a[" + i + "]/span[2]")));
			String category = element2.getText();
			System.out.println(category);
			element.click();

			JavascriptExecutor jsk = (JavascriptExecutor) driver;
			while (true) {
				try {
					jsk.executeScript("window.scrollBy(0,5000)", "");
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/div[1]/a")))
							.click();
					extra = true;
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					System.out.println("interrupted");
				} catch (Exception e) {
					jsk.executeScript("window.scrollTo(0,0)", "");
					break;
				}
			}

			int num2 = driver.findElements(By.xpath("//*[@id=\"main\"]/div/div/div[1]/div/a")).size();
			System.out.println(num2);
			Boolean first = true;
			for (int j = 1; j <= num2; j++) {
				if (first == false && extra == true) {
					Thread.sleep(3000);
					wait.until(
							ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/div[1]/a[1]")))
							.click();
					Thread.sleep(8000);
				}
				wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"main\"]/div/div/div[1]/div/a[" + j + "]"))).click();
				element = wait.until(ExpectedConditions.elementToBeClickable(By.className("entry-title")));
				String name = element.getText();
				System.out.println(name);

				String contact = "";

				contact += wait
						.until(ExpectedConditions.elementToBeClickable(
								By.xpath("//*[@id=\"listing_sidebar_map-2\"]/div[2]/address/div/span[1]")))
						.getText() + ", ";
				contact += wait
						.until(ExpectedConditions.elementToBeClickable(
								By.xpath("//*[@id=\"listing_sidebar_map-2\"]/div[2]/address/div/span[2]")))
						.getText() + "\n";

				List<WebElement> elements = driver.findElements(By.className("listing-contact"));
				int num3 = elements.size();
				for (int k = 0; k < num3; k++) {
					if (elements.get(k).getAttribute("class").contains("facebook")) {
						contact += "facebook:";
					} else if (elements.get(k).getAttribute("class").contains("fax")) {
						contact += "Fax:";
					}
					contact += elements.get(k).getAttribute("href") + "\n";
				}
				System.out.println(contact);
				System.out.println("----------------");
				Split.print(name, category, contact, writer);
				first = false;
				driver.navigate().back();
			}
			driver.navigate().back();
		}
		writer.close();
		System.out.println("FINISHED!");
	}
}

