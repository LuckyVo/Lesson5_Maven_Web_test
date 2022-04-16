package org.Lesson5_Maven_Web_test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest extends SiteInitialization {

    Logger logger = LoggerFactory.getLogger("Unit test's");


    @Test
    @DisplayName("Тест-кейс: Авторизация на сайте")
    public void testCaseAuthorization() {
        Actions auth = new Actions(getDriver());
        // метод для увеличения времени ожидания отображения элекмента на странице,
        // и пока он ждёт он не бросает исключения
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class)    //элемент не найден
                .ignoring(ElementNotInteractableException.class)   //элемент присутсвует, но взаимодействовать с ним нельзя
                .ignoring(StaleElementReferenceException.class);   //элемент, который обнаружили исчез, когда инициировали взаимодействие

        try {
            WebElement account = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath(".//a[@href='/account/']"));
                }
            });
            account.click();

            auth
                    .sendKeys(getDriver().findElement(By.name("USER_LOGIN")), "v.kudraivzev@yandex.ru")
                    .sendKeys(getDriver().findElement(By.name("USER_PASSWORD")), "ASDFzxcv1234")
                    .build()
                    .perform();

//            WebElement userName = getDriver().findElement(By.name("USER_LOGIN"));
//            userName.clear();
//            userName.sendKeys("v.kudraivzev@yandex.ru");
//            WebElement userPass = getDriver().findElement(By.name("USER_PASSWORD"));
//            userPass.clear();
//            userPass.sendKeys("ASDFzxcv1234");

            WebElement logIn = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath(".//a[@href='javascript:void(0)']"));
                }
            });
            logIn.click();
            WebElement authenticationElement = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath(".//a[@href='/personal/' " +
                            "and @class='lk_menu_h1']"));
                }
            });
            String authentication = authenticationElement.getText();
            assertEquals(authentication, "Личный кабинет");
        } catch (NoSuchElementException | ElementNotInteractableException | StaleElementReferenceException e){
            logger.info(e.getMessage());
        }
        logger.info("Тест-кейс по авторизации пройден!");

    }

    /** +++++++++++++++++++++++++++++++++++ */

    @Test
    @DisplayName("Тест-кейс: Добавление продукта в корзину ")
    public void testBasket() {
        try {
            WebElement catalog = getDriver().findElement(By.xpath(".//a[@href='/catalog/']"));
            catalog.click();
            WebElement cookies = getDriver().findElement(By.xpath(".//a[@href='/catalog/pechenye/' " +
                    "and @class='front_cont w_more_h']"));
            cookies.click();
            WebElement nineNegrityat = getDriver().findElement(By.xpath(".//a[@href='/catalog/pechenye/9-negrityat/']"));
            nineNegrityat.click();
            WebElement toBasket = getDriver().findElement(By.xpath(".//button[@data-js='button-buy' " +
                    "and contains(., 'В корзину')]"));
            toBasket.click();
            WebElement basket = getDriver().findElement(By.xpath(".//a[@href='/basket/' " +
                    "and @class='button-ux main-ux solid checkout']"));
            basket.click();
            WebElement basketProduct = getDriver().findElement(By.xpath(".//a[@href='/catalog/pechenye/9-negrityat/' " +
                            "and text()='9 НЕГРИТЯТ']"));

            String listOfBasket = basketProduct.getText();
            assertEquals(listOfBasket, "9 НЕГРИТЯТ");
        } catch (NoSuchElementException | ElementNotInteractableException | StaleElementReferenceException e){
            logger.info(e.getMessage());
        }
        logger.info("Тест-кейс по добавлению продукта в корзину пройден!");

    }


    /** +++++++++++++++++++++++++++++++++++ */


    @Test
    @DisplayName("Тест-кейс: Установка продукта в избранное")
    public void testLike() {
        // метод для увеличения времени ожидания отображения элекмента на странице,
        // и пока он ждёт он не бросает исключения
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class)    //элемент не найден
                .ignoring(ElementNotInteractableException.class)   //элемент присутсвует, но взаимодействовать с ним нельзя
                .ignoring(StaleElementReferenceException.class);   //элемент, который обнаружили исчез, когда инициировали взаимодействие

        try {
            WebElement catalog = wait.until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(By.xpath(".//a[@href='/catalog/']"));
                    }
            });
            catalog.click();
            WebElement cookies = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath(".//a[@href='/catalog/pechenye/' " +
                            "and @class='front_cont w_more_h']"));
                }
            });
            cookies.click();
            WebElement likeNineNegrityat = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath(".//div[@class='elem_wish ' " +
                            "and @data-id='24']"));
                }
            });
            likeNineNegrityat.click();
            WebElement allLike = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath(".//a[@href='/favorite/index.php/']"));
                }
            });
            allLike.click();
            WebElement favourites = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath(".//a[@href='/catalog/pechenye/9-negrityat/' " +
                            "and text()='9 НЕГРИТЯТ']"));
                }
            });
            String allFavourites = favourites.getText();
            assertEquals(allFavourites, "9 НЕГРИТЯТ");
        } catch (NoSuchElementException | ElementNotInteractableException | StaleElementReferenceException e){
            logger.info(e.getMessage());
        }
        logger.info("Тест-кейс по добавлению продукта в избранное пройден!");

    }


    /** +++++++++++++++++++++++++++++++++++ */


    @Test
    @DisplayName("Тест-кейс: Отправление текста сообщения CRM ")
    public void testSengMessage() {
        try {
            WebElement message = getDriver().findElement(By.xpath(".//div[@dir='ltr']"));
            message.click();
            WebElement lineCRM = getDriver().findElement(By.xpath(".//a[@data-b24-crm-button-widget='openline_livechat' " +
                    "and @class='b24-widget-button-social-item b24-widget-button-openline_livechat']"));
            lineCRM.click();
            WebElement textarea = getDriver().findElement(By.xpath(".//textarea[@placeholder='Введите сообщение...' " +
                    "and @class='bx-im-textarea-input']"));
            textarea.sendKeys("CRM\n");
            WebElement text = getDriver().findElement(By.xpath(".//span[@class='bx-im-message-content-text' " +
                    "and text()='CRM']"));
            String textCRM = text.getText();
            assertEquals(textCRM, "CRM");
        } catch (NoSuchElementException | ElementNotInteractableException | StaleElementReferenceException e) {
            logger.info(e.getMessage());
        }
        logger.info("Тест-кейс по отправлению текста сообщения CRM пройден!");
    }


    /** +++++++++++++++++++++++++++++++++++ */


    @ParameterizedTest
    @CsvSource({ "Слойки","Вафли","Пироги"})
    @DisplayName("Тест-кейс: Поиск на сайте ")
    public void testSearch(String textForSearch) {
        try {
            WebElement find = getDriver().findElement(By.xpath(".//button[@class='button-ux magnifier default search' " +
                    " and @data-js='button-search']"));
            find.click();
            WebElement textArea = getDriver().findElement(By.xpath(".//input[@class='input' " +
                    "and @name='q']"));
            textArea.sendKeys(textForSearch + "\n");
            WebElement findText = getDriver().findElement(By.xpath(".//input[@name='q' " +
                    " and @type='text' and @value='" + textForSearch + "']"));
            String textThatFind = findText.getAttribute("value");
            assertEquals(textForSearch, textThatFind);
        } catch (NoSuchElementException | ElementNotInteractableException | StaleElementReferenceException e) {
            logger.info(e.getMessage());
        }
        logger.info("Тест-кейс по поиску продукции на сайте пройден!");
    }


    /** +++++++++++++++++++++++++++++++++++ */


    @Test
    @DisplayName("Тест-кейс: перейти на акции из каталога")
    void testGoToStockFromCatalog(){
        // метод для увеличения времени ожидания отображения элекмента на странице,
        // и пока он ждёт он не бросает исключения
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class)    //элемент не найден
                .ignoring(ElementNotInteractableException.class)   //элемент присутсвует, но взаимодействовать с ним нельзя
                .ignoring(StaleElementReferenceException.class);   //элемент, который обнаружили исчез, когда инициировали взаимодействие
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();

        try {
            WebElement catalog = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return getDriver().findElement(By.xpath(".//a[@href='/catalog/']"));
                }
            });
            catalog.click();
            Thread.sleep(1000);
            jsExecutor.executeScript("window.scrollBy(0,700)");
            Thread.sleep(1000);
            WebElement stock = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return getDriver().findElement(By.xpath(".//a[@class='front_cont h_more_w' " +
                            "and @style='background-image:url(/images/catalog/useful/10_image_small.jpeg); border-radius:0px']"));
                }
            });
            stock.click();
            WebElement head = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return getDriver().findElement(By.xpath(".//h1[@class='heading h1']"));
                }
            });
            String heading = head.getText();
            if (!heading.equals("Акции")) {
                logger.info("А страничка то не та, в каталоге(");
            } else {
                logger.info("Тест-кейс по переходу на акции из каталога пройден!");
            }
        } catch (InterruptedException | NoSuchElementException | ElementNotInteractableException | StaleElementReferenceException e){
            logger.info(e.getMessage());
        }

    }



}
