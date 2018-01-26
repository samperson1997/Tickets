package tickets.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tickets.bean.OrderBean;
import tickets.service.OrderService;
import tickets.service.PlanService;

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
        planService.updatePlanSeat(orderBean.getPlanId(), orderBean.getSeatName(), -orderBean.getSeatNum());

        // 增加订单表
        String orderId = orderService.addOrder(orderBean);

        // 返回订单id
        return orderId;
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
