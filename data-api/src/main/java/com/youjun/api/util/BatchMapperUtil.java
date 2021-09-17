package com.youjun.api.util;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
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
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
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
public interface BatchMapperUtil {

    @Transactional(rollbackFor = {Exception.class})
    default <E> boolean saveBatch(Collection<E> entityList, Class<?> entityClass) {
        return this.saveBatch(entityList, 1000, entityClass);
    }

    @Transactional(rollbackFor = {Exception.class})
    default <E> boolean saveOrUpdateBatch(Collection<E> entityList, Class<?> entityClass) {
        return this.saveOrUpdateBatch(entityList, 1000, entityClass);
    }

    @Transactional(rollbackFor = {Exception.class})
    default <E> boolean updateBatchById(Collection<E> entityList, Class<?> entityClass) {
        return this.updateBatchById(entityList, 1000, entityClass);
    }

    @Transactional(rollbackFor = {Exception.class})
    default <E> boolean saveBatch(Collection<E> entityList, int batchSize, Class<?> entityClass) {
        String sqlStatement = this.sqlStatement(SqlMethod.INSERT_ONE, entityClass);
        return this.executeBatch(entityList, batchSize, entityClass, (sqlSession, entity) -> {
            sqlSession.insert(sqlStatement, entity);
        });
    }

    Object selectById(Serializable id);

    @Transactional(rollbackFor = {Exception.class})
    default <E> boolean saveOrUpdateBatch(Collection<E> entityList, int batchSize, Class<?> entityClass) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!", new Object[0]);
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!", new Object[0]);
        return this.executeBatch(entityList, batchSize, entityClass, (sqlSession, entity) -> {
            Object idVal = ReflectionKit.getFieldValue(entity, keyProperty);
            if (!StringUtils.checkValNull(idVal) && !Objects.isNull(this.selectById((Serializable) idVal))) {
                MapperMethod.ParamMap<E> param = new MapperMethod.ParamMap();
                param.put("et", entity);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE_BY_ID.getMethod()), param);
            } else {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), entity);
            }

        });
    }

    @Transactional(rollbackFor = {Exception.class})
    default <E> boolean updateBatchById(Collection<E> entityList, int batchSize, Class<?> entityClass) {
        String sqlStatement = this.sqlStatement(SqlMethod.UPDATE_BY_ID, entityClass);
        return this.executeBatch(entityList, batchSize, entityClass, (sqlSession, entity) -> {
            MapperMethod.ParamMap<E> param = new MapperMethod.ParamMap();
            param.put("et", entity);
            sqlSession.update(sqlStatement, param);
        });
    }


    default String sqlStatement(SqlMethod sqlMethod, Class<?> entityClass) {
        return SqlHelper.table(entityClass).getSqlStatement(sqlMethod.getMethod());
    }

    default <E> boolean executeBatch(Collection<E> list, int batchSize, Class<?> entityClass, BiConsumer<SqlSession, E> consumer) {
        Assert.isFalse(batchSize < 1, "batchSize must not be less than one", new Object[0]);
        return !CollectionUtils.isEmpty(list) && this.executeBatch((sqlSession) -> {
            int size = list.size();
            int i = 1;

            for (Iterator<E> var6 = list.iterator(); var6.hasNext(); ++i) {
                E element = var6.next();
                consumer.accept(sqlSession, element);
                if (i % batchSize == 0 || i == size) {
                    sqlSession.flushStatements();
                }
            }

        }, entityClass);
    }

    /**
     * @deprecated
     */
    @Deprecated
    default boolean executeBatch(Consumer<SqlSession> consumer, Class<?> entityClass) {
        SqlSessionFactory sqlSessionFactory = SqlHelper.sqlSessionFactory(entityClass);
        SqlSessionHolder sqlSessionHolder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sqlSessionFactory);
        boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
        SqlSession sqlSession;
        if (sqlSessionHolder != null) {
            sqlSession = sqlSessionHolder.getSqlSession();
            sqlSession.commit(!transaction);
        }

        sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        if (!transaction) {
            throw new RuntimeException("SqlSession [" + sqlSession + "] was not registered for synchronization because DataSource is not transactional");
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
}
