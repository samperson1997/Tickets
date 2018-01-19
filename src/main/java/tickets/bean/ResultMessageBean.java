package tickets.bean;

public class ResultMessageBean {
    /**
     * 操作结果：成功或失败
     */
    public boolean result;

    /**
     * 操作结果的提示信息
     */
    public String message;

    public ResultMessageBean(boolean result) {
        this.result = result;
        this.message = "success";
    }

    public ResultMessageBean(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
}
