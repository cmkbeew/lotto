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

    @Column(nullable = false)
    private int bonus;

    @Column(nullable = false)
    private String member;

    private String status;

    private int price;

    @CreatedDate
    @Column(name = "buyDate", updatable = false)
    private LocalDateTime buyDate;

    private LocalDateTime endDate;
}
