package com.prj.lotto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1; // 페이지 번호

    @Builder.Default
    private int size = 10; // 블럭 사이즈

    private String type; // 검색 종류

    private String keyword; // 검색어

    private String link; // 페이지 링크

    @Builder.Default
    private String boardType = "board";

    public String[] getTypes() {
        if(type == null || type.isEmpty()) {
            return null;
        }

        return type.split("");
    }

    public Pageable getPageable(String...props) {
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    public String getLink() {
        if(link == null) {
            StringBuilder sb = new StringBuilder();

            sb.append("boardType=" + this.boardType);
            sb.append("&page=" + this.page);
            sb.append("&size=" + this.size);

            if(type != null && type.length() > 0) {
                sb.append("&type=" + type);
            }

            if(keyword != null) {
                try {
                    sb.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }

            link = sb.toString();
        }

        return link;
    }
}
