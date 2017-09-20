package com.pharmacopoeia.util.db;

import android.content.Context;
import android.util.Log;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.List;

/**
 * Created by 52243 on 2017/4/26.
 */

public class LiteOrmDBUtil {

    public static LiteOrmDBUtil manager;
    private static final String DB_NAME = "pharmacopeia.db";
    public static LiteOrm liteOrm;

    private LiteOrmDBUtil(Context context) {
        if (liteOrm == null) {
            liteOrm = LiteOrm.newCascadeInstance(context, DB_NAME);
            liteOrm.setDebugged(true);
        }
    }

    public static LiteOrmDBUtil getInstance(Context context) {
        context = context.getApplicationContext();
        if (manager == null) {
            synchronized (LiteOrmDBUtil.class) {
                if (manager == null) {
                    manager = new LiteOrmDBUtil(context);
                }
            }
        }
        return manager;
    }

    public <T> void set(T t) {
        T myT = (T) getQueryFirst(t.getClass());
        if (myT == null) {
            insert(t);
        } else {
            update(t);
        }

    }

    /**
     * 插入一条记录
     *
     * @param t
     */
    public <T> void insert(T t) {
        liteOrm.save(t);
    }

    /**
     * 插入所有记录
     *
     * @param list
     */
    public <T> void insertAll(List<T> list) {
        liteOrm.save(list);
    }


    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    public <T> List<T> getQueryAll(Class<T> cla) {
        return liteOrm.query(cla);
    }

    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    public <T> T getQueryFirst(Class<T> cla) {
        List<T> list = liteOrm.query(cla);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public <T> List<T> getQueryByWhere(Class<T> cla, String field, Object[] value) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value));
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public <T> List<T> getQueryByWhereALl(Class<T> cla, String field, Object[] value) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field, value));
    }

    /**
     * 查询  某字段 like Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public <T> List<T> getQueryByWhereLike(Class<T> cla, String field, Object[] value) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + " like ?", value));
    }

    public <T> List<T> getQueryByWhereLikeBySql(Class<T> cla, String sql, Object[] value) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(sql, value));
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public <T> T getQueryFirstByWhere(Class<T> cla, String field, Object value) {
        return getQueryFirstByWhere(cla, field, new Object[]{value});
    }

    public <T> T getQueryFirstByWhere(Class<T> cla, String field, Object[] value) {
        List<T> list = liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value));
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     *
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    public <T> List<T> getQueryByWhereLength(Class<T> cla, String field, Object[] value, int start, int length) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value).limit(start, length));
    }

    /**
     * 删除所有
     *
     * @param cla
     */
    public <T> void deleteAll(Class<T> cla) {
        liteOrm.deleteAll(cla);
    }

    /**
     * 仅在以存在时更新
     *
     * @param t
     */
    public <T> void update(T t) {
        liteOrm.update(t, ConflictAlgorithm.Replace);
    }


    public <T> void updateALL(List<T> list) {
        liteOrm.update(list);
    }

}
