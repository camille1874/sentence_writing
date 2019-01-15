package data_constructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by giiso on 2019/1/15.
 */
public class DataConstructor {
    public void getData(int size){
        ESUtil.getInstance();
        List<String> keywords = new ArrayList<String>() {{add("发动机"); add("功率"); add("峰值扭矩"); }};
        String[] tags = {"汽车"};
        List<Map<String, Object>> docs = ESUtil.searchDocs(tags, size, keywords);
        List<String> results = TextUtil.parseResult(docs, keywords);
        FileUtil.writeFile("test.txt", results);
    }

    public static void main(String[] args)
    {
        DataConstructor d = new DataConstructor();
        d.getData(3000);
    }
}
