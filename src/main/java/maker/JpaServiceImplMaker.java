package maker;

import java.util.HashMap;

public class JpaServiceImplMaker extends ClassMaker {
    private final String daoPackgePath;

    public JpaServiceImplMaker(String packgePath, String daoPackgePath) {
        super(packgePath);
        this.daoPackgePath = daoPackgePath;
    }

    @Override
    public String generateContent(String tableName, String className, HashMap<String, String> columnMap, String primaryKey) {
        String packgeName = getPackgeName(packgePath);
        String daoPackgeName = getPackgeName(daoPackgePath);
        String entityClassName = getEntityClassName(tableName);
        String serviceClassName = entityClassName + "Service";
        String daoClassName = entityClassName + "Dao";
        String daoVariableName = underline2Hump(tableName) + "Dao";

        StringBuilder content = new StringBuilder();
        content.append("package " + packgeName + ";\n");
        content.append("\n");
        content.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        content.append("import org.springframework.stereotype.Service;\n");
        content.append("import " + daoPackgeName + "." + daoClassName + ";\n");
        content.append("\n");
        content.append("/**\n");
        content.append(" *\n");
        content.append(" * @Author " + author + "\n");
        content.append(" * @Date " + date + "\n");
        content.append(" */\n");
        content.append("\n");
        content.append("@Service\n");
        content.append("public class " + className + " implements "+ serviceClassName +" {\n");
        content.append("\n");
        content.append("\tprivate final " + daoClassName + " " + daoVariableName + ";\n");
        content.append("\n");
        content.append("\t@Autowired\n");
        content.append("\tpublic " + className + "(final " + daoClassName + " " + daoVariableName + ") {\n");
        content.append("\t\tthis." + daoVariableName + " = " + daoVariableName +";\n");
        content.append("\t}\n");
        content.append("}\n");
        return content.toString();
    }

    @Override
    public String getClassName(String tableName) {
        return getEntityClassName(tableName) + "ServiceImpl";
    }
}
