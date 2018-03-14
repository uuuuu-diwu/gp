package Parser;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TreatmentParser {
    public static String cnNumAndWordPatternStr = "（[一,二,三,四,五,六,七,八,九,十]）(.+)";
    public static String numAndWordPatternStr = "[1-9]+\\.*(.*)";
    //public static String wordAndDescriptionPatternStr = "（[1-9]）(.+)：(.+)";
    public static Pattern cnNumAndWordPattern = Pattern.compile(cnNumAndWordPatternStr);
    public static Pattern numAndWordPattern = Pattern.compile(numAndWordPatternStr);

    //public static Pattern wordAndDescriptionPattern = Pattern.compile(wordAndDescriptionPatternStr);
    private static void innerConstruct(Node parent, BufferedReader br) {
        String line;
        while ((line = Txt2Tree.markAndReadLine(br)) != null) {
            if (cnNumAndWordPattern.matcher(line).find()
                    || RgexPatern.knowledgePointPattern.matcher(line).find()) {
                Txt2Tree.readOffsetRest(br);
                break;
            }
            Matcher numAndWord = numAndWordPattern.matcher(line);
            if (numAndWord.find()) {
                Node word = new Node(numAndWord.group(1));
                word.setParent(parent);
                parent.insertChild(word);
            }
        }
    }

    public static Node constructTreatment(BufferedReader br) {
        String line;
        Node treatment = new Node("治疗");
        while ((line = Txt2Tree.markAndReadLine(br)) != null) {
            if (RgexPatern.chapterPattern.matcher(line).find()
                    || RgexPatern.knowledgePointPattern.matcher(line).find()) {
                Txt2Tree.readOffsetRest(br);
                break;
            }
            Matcher concreteTreatment = cnNumAndWordPattern.matcher(line);
            if (concreteTreatment.find()) {
                Node concreteTreatmentNode = new Node(concreteTreatment.group(1));
                innerConstruct(concreteTreatmentNode, br);
                treatment.insertChild(concreteTreatmentNode);
                concreteTreatmentNode.setParent(treatment);
            }
        }
        return treatment;
    }
}