package ru.th;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTests {

    /* тест на поисковую строку */
    @Test
    public void mainPage() {
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

    /* тест на переход по ссылке театра*/
    @Test
    public void theater() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080");
        assertEquals("Главная", driver.getTitle());

        WebElement cont = driver.findElement(By.id("container_1"));
        cont.findElement(By.id("th_1")).click();
        assertEquals("Театр на Таганке", driver.getTitle());

        driver.quit();
    }

    /* тест на переход по ссылке пьесы*/
    @Test
    public void play() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080");
        assertEquals("Главная", driver.getTitle());

        WebElement cont = driver.findElement(By.id("container_1"));
        cont.findElement(By.id("play_1")).click();
        assertEquals("Вишневый сад", driver.getTitle());

        driver.quit();
    }

    /* заказ билета */
    @Test
    public void order() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080");
        assertEquals("Главная", driver.getTitle());

        WebElement cont = driver.findElement(By.id("container_1"));
        cont.findElement(By.id("perf_1")).click();
        assertEquals("Вишневый сад", driver.getTitle());

        driver.findElement(By.linkText("купить место")).click();
        assertEquals("Оплата", driver.getTitle());

        WebDriver driver_looser = new ChromeDriver();
        driver_looser.get(driver.getCurrentUrl());

        driver.findElement(By.id("submit_it")).click();
        assertEquals("успех", driver.getTitle());

        driver_looser.findElement(By.id("submit_it")).click();
        assertEquals("ошибка", driver_looser.getTitle());

        driver.quit();
    }

    /* Войти экспертом и создать пьесу */
    @Test
    public void expert() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/expert/");
        assertEquals("Please sign in", driver.getTitle());

        WebElement cont = driver.findElement(By.id("username"));
        cont.sendKeys("expert");
        cont = driver.findElement(By.id("password"));
        cont.sendKeys("expert");

        driver.findElement(By.cssSelector("button")).click();
        assertEquals("Действие", driver.getTitle());

        driver.findElement(By.linkText("добавить пьесу")).click();
        assertEquals("Пьеса", driver.getTitle());

        driver.findElement(By.linkText("Антон Павлович Чехов")).click();
        assertEquals("Пьеса", driver.getTitle());

        //сначала не задал название пьесы, а так нельзя
        driver.findElement(By.id("submit_it")).click();
        assertEquals("ошибка", driver.getTitle());
        driver.findElement(By.linkText("Попробовать снова")).click();

        cont = driver.findElement(By.id("name"));
        cont.sendKeys("smth");
        driver.findElement(By.id("submit_it")).click();
        assertEquals("успех", driver.getTitle());

        driver.findElement(By.id("checkbox_1")).click();
        driver.findElement(By.id("submit_it")).click();
        assertEquals("успех2", driver.getTitle());

        driver.findElement(By.linkText("Выход")).click();
        assertEquals("Действие", driver.getTitle());



        /* Создать выступление по новой пьесе */

        driver.quit();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/contentmaker/");
        assertEquals("Please sign in", driver.getTitle());

        cont = driver.findElement(By.id("username"));
        cont.sendKeys("contentmaker");
        cont = driver.findElement(By.id("password"));
        cont.sendKeys("contentmaker");

        driver.findElement(By.cssSelector("button")).click();
        assertEquals("Холл", driver.getTitle());

        driver.findElement(By.linkText("1 Театр на Таганке")).click();
        assertEquals("Создать", driver.getTitle());

        cont = driver.findElement(By.id("start_time"));
        cont.sendKeys("2000-01-01 00:00:00");
        cont = driver.findElement(By.id("end_time"));
        cont.sendKeys("2000-01-01 00:05:00");
        cont = driver.findElement(By.id("submit_4"));

        cont.click();
        assertEquals("успех", driver.getTitle());
        driver.findElement(By.linkText("Выход")).click();
        assertEquals("Холл", driver.getTitle());



        /* И проверить, что представление создалось */

        driver.get("http://localhost:8080");
        assertEquals("Главная", driver.getTitle());

        WebElement line = driver.findElement(By.id("line_find"));
        line.sendKeys("smth");
        WebElement btn = driver.findElement(By.id("button_find"));
        btn.click();

        List<WebElement> lst = driver.findElements(By.tagName("div"));
        assertEquals(1, lst.size());

        driver.quit();
    }


}