package com.prj.lotto.notice;

import com.prj.lotto.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long nno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 20, nullable = false)
    private String writer;

    @Column(length = 5)
    private String fixed;

    // 공지 수정
    public void modify(String title, String content, String fixed) {
        this.title = title;
        this.content = content;
        this.fixed = fixed;
    }
}
