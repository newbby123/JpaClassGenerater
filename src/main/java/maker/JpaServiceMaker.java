package maker;

import utils.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Service层创建者
 * @Author shalt gao
 * @Date
 */

public class JpaServiceMaker extends ClassMaker {

    public JpaServiceMaker(String packgePath) {
        super(packgePath);
    }

    @Override
    public String generateContent(String tableName, String className, HashMap<String, String> columnMap, String primaryKey) {
        String packgeName = getPackgeName(packgePath);

        StringBuilder content = new StringBuilder();
        content.append("package " + packgeName + ";\n");
        content.append("\n");
        content.append("/**\n");
        content.append(" *\n");
        content.append(" * @Author " + author + "\n");
        content.append(" * @Date " + date + "\n");
        content.append(" */\n");
        content.append("\n");
        content.append("public interface " + className + " {\n");
        content.append("\n");
        content.append("}\n");
        return content.toString();
    }

    @Override
    public String getClassName(String tableName) {
        return getEntityClassName(tableName) + "Service";
    }
}
