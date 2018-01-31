package tickets.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import tickets.model.Plan;
import tickets.service.OrderService;
import tickets.service.PlanService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;

@Controller
public class TimerTasks extends TimerTask {

    @Override
    public void run() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        PlanService planService = (PlanService) ac.getBean("planServiceImpl");
        OrderService orderService = (OrderService) ac.getBean("orderServiceImpl");


        LocalDateTime attentionTime = LocalDateTime.now().plusWeeks(2);

        List<Plan> planList = planService.getPlans();
        for (Plan plan : planList) {
            // 任务1: 每分钟检测活动列表中有没有演出时间在两周后的活动
            if ((plan.getStartTime().equals(attentionTime)
                    || plan.getStartTime().isBefore(attentionTime)) && plan.getIsAssigned() == 0) {
                // 如果有，开始配票
                orderService.assignTickets(plan.getId());
            }

            // 任务2: 每分钟检测活动列表中已经结束的活动，把未使用的订单全部关闭
            if(plan.getEndTime().isBefore(LocalDateTime.now())){
                orderService.closeOrders(plan.getId());
            }
        }




    }
}