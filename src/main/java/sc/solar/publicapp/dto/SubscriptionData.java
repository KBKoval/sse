package sc.solar.publicapp.dto;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.FluxSink;

public class SubscriptionData {
    private String nickName;
    private FluxSink<ServerSentEvent> fluxSink;
    private boolean status;

    public SubscriptionData(String nickName, FluxSink<ServerSentEvent> fluxSink) {
        this.nickName = nickName;
        this.fluxSink = fluxSink;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
