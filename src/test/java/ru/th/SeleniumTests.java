package ru.th;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTests {

    @Test
    public void mainPage(){
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080");
        assertEquals("Главная", driver.getTitle());

        WebElement line = driver.findElement(By.id("line_find"));
        line.sendKeys("сад");
        WebElement btn = driver.findElement(By.id("button_find"));
        btn.click();

        List<WebElement> lst = driver.findElements(By.tagName("div"));
        //System.out.println(lst.size());
        assertEquals(4, lst.size());

        driver.quit();
    }

}