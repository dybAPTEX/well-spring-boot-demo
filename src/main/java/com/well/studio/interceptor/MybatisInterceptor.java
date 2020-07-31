package com.well.studio.interceptor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;

@Component
@Intercepts({
        @Signature(args = { MappedStatement.class, Object.class }, method = "update", type = Executor.class),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class,ResultHandler.class})})
/**
 * MybatisInterceptor 作用？
 * pagehelpe
 * sql防止注入
 * sql记录
 * =====
 */
public class MybatisInterceptor implements Interceptor {

    // 是否记录异常，可以不记录，由上层处理
    private boolean isLogError = false;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        //获取类名和方法名
        String classMethod = getClassMethod(mappedStatement.getId());
        // 得到sql语句
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = showSql(configuration, boundSql);
        System.out.println("SQL:"+sql);
        Object returnObj = null;
        try {
            /**
             * 获取invocation.proceed();后
             * 即可以对查询结果集做操作
             * if(returnObj instanceof ArrayList){
             * }
             */
            returnObj = invocation.proceed();
        } catch (Exception e) {
            /**
             * 日志
             */
        } finally {
            /**
             * 后置处理
             */
        }
        return returnObj;

    }

    /**
     * 获取类名和方法名
     * 
     * @param mappedStatementId
     * @return
     */
    private String getClassMethod(String mappedStatementId) {
        String[] strArr = mappedStatementId.split("\\.");
        String className = strArr[strArr.length - 2];
        String methodName = strArr[strArr.length - 1];
        StringBuilder sb = new StringBuilder();
        sb.append(className);
        sb.append(".");
        sb.append(methodName);
        return sb.toString();
    }

    /**
     * 获取SQL语句
     * @param configuration
     * @param boundSql
     * @return
     */
    public String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));

            }
            else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    }
                    else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    }
                }
            }
        }
        return sql;
    }

    private String getParameterValue(Object obj) {
        StringBuilder value = new StringBuilder();
        if (obj instanceof String) {
            value.append("'").append(obj.toString()).append("'");
        }
        else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                    DateFormat.DEFAULT, Locale.CHINA);
            value.append("'").append(formatter.format(obj)).append("'");
        }
        else {
            if (obj != null) {
                value.append(obj.toString());
            }
        }
        return value.toString();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor)
            return Plugin.wrap(target, this);
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
