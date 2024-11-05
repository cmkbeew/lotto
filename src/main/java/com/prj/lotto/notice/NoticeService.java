package com.prj.lotto.notice;

import com.prj.lotto.base.PageRequestDTO;
import com.prj.lotto.base.PageResponseDTO;
import com.prj.lotto.board.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {

    Long register(NoticeDTO noticeDTO);

    NoticeDTO readOne(Long nno);

    void modify(NoticeDTO noticeDTO);

    void remove(Long nno);

    PageResponseDTO<NoticeDTO> list(PageRequestDTO pageRequestDTO);
}
