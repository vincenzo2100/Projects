import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
import java.util.List;
import java.util.ArrayList;
import org.openqa.selenium.Point;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.text.SimpleDateFormat;
import java.util.Date;

public class intercity {
        public static String date_generate() {
                LocalDate new_date = LocalDate.now().plusDays(1);
                while (new_date.getDayOfWeek() == DayOfWeek.SATURDAY || new_date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        new_date = new_date.plusDays(1);
                }
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
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

        public static void bot(String city_1, String city_2) {

                String date = date_generate();
                String name = "Test";
                String surname = "Test";
                String mail = "Test@onet.pl";
                String time = "10:30";
                String status = "x";
                List<String> logs = new ArrayList<String>();
                Properties props = new Properties();
                try (FileInputStream fis = new FileInputStream("config.cfg")) {
                        props.load(fis);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                int width = Integer.parseInt(props.getProperty("window2.width"));
                int height = Integer.parseInt(props.getProperty("window2.height"));
                int posX = Integer.parseInt(props.getProperty("window2.position.x"));
                int posY = Integer.parseInt(props.getProperty("window2.position.y"));
                logs.add("Inicjalizacja WebDrivera");
                WebDriver driver = new FirefoxDriver();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebDriver.Window window = driver.manage().window();
                window.setSize(new Dimension(width, height));
                window.setPosition(new Point(posX, posY));
                Duration page1_duration = Duration.ofSeconds(0);
                Duration page2_duration = Duration.ofSeconds(0);
                Duration page3_duration = Duration.ofSeconds(0);
                Duration page4_duration = Duration.ofSeconds(0);
                Duration page5_duration = Duration.ofSeconds(0);
                Duration page6_duration = Duration.ofSeconds(0);
                Duration page7_duration = Duration.ofSeconds(0);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                try {
                        // Wejście na stronę
                        LocalDateTime page1_before = LocalDateTime.now();

                        logs.add("Ładowanie strony 1");
                        driver.get("https://www.intercity.pl/pl/");

                        wait.until(ExpectedConditions
                                        .visibilityOfElementLocated(By.cssSelector("a.btn-link.btn-adv-search")));

                        // Strona 1
                        logs.add("Znalezienie elementów strony 1");
                        WebElement begging_station = driver.findElement(By.name("stname[0]"));
                        WebElement end_station = driver.findElement(By.name("stname[1]"));
                        WebElement departure_date = driver.findElement(By.id("date_picker"));
                        WebElement departure_hour = driver.findElement(By.id("ic-seek-time"));
                        WebElement submit_button = driver.findElement(By.cssSelector("button[name='search']"));
                        logs.add("Obsługa strony 1");
                        begging_station.click();
                        begging_station.clear();
                        begging_station.sendKeys(city_1);
                        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                                        By.xpath("//ul[@class='typeahead dropdown-menu']//a[contains(@title, '%s')]"
                                                        .formatted(city_1))));
                        driver.findElement(By
                                        .xpath("//ul[@class='typeahead dropdown-menu']//a[contains(@title, '%s')]"
                                                        .formatted(city_1)))
                                        .click();

                        end_station.click();
                        end_station.clear();
                        end_station.sendKeys(city_2);
                        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                                        By.xpath("//ul[@class='typeahead dropdown-menu']//a[contains(@title, '%s')]"
                                                        .formatted(city_2))));
                        driver.findElement(By.xpath(
                                        "//ul[@class='typeahead dropdown-menu']//a[contains(@title, '%s')]"
                                                        .formatted(city_2)))
                                        .click();

                        departure_date.click();
                        departure_date.clear();
                        departure_date.sendKeys(date);

                        departure_hour.click();
                        departure_hour.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
                        departure_hour.sendKeys(time + Keys.ENTER);

                        submit_button.click();
                        LocalDateTime page1_after = LocalDateTime.now();
                        page1_duration = Duration.between(page1_before, page1_after);

