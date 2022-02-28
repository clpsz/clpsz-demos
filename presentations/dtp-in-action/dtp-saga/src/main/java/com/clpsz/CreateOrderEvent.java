package com.clpsz;

import com.clpsz.domain.SagaLog;
import com.clpsz.enums.SagaLogEventStatus;
import com.clpsz.enums.SagaStatus;
import com.clpsz.service.ItemStockService;
import com.clpsz.service.OrderService;
import com.clpsz.service.SagaLogService;
import com.clpsz.service.SagaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author clpsz
 */
public class CreateOrderEvent extends Event {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public final static String INIT = "INIT";

    public final static String CREATE_ORDER = "CREATE_ORDER";
    public final static String DEDUCT_STOCK = "DEDUCT_STOCK";
    public final static String FINISHED = "FINISHED";

    private Long sagaId;
    private Long itemId;
    private Long itemCount;

    private final SagaService sagaService = new SagaService();
    private final OrderService orderService = new OrderService();
    private final ItemStockService itemStockService = new ItemStockService();
    private final SagaLogService sagaLogService = new SagaLogService();

    public final Map<String, String> ORDER_TRANSIT_FORWARD = new HashMap<String, String>(){{
        put(INIT, CREATE_ORDER);
        put(CREATE_ORDER, DEDUCT_STOCK);
        put(DEDUCT_STOCK, FINISHED);
    }};

    public CreateOrderEvent(Long sagaId, String name, Long itemId, Long itemCount, Boolean succeed) {
        super(sagaId, name, succeed);

        this.sagaId = sagaId;
        this.itemId = itemId;
        this.itemCount = itemCount;
    }

    public void processEvent() throws SQLException {
        if(this.getSucceed()) {
            processEventForward();
        } else {
            processCancel();
        }
    }

    private void processEventForward() throws SQLException {
        String name = this.getName();
        String next = ORDER_TRANSIT_FORWARD.get(name);

        switch (name) {
            case INIT:
                // pass
                break;
            case CREATE_ORDER:
                sagaLogService.updateSagaLogEventStatus(sagaId, CREATE_ORDER, SagaLogEventStatus.SUCCEED.getVal());
                break;
            case DEDUCT_STOCK:
                sagaLogService.updateSagaLogEventStatus(sagaId, DEDUCT_STOCK, SagaLogEventStatus.SUCCEED.getVal());
                break;
            default:
                throw new RuntimeException("unknown CreateOrderEvent");
        }

        switch (next) {
            case CREATE_ORDER:
                sagaLogService.insertSagaLog(sagaId, CREATE_ORDER);
                orderService.createOrder(sagaId, itemId, itemCount, 100L);
                break;
            case DEDUCT_STOCK:
                sagaLogService.insertSagaLog(sagaId, DEDUCT_STOCK);
                itemStockService.deductStock(sagaId, itemId, itemCount);
                break;
            case FINISHED:
                sagaService.updateSagaStatus(sagaId, SagaStatus.FINISHED.getVal());
                break;
            default:
                throw new RuntimeException("unknown CreateOrderEvent");
        }
    }

    private void processCancel() throws SQLException {
        List<SagaLog> sagaLogList = sagaLogService.selectSagaLog(sagaId);

        Collections.reverse(sagaLogList);
        for (SagaLog sagaLog: sagaLogList) {
            if (sagaLog.getEventStatus().equals(SagaLogEventStatus.CANCELED.getVal())) {
                logger.info("already canceled {}", sagaLog.getEvent());
                continue;
            }

            if (sagaLog.getEvent().equals(DEDUCT_STOCK)) {
                if (itemStockService.cancelDeductStock(sagaId, itemId)) {
                    sagaLogService.updateSagaLogEventStatus(sagaId, DEDUCT_STOCK, SagaLogEventStatus.CANCELED.getVal());
                } else {
                    logger.warn("cancel deduct stock failed");
                    return;
                }
            }
            if (sagaLog.getEvent().equals(CREATE_ORDER)) {
                if (orderService.cancelOrder(sagaId)) {
                    sagaLogService.updateSagaLogEventStatus(sagaId, CREATE_ORDER, SagaLogEventStatus.CANCELED.getVal());
                } else {
                    logger.warn("cancel create order failed");
                    return;
                }
            }
        }

        sagaService.updateSagaStatus(sagaId, SagaStatus.CANCELED.getVal());
    }

    @Override
    public Long getSagaId() {
        return sagaId;
    }

    @Override
    public void setSagaId(Long sagaId) {
        this.sagaId = sagaId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemCount() {
        return itemCount;
    }

    public void setItemCount(Long itemCount) {
        this.itemCount = itemCount;
    }
}
