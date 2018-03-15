package Parser;

import java.util.regex.Pattern;

public class RgexPatern {
    public static String  chapterPatternStr = "第.+章";
    public static String disasePatternStr = "^[一，二,三,四,五,六,七,八,九]，\\s*(.*)";
    public static String knowledgePointStr = "【(.*)】";
    public static Pattern chapterPattern = Pattern.compile(chapterPatternStr);
    public static Pattern disasePattern = Pattern.compile(disasePatternStr);
    public static Pattern knowledgePointPattern = Pattern.compile(knowledgePointStr);
}

