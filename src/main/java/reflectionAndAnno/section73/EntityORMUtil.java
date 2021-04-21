package reflectionAndAnno.section73;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface EntityORMUtil {
    int insertEntity(Connection conn, Object entity) throws Exception;

    int updateEntity(Connection conn, int id, Object entity) throws Exception;

    List<Object> getObjectList(Object entity, Map<String, Object> ifs, Connection conn) throws Exception;

    int delete(Connection conn, int id, Object obj) throws Exception;
}
