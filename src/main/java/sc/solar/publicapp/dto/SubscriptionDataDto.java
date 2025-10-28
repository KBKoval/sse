package sc.solar.publicapp.dto;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.FluxSink;

public class SubscriptionDataDto {
    private String email;
    private FluxSink<ServerSentEvent> fluxSink;
    private boolean status;

    public SubscriptionDataDto(String email, FluxSink<ServerSentEvent> fluxSink) {
        this.email = email;
        this.fluxSink = fluxSink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FluxSink<ServerSentEvent> getFluxSink() {
        return fluxSink;
    }

    public void setFluxSink(FluxSink<ServerSentEvent> fluxSink) {
        this.fluxSink = fluxSink;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
