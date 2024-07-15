import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.time.LocalTime;

public class App {
        private static boolean isServiceTime(LocalTime startTime, LocalTime endTime) {
                LocalTime now = LocalTime.now();
                return now.isAfter(startTime) && now.isBefore(endTime);
        }

        public static void main(String[] args) {
                Properties properties = new Properties();
                try (InputStream input = new FileInputStream("config.cfg")) {
                        properties.load(input);
                } catch (IOException e) {
                        e.printStackTrace();
                        return;
                }
                int interval1 = Integer.parseInt(properties.getProperty("executor.intervalTime"));
                TimeUnit timeUnit1 = TimeUnit.valueOf(properties.getProperty("executor.timeUnit"));
                int threadPoolSize1 = Integer.parseInt(properties.getProperty("executor.threadPoolSize"));
                LocalTime serviceStartTime = LocalTime.parse(properties.getProperty("serviceHours.start"));
                LocalTime serviceEndTime = LocalTime.parse(properties.getProperty("serviceHours.end"));

                try (FileReader reader = new FileReader("connection_list.csv");
                                CSVParser parser = new CSVParser(reader,
                                                CSVFormat.DEFAULT.withHeader())) {

                        ScheduledExecutorService executor1 = Executors.newScheduledThreadPool(threadPoolSize1);

                        for (CSVRecord record : parser) {

                                String from = record.get("Source");
                                String to = record.get("Destination");
                                String carrier = record.get("Carrier");
                                Runnable task = () -> {
                                        if (!isServiceTime(serviceStartTime, serviceEndTime)) {
                                                bilkom.bot(from, to, carrier);
                                        } else {
                                                long end = serviceEndTime.toNanoOfDay() / 1_000_000;
                                                long start = serviceStartTime.toNanoOfDay() / 1_000_000;
                                                try {
                                                        Thread.sleep(end - start);
                                                } catch (InterruptedException e) {
                                                        System.out.println("Interrupted.");
                                                }

                                        }
                                };

                                executor1.scheduleWithFixedDelay(task, 0, interval1, timeUnit1);
                        }

                } catch (IOException e) {
                        e.printStackTrace();
                }
                int interval2 = Integer.parseInt(properties.getProperty("executor2.intervalTime"));
                TimeUnit timeUnit2 = TimeUnit.valueOf(properties.getProperty("executor2.timeUnit"));
                int threadPoolSize2 = Integer.parseInt(properties.getProperty("executor2.threadPoolSize"));
                ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(threadPoolSize2);
                Runnable task2 = () -> {
                        if (!isServiceTime(serviceStartTime, serviceEndTime)) {
                                intercity.bot("Gdańsk", "Kraków");
                        }
                };
                executor2.scheduleWithFixedDelay(task2, 0, interval2, timeUnit2);

        }
}