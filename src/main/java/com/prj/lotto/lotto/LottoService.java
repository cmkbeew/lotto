package com.prj.lotto.lotto;

public interface LottoService {

    // 일반 회원 로또 구매
    void buyLotto(LottoDTO lottoDto);

    // 회차 별 판매 수
    int sellCount(int round, String member);

    // 당첨 번호 추출
    LottoDTO standardLotto(int round, int sellCount);

    // 당첨 번호 조회
    LottoDTO readLotto(int round, String member, String status);

}