                        // Strona 2
                        LocalDateTime page2_before = LocalDateTime.now();
                        logs.add("Ładowanie strony 2");
                        wait.until(ExpectedConditions
                                        .visibilityOfElementLocated(
                                                        By.xpath("//div[@class='wrap train_main_content_box']")));
                        logs.add("Obsługa elementów strony 2");
                        driver.findElement(
                                        By.xpath("//li[@class='active']//div[@class='przycisk_wybierz_relacje choose_button_ralation']"))
                                        .click();
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("zakup_biletu_form")));
                        driver.findElement(By.xpath("//input[@id='strefa_modal']")).click();
                        LocalDateTime page2_after = LocalDateTime.now();
                        page2_duration = Duration.between(page2_before, page2_after);
                        // Strona 3
                        LocalDateTime page3_before = LocalDateTime.now();
                        logs.add("Obsługa strony 3");
                        WebElement name_and_surname = driver
                                        .findElement(By.xpath("//input[@id='imie_nazwisko_podroznego']"));
                        name_and_surname.click();
                        name_and_surname.clear();
                        name_and_surname.sendKeys(name + " " + surname);
                        driver.findElement(By.xpath("//div[@class='kup_bilet_button blue_bg']")).click();
                        LocalDateTime page3_after = LocalDateTime.now();
                        page3_duration = Duration.between(page3_before, page3_after);
                        // Strona 4
                        LocalDateTime page4_before = LocalDateTime.now();
                        logs.add("Obsługa strony 4");
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("logowanie_module")));
                        driver.findElement(By.xpath("//a[@href='/konto_gosc_rejestracja.jsp?']")).click();
                        LocalDateTime page4_after = LocalDateTime.now();
                        page4_duration = Duration.between(page4_before, page4_after);
                        // Strona 5
                        LocalDateTime page5_before = LocalDateTime.now();
                        logs.add("Ładowanie strony 5");
                        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("form-box")));
                        logs.add("Znalezienie elementów strony 5");
                        WebElement name_form = driver.findElement(By.name("imie"));
                        WebElement surname_form = driver.findElement(By.name("nazwisko"));
                        WebElement mail_form = driver.findElement(By.name("email"));
                        WebElement mail_form_repeated = driver.findElement(By.name("powt_email"));
                        logs.add("Obsługa elementów strony 5");
                        name_form.click();
                        name_form.clear();
                        name_form.sendKeys(name);
                        surname_form.click();
                        surname_form.clear();
                        surname_form.sendKeys(surname);
                        mail_form.click();
                        mail_form.clear();
                        mail_form.sendKeys(mail);
                        mail_form_repeated.click();
                        mail_form_repeated.clear();
                        mail_form_repeated.sendKeys(mail);
                        driver.findElement(By.id("akceptacja")).click();
                        driver.findElement(By.className("orangebutton")).click();
                        LocalDateTime page5_after = LocalDateTime.now();
                        page5_duration = Duration.between(page5_before, page5_after);
                        // Strona 6
                        LocalDateTime page6_before = LocalDateTime.now();
                        logs.add("Obsługa strony 6");
                        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("form-box")));
                        driver.findElement(By.className("orangebutton")).click();
                        LocalDateTime page6_after = LocalDateTime.now();
                        page6_duration = Duration.between(page6_before, page6_after);
                        // Strona 7
                        LocalDateTime page7_before = LocalDateTime.now();
                        logs.add("Obsługa strony 7");
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("platnosc_label")));
                        driver.findElement(By.id("platosc_fieldset_4")).click();
                        driver.findElement(By.id("pay_button")).click();

                        // Przelewy 24
                        logs.add("Obsługa strony 8");
                        wait.until(ExpectedConditions
                                        .presenceOfElementLocated(By.className("payment-method__content")));
                        LocalDateTime page7_after = LocalDateTime.now();
                        page7_duration = Duration.between(page7_before, page7_after);
                        logs.add("Znaleziono połączenie");
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
                                String filepath = getUniqueFilename(filename, "raport/screenshot/intercity/", ".png");
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
                                        File soundFile = new File(
                                                        "error_sound.wav");
                                        Clip clip = AudioSystem.getClip();
                                        clip.open(AudioSystem.getAudioInputStream(soundFile));

                                        clip.start();
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                                driver.quit();
                        }

                        try {

                                String line = String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s", city_1, city_2,
                                                page1_duration.toMillis() * 0.001, page2_duration.toMillis() * 0.001,
                                                page3_duration.toMillis() * 0.001, page4_duration.toMillis() * 0.001,
                                                page5_duration.toMillis() * 0.001, page6_duration.toMillis(),
                                                page7_duration.toMillis(), status);

                                FileWriter myWriter = new FileWriter(
                                                "raport/intercity.txt", true);
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