package com.prj.lotto.notice.search;

import com.prj.lotto.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeSearch {
    Page<Notice> searchAll(String[] types, String keyword, Pageable pageable);
}
