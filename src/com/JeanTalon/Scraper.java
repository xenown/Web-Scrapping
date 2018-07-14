package com.JeanTalon;

import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Scraper {
	public static void main(String[] args) throws IOException, InterruptedException {
		FileWriter writer = new FileWriter("JTalonFile.txt");
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		String baseURL = "http://jeantalonest.com/en/commerces_professionnals/";
		driver.get(baseURL);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement element;

		int num1 = driver.findElements(By.xpath("//*[@id=\"searchwidget\"]/ul/li[1]/ul/li")).size();
		for (int i = 1; i <= num1; i++) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"searchwidget\"]/ul/li[1]/a")))
					.click();

			element = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"searchwidget\"]/ul/li[1]/ul/li[" + i + "]/a")));
			String category = element.getText();
			System.out.println("Category: " + category);
			element.click();
			Thread.sleep(3000);
			int num2 = driver.findElements(By.xpath("//*[@id=\"contentholder\"]/div/ul/li")).size();
			System.out.println("num2: " + num2);
			if (num2 != 0) {
				while (true) {
					int num3 = driver.findElements(By.xpath("//*[@id=\"contentholder\"]/ul")).size();
					System.out.println("num3: " + num3);

					for (int k = 1; k <= num3; k++) {
						element = wait.until(ExpectedConditions
								.elementToBeClickable(By.xpath("//*[@id=\"contentholder\"]/ul[" + k + "]/h2/a")));
						String name = element.getText();
						System.out.println(name);
						int num4 = driver.findElements(By.xpath("//*[@id=\"contentholder\"]/ul[" + k + "]/li[2]/p"))
								.size();
						String contact = "";
						for (int l = 3; l <= num4; l++) {
							element = driver.findElement(
									By.xpath("//*[@id=\"contentholder\"]/ul[" + k + "]/li[2]/p[" + l + "]"));
							contact += element.getText() + "\n";
						}
						System.out.println(contact);
						System.out.println("----------");
						Split.print(name, category, contact, writer);
					}

					try {
						wait.until(ExpectedConditions.elementToBeClickable(By.linkText(">"))).click();
						Thread.sleep(3000);
					} catch (Exception e) {
						break;
					}
				}
			} else {
				int num3 = driver.findElements(By.xpath("//*[@id=\"contentholder\"]/ul")).size();
				System.out.println(num3);

				for (int k = 1; k <= num3; k++) {
					element = wait.until(ExpectedConditions
							.elementToBeClickable(By.xpath("//*[@id=\"contentholder\"]/ul[" + k + "]/h2/a")));
					String name = element.getText();
					System.out.println(name);
					int num4 = driver.findElements(By.xpath("//*[@id=\"contentholder\"]/ul[" + k + "]/li[2]/p")).size();
					String contact = "";
					for (int l = 3; l <= num4; l++) {
						element = driver
								.findElement(By.xpath("//*[@id=\"contentholder\"]/ul[" + k + "]/li[2]/p[" + l + "]"));
						contact += element.getText() + "\n";
					}
					System.out.println(contact);
					System.out.println("----------");
					Split.print(name, category, contact, writer);
				}
			}
		}
		writer.close();
	}
}
