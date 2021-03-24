package com.projector;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException {
        GoogleAnalytics googleAnalytics = GoogleAnalytics.builder()
                .withTrackingId("G-R5Z91C3QC0")
                .build();

        Random random = new Random();

        log.info("Starting send events to GA");

        for (int i = 1; i <= 100; i++) {
            int value = random.nextInt();

            googleAnalytics.screenView()
                    .sessionControl("start")
                    .send();

            googleAnalytics.pageView("/hello", "Hello world").send();

            googleAnalytics.event()
                    .eventCategory("My category")
                    .eventValue(value)
                    .send();

            log.info("Value is {}", value);

            Thread.sleep(5000);
            googleAnalytics.screenView()
                    .sessionControl("end")
                    .send();
        }

        log.info("End");
    }
}
