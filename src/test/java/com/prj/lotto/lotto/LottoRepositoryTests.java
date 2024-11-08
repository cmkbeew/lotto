package com.prj.lotto.lotto;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class LottoRepositoryTests {

    @Autowired
    private LottoRepository lottoRepository;

    @Test // 당첨 번호 추출 및 구매 수를 기준으로 당첨 가격 정하기
    public void testInsertStandard() {
        int round = 4;
        int numbers[] = new int[7];

        for(int i=0; i<numbers.length; i++) {
            numbers[i] = (int) (Math.random() * 45) + 1;

            for(int j=0; j<i; j++) {
                if(numbers[i] == numbers[j]) {
                    i--;
                    break;
                }
            }
        }
        int bonus = numbers[6];
        log.info(bonus);
        log.info("보너스 포함 번호 : {}", Arrays.toString(numbers));

        // 보너스 번호 제외
        numbers = Arrays.copyOf(numbers, numbers.length -1);

        log.info("정렬 전 : {}", Arrays.toString(numbers));

        Arrays.sort(numbers);
        log.info("정렬 후 : {}", Arrays.toString(numbers));

        // 회차 별 판매 수
        int sellCount = lottoRepository.countLottoByRoundAndMemberNot(round, "admin");

        // 판매 수 * 개 당 가격
        int price = sellCount * 1000;

        Lotto lotto = Lotto.builder()
                .round(round)
                .n1(numbers[0])
                .n2(numbers[1])
                .n3(numbers[2])
                .n4(numbers[3])
                .n5(numbers[4])
                .n6(numbers[5])
                .bonus(bonus)
                .member("admin")
                .status("standard")
                .price(price)
                .build();

        log.info("당첨 번호 추출 : {}", lotto);
        lottoRepository.save(lotto);
    }

    @Test // 로또 구매 (반복문 처리)
    public void testInsertBuying() {
        int round = 4;

        int numbers[] = new int[6];

        IntStream.rangeClosed(1, 10).forEach(num -> {
            for(int i=0; i<numbers.length; i++) {
                numbers[i] = (int) (Math.random() * 45) + 1;

                for(int j=0; j< i; j++) {
                    if(numbers[i] == numbers[j]) {
                        i--;
                        break;
                    }
                }
            }
            Arrays.sort(numbers);

            Lotto lotto = Lotto.builder()
                    .round(round)
                    .n1(numbers[0])
                    .n2(numbers[1])
                    .n3(numbers[2])
                    .n4(numbers[3])
                    .n5(numbers[4])
                    .n6(numbers[5])
                    .bonus(0)
                    .member("member" + num)
                    .status("waiting")
                    .price(1000)
                    .build();

            lottoRepository.save(lotto);
        });
    }

    @Test // 회차의 결과 번호
    public void testSelectOne() {
        int round = 4;
        String member = "admin";
        String status = "standard";

        Lotto lotto = lottoRepository.findByRoundAndMemberAndStatus(round, member, status);

        log.info("당첨 번호 : {}", lotto);
    }

    @Test // 회차 별 당첨된 목록
    public void testWinningListAndUpdateResult() {
        int round = 4;
        // 당첨 번호
        Lotto winningNumber = lottoRepository.findByRoundAndMemberAndStatus(round, "admin", "standard");

        int result[] = {winningNumber.getN1(), winningNumber.getN2(), winningNumber.getN3(), winningNumber.getN4(), winningNumber.getN5(), winningNumber.getN6()};
        int bonus = winningNumber.getBonus();

        log.info("당첨 번호 : {}", Arrays.toString(result));
        log.info("보너스 번호 : {}", bonus);

        // 회차 별 구매 목록
        List<Lotto> sellList = lottoRepository.findAllByRoundAndMemberNotAndStatus(round, "admin", "waiting");

//        log.info("회차 별 구매 목록 : {}", sellList);

        for(Lotto lotto : sellList) {
            int myNumbers[] = {lotto.getN1(), lotto.getN2(), lotto.getN3(), lotto.getN4(), lotto.getN5(), lotto.getN6()};
            List<Integer> correctNumbers = new ArrayList<>();

            for(int i=0; i<result.length; i++) {
                for(int j=0; j<myNumbers.length; j++) {
                    if(result[i] == myNumbers[j]) {
                        correctNumbers.add(myNumbers[j]);
                    }
                }
            }

            if(correctNumbers.size() == 6) {
                log.info("1등 당첨자 : {}", lotto);
                Optional<Lotto> optionalLotto = lottoRepository.findById(lotto.getLno());
                Lotto winner =  optionalLotto.orElseThrow();

                // 당첨 인원 수
                int count = lottoRepository.countAllByRoundAndWinResult(round, "1st");
                log.info(count);

                // TODO: 당첨금 비율 + 1/n 해야함
                winner.modify("end", "1st", (winningNumber.getPrice() * 0.8));
                lottoRepository.save(winner);
            }else if(correctNumbers.size() == 5) {
                for(int myNumber : myNumbers) {
                    if(myNumber == bonus) {
                        correctNumbers.add(myNumber);

                        log.info("2등 당첨자 : {}", lotto);

                        Optional<Lotto> optionalLotto = lottoRepository.findById(lotto.getLno());
                        Lotto winner =  optionalLotto.orElseThrow();

                        // 당첨 인원 수
                        int count = lottoRepository.countAllByRoundAndWinResult(round, "2nd");
                        log.info(count);

                        winner.modify("end", "2nd", (winningNumber.getPrice() * 0.15));
                        lottoRepository.save(winner);
                    }
                }
                if(correctNumbers.size() == 5) {
                    log.info("3등 당첨자 : {}", lotto);

                    Optional<Lotto> optionalLotto = lottoRepository.findById(lotto.getLno());
                    Lotto winner =  optionalLotto.orElseThrow();

                    // 당첨 인원 수
                    int count = lottoRepository.countAllByRoundAndWinResult(round, "3rd");
                    log.info(count);

                    winner.modify("end", "3rd", (winningNumber.getPrice() * 0.05));
                    lottoRepository.save(winner);
                }
            } else {
                Optional<Lotto> optionalLotto = lottoRepository.findById(lotto.getLno());
                Lotto winner =  optionalLotto.orElseThrow();

                winner.modify("end", "-", 0);
                lottoRepository.save(winner);
            }

            log.info("일치한 번호 : {}", correctNumbers);
        }
    }
}
