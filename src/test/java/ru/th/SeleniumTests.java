package ru.th;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.th.DAO.FreeSeatsDAO;
import ru.th.models.FreeSeats;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTests {

    /* тест на поисковую строку */
    @Test
    public void mainPage() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080");
        assertEquals("Главная", driver.getTitle());

        driver.findElement(By.id("line_find")).sendKeys("сад");
        driver.findElement(By.id("submit_1")).click();
        assertEquals("Главная", driver.getTitle());
        List<WebElement> lst = driver.findElements(By.tagName("div"));
        assertEquals(16, lst.size());

        driver.findElement(By.id("drama")).click();
        driver.findElement(By.id("submit_2")).click();
        lst = driver.findElements(By.tagName("div"));
        assertEquals(18, lst.size());

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
        driver_looser.quit();
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
        WebElement btn = driver.findElement(By.id("submit_1"));
        btn.click();

        assertEquals("Главная", driver.getTitle());
        List<WebElement> lst = driver.findElements(By.tagName("div"));
        assertEquals(2, lst.size());

        driver.quit();
    }

    /* Добавим актера, отпедактируем и удалим */
    @Test
    public void actors() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/expert/");
        assertEquals("Please sign in", driver.getTitle());

        WebElement cont = driver.findElement(By.id("username"));
        cont.sendKeys("expert");
        cont = driver.findElement(By.id("password"));
        cont.sendKeys("expert");

        driver.findElement(By.cssSelector("button")).click();
        assertEquals("Действие", driver.getTitle());

        driver.findElement(By.linkText("редактировать актерский состав")).click();
        assertEquals("Актеры", driver.getTitle());

        driver.findElement(By.linkText("Добавить нового актера/режиссера")).click();
        assertEquals("Добавить", driver.getTitle());

        driver.findElement(By.id("name")).sendKeys("NAME_N");
        driver.findElement(By.id("submit_it")).click();
        assertEquals("Актеры", driver.getTitle());

        driver.findElement(By.linkText("Редактировать NAME_N")).click();
        assertEquals("Редактировать", driver.getTitle());

        driver.findElement(By.id("name")).sendKeys("M");
        driver.findElement(By.id("submit_it")).click();
        assertEquals("Актеры", driver.getTitle());

        driver.findElement(By.linkText("Удалить из базы данных NAME_NM")).click();
        assertEquals("Актеры", driver.getTitle());
        driver.findElement(By.id("answer_3"));

        driver.findElement(By.linkText("Удалить из базы данных Абрамов Абрам Абрамович")).click();
        assertEquals("Актеры", driver.getTitle());
        driver.findElement(By.id("answer_3"));

        driver.findElement(By.linkText("Удалить из базы данных Горький Максим")).click();
        assertEquals("Актеры", driver.getTitle());
        driver.findElement(By.id("answer_3"));

        driver.quit();
    }

    /* Массово заказать битеты */
    @Test
    public void tickets() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/cashier/");
        assertEquals("Please sign in", driver.getTitle());

        WebElement cont = driver.findElement(By.id("username"));
        cont.sendKeys("cashier");
        cont = driver.findElement(By.id("password"));
        cont.sendKeys("cashier");

        driver.findElement(By.cssSelector("button")).click();
        assertEquals("Представление", driver.getTitle());

        FreeSeatsDAO dao = new FreeSeatsDAO();
        List<FreeSeats> old = dao.findByPerformance(2);

        driver.findElement(By.id("perf_2")).click();
        assertEquals("Билеты", driver.getTitle());

        driver.findElement(By.id("checkbox_34")).click();
        driver.findElement(By.id("checkbox_35")).click();
        driver.findElement(By.id("checkbox_36")).click();

        driver.findElement(By.id("submit_it")).click();
        assertEquals("Представление", driver.getTitle());
        driver.findElement(By.id("answer_1"));

        List<FreeSeats> new_one = dao.findByPerformance(2);
        assertEquals(3, old.size() - new_one.size());

        driver.quit();
    }


    }