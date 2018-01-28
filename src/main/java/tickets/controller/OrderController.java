package tickets.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tickets.bean.OrderBean;
import tickets.bean.OrderPlanBean;
import tickets.bean.OrderStatisticBean;
import tickets.bean.ResultMessageBean;
import tickets.service.OrderService;
import tickets.service.PlanService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PlanService planService;

    /**
     * 下新订单
     *
     * @param orderBean
     * @return
     */
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

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
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

    /**
     * 更换订单状态
     *
     * @param orderId
     * @return
     */
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

    /**
     * 查看订单是否有效
     *
     * @param orderId
     * @return
     */
    @RequestMapping(
            value = "/checkValidation",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ResultMessageBean checkValidation(String orderId, String venueId) {

        return orderService.checkValidation(orderId, venueId);
    }

    /**
     * 获得订单详细信息
     *
     * @param orderId
     * @return
     */
    @RequestMapping(
            value = "/order",
            method = RequestMethod.GET
    )
    @ResponseBody
    public OrderBean getOrderByOrderId(String orderId) {

        return orderService.getOrderByOrderId(orderId);
    }

    /**
     * 获得会员订单列表
     *
     * @param email
     * @return
     */
    @RequestMapping(
            value = "/list",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<OrderPlanBean> getOrderByEmail(String email) {

        return orderService.getOrderByEmail(email);
    }

    /**
     * 获得会员订单统计数据
     *
     * @param email
     * @return
     */
    @RequestMapping(
            value = "/statistics",
            method = RequestMethod.GET
    )
    @ResponseBody
    public OrderStatisticBean getOrderStatistic(String email) {

        return orderService.getOrderStatistic(email);
    }

}
