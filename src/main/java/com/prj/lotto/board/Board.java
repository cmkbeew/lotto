package com.prj.lotto.board;

import com.prj.lotto.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long bno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 20, nullable = false)
    private String writer;

    @Column(length = 10, nullable = false)
    private String boardType;

    @Column(length = 10)
    private String fixed;
    
    // 게시글 수정
    public void modify(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
