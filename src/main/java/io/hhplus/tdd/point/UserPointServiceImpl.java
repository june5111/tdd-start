package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserPointServiceImpl implements UserPointService {

    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;

    public UserPointServiceImpl(UserPointTable userPointTable, PointHistoryTable pointHistoryTable) {
        this.userPointTable = userPointTable;
        this.pointHistoryTable = pointHistoryTable;
    }

    @Override
    public UserPoint getUserPoint(long id) {
        return userPointTable.selectById(id);
    }

    @Override
    public List<PointHistory> getUserPointHistory(long id) {
        return pointHistoryTable.selectAllByUserId(id);
    }

    @Override
    public UserPoint chargePoint(long id, long amount) {

        UserPoint userPoint = userPointTable.selectById(id);
        userPoint = userPointTable.insertOrUpdate(id,userPoint.point() + amount);
        pointHistoryTable.insert(id, amount, TransactionType.CHARGE, System.currentTimeMillis());

        return userPoint;
    }

    @Override
    public UserPoint usePoint(long id, long amount) {

        UserPoint userPoint = userPointTable.selectById(id);

        if(userPoint.point() == 0) { // 포인트가 0 일시 IllegalArgumentException으로 예외 날리기
            throw new IllegalArgumentException("포인트가 없습니다.");
        }

        if(userPoint.point() < amount) { // 보유 포인트가 amount보다 작을 때 IllegalArgumentException으로 예외 날리기
            throw new IllegalArgumentException("포이트가 부족합니다.");
        }

        userPoint = userPointTable.insertOrUpdate(id, userPoint.point() - amount);
        pointHistoryTable.insert(id, -amount, TransactionType.USE, System.currentTimeMillis());


        return userPoint;
    }


}
