package maker;

import org.apache.commons.lang3.StringUtils;
import utils.DatabaseMetadataUtil;
import utils.DateTimeUtil;
import utils.FileUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public abstract class ClassMaker {
    protected final String packagePath;
    protected String author = System.getProperty("user.name");
    protected final String date = DateTimeUtil.dateTimeToString(LocalDateTime.now(), null);

    public ClassMaker(String packgePath) {
        this.packagePath = packgePath;
    }

    public void generate(String tableName) {
        String className = getClassName(tableName);
        String path = packagePath + "\\" + className + ".java";

        DatabaseMetadataUtil databaseMetadataUtil = new DatabaseMetadataUtil();
        String primaryKey = databaseMetadataUtil.getPrimaryKey(tableName);

        HashMap<String, String> columnMap = databaseMetadataUtil.getColumnsAndTypeMap(tableName);

        if (StringUtils.isBlank(primaryKey)) {
            throw new RuntimeException("表未设置主键");
        }
        if (columnMap.get(primaryKey) == null) {
            throw new RuntimeException("id字段未找到！");
        }

        BufferedWriter writer = FileUtil.createFileWriter(path);
        try {
            writer.write(generateContent(tableName, className, columnMap, primaryKey));
            writer.close();
            databaseMetadataUtil.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String generateContent(String tableName, String className, HashMap<String, String> columnMap, String primaryKey);

    public abstract String getClassName(String tableName);

    public String getEntityClassName(String tableName) {
        if (StringUtils.isBlank(tableName) || tableName.length() <= 0) {
            throw new RuntimeException("表名为空");
        }
        if (tableName.length() == 1) {
            return tableName.toUpperCase();
        }
        String className = underline2Hump(tableName);
        return className.substring(0, 1).toUpperCase() + className.substring(1);
    }

    /**
     * 下划线转小驼峰
     * @param underlineName
     * @return
     */
    public String underline2Hump(String underlineName) {
        underlineName = underlineName.toLowerCase();
        char[] charArray = underlineName.toCharArray();
        // 判断前一个字符是否为"_"
        boolean underlineBefor = false;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == 95) {
                underlineBefor = true;
            } else if (underlineBefor) {
                buffer.append(charArray[i] -= 32);
                underlineBefor = false;
            } else {
                buffer.append(charArray[i]);
            }
        }
        return buffer.toString();
    }

    public void setAuthor(String author) {
        if (StringUtils.isBlank(author)) {
            author = System.getProperty("user.name");
        }
        this.author = author;
    }

    public String getPackgeName (String packgePath) {
        if (StringUtils.isBlank(packgePath)) {
            return null;
        }
        return packgePath.substring(packgePath.indexOf("java\\")).replace("\\", ".").replace("java.", "");
    }

}
