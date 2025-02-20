<!--
  제목은 [(과제 STEP)] (작업한 내용) 로 작성해 주세요
  예시: [STEP-9] 이커머스 시스템 설계 
-->
# [STEP-17] docker를 이용한 kafka 설치
# [STEP-18] 카프카의 발행 실패 방지를 위한 Transactional Outbox Pattern 적용 && 발행 실패 케이스를 위한 재처리 구현(Scheduler)

## PR 설명
<!-- 해당 PR이 왜 발생했고, 어떤부분에 대한 작업인지 작성해주세요. -->
해당 과제에서는 [STEP-17] 과 [STEP-18]을 함께 Pull_request 합니다.
1. [STEP-17]의 경우 kafka controller 와 kafka controller, kafka producer, kafka consumer 구성해서 터미널에서
curl -X POST "http://localhost:8080/kafka/send?message=HelloKafka"  를 통해 해당 응답 메시지를 확인했습니다.

2. [STEP-17]에서 kafka test container도 구현하려고 했으나 실패하여, 후에 시도해볼 Try if you want로 남았습니다.

3. [STEP-18]에서는 기존에 구현했던 Transactional outbox pattern을 카프카의 이벤트 발행과 연계해서 개선했습니다.
4. [STEP-18] outbox save 로직을 spring event Listener에서 before_commit 그리고 kafka 이벤트 발행 로직을 after_commit으로 구현했습니다.
5. 만약 트랜잭션 commit 이후에 해당 이벤트를 Listener가 읽지 못하는 상황을 발생해 오랫동안 INIT 상태의 이벤트를 체크하기 위해 
스케줄러에서는 SEND_FAIL 메시지와 INIT 메시지중 지금으로부터 10분을 기준으로 그것보다 오래된 이벤트를 긁어 재발행하도록 구현했습니다.
6. 그리고 트랜잭션 commit 직후에 (서비스 배포와 같이) 로직을 실행하는 프로세스가 shutdown되는 상황을 방지하고 graceful shutdown이 정상적으로 이루어지도록
무신사 29cm outbox transaction 적용기를 참고해서 TreadPoolTaskExecutor의 설정을 종료시 대기, 최대 10초까지 대기하도록 구성했습니다.

## Try if you want..
[과제 제출 이후 해야할 사항 정리]
1. kafka test container 적용하기.
2. 30초마다 재시도 처리를 일정 횟수 제한을 둔 뒤 제한을 넘어가는 이벤트를 위해 DLT 적용하기.
3. 혹은 슬랙 이메일 등을 통한 파이프라인 구축(개념이 부족해 학습 필요..)
4. 대규모 실시간성 로그를 기록할 있도록 kafka를 통한 파이프라인 구축
 

## Definition of Done (DoD)
<!--
    DOD 란 해당 작업을 완료했다고 간주하기 위해 충족해야 하는 기준을 의미합니다.
    어떤 기능을 위해 어떤 요구사항을 만족하였으며, 어떤 테스트를 수행했는지 등을 명확하게 체크리스트로 기재해 주세요.
    리뷰어 입장에서, 모든 맥락을 파악하기 이전에 작업의 성숙도/완성도를 파악하는 데에 도움이 됩니다.
    만약 계획되거나 연관 작업이나 파생 작업이 존재하는데, 이후로 미뤄지는 경우 TODO -, 사유와 함께 적어주세요.

    ex:
    - [x] 상품 도메인 모델 구조 설계 완료 ( [정책 참고자료](관련 문서 링크) )
    - [x] 상품 재고 차감 로직 유닛/통합 테스트 완료
    - [ ] TODO - 상품 주문 로직 개발 ( 정책 미수립으로 인해 후속 작업에서 진행 )
-->
