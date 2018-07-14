package com.FleuryOuest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
//Gave up site structure is awful
	public static void main(String[] args) throws IOException, InterruptedException {
		writer = new FileWriter("FleuryOuest.txt");
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		driver = new ChromeDriver();
		String baseURL = "http://www.fleuryouest.com/";
		driver.get(baseURL);
		wait = new WebDriverWait(driver, 1);

		List<WebElement> categories = driver.findElements(By.className("style-is3b3xou"));
		for (int i = 0; i < categories.size(); i++) {
			categories.get(i).findElement(By.tagName("a")).click();
			Thread.sleep(3000);
			String url = driver.getCurrentUrl();
			int index = url.indexOf("m/");
			category = url.substring(index + 2);
			System.out.println("CATEGORY: " + category);

			int boxes = driver.findElements(By.xpath("//*[@id=\"c1t8inlineContent\"]/div")).size();
			System.out.println(boxes);
			for (int j = 1; j <= boxes; j++) {
				try {
					int rows = driver
							.findElements(By.xpath("//*[@id=\"c1t8inlineContent\"]/div[" + j + "]/div[2]/div[3]/h6"))
							.size();
					String info = "";
					if (rows != 1) {
						for (int k = 1; k <= rows; k++) {
							element = wait.until(ExpectedConditions
									.elementToBeClickable(By.xpath("//*[@id=\"c1t8inlineContent\"]/div[" + j
											+ "]/div[2]/div[3]/h6[" + k + "]")));
							info += element.getText() + "\n";
						}
					} else {
						element = wait.until(ExpectedConditions
								.elementToBeClickable(By.xpath("//*[@id=\"c1t8inlineContent\"]/div[" + j
										+ "]/div[2]/div[3]/h6")));
						info += element.getText() + "\n";
					}
					System.out.println(info);
					System.out.println("-----------");
				} catch (Exception e) {
					System.out.println("Not a store");
					break;
				}
			}
			driver.navigate().back();
			Thread.sleep(3000);
		}
		System.out.println("FINISHED!");
		writer.close();
	}
}
