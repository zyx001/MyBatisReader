package zyp.reflection;

import org.apache.ibatis.reflection.factory.ObjectFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ReflectPermission;
import java.util.*;

public class ObjectFactoryDemo implements ObjectFactory {
    @Override
    public void setProperties(Properties properties) {
        ObjectFactory.super.setProperties(properties);
    }

    @Override
    public <T> T create(Class<T> type) {
        return this.create(type, null, null);
    }

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        Class<?> createToClass = resolveType(type);
        return (T) newInstance(createToClass, constructorArgTypes, constructorArgs);
    }

    private <T> T newInstance(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        Constructor<T> constructor;
        try {
            if (null == constructorArgTypes || constructorArgTypes.isEmpty()) {
                constructor = type.getDeclaredConstructor();
                try {
                    return constructor.newInstance();
                } catch (IllegalAccessException e) {

                    if (checkAccess()) {
                        constructor.setAccessible(true);
                        return constructor.newInstance();
                    }
                    throw new RuntimeException(e);
                }
            }

            constructor = type.getDeclaredConstructor(constructorArgTypes.toArray(new Class[constructorArgs.size()]));
            try {
                return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
            } catch (IllegalAccessException e) {
                if (checkAccess()) {
                    constructor.setAccessible(true);
                    return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
                }
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean checkAccess() {
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        } catch (SecurityException e) {
            return false;
        }

        return true;
    }

    private Class<?> resolveType(Class<?> type) {
        if (type == List.class || type == Collection.class) {
            return ArrayList.class;
        } else if (type == Map.class) {
            return HashMap.class;
        } else if (type == Set.class) {
            return TreeSet.class;
        }
        return type;
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }
}
