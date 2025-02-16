package zyp.reflection;

import org.apache.ibatis.reflection.Reflector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyCopier {

    // copy with parent
    public static void copyBeanProperties(Class<?> type, Object sourceBean, Object targetBean) {
        Field[] fields = getAllField(type);
        for (Field field : fields) {
            try {
                try {
                    field.set(targetBean, field.get(sourceBean));
                } catch (IllegalAccessException e) {
                    if (Reflector.canControlMemberAccessible()) {
                        field.setAccessible(true);
                        field.set(targetBean, field.get(sourceBean));
                    } else {
                        throw new RuntimeException(e);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private static Field[] getAllField(Class<?> type) {
        Map<String, Field> fieldMap = new HashMap<>();

        Class<?> superclass = type;

        while (superclass != null) {
            Field[] declaredFields = superclass.getDeclaredFields();
            for (Field field : declaredFields) {
                if (!fieldMap.containsKey(field.getName())) {
                    fieldMap.put(field.getName(), field);
                }
            }
            superclass = type.getSuperclass();
        }

        return fieldMap.values().toArray(new Field[fieldMap.values().size()]);
    }

}
