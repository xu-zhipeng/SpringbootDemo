package com.youjun.api.util;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * <p>
 * 批量插入Mapper 借助mybatis-plus实现
 * </p>
 *
 * @author kirk
 * @since 2021/7/8
 */
public interface BatchMapper<T> extends BaseMapper<T> {
    Logger logger = LoggerFactory.getLogger(BatchMapper.class);

    /**
     * 通过反射获取T的class
     *
     * @return
     */
    default Class<T> entityClass() {
        return (Class<T>) getInterfacesGenericType(this.getClass(), 0);
    }

    default Class<?> getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            logger.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
            return Object.class;
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

    default Class<?> getInterfacesGenericType(final Class<?> clazz, final int index) {
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

    /*********************** IService **************************/

    @Transactional(rollbackFor = {Exception.class})
    default boolean saveBatch(Collection<T> entityList) {
        return this.saveBatch(entityList, 1000);
    }

    @Transactional(rollbackFor = {Exception.class})
    default boolean saveOrUpdateBatch(Collection<T> entityList) {
        return this.saveOrUpdateBatch(entityList, 1000);
    }

    @Transactional(rollbackFor = {Exception.class})
    default boolean updateBatchById(Collection<T> entityList) {
        return this.updateBatchById(entityList, 1000);
    }

    default boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper) {
        return SqlHelper.retBool(this.update(entity, updateWrapper)) || this.saveOrUpdate(entity);
    }

    /*********************** serviceImpl *****************************/

    default String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(this.entityClass()).getSqlStatement(sqlMethod.getMethod());
    }

    @Transactional(rollbackFor = {Exception.class})
    default boolean saveBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = this.sqlStatement(SqlMethod.INSERT_ONE);
        return this.executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            sqlSession.insert(sqlStatement, entity);
        });
    }

    @Transactional(rollbackFor = {Exception.class})
    default boolean saveOrUpdate(T entity) {
        if (null == entity) {
            return false;
        } else {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!", new Object[0]);
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!", new Object[0]);
            Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
            return !StringUtils.checkValNull(idVal) && !Objects.isNull(this.selectById((Serializable) idVal)) ? SqlHelper.retBool(this.updateById(entity)) : SqlHelper.retBool(this.insert(entity));
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    default boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass());
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!", new Object[0]);
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!", new Object[0]);
        return this.executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            Object idVal = ReflectionKit.getFieldValue(entity, keyProperty);
            if (!StringUtils.checkValNull(idVal) && !Objects.isNull(this.selectById((Serializable) idVal))) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap();
                param.put("et", entity);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE_BY_ID.getMethod()), param);
            } else {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), entity);
            }

        });
    }

    @Transactional(rollbackFor = {Exception.class})
    default boolean updateBatchById(Collection<T> entityList, int batchSize) {
        String sqlStatement = this.sqlStatement(SqlMethod.UPDATE_BY_ID);
        return this.executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap();
            param.put("et", entity);
            sqlSession.update(sqlStatement, param);
        });
    }

    /**
     * @deprecated
     */
    @Deprecated
    default boolean executeBatch(Consumer<SqlSession> consumer) {
        SqlSessionFactory sqlSessionFactory = SqlHelper.sqlSessionFactory(this.entityClass());
        SqlSessionHolder sqlSessionHolder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sqlSessionFactory);
        boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
        SqlSession sqlSession;
        if (sqlSessionHolder != null) {
            sqlSession = sqlSessionHolder.getSqlSession();
            sqlSession.commit(!transaction);
        }

        sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        if (!transaction) {
            logger.warn("SqlSession [" + sqlSession + "] was not registered for synchronization because DataSource is not transactional");
        }

        boolean var6;
        try {
            consumer.accept(sqlSession);
            sqlSession.commit(!transaction);
            var6 = true;
        } catch (Throwable var12) {
            sqlSession.rollback();
            Throwable unwrapped = ExceptionUtil.unwrapThrowable(var12);
            if (unwrapped instanceof RuntimeException) {
                MyBatisExceptionTranslator myBatisExceptionTranslator = new MyBatisExceptionTranslator(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true);
                throw (DataAccessException) Objects.requireNonNull(myBatisExceptionTranslator.translateExceptionIfPossible((RuntimeException) unwrapped));
            }

            throw ExceptionUtils.mpe(unwrapped);
        } finally {
            sqlSession.close();
        }

        return var6;
    }

    default <E> boolean executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        Assert.isFalse(batchSize < 1, "batchSize must not be less than one", new Object[0]);
        return !CollectionUtils.isEmpty(list) && this.executeBatch((sqlSession) -> {
            int size = list.size();
            int i = 1;

            for (Iterator var6 = list.iterator(); var6.hasNext(); ++i) {
                E element = (E) var6.next();
                consumer.accept(sqlSession, element);
                if (i % batchSize == 0 || i == size) {
                    sqlSession.flushStatements();
                }
            }

        });
    }

    default <E> boolean executeBatch(Collection<E> list, BiConsumer<SqlSession, E> consumer) {
        return this.executeBatch(list, 1000, consumer);
    }
}
