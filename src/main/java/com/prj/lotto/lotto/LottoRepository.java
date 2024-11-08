package com.prj.lotto.lotto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LottoRepository extends JpaRepository<Lotto, Long> {

    // 회차 별 당첨 번호 (member == admin, status == standard)
    Lotto findByRoundAndMemberAndStatus(int round, String member, String status);

    // 회차 별 판매 수 (member != admin)
    int countLottoByRoundAndMemberNot(int round, String member);

    // 회차 별 구매 리스트
    List<Lotto> findAllByRoundAndMemberNotAndStatus(int round, String member, String status);

    // 회차 별 당첨 수
    int countAllByRoundAndWinResult(int round, String winResult);
}
