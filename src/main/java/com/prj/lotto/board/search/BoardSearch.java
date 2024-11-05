package com.prj.lotto.board.search;

import com.prj.lotto.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    Page<Board> searchAll(String[] types, String keyword, String boardType, Pageable pageable);
}
