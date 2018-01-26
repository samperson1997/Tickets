package tickets.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tickets.bean.OrderBean;
import tickets.bean.ResultMessageBean;
import tickets.service.OrderService;
import tickets.service.PlanService;

import java.util.Timer;
import java.util.TimerTask;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PlanService planService;

    @RequestMapping(
            value = "/addOrder",
            method = RequestMethod.POST,
            consumes = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public String addOrder(@RequestBody OrderBean orderBean) {
        // 更新座位信息
        if (!orderBean.getSeatName().equals("")) {
            planService.updatePlanSeat(orderBean.getPlanId(), orderBean.getSeatName(), -orderBean.getSeatNum());
        }

        // 增加订单表
        String orderId = orderService.addOrder(orderBean);

        // 倒计时15分钟
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            public void run() {

                // 取消订单
                if (orderService.getOrderByOrderId(orderId).getState().equals("未支付")) {
                    cancelOrder(orderId);
                }
                cancel();

            }
        };
        timer.scheduleAtFixedRate(task, 900000L, 900000L);

        // 返回订单id
        return orderId;
    }

    @RequestMapping(
            value = "/cancelOrder",
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResultMessageBean cancelOrder(String orderId) {

        OrderBean orderBean = orderService.getOrderByOrderId(orderId);

        // 更新座位信息
        if (!orderBean.getSeatName().equals("")) {
            planService.updatePlanSeat(orderBean.getPlanId(), orderBean.getSeatName(), orderBean.getSeatNum());
        }

        // 修改订单状态
        orderBean.setState("已关闭");
        orderService.updateOrder(orderBean);

        return new ResultMessageBean(true);
    }

    @RequestMapping(
            value = "/changeOrder",
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResultMessageBean changeOrderState(String orderId, String state) {

        OrderBean orderBean = orderService.getOrderByOrderId(orderId);
        orderBean.setState(state);
        orderService.updateOrder(orderBean);

        return new ResultMessageBean(true);
    }

    @RequestMapping(
            value = "/order",
            method = RequestMethod.GET
    )
    @ResponseBody
    public OrderBean getOrder(String orderId) {

        return orderService.getOrderByOrderId(orderId);
    }

}
