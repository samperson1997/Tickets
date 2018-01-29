package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tickets.bean.ManagerStatisticBean;
import tickets.bean.ResultMessageBean;
import tickets.bean.VenueMiniBean;
import tickets.service.ManagerService;

import java.util.List;

@Controller
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    /**
     * 获得待审核场馆列表
     *
     * @return
     */
    @RequestMapping(
            value = "/uncheckedVenues",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<VenueMiniBean> getUncheckedVenues() {

        return managerService.getUncheckedVenues();
    }

    /**
     * 审核场馆
     *
     * @param id
     * @param result
     * @return
     */
    @RequestMapping(
            value = "/checkVenue",
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResultMessageBean checkedVenue(String id, int result) {

        return managerService.checkVenue(id, result);
    }

    /**
     * 获得系统统计信息
     *
     * @return
     */
    @RequestMapping(
            value = "/managerStatistics",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ManagerStatisticBean getManagerStatistic() {

        return managerService.getManagerStatistic();
    }

    /**
     * 场馆支付结算
     *
     * @return
     */
    @RequestMapping(
            value = "charge",
            method = RequestMethod.GET
    )
    @ResponseBody
    public ResultMessageBean charge() {

        return managerService.charge();
    }
}
