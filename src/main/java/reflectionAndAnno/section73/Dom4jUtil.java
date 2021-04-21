package reflectionAndAnno.section73;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.Iterator;

/**
 * 此类用于解析XML
 */
public class Dom4jUtil {
    public static String getTableNameByEntity(Class obj) throws Exception {
        SAXReader reader = new SAXReader();
        String entityName = obj.getName();
        String path = getPath(entityName);
        Document document = reader.read(path);
        Element e = document.getRootElement();
        if (e.getName().equals("class")) {
            for (Iterator<Attribute> it = e.attributeIterator(); it.hasNext();) {
                Attribute att = it.next();
                if (att.getName().equals("name")) {
                    if (entityName.equals(att.getValue())) {
                        Iterator<Element> itElement = e.elementIterator();
                        while (itElement.hasNext()) {
                            Element next = itElement.next();
                            if (next.getName().equals("table")) {
                                return next.getText();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String getColumnNameByField(Class obj, Field field) throws Exception {
        SAXReader reader = new SAXReader();
        String entityName = obj.getName();
        String path = getPath(entityName);
        String fieldName = field.getName();
        Document document = reader.read(path);
        Element root = document.getRootElement();
        if (root.getName().equals("class")) {
            Iterator<Attribute> attributeIterator = root.attributeIterator();
            while (attributeIterator.hasNext()) {
                Attribute attr = attributeIterator.next();
                if (attr.getName().equals("name")) {
                    if (entityName.equals(attr.getValue())) {
                        Iterator<Element> elementIterator = root.elementIterator();
                        while (elementIterator.hasNext()) {
                            Element next = elementIterator.next();
                            if (next.getName().equals("field")) {
                                Iterator<Attribute> iterator = next.attributeIterator();
                                while (iterator.hasNext()) {
                                    Attribute next1 = iterator.next();
                                    if (next1.getName().equals("name")) {
                                        if (next1.getValue().equals(fieldName)) {
                                            Iterator<Element> iterator1 = next.elementIterator();
                                            while (iterator1.hasNext()) {
                                                Element next2 = iterator1.next();
                                                if (next2.getName().equals("column")) {
                                                    return next2.getText();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    public static String getColumnTypeByField(Class obj, Field field) throws Exception{
        SAXReader reader = new SAXReader();
        String entityName = obj.getName();
        String path = getPath(entityName);
        String fieldName = field.getName();
        Document document = reader.read(path);
        Element root = document.getRootElement();
        if (root.getName().equals("class")) {
            Iterator<Attribute> attributeIterator = root.attributeIterator();
            while (attributeIterator.hasNext()) {
                Attribute attr = attributeIterator.next();
                if (attr.getName().equals("name")) {
                    if (entityName.equals(attr.getValue())) {
                        Iterator<Element> elementIterator = root.elementIterator();
                        while (elementIterator.hasNext()) {
                            Element next = elementIterator.next();
                            if (next.getName().equals("field")) {
                                Iterator<Attribute> iterator = next.attributeIterator();
                                while (iterator.hasNext()) {
                                    Attribute next1 = iterator.next();
                                    if (next1.getName().equals("name")) {
                                        if (next1.getValue().equals(fieldName)) {
                                            Iterator<Element> iterator1 = next.elementIterator();
                                            while (iterator1.hasNext()) {
                                                Element next2 = iterator1.next();
                                                if (next2.getName().equals("type")) {
                                                    return next2.getText();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    public static String getPath(String entityName) throws URISyntaxException {
        String filePath = entityName.replace(".","/") + ".xml";
        ClassLoader classLoader = Dom4jUtil.class.getClassLoader();
        String path = classLoader.getResource(filePath).toURI().getPath();
        return path;
    }

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("reflectionAndAnno.section73.User");
        for (Field field : clazz.getDeclaredFields()) {
            String columnNameByField = getColumnTypeByField(clazz,field);
            System.out.println(columnNameByField);
        }
    }
}
