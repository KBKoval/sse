package sc.solar.publicapp.services;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import sc.solar.publicapp.dto.SubscriptionDataDto;
import sc.solar.publicapp.dto.TypeEventDto;

@Service
public class SseNotificationsImpl implements SseNotifications {
    private Map<String, SubscriptionDataDto>  subscriptions = new ConcurrentHashMap<>();

    public Flux<ServerSentEvent> addSubscriptions(String email) {
        return Flux.create(fluxSink -> {
           fluxSink.onCancel(
                    () -> {
                        subscriptions.remove(email);
                    }
            );
            SubscriptionDataDto subscriptionData = new SubscriptionDataDto(email, fluxSink);
            subscriptions.put(email, subscriptionData);

            ServerSentEvent<String> helloEvent = ServerSentEvent.builder("connection " + email).build();
            fluxSink.next(helloEvent);
        });
    }

    public void sendNotifications(String email){
        TypeEventDto typeEvent = new TypeEventDto("note");
        ServerSentEvent<TypeEventDto> serverEvent = ServerSentEvent.builder(typeEvent).build();
        subscriptions.forEach((key, subscriptionData) -> {
                    if (email.equals(subscriptionData.getEmail())) {
                        subscriptionData.getFluxSink().next(serverEvent);
                    }
                }
        );
    }
}
