package com.youjun.api.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.youjun.api.domain.AdminUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * MyBatis配置类
 * Created on 2019/4/8.
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@MapperScan({"com.youjun.api.modules.*.mapper"})
public class MyBatisConfig {
    /**
     * 分页
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     * <p>
     * 乐观锁
     * 支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
     * 整数类型下 newVersion = oldVersion + 1
     * newVersion 会回写到 entity 中
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     * 在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        /**
         * 乐观锁
         */
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        /**
         * 分页
         */
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 分页失效问题，新版本已经修复该功能不需要配置了
     */
    //@Bean
    //public ConfigurationCustomizer configurationCustomizer() {
    //    return configuration -> configuration.setUseDeprecatedExecutor(false);
    //}

    /**
     * 自动填充功能
     * MetaObjectHandler提供的默认方法的策略均为:如果属性有值则不覆盖,如果填充值为null则不填充
     * 注：如果修改时间不为null，那就必须手动做更新
     *
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                log.info("start insert fill ....");
                // 起始版本 3.3.0(推荐使用)
                //this.strictInsertFill(metaObject, "createdDt", LocalDateTime.class, LocalDateTime.now());
                // 或者 起始版本 3.3.3(推荐)
                this.strictInsertFill(metaObject, "createdBy", this::getUserName, String.class);
                this.strictInsertFill(metaObject, "createdDt", () -> LocalDateTime.now(), LocalDateTime.class);
                this.strictInsertFill(metaObject, "modifiedBy", this::getUserName, String.class);
                this.strictInsertFill(metaObject, "modifiedDt", () -> LocalDateTime.now(), LocalDateTime.class);
                // 或者 // 也可以使用(3.3.0 该方法有bug)
                //this.fillStrategy(metaObject, "createdDt", LocalDateTime.now());
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                log.info("start update fill ....");
                // 起始版本 3.3.0(推荐)
                //this.strictUpdateFill(metaObject, "modifiedDt", LocalDateTime.class, LocalDateTime.now());
                // 或者 起始版本 3.3.3(推荐)
                this.strictInsertFill(metaObject, "modifiedBy", this::getUserName, String.class);
                this.strictUpdateFill(metaObject, "modifiedDt", () -> LocalDateTime.now(), LocalDateTime.class);
                // 或者 也可以使用(3.3.0 该方法有bug)
                //this.fillStrategy(metaObject, "modifiedDt", LocalDateTime.now());
            }

            /**
             * 获取用户名
             * @return
             */
            public String getUserName() {
                Object principal = Optional.ofNullable(SecurityContextHolder.getContext())
                        .map(v -> v.getAuthentication())
                        .map(v -> v.getPrincipal())
                        .orElse(null);
                String username = null;
                if (Objects.nonNull(principal) && principal instanceof AdminUserDetails) {
                    AdminUserDetails userDetails = (AdminUserDetails) principal;
                    username = Optional.ofNullable(userDetails.getUsername()).orElse(null);
                }
                return username;
            }
        };
    }
}
