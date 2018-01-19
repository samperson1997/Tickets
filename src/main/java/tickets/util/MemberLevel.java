package tickets.util;

public class MemberLevel {
    private final String[] levelName = {"大众会员", "青铜会员", "白银会员", "黄金会员", "鸣钻会员"};
    private final double[] levelDiscount = {0.99, 0.95, 0.9, 0.85, 0.8};

    public String getLevelName(int level) {
        return levelName[level - 1];
    }

    private double getLevelDiscount(int level) {
        return levelDiscount[level - 1];
    }
}
