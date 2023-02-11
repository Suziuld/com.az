package cn.hp.util.gen;


import com.alibaba.druid.pool.DruidDataSource;

import java.io.*;
import java.util.List;

/**
 * 创建工具类
 */
public class CreateUtil {

    public static final String schemaName = "hp";   // 数据库名称，必填
    private static final String[][] tables = {{"test", "Test"}};   // 必填
    private static final String modelName = "会员管理";   // 必填


    static String path = "src/main/java/cn/hp/util/gen";//文件读取路径（从src到gen包的路径）
    static String genPath = "target/gen";//项目生成路径,如果没有必要，不需要改动
    //查询方法
    static String sql = "SELECT table_name,column_name,data_type, column_comment FROM information_schema.COLUMNS WHERE table_schema=? and table_name = ?";
    static String database = "";//要生成的对象
    static String remark = "";//对应的实体的说明名称
    static String packages = "cn.hp";//包的结构
    static String account = "";//账号
    static String pwd = "";//密码
    static String table = "";//对应的实体类型名称 首字母大写的
    static String tableName = "";//对应的实体类型名称 首字母大写的
    static String tableName2 = "";//对应的实体类型名称 首字母小写的

    /**
     * 逆向生成
     *
     * @param packages1 要生成的包结构 例：com.hp.ssm
     * @param remark1   表的中文意思
     * @param username  数据库账号
     * @param password  数据库密码
     * @param database1 要访问的数据库名称
     * @param tables    要生成的数据表(可变参数，可以写多个)
     */
    public static void reverseGen(String packages1, String remark1, String username, String password, String database1, String... tables) {
        database = database1;
        for (int i = 0; i < tables.length; i++) {
            table = tables[i];
            packages = packages1;
            remark = remark1;
            account = username;
            pwd = password;
            tableName = Change_Upper(table);
            tableName2 = Change_Upper2(table);
            List<TableColumn> student = DBHelper.select(sql, database, table);
            if (student.size() == 0) {
                System.out.println(table + "表名有错误");
                return;
            }
            entity(student);//实体类创建
            mapper(student);//mapper接口创建
            service(student);//service接口创建
            serviceImp(student);//serviceImp创建
            controller(student);//控制层创建
            mapperXMl(student);//mapperXMl创建
//            util();//需要的json工具类
        }
    }

    //
    public static void main(String[] args) {
        reverseGen(packages, "", "root", "root",
                "hp",
                "test"
//                ,"goout","stock"
        );
    }

    /**
     * 需要的json工具类
     */
    private static void util() {
        pathMkdirs(genPath + "/util");
        String clz = inVm(path + "/vm/util.txt");
        outVM(clz.replace("${packages}", packages), genPath + "/util/Result.java");
    }

    /**
     * mapperxml创建
     */
    private static void mapperXMl(List<TableColumn> student) {
        StringBuffer resultMap = new StringBuffer();
        StringBuffer insert1 = new StringBuffer();
        StringBuffer insert2 = new StringBuffer();
        StringBuffer update = new StringBuffer();
        student.forEach(t -> {
            resultMap.append("        <result property=\"" + Change_Upper2(t.getColumnName()) + "\" column=\"" + t.getColumnName() + "\"/>").append("\n");
            if ("varchar".equals(t.getDataType())) {
                insert1.append("            <if test=\"" + Change_Upper2(t.getColumnName()) + " != null and " + Change_Upper2(t.getColumnName()) + " != ''\">" + t.getColumnName() + ",</if>").append("\n");
                insert2.append("            <if test=\"" + Change_Upper2(t.getColumnName()) + " != null and " + Change_Upper2(t.getColumnName()) + " != ''\">#{" + Change_Upper2(t.getColumnName()) + "},</if>").append("\n");
                update.append("            <if test=\"" + Change_Upper2(t.getColumnName()) + " != null and " + Change_Upper2(t.getColumnName()) + " != ''\">" + t.getColumnName() + "=#{" + Change_Upper2(t.getColumnName()) + "},</if>").append("\n");
            } else {
                insert1.append("            <if test=\"" + Change_Upper2(t.getColumnName()) + " != null\">" + t.getColumnName() + ",</if>").append("\n");
                insert2.append("            <if test=\"" + Change_Upper2(t.getColumnName()) + " != null\">#{" + Change_Upper2(t.getColumnName()) + "},</if>").append("\n");
                update.append("            <if test=\"" + Change_Upper2(t.getColumnName()) + " != null \">" + t.getColumnName() + "=#{" + Change_Upper2(t.getColumnName()) + "},</if>").append("\n");
            }
        });
        String packages1 = packages;
        pathMkdirs(genPath + "/" + packages1.replace(".", "/") + "/mapper/");
        String clz = inVm(path + "/vm/mapperXMl.txt");
        outVM(clz.replace("${packages}", packages)
                        .replace("${tableName}", tableName)
                        .replace("${table}", table)
                        .replace("${resultMap}", resultMap.toString())
                        .replace("${insert1}", insert1.toString())
                        .replace("${insert2}", insert2.toString())
                        .replace("${update}", update.toString())
                , genPath + "/" + packages1.replace(".", "/") + "/mapper/".concat(tableName).concat("Mapper.xml"));
    }

