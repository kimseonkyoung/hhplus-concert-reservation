package kr.hhplus.be.server.application.kafka;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KafkaMessage {
    private String key;
    private String value;

    public KafkaMessage(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
