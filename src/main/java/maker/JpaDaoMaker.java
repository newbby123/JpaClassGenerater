package maker;

import mapping.TypeMapping;

import java.util.HashMap;

public class JpaDaoMaker extends ClassMaker {
    private final String entityPackgePath;

    public JpaDaoMaker(String packgePath, String entityPackgePath) {
        super(packgePath);
        this.entityPackgePath = entityPackgePath;
    }

    @Override
    public String generateContent(String tableName, String className, HashMap<String, String> columnMap, String primaryKey) {
        String packgeName = getPackgeName(packagePath);
        String entityPackgeName = getPackgeName(entityPackgePath);

        String idType = columnMap.get(primaryKey).split("#")[0].toLowerCase();
        idType = TypeMapping.get(idType) == null ? "String" : TypeMapping.get(idType);

        StringBuilder content = new StringBuilder();
        content.append("package " + packgeName + ";\n");
        content.append("\n");
        content.append("import " + entityPackgeName + "." + getEntityClassName(tableName) + ";\n");
        content.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
        content.append("import org.springframework.stereotype.Repository;\n");
        content.append("\n");

        content.append("/**\n");
        content.append(" *\n");
        content.append(" * @Author " + author + "\n");
        content.append(" * @Date " + date + "\n");
        content.append(" */\n");
        content.append("\n");

        content.append("@Repository\n");
        content.append("public interface " + className + " extends JpaRepository<" + getEntityClassName(tableName) + ", " + idType +"> {\n");
        content.append("\n");
        content.append("}\n");
        return content.toString();
    }

    public String getClassName(String tableName) {
        return getEntityClassName(tableName) + "Dao";
    }
}
