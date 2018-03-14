package Parser;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class EtiologyParser {
    public static String linePatternStr = "(（[1-9]+）+)(.+)：(.+)";//如果匹配失败，说明没有“：”，则进行分词处理
    public static Pattern linePattern = Pattern.compile(linePatternStr);
    public static Node constructEtiology(BufferedReader br) {
        String line;
        Node res = new Node("病因");
        while((line = Txt2Tree.markAndReadLine(br)) != null) {
            if(RgexPatern.chapterPattern.matcher(line).find()
                    ||RgexPatern.knowledgePointPattern.matcher(line).find()) {
                Txt2Tree.readOffsetRest(br);
                break;
            }
            Matcher mat = linePattern.matcher(line);
            if(mat.find()) {
                String word = mat.group(2);
                String description = mat.group(3);
                //normal format word:description
                Node concreteEtiology = new Node(word);
                Vocab vocab = new Vocab(description,concreteEtiology);
                concreteEtiology.insertVocab(vocab);
                concreteEtiology.setParent(res);
                res.insertChild(concreteEtiology);
            }
            else {
                //分词处理
                //todo
            }
        }
        return res;
    }
}
