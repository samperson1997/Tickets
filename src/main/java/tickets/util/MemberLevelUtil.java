package tickets.util;

public class MemberLevelUtil {
    private final String[] levelName = {"大众会员", "青铜会员", "白银会员", "黄金会员", "鸣钻会员"};
    private final double[] levelDiscount = {0.99, 0.95, 0.9, 0.85, 0.8};
    private final int[] levelScore = {0, 10000, 30000, 60000, 100000};

    public String getLevelName(int score) {

        for (int i = 0; i < levelScore.length - 1; i++) {
            if (score >= levelScore[i] && score < levelScore[i + 1]) {
                return levelName[i];
            }
        }
        return levelName[levelScore.length - 1];
    }

    public double getLevelDiscount(int score) {
        for (int i = 0; i < levelScore.length - 1; i++) {
            if (score >= levelScore[i] && score < levelScore[i + 1]) {
                return levelDiscount[i];
            }
        }
        return levelDiscount[levelScore.length - 1];
    }
}
