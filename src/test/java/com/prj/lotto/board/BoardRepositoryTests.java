package com.prj.lotto.board;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsertOne() {
        Board board = Board.builder()
                .title("insert title")
                .content("insert content")
                .writer("member")
                .build();

        boardRepository.save(board);
        log.info("new board: {}", board);
    }

    @Test
    public void testInsert100() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer("member" + i)
                    .build();

            Board result = boardRepository.save(board);
            log.info("result: {}", result);
        });
    }

    @Test
    public void testSelectOne() {
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info("board: {}", board);
    }

    @Test
    public void testUpdate() {
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();

        board.modify("modified title 100", "modified content 100");

        boardRepository.save(board);

        log.info("board: {}", board);
    }

    @Test
    public void testDelete() {
        Long bno = 101L;

        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count: {}", result.getTotalElements());
        log.info("total pages: {}", result.getTotalPages());
        log.info("page number: {}", result.getNumber());
        log.info("page size: {}", result.getSize());

        List<Board> dtoList = result.getContent();

        dtoList.forEach(board -> log.info(board));
    }

    @Test
    public void testSearchAll() {
        String[] types = {"t", "c", "w"};

        String keyword = "1";

        String boardType = "board";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, boardType, pageable);

        log.info(result.getTotalPages());
        log.info(result.getTotalElements());
        result.forEach(board -> log.info(board));
    }
}
