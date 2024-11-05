package com.prj.lotto.notice;

import com.prj.lotto.base.PageRequestDTO;
import com.prj.lotto.base.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(NoticeDTO noticeDTO) {
        Notice notice = modelMapper.map(noticeDTO, Notice.class);

        Long nno = noticeRepository.save(notice).getNno();

        return nno;
    }

    @Override
    public NoticeDTO readOne(Long nno) {
        Optional<Notice> result = noticeRepository.findById(nno);

        Notice notice = result.orElseThrow();

        NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);

        return noticeDTO;
    }

    @Override
    public void modify(NoticeDTO noticeDTO) {
        Optional<Notice> result = noticeRepository.findById(noticeDTO.getNno());

        Notice notice = result.orElseThrow();

        notice.modify(noticeDTO.getTitle(), noticeDTO.getContent(), noticeDTO.getFixed());

        noticeRepository.save(notice);
    }

    @Override
    public void remove(Long nno) {
        noticeRepository.deleteById(nno);
    }

    @Override
    public PageResponseDTO<NoticeDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("nno");

        Page<Notice> result = noticeRepository.searchAll(types, keyword, pageable);

        List<NoticeDTO> dtoList = result.getContent().stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());

//        dtoList.forEach(dto -> dto.setContent(dto.getContent().substring(0, 5) + "....."));

        return PageResponseDTO.<NoticeDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
