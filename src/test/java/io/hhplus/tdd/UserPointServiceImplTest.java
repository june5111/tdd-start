package io.hhplus.tdd;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.UserPointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserPointServiceImplTest {

    @Mock
    private UserPointTable userPointTable;

    @Mock
    private PointHistoryTable pointHistoryTable;

    @InjectMocks
    private UserPointServiceImpl userPointServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUseUserPoint() { // 유저의 포인트가 사용금액보다 적으면 실패해야 한다.

        long amount = 100000;

        //given
        UserPoint userPoint = new UserPoint(1L,10000,System.currentTimeMillis());

        //when
        userPointServiceImpl.usePoint(1L, amount);

        //then
        // 사용할 금액보다 보유 포인트가 적을 시 IllegalArgumentException을 던지게 하여서 해당 메시지와 비교....
        assertThatThrownBy(() -> userPointServiceImpl.usePoint(1L,amount)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포이트가 부족합니다.");

    }

}
