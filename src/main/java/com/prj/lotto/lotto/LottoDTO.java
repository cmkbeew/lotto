package com.prj.lotto.lotto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LottoDTO {

    private Long lno;

    @NotBlank
    private int round;

    @NotBlank
    private int n1;

    @NotBlank
    private int n2;

    @NotBlank
    private int n3;

    @NotBlank
    private int n4;

    @NotBlank
    private int n5;

    @NotBlank
    private int n6;

    @NotBlank
    private int bonus;

    @NotBlank
    private String member;

    private String status;

    private int price;

    private LocalDateTime buyDate;

    private LocalDateTime endDate;
}
