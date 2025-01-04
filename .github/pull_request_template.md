<!--
  제목은 [(과제 STEP)] (작업한 내용) 로 작성해 주세요
  예시: [STEP-5] 이커머스 시스템 설계 
-->
# [STEP-06] 콘서트 예매 시스템 설계

## PR 설명
<!-- 해당 PR이 왜 발생했고, 어떤부분에 대한 작업인지 작성해주세요. -->
해당 PR은 콘서트 예매 시스템 설계에 관한 문서로 구성되어있습니다. 해당 문서를 통해서 시스템 구현시 더욱 세밀하고 구체적인 설계를 기반으로 효율적인 구현을 진행할 수 있도록 돕습니다. 
시스템의 주요 요구사항, 프로젝트 총 기간 대비 소요 이슈에 대한 MD 산출, 데이터 흐름 등을 포함하여 원활한 이해를 도모할 수 있도록 작성되었습니다.

1. Requirements: 해당 콘서트 예매 시스템의 API 요구사항에 대해 소개합니다.
[해당자료]((https://github.com/kimseonkyoung/hhplus-concert-reservation/wiki/01.-%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD))
2. Requirements: 해당 콘서트 예매 시스템의 API 요구사항에 대해 상세 분석합니다.
[해당자료]((https://github.com/kimseonkyoung/hhplus-concert-reservation/wiki/02.-%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD-%EB%B6%84%EC%84%9D))
3. Milestones: 해당 콘서트 예매 시스템을 설계 구현에 걸리는 총 기간과 기간에 따른 이슈사항, MD를 산출하고 계획합니다.
[해당자료]((https://github.com/kimseonkyoung/hhplus-concert-reservation/wiki/03.-%EB%A7%88%EC%9D%BC%EC%8A%A4%ED%86%A4))
4. Flowchart: 해당 콘서트 예매 시스템에서 유저가 예약 페이지에 최초 진입부터 결제후 유저의 토큰 만료까지의 플로우를 차트로 표현합니다.
[해당자료]((https://github.com/kimseonkyoung/hhplus-concert-reservation/wiki/04.-%ED%94%8C%EB%A1%9C%EC%9A%B0-%EC%B0%A8%ED%8A%B8))
5. Sequence_Diagrams: 해당 콘서트 예매 시스템에서 domain을 중심으로 user <-> web client <-> domain <-> db 사이의 호출과 응답의 흐름을 표현합니다.
[해당자료]((https://github.com/kimseonkyoung/hhplus-concert-reservation/wiki/05.-%EC%8B%9C%ED%80%80%EC%8A%A4-%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8))
6. ERD_Design: 해당 콘서트 예매 시스템의 ERD 설계를 했습니다. 테이블관의 관계와 해당 테이블 내의 컬럼을 표현합니다.
[해당자료]((https://github.com/kimseonkyoung/hhplus-concert-reservation/wiki/06.-ERD-%EC%84%A4%EA%B3%84))

7. Package_Structure: 해당 콘서트 예매 시스템의 패키지 구조를 설계했습니다. DDD 방식을 지향합니다.
[해당자료]((https://github.com/kimseonkyoung/hhplus-concert-reservation/wiki/07.-%ED%8C%A8%ED%82%A4%EC%A7%80-%EA%B5%AC%EC%A1%B0-%EC%84%A4%EA%B3%84))
8. API_Specification: 해당 콘서트 예매 시스템의 API 명세를 정의합니다. REST API HTTP Method와 request response 방식에 대해 정의합니다.
[해당자료]((https://github.com/kimseonkyoung/hhplus-concert-reservation/wiki/08.-API-%EB%AA%85%EC%84%B8%EC%84%9C))

## 리뷰 포인트
<!-- 
    리뷰어가 함께 고민해주었으면 하는 내용을 간략하게 기재해주세요.
    커밋 링크가 포함되면, 더욱이 효과적일 거예요! 
-->
1. 좌석 예약 API에서 시퀀스 다이어그램의 데이터 흐름이 적합한지 리뷰 부탁드립니다.
2. 해당 API 요구사항 분석에서 기술적으로 더 고려해야될 사항이 있는지 리뷰 부탁드립니다.
3. ERD 설계에서 token테이블과 queue 테이블을 분리할지 고민입니다. 또한 concert 테이블과 concert_scheduler 테이블도 통합 가능성이 있는지 리뷰 부탁드립니다.
4. 패키지 구조에서 현재 facade는 하나로 구성되어있지만, 차후에 두 개의 facade로 분리하려고 합니다. facade가 동일한 진입점으로써의 역할임에도 불구하고
다시 facade가 분리되면 facade의 장점이 사라지게 되는 것이 아닌지 궁금합니다.

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
- [x] API 요구사항 분석 완료
- [x] UML 작성 완료 
- [x] DB 완료 
- [x] API 명세 및 Mock API 완료 
- [x] 기본 패키지 구조 및 기타 완료
