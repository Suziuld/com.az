package cn.hp.util.gen;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对service 提供连接，关闭连接
 */
public class DBHelper {
    private static final DruidDataSource ds = new DruidDataSource();
    //设置url ，建立链接时，指向数据库的地址
    private static String url = "jdbc:mysql://localhost:3306/"+CreateUtil.database+"?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    //设置遍历和用户建立连接
    private static String driver = "com.mysql.jdbc.Driver";
    private static String username = CreateUtil.account;
    private static String password = CreateUtil.pwd;
    private static DruidPooledConnection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;
    /**
     * 默认构造方法
     */
    private DBHelper() {
    }

    static {
        // 必填
        ds.setUrl("jdbc:mysql://localhost:3306/" + CreateUtil.schemaName + "?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2b8");
        ds.setUsername("root");
        ds.setPassword("root");
    }

    /**
     * 加载驱动，提供连接
     */
    public static DruidPooledConnection getConn() {
        try {
            conn = ds.getConnection();
            System.out.println("连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接失败");
        }
        return conn;
    }

    /**
     * 关闭连接
     *
     * @throws SQLException
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public static void close(Connection conn, PreparedStatement ps) throws SQLException {
        close(conn, ps, null);
    }

    public static void close(Connection conn) throws SQLException {
        close(conn, null, null);
    }

    /**
     * 查询方法
     *
     * @param sql
     * @param obj
     * @return
     */
    public static List<TableColumn> select(String sql, Object... obj) {
        // 获得连接
        conn = getConn();
        List<TableColumn> student = new ArrayList<>();
        try {
            // 创建一个基于该连接的语句对象
            ps = conn.prepareStatement(sql);
//            System.out.println(ps);
            //循环遍历导入参数
            for (int i = 0; i < obj.length; i++) {
//                System.out.println(obj[i]);
                ps.setObject(i + 1, obj[i]);
            }
            //执行查询语句，返回结果集
            rs = ps.executeQuery();
            while (rs.next()) {
                TableColumn tableColumn = new TableColumn(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
//                    System.out.println(tableColumn.toString());
                student.add(tableColumn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                close(conn, ps, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return student;
    }

    /**
     * 修改方法
     *
     * @param sql
     * @param obj
     * @return
     */
    public static int update(String sql, Object... obj) {
        //获得连接
        conn = getConn();
        int a = 0;
        try {
            //创建一个基于该连接的语句对象，
            ps = conn.prepareStatement(sql);
            System.out.println(ps);
            //循环遍历导入参数
            for (int i = 0; i < obj.length; i++) {
                System.out.println(obj[i]);
                ps.setObject(i + 1, obj[i]);
            }
            //执行sql
            a = ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return a;
    }

}

