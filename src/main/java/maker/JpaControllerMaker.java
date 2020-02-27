package maker;

import mapping.TypeMapping;
import org.apache.commons.lang3.StringUtils;
import utils.PropertiesUtil;

import java.util.HashMap;

public class JpaControllerMaker extends ClassMaker{
    private final String servicePackagePath;
    private final String entityPackagePath;
    private final String resultValuePackagePath;
    private final String resultBuilderPackagePath;

    public JpaControllerMaker(String packagePath,
                              String servicePackgePath,
                              String entityPackagePath) {
        super(packagePath);
        this.servicePackagePath = servicePackgePath;
        this.entityPackagePath = entityPackagePath;
        this.resultValuePackagePath = getPackgeName(PropertiesUtil.getProperty("package.path.result-value"));
        this.resultBuilderPackagePath = getPackgeName(PropertiesUtil.getProperty("package.path.result-builder"));
    }

    @Override
    public String generateContent(String tableName, String className, HashMap<String, String> columnMap, String primaryKey) {
        String packageName = getPackgeName(packagePath);
        String servicePackgeName = getPackgeName(servicePackagePath);
        String entityPackageName = getPackgeName(entityPackagePath);

        String entityClassName = getEntityClassName(tableName);
        String entityVariableName = underline2Hump(tableName);
        String serviceClassName = entityClassName + "Service";
        String serviceVariableName = underline2Hump(tableName) + "Dao";

        String idType = columnMap.get(primaryKey).split("#")[0].toLowerCase();
        idType = TypeMapping.get(idType) == null ? "String" : TypeMapping.get(idType);

        StringBuilder content = new StringBuilder();
        content.append("package " + packageName + ";\n");
        content.append("\n");
        content.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        if (!StringUtils.isBlank(resultValuePackagePath) && ! StringUtils.isBlank(resultBuilderPackagePath)) {
            content.append("import " + resultValuePackagePath + ".ResultValue;\n");
            content.append("import " + resultBuilderPackagePath + ".ResultBuilder;\n");
        }
        content.append("import io.swagger.annotations.Api;\n");
        content.append("import io.swagger.annotations.ApiOperation;\n");
        content.append("import org.springframework.web.bind.annotation.*;\n");
        content.append("import " + servicePackgeName + "." + serviceClassName + ";\n");
        content.append("import " + entityPackageName + "." + entityClassName + ";\n");
        content.append("\n");

        content.append("/**\n");
        content.append(" *\n");
        content.append(" * @Author " + author + "\n");
        content.append(" * @Date " + date + "\n");
        content.append(" */\n");
        content.append("\n");

        content.append("@RestController\n");
        content.append("@Api(tags=\"" + entityClassName + " Api\")\n");
        content.append("@RequestMapping(value = \"\")\n");
        content.append("public class " + className + " {\n");
        content.append("\n");
        content.append("\tprivate final " + serviceClassName + " " + serviceVariableName + ";\n");
        content.append("\n");

        content.append("\t@Autowired\n");
        content.append("\tpublic " + className + "(final " + serviceClassName + " " + serviceVariableName + ") {\n");
        content.append("\t\tthis." + serviceVariableName + " = " + serviceVariableName +";\n");
        content.append("\t}\n");

        if (!StringUtils.isBlank(resultValuePackagePath) && ! StringUtils.isBlank(resultBuilderPackagePath)) {
            content.append("\t@ApiOperation(value = \"创建数据\", httpMethod = \"POST\", response = " + entityClassName + ".class)\n");
            content.append("\t@RequestMapping(value = \"/create\", method = RequestMethod.POST)\n");
            content.append("\tpublic ResultValue create (@RequestBody " + entityClassName + " " + entityVariableName + ") {\n");
            content.append("\t\treturn null;\n");
            content.append("\t}\n");

            content.append("\t@ApiOperation(value = \"分页获取数据\", httpMethod = \"GET\", response = " + entityClassName + ".class)\n");
            content.append("\t@RequestMapping(value = \"/page\", method = RequestMethod.GET)\n");
            content.append("\tpublic ResultValue page (@RequestParam(\"pageSize\") Integer pageSize, @RequestParam(\"pageNum\") Integer pageNum) {\n");
            content.append("\t\treturn null;\n");
            content.append("\t}\n");

            content.append("\t@ApiOperation(value = \"获取所有数据\", httpMethod = \"GET\", response = " + entityClassName + ".class)\n");
            content.append("\t@RequestMapping(value = \"/all\", method = RequestMethod.GET)\n");
            content.append("\tpublic ResultValue all (@RequestBody " + entityClassName + " " + entityVariableName + ") {\n");
            content.append("\t\treturn null;\n");
            content.append("\t}\n");

            content.append("\t@ApiOperation(value = \"获取单个数据\", httpMethod = \"GET\", response = " + entityClassName + ".class)\n");
            content.append("\t@RequestMapping(value = \"/one\", method = RequestMethod.GET)\n");
            content.append("\tpublic ResultValue one (@RequestParam(\"id\") "+ idType +" id) {\n");
            content.append("\t\treturn null;\n");
            content.append("\t}\n");

            content.append("\t@ApiOperation(value = \"删除单个数据\", httpMethod = \"DELETE\", response = Boolean.class)\n");
            content.append("\t@RequestMapping(value = \"/delete\", method = RequestMethod.DELETE)\n");
            content.append("\tpublic ResultValue delete (@RequestParam(\"id\") "+ idType +" id) {\n");
            content.append("\t\treturn null;\n");
            content.append("\t}\n");
        }

        content.append("}\n");
        return content.toString();
    }


    @Override
    public String getClassName(String tableName) {
        return getEntityClassName(tableName) + "Controller";
    }
}
