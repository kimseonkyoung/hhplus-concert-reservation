package kr.hhplus.be.server.domain.service.dto;

import kr.hhplus.be.server.domain.concert.Concert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertServiceResponse {

    private int totalCount; // 총 콘서트 수
    private int offset;     // 현재 페이지 시작 위치
    private int limit;      // 한 페이지에 표시할 데이터 수
    private List<ConcertList> ConcertList; // 콘서트 목록

    // 팩토리 메서드
    public static ConcertServiceResponse create(int totalCount, int offset, int limit, List<ConcertList> concerts) {
        return new ConcertServiceResponse(totalCount, offset, limit, concerts);
    }
}
