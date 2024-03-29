package tickets.service;

import tickets.bean.ManagerStatisticBean;
import tickets.bean.ResultMessageBean;
import tickets.bean.VenueMiniBean;

import java.util.List;

public interface ManagerService {

    /**
     * 获得待审批场馆列表
     *
     * @return
     */
    List<VenueMiniBean> getUncheckedVenues();

    /**
     * 审批场馆
     *
     * @param id     场馆id
     * @param result 审批结果, 1表示通过, -1表示不通过
     * @return
     */
    ResultMessageBean checkVenue(String id, int result);

    /**
     * 获得系统统计信息
     *
     * @return
     */
    ManagerStatisticBean getManagerStatistic();

    /**
     * 场馆支付结算
     *
     * @return
     */
    ResultMessageBean charge();
}
