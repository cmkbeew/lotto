package com.prj.lotto.notice;

import com.prj.lotto.notice.search.NoticeSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeSearch {
}
