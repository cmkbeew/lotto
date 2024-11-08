package com.prj.lotto.lotto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class LottoServiceImpl implements LottoService {

    private final LottoRepository lottoRepository;
    private final ModelMapper modelMapper;

    @Override
    public void buyLotto(LottoDTO lottoDto) {
        Lotto lotto = modelMapper.map(lottoDto, Lotto.class);

        lottoRepository.save(lotto);
    }

    @Override
    public int sellCount(int round, String member) {
        return 0;
    }

    @Override
    public LottoDTO standardLotto(int round, int sellCount) {
        return null;
    }

    @Override
    public LottoDTO readLotto(int round, String member, String status) {
        return null;
    }
}
