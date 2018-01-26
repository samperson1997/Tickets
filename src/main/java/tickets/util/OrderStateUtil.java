package tickets.util;

import tickets.bean.OrderBean;
import tickets.model.Order;

public class OrderStateUtil {

    public Order updateOrderState(OrderBean orderBean, Order order) {
        switch (orderBean.getState()) {
            case "未开票":
                order.setIsPaid(1);
                break;
            case "已开票":
                order.setIsAssigned(1);
                break;
            case "未配票":
                order.setIsPaid(1);
                break;
            case "已配票":
                order.setIsAssigned(1);
                break;
            case "已使用":
                order.setIsUsed(1);
                order.setIsClosed(1);
                break;
            case "已关闭":
                order.setIsClosed(1);
                break;
        }

        return order;
    }

    public String getOrderState(int isPaid, int isSeatSelected, int isAssigned, int isUsed, int isClosed) {
        if (isPaid == 0) {
            return "未支付";
        } else if (isUsed == 0 && isClosed == 0) {
            if (isAssigned == 0) {
                if (isSeatSelected == 1) {
                    return "未开票";
                } else if (isSeatSelected == 0) {
                    return "未配票";
                }
            } else if (isAssigned == 1) {
                if (isSeatSelected == 1) {
                    return "已开票";
                } else if (isSeatSelected == 0) {
                    return "已配票";
                }
            }
        } else if (isClosed == 1) {
            return "已关闭";
        } else if (isUsed == 1) {
            return "已使用";
        }

        return "";
    }
}
