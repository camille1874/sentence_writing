package data_constructor;

import java.util.*;

/**
 * Created by xuzh on 2019/1/15.
 */
public class DataConstructor {
    public static Map<String, List<String>> key_map;

    public DataConstructor() {
        List<String> keywords_motivation = new ArrayList<String>() {{
            add("发动机");
            add("功率");
            add("峰值扭矩");
        }};
        List<String> keywords_look = new ArrayList<String>() {{
//            add("车身尺寸");
            add("长宽高");
            add("轴距");
        }};
        List<String> keywords_main = new ArrayList<String>() {{
            add("中型");
            add("黑色");
        }};
        key_map = new HashMap<>();
        key_map.put("外观", keywords_look);
        key_map.put("动力", keywords_motivation);
        key_map.put("描述", keywords_main);
    }

    public void getData(int size, String[] tags, String type, List<String> shieldWords, boolean hasPara) {
        ESUtil.getInstance();
        List<String> keywords = key_map.get(type);
        List<Map<String, Object>> docs = ESUtil.searchDocs(tags, size, keywords);
        List<String> results = TextUtil.parseResult(docs, keywords, type, shieldWords, hasPara);
        FileUtil.writeFile(type + ".txt", results, true);
    }

    public void getData(int size, String[] tags, String type, boolean hasPara) {
        ESUtil.getInstance();
        List<String> keywords = key_map.get(type);
        List<Map<String, Object>> docs = ESUtil.searchDocs(tags, size, keywords);
        List<String> results = TextUtil.parseResult(docs, keywords, type, null, hasPara);
        FileUtil.writeFile(type + ".txt", results, true);
    }

    public static void main(String[] args) {
        DataConstructor d = new DataConstructor();
        String[] tags = {"汽车"};

//        String type = "动力";
//        List<String> shieldWords = new ArrayList<String>(){{add("两"); add("分别");}};
//        d.getData(3000, tags, type, shieldWords, true);

//        String type = "外观";
//        List<String> shieldWords = new ArrayList<String>(){{add("："); }};
//        d.getData(3000, tags, type, shieldWords, true);


//        String type = "描述";
//        String[] color = {"白色", "黑色", "银色", "红色", "黄色", "蓝色", "紫色", "绿色", "橙色"};
//        String[] carType = {"中型", "小型", "微型", "大中型", "大型", "紧凑型", "中小型"};
//        for (int i = 0; i < color.length; ++i) {
//            for (int j = 0; j < carType.length; ++j) {
//                System.out.println("当前属性:" + carType[j] + "," + color[i]);
//                List<String> keywords_main = new ArrayList<>();
//                keywords_main.add(carType[j]);
//                keywords_main.add(color[i] + "车身");
//                key_map.put(type, keywords_main);
//                d.getData(3000, tags, type, false);
//            }
//        }

//        String type = "单一属性_颜色";
//        String[] color = {"白色车身", "黑色车身", "银色车身", "红色车身", "黄色车身", "蓝色车身", "紫色车身", "绿色车身", "橙色车身"};
//        for (int i = 0; i < color.length; ++i) {
//            List<String> l = new ArrayList<>();
//            l.add(color[i]);
//            key_map.put(type, l);
////            List<String> shieldWords = Arrays.asList(color);
////            shieldWords.remove(i); //UnsupportedOperationException
//            List<String> shieldWords = new ArrayList<>();
//            for (int k = 0; k < color.length; ++k) {
//                if (k != i) {
//                    shieldWords.add(color[k]);
//                }
//            }
//            d.getData(5000, tags, type, shieldWords, false);
//            key_map.clear();
//        }

        String type = "单一属性_类型";
        String[] carType = {"中型车", "小型车", "微型车", "大中型车", "大型车", "紧凑型车", "中小型车"};
        for (int i = 0; i < carType.length; ++i) {
            List<String> l = new ArrayList<>();
            l.add(carType[i]);
            key_map.put(type, l);
            List<String> shieldWords = new ArrayList<>();
            for (int k = 0; k < carType.length; ++k) {
                if (k != i) {
                    shieldWords.add(carType[k]);
                }
            }
            shieldWords.add("排名");
            d.getData(5000, tags, type, shieldWords, false);
            key_map.clear();
        }
    }
}
