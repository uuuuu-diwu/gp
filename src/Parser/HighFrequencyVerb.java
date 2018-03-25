package Parser;



/*
*
* 【临床表现】
出现 {显现，暴露，到，产生，显示，}
发生 {发生，变成，产生，存在，出现，进行，存在}
表现 {呈现，显示}
引起 {造成，致，使发生，造成}
【病因】
发生
导致 {引起，造成，致，结果}
引起
损害 {专业用词}
感染 {专业用词}
出现

*
*
* */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//class InnerHFV {
//    private static Pattern chuxian = Pattern.compile("出现|显现|暴露|到|产生|显示(.*)(，|。)*");//出现 kp，kp，kp.....
//    private static Pattern fashen = Pattern.compile("发生|变成|产生|出现|进行|存在(.*)(，|。)*");
//    private static Pattern biaoxian = Pattern.compile("呈现|显示");
//    private static Pattern yinqi = Pattern.compile("造成|致|使发生|引起(.*)(，|。)")；
//    private static Pattern daozhi = Pattern.compile("(，|。)*(.*)引起|造成|致|所致|结果(.*)(，|。)*");
//    private static Pattern sunhai = Pattern.compile("(，|。)*(.*)损害(.*)(，|。)*");
//    //private static Pattern ganran = Pattern.compile("")
//}

public class HighFrequencyVerb {
    private static Pattern chuxian = Pattern.compile("(出现|显现|暴露|到|产生|显示|包括)(.*)(，|。)*");//出现 kp，kp，kp.....
    private static Pattern fashen = Pattern.compile("(发生|变成|产生|出现|存在|致)(.*)(，|。)*");
    private static Pattern biaoxian = Pattern.compile("(呈现|显示)(.*)(，|。)*");
    private static Pattern yinqi = Pattern.compile("(造成|致|使发生|引起)(.*)");
    private static Pattern daozhi = Pattern.compile("(，|。)*(.*)(引起|造成|致|所致|结果)(.*)(，|。)*");
    private static Pattern sunhai = Pattern.compile("(，|。)*(.*)(损害){1,1}(.*)(，|。)*");
    private static int PATTERNUM = 6;
    public static String etiologyParser(String line) {
        Matcher mat = chuxian.matcher(line);
        if(mat.find()) {
            //todo
            return mat.group(2);
        }
        mat = fashen.matcher(line);
        if(mat.find()) {
            //todo
            String t1 = mat.group(0);
            String t2 = mat.group(1);
            String t3 = mat.group(2);
            return mat.group(2);
        }
        mat = biaoxian.matcher(line);
        if(mat.find()) {
            //todo
            return mat.group(2);
        }
        mat = yinqi.matcher(line);
        if(mat.find()) {
            //todo
            String t1 = mat.group(1);
            return mat.group(2);
        }
        mat = daozhi.matcher(line);
        if(mat.find()) {
            return  mat.group(1) + "、" + mat.group(3);
        }
        return null;
    }
    public static String diagnoParser(String line) {
        Matcher mat = chuxian.matcher(line);
        if(mat.find()) {
            //todo
            return mat.group(2);
        }
        mat = fashen.matcher(line);
        if(mat.find()) {
            //todo
            return mat.group(2);
        }
        mat = daozhi.matcher(line);
        if(mat.find()) {
            //tod
            String t0 = mat.group(0);
            String t1 = mat.group(1);
            String t2 = mat.group(2);
            String t3 = mat.group(3);
            String t4 = mat.group(4);
            return mat.group(2) + "、" + mat.group(4);
        }
        mat = yinqi.matcher(line);
        if(mat.find()) {
            //todo
            return mat.group(2);
        }
        return null;
    }
}
