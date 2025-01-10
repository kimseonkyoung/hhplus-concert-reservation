package kr.hhplus.be.server.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "콘서트 목록 데이터")
public class ConcertItems {

    @Schema(description = "콘서트 ID", example = "1L")
    private Long id;

    @Schema(description = "콘서트명", example = "김선경의 콘서트")
    private String title;
}
