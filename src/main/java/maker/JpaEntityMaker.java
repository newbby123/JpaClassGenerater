package maker;

import mapping.TypeMapping;

import java.util.HashMap;
import java.util.Map;

public class JpaEntityMaker extends ClassMaker {

    public JpaEntityMaker(String packgePath) {
        super(packgePath);
    }

    public static void main(String[] args) {
        JpaEntityMaker jpaEntityMaker = new JpaEntityMaker("D:\\workspace\\fly-rest-etl\\src\\main\\java\\com\\fly\\test");
        jpaEntityMaker.generate("extract_database");
    }

    public String generateContent(String tableName, String className, HashMap<String, String> columnMap, String primaryKey) {
        String packgeName = getPackgeName(packagePath);
        StringBuilder content = new StringBuilder();
        content.append("package " + packgeName + ";\n");
        content.append("\n");
        content.append("import lombok.Data;\n");
        content.append("import javax.persistence.*;\n");
        content.append("import org.hibernate.annotations.GenericGenerator;\n");
        content.append("import java.io.Serializable;\n");
        content.append("\n");

        content.append("/**\n");
        content.append(" *\n");
        content.append(" * @Author " + author + "\n");
        content.append(" * @Date " + date + "\n");
        content.append(" */\n");
        content.append("\n");

        content.append("@Data\n");
        content.append("@Entity\n");
        content.append("@Table(name = \"" + tableName + "\")\n");
        content.append("public class " + className + " implements Serializable {\n");
        content.append("\n");
        content.append("\tprivate static final long serialVersionUID = 1L;\n");
        content.append("\n");

        String idType = columnMap.get(primaryKey).split("#")[0].toLowerCase();
        primaryKey = underline2Hump(primaryKey);
        if (idType.contains("int")) {
            content.append("\t@Id\n");
            content.append("\t@GeneratedValue(strategy = GenerationType.IDENTITY)\n");
            idType = TypeMapping.get(idType) == null ? "String" : TypeMapping.get(idType);
            content.append("\tprivate "+ idType + " " + primaryKey + ";\n");
        } else {
            // 这里使用32位UUID作为id
            content.append("\t@Id\n");
            content.append("\t@GenericGenerator(name = \"" + tableName +"_uuid_id\", strategy = \"uuid\")\n");
            content.append("\t@GeneratedValue(generator = \"" + tableName +"_uuid_id\")\n");
            content.append("\t@Column(length = 32)\n");
            idType = TypeMapping.get(idType) == null ? "String" : TypeMapping.get(idType);
            content.append("\tprivate "+ idType + " " + primaryKey + ";\n");
        }

        for (Map.Entry<String, String> column: columnMap.entrySet()) {
            String columnName = column.getKey();
            columnName = underline2Hump(columnName);
            String columnType = column.getValue().split("#")[0];
            if (primaryKey.equals(columnName)) {
                continue;
            }
            columnType = TypeMapping.get(columnType) == null ? "String" : TypeMapping.get(columnType);

            content.append("\n");
            // 注释
            if (column.getValue().split("#").length > 1) {
                String remarks = column.getValue().split("#")[1];
                content.append("\t/** " + remarks + " */\n");
            }

            content.append("\t@Column(name = \"" + column.getKey() + "\")\n");
            content.append("\tprivate " + columnType + " " + columnName + ";\n");
        }

        content.append("}\n");

        return content.toString();
    }

    @Override
    public String getClassName(String tableName) {
        return getEntityClassName(tableName);
    }
}