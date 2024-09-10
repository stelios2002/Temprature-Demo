package org.example;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.StringEntity;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebScraper {

    public static void main(String[] args) {
        // Set the system property for ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\L1oK3y\\eclipse-workspace\\KairosScraper\\src\\main\\java\\org\\example\\chromedriver.exe");

        // Set up ChromeOptions (optional)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode (no GUI)

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver(options);
        try {
            // Navigate to the website
            driver.get("https://www.okairos.gr/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("q")));
            searchBox.sendKeys("Αθήνα");

            WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit']"));
            submitButton.click();

            WebElement link = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"search-results\"]/ol/li[1]/a")));
            link.click();

            // Wait until the temperature element is visible
            WebElement todayTemperatureElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"city-weather\"]/table/tbody/tr[14]/td[1]/div")));
            String todayTemperature = todayTemperatureElement.getText();

            // Print or use the temperature and dateTime
            todayTemperature = todayTemperature.substring(0,2);
            double dTemp = Double.parseDouble(todayTemperature);
            System.out.println("Temperature: " + todayTemperature);

            // Send POST request to API
            sendTemperatureData(dTemp);
        }
        finally {
            // Close the browser
            driver.quit();
        }
    }

    private static void sendTemperatureData(Double temperature) {
        String apiUrl = "http://localhost:8080/temperature";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(apiUrl);

            // Create JSON payload using org.json.JSONObject
            JSONObject json = new JSONObject();
            json.put("temperature", temperature);

            StringEntity entity = new StringEntity(json.toString());
            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");

            // Execute the request
            HttpResponse response = httpClient.execute(post);
            System.out.println("Response Status: " + response.getStatusLine());
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}