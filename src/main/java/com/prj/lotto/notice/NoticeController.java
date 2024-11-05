package com.prj.lotto.notice;

import com.prj.lotto.base.PageRequestDTO;
import com.prj.lotto.base.PageResponseDTO;
import com.prj.lotto.board.BoardDTO;
import com.prj.lotto.board.BoardService;
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
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public void noticeList(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<NoticeDTO> dto = noticeService.list(pageRequestDTO);

        model.addAttribute("dto", dto);
    }

    @GetMapping("/register")
    public void noticeRegisterGet(PageRequestDTO pageRequestDTO) {

    }

    @PostMapping("/register")
    public String noticeRegisterPost(@Valid NoticeDTO noticeDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/notice/register";
        }

        Long bno = noticeService.register(noticeDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/notice/list";
    }

    @GetMapping({"/read", "modify"})
    public void noticeRead(Long nno, PageRequestDTO pageRequestDTO, Model model) {
        NoticeDTO noticeDTO = noticeService.readOne(nno);

        model.addAttribute("dto", noticeDTO);
    }

    @PostMapping("/modify")
    public String noticeModify(PageRequestDTO pageRequestDTO,
                              @Valid NoticeDTO noticeDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", noticeDTO.getNno());

            return "redirect:/notice/modify?" + link;
        }

        noticeService.modify(noticeDTO);

        redirectAttributes.addAttribute("bno", noticeDTO.getNno());

        return "redirect:/notice/read";
    }

    @PostMapping("/remove")
    public String noticeRemove(Long nno) {
        noticeService.remove(nno);

        return "redirect:/notice/list";
    }
}