    /**
     * mapper接口创建
     */
    private static void mapper(List<TableColumn> student) {
        pathMkdirs(genPath + "/mapper");
        String clz = inVm(path + "/vm/mapper.txt");
        outVM(clz.replace("${remark}", remark)
                        .replace("${packages}", packages)
                        .replace("${tableName}", tableName)
                        .replace("${tableName2}", tableName2)
                , genPath + "/mapper/".concat(tableName.concat("Mapper.java")));
    }

    /**
     * service接口创建
     */
    private static void service(List<TableColumn> student) {
        pathMkdirs(genPath + "/service/");
        String clz = inVm(path + "/vm/service.txt");
        outVM(clz.replace("${remark}", remark)
                        .replace("${packages}", packages)
                        .replace("${tableName}", tableName)
                        .replace("${tableName2}", tableName2)
                , genPath + "/service/".concat("I".concat(tableName.concat("Service.java"))));
    }

    /**
     * serviceImp创建
     */
    private static void serviceImp(List<TableColumn> student) {
        pathMkdirs(genPath + "/service/imp/");
        String clz = inVm(path + "/vm/serviceImp.txt");
        outVM(clz.replace("${remark}", remark)
                        .replace("${packages}", packages)
                        .replace("${tableName}", tableName)
                        .replace("${tableName2}", tableName2)
                , genPath + "/service/imp/".concat(tableName.concat("ServiceImp.java")));
    }


    /**
     * 控制层创建
     */
    private static void controller(List<TableColumn> student) {
        pathMkdirs(genPath + "/controller/");
        String clz = inVm(path + "/vm/controller.txt");
        outVM(clz.replace("${remark}", remark)
                        .replace("${packages}", packages)
                        .replace("${tableName}", tableName)
                        .replace("${tableName2}", tableName2)
                , genPath + "/controller/".concat(tableName).concat("Controller.java"));
    }

    /**
     * 实体类创建
     */
    public static void entity(List<TableColumn> student) {
        pathMkdirs(genPath + "/entity/");
        String clz = inVm(path + "/vm/entity.txt");
        //用来记录实体类的参数信息的
        StringBuffer property = new StringBuffer();
        for (TableColumn tableColumn : student) {
            property.append("    /*\n")
                    .append("    * " + tableColumn.getColumnComment() + "\n")
                    .append("    */\n")
                    .append("    private ")
                    .append(convertDataType(tableColumn.getDataType()))
                    .append(" " + Change_Upper2(tableColumn.getColumnName()) + ";\n");
        }
        outVM(clz.replace("${packages}", packages)
                .replace("${tableName}", tableName)
                .replace("${tableName}", tableName)
                .replace("${property}", property.toString()), genPath + "/entity/".concat(tableName).concat(".java"));
    }


    /**
     * 读取模板
     *
     * @param path
     * @return
     */
    public static String inVm(String path) {
        StringBuffer sb = new StringBuffer();
        BufferedReader re = null;
        try {
            re = new BufferedReader(new FileReader(path));
            String line = null;
            while ((line = re.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (re != null) {
                    re.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 输出文件
     *
     * @param clz     要创建的文件内容
     * @param clzName 要创建的文件名称
     */
    public static void outVM(String clz, String clzName) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(clzName, "UTF-8");
            pw.write(clz);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * _后的首字母大写方法
     *
     * @param s1
     * @return
     */
    public static String Change_Upper2(String s1) {
        String r = "";
        String[] a = s1.split("_");//以空格为分隔符，将单词分开
        String[] b = new String[a.length + 1];
        if (s1.indexOf("_") != -1) {
            r = a[0];
            for (int i = 1; i < a.length; i++) {
                //substring(0,1)首字母，toUpperCase()大写
                b[i] = a[i].substring(0, 1).toUpperCase() + a[i].substring(1, a[i].length());
                r = r + b[i];
            }
        } else {
            r = s1;
        }
        return r;
    }

    /**
     * 首字母大写方法
     *
     * @param s1
     * @return
     */
    public static String Change_Upper(String s1) {
        String r = "";
        String[] a = s1.split("_");//以空格为分隔符，将单词分开
        String[] b = new String[a.length + 1];
        for (int i = 0; i < a.length; i++) {
            //substring(0,1)首字母，toUpperCase()大写
            b[i] = a[i].substring(0, 1).toUpperCase() + a[i].substring(1, a[i].length());
            r = r + b[i];
        }
        return r;
    }

    //类型转换
    public static String convertDataType(String sqlType) {

        switch (sqlType) {
            case "varchar":
            case "char":
            case "longtext":
            case "text":
                return "String";
            case "double":
                return "Double";
            case "int":
//            case "tinyint":
                return "Integer";
            case "bigint":
                return "Long";
            case "datetime":
            case "timestamp":
            case "date":
                return "Date";
            case "decimal":
                return "BigDecimal";
            case "tinyint":
                return "Boolean";
            default:
                return "";
        }
    }

    public static void pathMkdirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

}
