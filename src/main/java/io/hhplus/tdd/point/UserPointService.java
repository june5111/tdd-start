package io.hhplus.tdd.point;

import java.util.List;

public interface UserPointService {
    //사용 할 기능 interface 구현
    UserPoint getUserPoint(long id);
    List<PointHistory> getUserPointHistory(long id);
    UserPoint chargePoint(long id, long amount);
    UserPoint usePoint(long id, long amount);

}
