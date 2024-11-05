package com.prj.lotto.board;

import com.prj.lotto.base.PageRequestDTO;
import com.prj.lotto.base.PageResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void boardList(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<BoardDTO> dto = boardService.list(pageRequestDTO);

        log.info(pageRequestDTO);

        model.addAttribute("dto", dto);
        model.addAttribute("boardType", pageRequestDTO.getBoardType());
    }

    @GetMapping("/register")
    public void boardRegisterGet(PageRequestDTO pageRequestDTO) {

    }

    @PostMapping("/register")
    public String boardRegisterPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/board/register";
        }

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }

    @GetMapping({"/read", "modify"})
    public void boardRead(Long bno, PageRequestDTO pageRequestDTO, Model model) {
        BoardDTO boardDTO = boardService.readOne(bno);

        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String boardModify(PageRequestDTO pageRequestDTO,
                              @Valid BoardDTO boardDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/board/modify?" + link;
        }

        boardService.modify(boardDTO);

        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String boardRemove(Long bno) {
        boardService.remove(bno);

        return "redirect:/board/list";
    }
}