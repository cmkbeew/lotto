package com.prj.lotto.board;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDTO {

    private Long bno;

    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 3, max = 100, message = "3 ~ 100자 이내로 입력하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    @NotBlank(message = "작성자를 입력하세요.")
    private String writer;

    @NotBlank(message = "게시판 종류를 선택하세요.")
    private String boardType;

    private String fixed;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
