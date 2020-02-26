import maker.*;
import utils.DatabaseMetadataUtil;
import utils.PropertiesUtil;

import java.util.List;

public class Main {

    // 实体类包路径(绝对路径)
    private final static String entityPackgePath = PropertiesUtil.getProperty("package.path.entity");
    // Dao层包路径(绝对路径)
    private final static String daoPackgePath = PropertiesUtil.getProperty("package.path.dao");
    // Service层包路径(绝对路径)
    private final static String servicePackgePath = PropertiesUtil.getProperty("package.path.service");
    // Service Impl包路径(绝对路径)
    private final static String serviceImplPackgePath = PropertiesUtil.getProperty("package.path.service-impl");
    // Controller层包路径(绝对路径)
    private final static String controllerPackgePath = PropertiesUtil.getProperty("package.path.controller");

    // 是否生成Entity
    private final static Boolean makeEntity = Boolean.valueOf(PropertiesUtil.getProperty("make.entity"));

    // 是否生成Dao
    private final static Boolean makeDao = Boolean.valueOf(PropertiesUtil.getProperty("make.dao"));

    // 是否生成Service
    private final static Boolean makeService = Boolean.valueOf(PropertiesUtil.getProperty("make.service"));

    // 是否生成Service
    private final static Boolean makeServiceImpl = Boolean.valueOf(PropertiesUtil.getProperty("make.service-impl"));

    // 是否生成Controller
    private final static Boolean makeController = Boolean.valueOf(PropertiesUtil.getProperty("make.controller"));

    // 是否全量生成（true:生成该数据库所有表对应的类，false:生成该数据库指定的表对应的类）
    private final static Boolean allTable = Boolean.valueOf(PropertiesUtil.getProperty("number.all-table"));

    // 当allTable参数为false时，指定生成的表名
    private final static String tableName = PropertiesUtil.getProperty("number.table-name");

    // 作者姓名（选填）
    private final static String author = PropertiesUtil.getProperty("maker.author");

    public static void make(String tableName) {
        ClassMaker classMaker = null;
        if (makeEntity) {
            classMaker = new JpaEntityMaker(entityPackgePath);
            classMaker.setAuthor(author);
            classMaker.generate(tableName);
        }
        if (makeDao) {
            classMaker = new JpaDaoMaker(daoPackgePath, entityPackgePath);
            classMaker.setAuthor(author);
            classMaker.generate(tableName);
        }
        if (makeService) {
            classMaker = new JpaServiceMaker(servicePackgePath);
            classMaker.setAuthor(author);
            classMaker.generate(tableName);
        }
        if (makeServiceImpl) {
            classMaker = new JpaServiceImplMaker(serviceImplPackgePath, daoPackgePath);
            classMaker.setAuthor(author);
            classMaker.generate(tableName);
        }
        if (makeController) {
            classMaker = new JpaControllerMaker(
                    controllerPackgePath,
                    servicePackgePath,
                    entityPackgePath);
            classMaker.setAuthor(author);
            classMaker.generate(tableName);
        }

    }
    public static void main(String[] args) {
        DatabaseMetadataUtil databaseMetadataUtil = new DatabaseMetadataUtil();

        if (allTable) {
            List<String> tableList = databaseMetadataUtil.getTables();
            for (String tableName: tableList) {
                make(tableName);
            }
        } else {
            make(tableName);
        }
    }
}
