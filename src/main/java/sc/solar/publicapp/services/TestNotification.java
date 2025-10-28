package sc.solar.publicapp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TestNotification {

    private final SseNotifications notifications;

    @Autowired
    public TestNotification(SseNotifications notifications) {
        this.notifications = notifications;
    }

    public void thread() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        System.out.println("Executing start task at: " + System.currentTimeMillis());
        Runnable task = () -> {
            // Simulate notifications me work
                notifications.sendNotifications("kbkoval@gmail.com");
                System.out.println("Executing task at: " + System.currentTimeMillis());
        };
        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
        scheduler.schedule(() -> {
            scheduler.shutdown();
            System.out.println("Scheduler shut down.");
        }, 300, TimeUnit.SECONDS);
      //  task.run();
    }
}
