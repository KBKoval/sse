package sc.solar.publicapp.services;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface SseNotifications {
    public Flux<ServerSentEvent> addSubscriptions(String email);
    public void sendNotifications(String email);
}
