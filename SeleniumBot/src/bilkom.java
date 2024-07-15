import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.io.FileInputStream;
import org.openqa.selenium.Point;
import org.openqa.selenium.Dimension;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import org.apache.commons.io.FileUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

public class bilkom {
    public static String date_generate() {
        LocalDate new_date = LocalDate.now().plusDays(1);
        while (new_date.getDayOfWeek() == DayOfWeek.SATURDAY || new_date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            new_date = new_date.plusDays(1);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        String output = dtf.format(new_date);
        return output;
    }

    public static void sleep_time(Integer time) {

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Interrupted.");
        }
    }

    private static String getUniqueFilename(String filename, String directory, String extension) {
        String filePath = directory + filename + extension;
        File file = new File(filePath);
        int count = 1;

        while (file.exists()) {
            filePath = directory + filename + "-" + count + extension;
            file = new File(filePath);
            count++;
        }

        return filePath;
    }

    public static void bot(String city_1, String city_2, String carrier_name) {

        String date = date_generate();
        String name = "Test";
        String surname = "Test";
        String mail = "Test@onet.pl";
        String time = "10:30";
        HashMap<String, String> map = new HashMap<String, String>();
        String status = "x";
        List<String> logs = new ArrayList<String>();
        map.put("ARRIVA", "inPp-0");
        map.put("KD", "inPp-1");
        map.put("KML", "inPp-2");
        map.put("KS", "inPp-4");
        map.put("KW", "inPp-5");
        map.put("ŁKA", "inPp-6");
        map.put("INTERCITY", "inPp-7");
        map.put("SKM", "inPp-8");
        map.put("PSA", "inPp-9");
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config.cfg")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = Integer.parseInt(props.getProperty("window.width"));
        int height = Integer.parseInt(props.getProperty("window.height"));
        int posX = Integer.parseInt(props.getProperty("window.position.x"));
        int posY = Integer.parseInt(props.getProperty("window.position.y"));
        LocalDateTime page1_before = LocalDateTime.now();
        logs.add("Inicjalizacja WebDrivera");
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriver.Window window = driver.manage().window();
        window.setSize(new Dimension(width, height));
        window.setPosition(new Point(posX, posY));
        Duration page1_duration = Duration.ofSeconds(0);
        Duration page2_duration = Duration.ofSeconds(0);
        Duration page3_duration = Duration.ofSeconds(0);
        Duration page4_duration = Duration.ofSeconds(0);
        Duration page5_duration = Duration.ofSeconds(0);
        try {

            // Strona 1

            logs.add("Ładowanie strony strony 1");
            driver.get("https://bilkom.pl/");

            logs.add("Zamykanie reklamy");
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//div[@class='modal-body']//img[@class='img-responsive']")));
            driver.findElement(By.xpath("//div[@class='modal-body']//button[@class='close']")).click();

            sleep_time(1500);

            logs.add("Znalezienie elementów strony 1");
            WebElement begging_station = driver.findElement(By.xpath("//*[@id='fromStation']"));
            WebElement end_station = driver.findElement(By.cssSelector("#toStation"));
            WebElement date_element = driver.findElement(By.xpath("//*[@id='date']"));
            WebElement time_element = driver.findElement(By.xpath("//*[@id='time']"));
            logs.add("Wypełnienie elementów strony 1");
            begging_station.click();
            begging_station.clear();
            begging_station.sendKeys(city_1);
            sleep_time(1000);
            begging_station.sendKeys(Keys.ARROW_DOWN);
            begging_station.sendKeys(Keys.ENTER);

            end_station.click();
            end_station.clear();
            end_station.sendKeys(city_2 + Keys.ARROW_DOWN);
            begging_station.sendKeys(city_1);
            sleep_time(1000);

            end_station.sendKeys(Keys.ARROW_DOWN);
            end_station.sendKeys(Keys.ENTER);

            js.executeScript("arguments[0].removeAttribute('readonly',0);", date_element);

            date_element.click();
            date_element.clear();
            date_element.sendKeys(date);

            js.executeScript("arguments[0].removeAttribute('readonly',0);", time_element);

            time_element.click();
            time_element.clear();
            time_element.sendKeys(time);
            try {
                WebElement catpcha = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(By.id("recaptcha-anchor-label")));
                WebElement click = catpcha.findElement(By.xpath("//*[@id=recaptcha-anchor]"));
                if (click.isDisplayed()) {
                    click.click();
                }
            } catch (org.openqa.selenium.TimeoutException e) {
                logs.add("Nie udało się załadować captcha");
            } catch (org.openqa.selenium.NoSuchElementException e1) {
                logs.add("Nie udało się znaleźć captcha");
            }

            driver.findElement(By.id("toggle-carriers-section")).click();
            driver.findElement(By.xpath("//*[@for='inPp-all']")).click();
            driver.findElement(By.xpath("//*[@for='%s']".formatted(map.get(carrier_name.toUpperCase())))).click();

            driver.findElement(By.xpath("//*[@id='search-btn']")).click();
            WebElement ticket_button = driver
                    .findElement(By.xpath("/html/body/section[3]/div/ul[2]/li[1]/div[1]/div[2]/div[2]/form/button"));
            js.executeScript("arguments[0].scrollIntoView(true);", ticket_button);
            js.executeScript("arguments[0].click()", ticket_button);
            LocalDateTime page1_after = LocalDateTime.now();
            page1_duration = Duration.between(page1_before, page1_after);

            LocalDateTime page2_before = LocalDateTime.now();
            // Strona 2
            logs.add("Ładowanie strony 2");
            try {
                WebElement popup = wait
                        .until(ExpectedConditions.visibilityOfElementLocated(By.id("stationChangedMsg")));
                WebElement confirm = popup.findElement(By.xpath(".//button[contains(text(), 'Zatwierdź')]"));
                if (confirm.isDisplayed()) {
                    confirm.click();
                }
            } catch (org.openqa.selenium.TimeoutException e) {
                logs.add("Nie udało się załodować popup");
            } catch (org.openqa.selenium.NoSuchElementException e1) {
                logs.add("Nie udało się znaleźć popup");
            }

            logs.add("Obsługa elementów strony 2");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cart")));
            WebElement confirmation = driver.findElement(By.xpath("//*[@id='go-to-summary']"));
            js.executeScript("arguments[0].scrollIntoView(true);", confirmation);
            js.executeScript("arguments[0].click()", confirmation);
            LocalDateTime page2_after = LocalDateTime.now();
            page2_duration = Duration.between(page2_before, page2_after);
            // Strona 3
            LocalDateTime page3_before = LocalDateTime.now();

            logs.add("Ładowanie strony 3");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#mainPassenger")));
            logs.add("Znalezienie elementów strony 3");
            WebElement passenger_name = driver.findElement(By.xpath("//*[@id='passenger[0].name']"));
            WebElement passenger_surname = driver.findElement(By.xpath("//*[@id='passenger[0].surname']"));
            WebElement passenger_mail = driver.findElement(By.xpath("//*[@id='passenger[0].email']"));
            WebElement passenger_mail_repeated = driver.findElement(By.xpath("//*[@id='passenger[0].email2']"));
            logs.add("Obsługa elementów strony 3");
            passenger_name.click();
            passenger_name.clear();
            passenger_name.sendKeys(name);

            passenger_surname.click();
            passenger_surname.clear();
            passenger_surname.sendKeys(surname);

            passenger_mail.click();
            passenger_mail.clear();
            passenger_mail.sendKeys(mail);

            passenger_mail_repeated.click();
            passenger_mail_repeated.clear();
            passenger_mail_repeated.sendKeys(mail);

            driver.findElement(By.xpath("//*[@id='go-to-summary']")).click();

            LocalDateTime page3_after = LocalDateTime.now();
            page3_duration = Duration.between(page3_before, page3_after);

            // Strona 4
            LocalDateTime page4_before = LocalDateTime.now();
            logs.add("Ładowanie strony 4");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#summary-wrapper")));
            logs.add("Znalezienie elementów strony 5");
            WebElement regulation_1 = driver.findElement(By.xpath("//*[@id='regulations']"));
            WebElement regulation_2 = driver.findElement(By.xpath("//*[@id='carriers']"));
            WebElement payment = driver.findElement(By.xpath("//*[@id='payment']"));
            logs.add("Obsługa elementów strony 4");
            js.executeScript("arguments[0].scrollIntoView(true);", regulation_1);
            js.executeScript("arguments[0].click()", regulation_1);

            js.executeScript("arguments[0].scrollIntoView(true);", regulation_2);
            js.executeScript("arguments[0].click()", regulation_2);

            js.executeScript("arguments[0].scrollIntoView(true);", payment);
            js.executeScript("arguments[0].click()", payment);
            LocalDateTime page4_after = LocalDateTime.now();
            page4_duration = Duration.between(page4_before, page4_after);
            // Strona 5
            LocalDateTime page5_before = LocalDateTime.now();
            logs.add("Ładowanie strony przelewy24");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".price")));

            LocalDateTime page5_after = LocalDateTime.now();
            page5_duration = Duration.between(page5_before, page5_after);
            sleep_time(2000);
            logs.add("Znaleziono połączenie");
            sleep_time(4000);

        } catch (org.openqa.selenium.TimeoutException error_1) {
            logs.add("Nie udało się załadować wybranej strony");

        } catch (org.openqa.selenium.NoSuchElementException error_2) {
            logs.add("Nie udało się znaleźć elementów danej strony");
        } catch (org.openqa.selenium.StaleElementReferenceException error_3) {
            logs.add("Nie udało się obsłużyć elementów danej strony");
        } finally {
            status = logs.get(logs.size() - 1);
            if (status == "Znaleziono połączenie") {
                String script = "var okDiv = document.createElement(\"div\");\n" +
                        "okDiv.style.position = \"fixed\";\n" +
                        "okDiv.style.top = \"0\";\n" +
                        "okDiv.style.left = \"0\";\n" +
                        "okDiv.style.width = \"100vw\";\n" +
                        "okDiv.style.height = \"100vh\";\n" +
                        "okDiv.style.backgroundColor = \"green\";\n" +
                        "okDiv.style.border = \"10px solid black\";\n" +
                        "okDiv.style.display = \"flex\";\n" +
                        "okDiv.style.justifyContent = \"center\";\n" +
                        "okDiv.style.alignItems = \"center\";\n" +
                        "okDiv.style.fontSize = \"5em\";\n" +
                        "okDiv.style.color = \"white\";\n" +
                        "okDiv.style.textAlign = \"center\";\n" +
                        "okDiv.innerText = \"OK\";\n" +
                        "document.body.appendChild(okDiv);\n" +
                        "setTimeout(function(){\n" +
                        "  okDiv.style.opacity = \"0\";\n" +
                        "  setTimeout(function(){\n" +
                        "    okDiv.remove();\n" +
                        "  }, 10000);\n" +
                        "}, 10000);";
                js.executeScript(script);
                sleep_time(3000);
                driver.quit();

            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
                String timestamp = dateFormat.format(new Date());
                String filename = city_1 + "-" + city_2 + " " + timestamp + "blad";
                String filepath = getUniqueFilename(filename, "raport/screenshot/bilkom/", ".png");
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileUtils.copyFile(scrFile, new File(filepath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
                String script2 = "var okDiv = document.createElement(\"div\");\n" +
                        "okDiv.style.position = \"fixed\";\n" +
                        "okDiv.style.top = \"0\";\n" +
                        "okDiv.style.left = \"0\";\n" +
                        "okDiv.style.width = \"100vw\";\n" +
                        "okDiv.style.height = \"100vh\";\n" +
                        "okDiv.style.backgroundColor = \"red\";\n" +
                        "okDiv.style.border = \"10px solid black\";\n" +
                        "okDiv.style.display = \"flex\";\n" +
                        "okDiv.style.justifyContent = \"center\";\n" +
                        "okDiv.style.alignItems = \"center\";\n" +
                        "okDiv.style.fontSize = \"5em\";\n" +
                        "okDiv.style.color = \"white\";\n" +
                        "okDiv.style.textAlign = \"center\";\n" +
                        "okDiv.innerText = \"Błąd\";\n" +
                        "document.body.appendChild(okDiv);\n" +
                        "setTimeout(function(){\n" +
                        "  okDiv.style.opacity = \"0\";\n" +
                        "  setTimeout(function(){\n" +
                        "    okDiv.remove();\n" +
                        "  }, 10000);\n" +
                        "}, 10000);";
                js.executeScript(script2);
                sleep_time(3000);
                try {
                    File soundFile = new File("error_sound.wav");
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(soundFile));

                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                driver.quit();
            }

            try {

                String line = String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s", city_1, city_2, carrier_name,
                        page1_duration.toMillis() * 0.001, page2_duration.toMillis() * 0.001,
                        page3_duration.toMillis() * 0.001, page4_duration.toMillis() * 0.001,
                        page5_duration.toMillis() * 0.001, status);

                FileWriter myWriter = new FileWriter(
                        "raport/bilkom.txt", true);
                myWriter.write(line + "\n");
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        System.out.println(logs);
    }
}