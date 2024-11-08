package com.prj.lotto.lotto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
public class Lotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lno;

    @Column(nullable = false)
    private int round;

    @Column(nullable = false)
    private int n1;

    @Column(nullable = false)
    private int n2;

    @Column(nullable = false)
    private int n3;

    @Column(nullable = false)
    private int n4;

    @Column(nullable = false)
    private int n5;

    @Column(nullable = false)
    private int n6;

    @Builder.Default
    @Column(nullable = false)
    private int bonus = 0;

    @Column(nullable = false)
    private String member;

    private String status;

    @Builder.Default
    private String winResult = "-";

    @Builder.Default
    private long price = 1000;

    @Builder.Default
    private double winPrice = 0.0;

    @CreatedDate
    @Column(name = "buyDate", updatable = false)
    private LocalDateTime buyDate;

    private LocalDateTime endDate;

    // 당첨 결과로 수정
    public void modify(String status, String winResult, double winPrice) {
        this.status = status;
        this.winResult = winResult;
        this.winPrice = winPrice;
    }
}
