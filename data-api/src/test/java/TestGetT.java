import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/9/17
 */
public class TestGetT {
    public static final Logger logger = LoggerFactory.getLogger(TestGetT.class);

    public static void main(String[] args) {
        TestMapperSub testMapper = new TestMapperSub() {
            @Override
            public void test() {
                System.out.println();
            }

            @Override
            public void test1() {
                System.out.println();
            }
        };
        Class<?> clz = getInterfacesGenericType(testMapper.getClass(), 0);
        Class<?> clz1 = getInterfacesGenericType(TestMapperSubImpl.class, 0);
        System.out.println(clz.getSimpleName());
    }


    public static Class<?> getInterfacesGenericType(final Class<?> clazz, final int index) {
        Type[] interfaces = clazz.getGenericInterfaces();
        if (interfaces.length == 0) {
            logger.warn(String.format("Warn: %s not implements interface ", clazz.getSimpleName()));
            return Object.class;
        }
        if (index >= interfaces.length || index < 0) {
            logger.warn(String.format("Warn: index less than 0 or greater than interfaces.length %s", interfaces.length));
            return Object.class;
        }
        Type genType = interfaces[index];
        if (!(genType instanceof ParameterizedType)) {
            //获取实现接口的类型 如果不是ParameterizedType 则获取父接口
            return getInterfacesGenericType((Class<?>) genType, index);
        } else {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            if (index < params.length && index >= 0) {
                if (!(params[index] instanceof Class)) {
                    logger.warn(String.format("Warn: %s not set the actual class on superclass generic parameter", clazz.getSimpleName()));
                    return Object.class;
                } else {
                    return (Class) params[index];
                }
            } else {
                logger.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index, clazz.getSimpleName(), params.length));
                return Object.class;
            }
        }
    }

    /**
     * 获取接口上的泛型T
     *
     * @param o     接口
     * @param index 泛型索引
     */
    public static Class<?> getInterfaceT(Object o, int index) {
        Type[] types = o.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[index];
        Type type = parameterizedType.getActualTypeArguments()[index];
        return checkType(type, index);

    }

    /**
     * 获取类上的泛型T
     *
     * @param o     接口
     * @param index 泛型索引
     */
    public static Class<?> getClassT(Object o, int index) {
        Type type = o.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type actType = parameterizedType.getActualTypeArguments()[index];
            return checkType(actType, index);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType"
                    + ", but <" + type + "> is of type " + className);
        }
    }

    private static Class<?> checkType(Type type, int index) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type t = pt.getActualTypeArguments()[index];
            return checkType(t, index);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType"
                    + ", but <" + type + "> is of type " + className);
        }
    }
}
