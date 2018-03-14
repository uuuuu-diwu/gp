package Parser;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DiagnosisKp {
    public static String cnNumAndWordPatternStr = "（[一,二,三,四,五,六,七,八,九,十]）(.+)";
    public static String numAndWordPatternStr = "[1-9]+\\.*(.*)";
    public static String wordAndDescriptionPatternStr = "（[1-9]）(.+)：(.+)";
    public static Pattern cnNumAndWordPattern = Pattern.compile(cnNumAndWordPatternStr);
    public static Pattern numAndWordPattern = Pattern.compile(numAndWordPatternStr);
    public static Pattern wordAndDescriptionPattern = Pattern.compile(wordAndDescriptionPatternStr);
    private static void constructSymptom(Node parent,BufferedReader br) {
        String line;
        while((line = Txt2Tree.markAndReadLine(br)) != null) {
            if(numAndWordPattern.matcher(line).find()
                    ||cnNumAndWordPattern.matcher(line).find()
                    ||RgexPatern.knowledgePointPattern.matcher(line).find()) {
                Txt2Tree.readOffsetRest(br);
                break;
            }
            Matcher wordAndDescription = wordAndDescriptionPattern.matcher(line);
            if(wordAndDescription.find()) {
                Node word = new Node(wordAndDescription.group(1));
                Vocab vocab = new Vocab(wordAndDescription.group(2),word);
                word.insertVocab(vocab);
                parent.insertChild(word);
                word.setParent(parent);
            }
            else { //todo 分词或者其他处理

            }
        }

    }
    private static void constructSpecialCheck(Node parent,BufferedReader br) {
        String line;
        while((line = Txt2Tree.markAndReadLine(br)) != null) {
            if(cnNumAndWordPattern.matcher(line).find()
                    ||RgexPatern.knowledgePointPattern.matcher(line).find()) {
                Txt2Tree.readOffsetRest(br);
                break;
            }
            Matcher numAndWord = numAndWordPattern.matcher(line);
            if(numAndWord.find()) {
                Node word = new Node(numAndWord.group(1));
                parent.insertChild(word);
                word.setParent(parent);
            }
        }
    }
    public static Node constructDiagnosis(BufferedReader br) {
        String line;
        Node res = new Node("诊断要点");
        while((line = Txt2Tree.markAndReadLine(br)) != null) {
            if(RgexPatern.chapterPattern.matcher(line).find()
                    ||RgexPatern.knowledgePointPattern.matcher(line).find()) {
                Txt2Tree.readOffsetRest(br);
                break;
            }
            Matcher cnNumAndWord = cnNumAndWordPattern.matcher(line);
            Matcher numAndWord = numAndWordPattern.matcher(line);
            if(cnNumAndWord.find()) {
                String word = cnNumAndWord.group(1);
                if(word == "临床表现") {
                    continue;
                }
                else if(word == "特殊检查") {
                    Node specialCheck = new Node("特殊检查");
                    constructSpecialCheck(specialCheck,br);
                    res.insertChild(specialCheck);
                    specialCheck.setParent(res);
                }
                else {
                    continue;
                    //todo
                }
            }
            else if(numAndWord.find()) {
                String word = numAndWord.group(1);
                if(word == "症状") {
                    Node symptom = new Node("症状");
                    constructSymptom(symptom,br);
                    res.insertChild(symptom);
                    symptom.setParent(res);
                }
                else if(word == "体征") {
                    Node sign = new Node("体征");
                    constructSymptom(sign,br);
                    res.insertChild(sign);
                    sign.setParent(res);
                }
            }
        }
        return res;
    }
}
