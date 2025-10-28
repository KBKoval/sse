package sc.solar.publicapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import sc.solar.publicapp.dto.SendMessageRequest;
import sc.solar.publicapp.dto.SubscriptionData;
import sc.solar.publicapp.services.SseNotifications;
import sc.solar.publicapp.services.TestNotification;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("api")
public class SseRestController {

    private final SseNotifications notifications;
    private final TestNotification testNotification;

    @Autowired
    public SseRestController(SseNotifications notifications, TestNotification testNotification) {
        this.notifications = notifications;
        this.testNotification = testNotification;
    }

    @GetMapping(path = "/open/{email}", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // 2
    public Flux<ServerSentEvent> openStream(@PathVariable String email) {
        System.out.println("open stream");
        return notifications.addSubscriptions(email);
    }

    @GetMapping(path = "/test") // 2
    public void startTest() {
        testNotification.thread();
    }

    Map<UUID, SubscriptionData> subscriptions = new ConcurrentHashMap<>(); // 1

    @GetMapping(path = "/open-sse-stream/{nickName}", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // 2
    public Flux<ServerSentEvent> openSseStream(@PathVariable String nickName) {

        return Flux.create(fluxSink -> { // 3
            System.out.println("create subscription for " + nickName);

            UUID uuid = UUID.randomUUID();

            fluxSink.onCancel( // 4
                    () -> {
                        subscriptions.remove(uuid);
                        System.out.println("subscription " + nickName + " was closed");
                    });

            SubscriptionData subscriptionData = new SubscriptionData(nickName, fluxSink);
            subscriptions.put(uuid, subscriptionData);

            // 5
            ServerSentEvent<String> helloEvent = ServerSentEvent.builder("Hello " + nickName).build();
            fluxSink.next(helloEvent);
        });
    }

    @PutMapping(path = "/send-message-for-all")
    public void sendMessageForAll(@RequestBody SendMessageRequest request) {

        // 1
        ServerSentEvent<String> event = ServerSentEvent.builder(request.getMessage()).build();

        // 2
        subscriptions.forEach((uuid, subscriptionData) -> subscriptionData.getFluxSink().next(event));
    }

    @PutMapping(path = "/send-message-by-name/{nickName}")
    public void sendMessageByName(@PathVariable String nickName, @RequestBody SendMessageRequest request) {

        ServerSentEvent<String> event = ServerSentEvent.builder(request.getMessage()).build();

        subscriptions.forEach((uuid, subscriptionData) -> {
            if (nickName.equals(subscriptionData.getNickName())) {
                subscriptionData.getFluxSink().next(event);
            }
        });
    }
}
