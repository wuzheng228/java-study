package reflectionAndAnno.section73;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.function.Function;

public class EntityORMUtilImpl implements EntityORMUtil{
    @Override
    public int insertEntity(Connection conn, Object entity) throws Exception {
        if (conn == null || entity == null) {
            return -1;
        }
        Class clazz = entity.getClass();
        // 获取表名
        String tableName = Dom4jUtil.getTableNameByEntity(clazz);
        // 获取实体类的所有属性
        Field[] fields = clazz.getDeclaredFields();
        // 列名
        List<String> names = new ArrayList<>();
        // 列值
        List<Object> values = new ArrayList<>();
        // column类型
        List<String> types = new ArrayList<>();
        // 定义StringBuffer动态生成SQL语句
        StringBuffer buffer = new StringBuffer();
        buffer.append("insert into ");
        buffer.append(tableName);
        buffer.append(" (");
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(entity);
            Id idAnoo = field.getAnnotation(Id.class);
            if (value != null && idAnoo == null) {
                names.add(Dom4jUtil.getColumnNameByField(clazz, field));
                values.add(value);
                types.add(Dom4jUtil.getColumnTypeByField(clazz, field));
            }
        }
        buffer.append(String.join(",", names));
        buffer.append(") VALUES (");
        String[] strings = new String[values.size()];
        Arrays.fill(strings,"?");
        buffer.append(String.join(",",strings));
        buffer.append(")");
        // SQL 生成完毕，打印输出到控制台
        System.out.println(buffer.toString());
        // 下面获取预编译对象并执行SQL语句
        PreparedStatement pstm = conn.prepareStatement(buffer.toString(), Statement.RETURN_GENERATED_KEYS);
        int idx = 1;
        for (int i = 0; i < values.size(); i++) {
            String type = types.get(i);
            Object value = values.get(i);
            if (type.equals("int")) {
                pstm.setInt(idx, (Integer)value);
            } else if (type.equals("String")) {
                pstm.setString(idx,(String)value);
            }
            idx++;
        }
        // 执行SQL语句
        pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public int updateEntity(Connection conn, int id, Object entity) throws Exception {
        if (conn == null || entity == null)
            return -1;
        // SQL UPDATE table SET loginName = ?, password = ? WHERE
        Class clazz = entity.getClass();
        StringBuffer buffer = new StringBuffer();
        buffer.append("UPDATE ");
        String table = Dom4jUtil.getTableNameByEntity(clazz);
        buffer.append(table + " SET ");
        Field[] fields = clazz.getDeclaredFields();
        List<String> columns = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        String primaryKey = null;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getAnnotation(Id.class) != null) {
                primaryKey = Dom4jUtil.getColumnNameByField(clazz, field);
                continue;
            }

            if (field.get(entity) != null) {
                columns.add(Dom4jUtil.getColumnNameByField(clazz, field) + "=?");
                values.add(field.get(entity));
            }
        }
        buffer.append(String.join(",", columns));
        buffer.append(" WHERE " + primaryKey + "=?");
        System.out.println(buffer.toString());
        PreparedStatement pst = conn.prepareStatement(buffer.toString());
        int idx = 1;
        for (Object value : values) {
            pst.setObject(idx, value);
            idx++;
        }
        pst.setInt(idx, id);
        return pst.executeUpdate();
    }

    @Override
    public List<Object> getObjectList(Object entity, Map<String, Object> ifs, Connection conn) throws Exception{
        if (conn == null || entity == null)
            return null;
        // select * from user where loginName = ? AND password = ?
        Class<?> clazz = entity.getClass();
        List<Object> values = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT * FROM ");
        buffer.append(Dom4jUtil.getTableNameByEntity(clazz));
        if (!ifs.isEmpty()) {
            buffer.append(" WHERE ");
            Set<String> keys = ifs.keySet();
            List<String> strs = new ArrayList<>();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                String mapto = next + "=?";
                strs.add(mapto);
                values.add(ifs.get(next));
            }
            buffer.append(String.join(" AND ", strs));
        }
        System.out.println(buffer.toString());
        // 获取预编译对象
        PreparedStatement pst = conn.prepareStatement(buffer.toString());
        for (int i = 1; i <= values.size(); i++) {
            pst.setObject(i, values.get(i - 1));
        }
        // 执行Sql
        ResultSet rs = pst.executeQuery();
        // 方法返回所有数据的对象集合
        List<Object> objects = new ArrayList<>();
        // 获取对象的所有属性
        Field[] fields = clazz.getDeclaredFields();
        // 遍历结果集合
        while (rs.next()) {
            // 通过反射创建一个对象
            Object obj = clazz.newInstance();
            for (int i = 0; i < fields.length ; i++) {
                // 获取属性名，并将首字母大写，为后面调用Set方法做准备
                String name = fields[i].getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                // 获取列名
                String column = Dom4jUtil.getColumnNameByField(clazz,fields[i]);
                // 获取属性类型
                String type = fields[i].getGenericType().toString();
                if (type.equals("class java.lang.String")) {
                    Method method = obj.getClass().getMethod("set" + name, String.class);
                    method.invoke(obj, rs.getString(column));
                }
                if (type.equals("int")) {
                    Method method = obj.getClass().getMethod("set" + name, int.class);
                    method.invoke(obj, rs.getInt(column));
                }
            }
            objects.add(obj);
        }
        return objects;
    }

    @Override
    public int delete(Connection conn, int id, Object obj) throws Exception {
        // SQL delete from tablename where id = ?
        if (conn == null || obj == null) {
            return -1;
        }
        Class<?> clazz = obj.getClass();
        StringBuffer buffer = new StringBuffer();
        buffer.append("DELETE FROM ");
        String table = Dom4jUtil.getTableNameByEntity(clazz);
        buffer.append(table);
        buffer.append(" WHERE ");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                buffer.append(Dom4jUtil.getColumnNameByField(clazz, field) + "=?");
            }
        }
        System.out.println(buffer.toString());
        PreparedStatement pst = conn.prepareStatement(buffer.toString());
        pst.setInt(1, id);
        return pst.executeUpdate();
    }

    public static void main(String[] args) throws Exception {
        Connection connection = DBUtil.getConnection();
        EntityORMUtilImpl entityORMUtil = new EntityORMUtilImpl();
        int i = entityORMUtil
                .insertEntity(connection,
                        new User("消灭猕猴桃", "1234", "zz", 18,"男"));
        int j = entityORMUtil.insertEntity(connection, new User("zzspace", "12345"));
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", "消灭猕猴桃");
        List<Object> objectList = entityORMUtil.getObjectList(new User(), map, connection);
        System.out.println(objectList);
        int delete = entityORMUtil.delete(connection, 14, new User());
        System.out.println(delete);
        entityORMUtil.updateEntity(connection, 9, new User("消灭猕猴桃", "21129"));
        connection.close();
    }
}
