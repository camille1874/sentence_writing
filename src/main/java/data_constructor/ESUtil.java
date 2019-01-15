package data_constructor;

import com.giiso.elasticsearch.TianjiNewsClient;
import com.giiso.elasticsearch.TianjiNewsClientFactory;
import com.sun.deploy.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by xuzh on 2018/11/23.
 */
public class ESUtil {
    private static Logger logger = LogManager.getLogger(ESUtil.class);
    private static TianjiNewsClient client = null;

    private ESUtil() {
    }

    synchronized public static TianjiNewsClient getInstance() {
        if (client == null) {
            Properties pro = new Properties();

            try {
                pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("es_config.properties"));
                String username = pro.getProperty("username");
                String password = pro.getProperty("password");
                String prefix = pro.getProperty("prefix");
                logger.info("Is connecting to ES.");
                client = TianjiNewsClientFactory.initInstance(username, password, prefix);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    public static List<Map<String, Object>> searchDocs(String[] tags, int size, List<String> keywords) {
        List<Map<String, Object>> res;
        try {
            res = client.search(0, buildMatchQuery(tags, keywords), null, size, "textcontent");
        } catch (IOException e) {
            return null;
        }
        return res;
    }

    private static QueryBuilder buildMatchQuery(String[] tags, List<String> keywords) {
        BoolQueryBuilder bqb = new BoolQueryBuilder();
//        bqb.must(QueryBuilders.termsQuery("textcontent", keywords)).must(QueryBuilders.termsQuery("tags", tags));

        bqb.must(QueryBuilders.termsQuery("tags", tags)).must(QueryBuilders.rangeQuery("time")
                .from("2017-06-01 00:00:00")).must(QueryBuilders.matchQuery("textcontent",
                StringUtils.join(keywords, " ")));

        return bqb;
    }
}