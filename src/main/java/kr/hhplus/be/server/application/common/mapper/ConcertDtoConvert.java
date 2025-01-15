package kr.hhplus.be.server.application.common.mapper;

import kr.hhplus.be.server.domain.common.dto.*;
import kr.hhplus.be.server.interfaces.api.dto.*;

import java.util.ArrayList;
import java.util.List;

public class ConcertDtoConvert {

    public static ConcertResponse toControllerConcertListResponse(ConcertServiceResponse serviceResponse) {
        List<ConcertItems> response = new ArrayList<>();

        for (ConcertList concertList : serviceResponse.getConcertList()) {
            response.add(new ConcertItems(concertList.getId(), concertList.getTitle()));
        }

        return new ConcertResponse(
                serviceResponse.getTotalCount(),
                serviceResponse.getOffset(),
                serviceResponse.getLimit(),
                response
        );
    }

    public static ConcertDatesResponse tocontrollerConcertDateListResponse(ConcertServiceDatesResponse serviceResponse) {
        List<ConcertDates> response = new ArrayList<>();

        for (ConcertDateList dateList : serviceResponse.getDates()) {
            response.add(new ConcertDates(dateList.getId(), dateList.getDate()));
        }
        return new ConcertDatesResponse(
                serviceResponse.getTotalCount(),
                serviceResponse.getOffset(),
                serviceResponse.getLimit(),
                response
        );
    }
    public static List<SeatResponse> tocontrollerConcertSeatListResponse(List<SeatServiceResponse> seatResponse) {
        List<SeatResponse> response = new ArrayList<>();

        for(SeatServiceResponse serviceResponse : seatResponse) {
            response.add(new SeatResponse(serviceResponse));
        }
        return response;
    }


}