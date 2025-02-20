package kr.hhplus.be.server.domain.apioutbox;

public enum OutboxStatus {
    INIT, // 트랜잭션이 commit 되었지만, Listenner가 이벤트를 읽기 못한 상태
    PROCESSING, //Kafka 발행 중, 이 상태를 유지하면 중복 발행 방지
    SEND_FAILED, //발행 실패, 재시도 가능
    SEND_SUCCESS, //정상 처리 완료
}
