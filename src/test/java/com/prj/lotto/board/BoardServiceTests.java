package com.prj.lotto.board;

import com.prj.lotto.base.PageRequestDTO;
import com.prj.lotto.base.PageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        BoardDTO boardDTO = BoardDTO.builder()
                .title("register title")
                .content("register content")
                .writer("register writer")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("bno: {}", bno);
    }

    @Test
    public void testReadOne() {
        Long bno = 100L;

        BoardDTO dto = boardService.readOne(bno);

        log.info("BoardDTO: {}", dto);
    }

    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("modified title")
                .content("modified content")
                .build();

        boardService.modify(boardDTO);
    }

    @Test
    public void testRemove() {
        boardService.remove(1L);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);
    }
}
