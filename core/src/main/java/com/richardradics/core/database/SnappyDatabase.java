package com.richardradics.core.database;

import android.content.Context;

import com.richardradics.commons.util.DateUtil;
import com.richardradics.core.error.SnappyErrorHandler;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by radicsrichard on 15. 04. 28..
 */
@EBean
public class SnappyDatabase {

    @RootContext
    protected Context context;

    protected static DB db;

    @Bean
    protected SnappyErrorHandler snappyErrorHandler;

    @AfterInject
    protected void afterBaseInject() {
        openDatabase();
    }

    public void openDatabase() {
        if (db == null)
            try {
                db = DBFactory.open(context);
            } catch (SnappydbException e) {
                snappyErrorHandler.handlerError(e);
            }
    }

    public void closeDatabase() {
        if (db != null)
            try {
                db.close();
            } catch (SnappydbException e) {
                snappyErrorHandler.handlerError(e);
            }
    }


    public void setValue(String key, Boolean value) {
        try {
            db.put(key, value);
        } catch (Exception e) {
            snappyErrorHandler.handlerError(e);
        }
    }

    public Boolean getBooleanValue(String key) {
        try {
            if (isDataCached(key)) {
                return db.getBoolean(key);
            } else {
                return null;
            }
        } catch (Exception e) {
            snappyErrorHandler.handlerError(e);
            return null;
        }
    }

    public void setValue(String key, Integer value) {
        try {
            db.put(key, value);
        } catch (Exception e) {
            snappyErrorHandler.handlerError(e);
        }
    }

    public Integer getIntegerValue(String key) {
        try {
            if (isDataCached(key)) {
                return new Integer(db.getInt(key));
            } else {
                return null;
            }
        } catch (Exception e) {
            snappyErrorHandler.handlerError(e);
            return null;
        }
    }

    public void setValue(String key, Long value) {
        try {
            db.put(key, value);
        } catch (Exception e) {
            snappyErrorHandler.handlerError(e);
        }
    }

    public Long getLongValue(String key) {
        try {
            if (isDataCached(key)) {
                return new Long(db.getLong(key));
            } else {
                return null;
            }
        } catch (Exception e) {
            snappyErrorHandler.handlerError(e);
            return null;
        }
    }


    public void setValue(String key, String value) {
        try {
            db.put(key, value);
        } catch (Exception e) {
            snappyErrorHandler.handlerError(e);
        }
    }

    public String getStringValue(String key) {
        try {
            if (isDataCached(key)) {
                return db.get(key);
            } else {
                return null;
            }
        } catch (Exception e) {
            snappyErrorHandler.handlerError(e);
            return null;
        }
    }

    public void setStringListValue(String key, List<String> value) {
        try {
            db.put(key, value.toArray(new String[value.size()]));
        } catch (Exception e) {
            snappyErrorHandler.handlerError(e);
        }
    }


    public List<String> getStringListValue(String key) {
        List<String> values = new ArrayList<String>();
        try {
            values.addAll(Arrays.asList(db.getArray(key, String.class)));
        } catch (Exception e) {
            snappyErrorHandler.handlerError(e);
        }
        return values;
    }

    public String getUniqueKey(String tag) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tag);
        stringBuilder.append(DateUtil.getSimplDateFormat().format(new Date()));
        String s = stringBuilder.toString();
        s = s.replace(".", "_");
        s = s.replace(":", "_");
        return s;
    }

    public boolean isDataCached(String key) {
        try {
            if (db.exists(key)) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (SnappydbException e) {
            snappyErrorHandler.handlerError(e);
        }
        return Boolean.FALSE;
    }

    public <T> List<T> getObjectList(String key, Class<T> resultClass) {
        List<T> resultList = new ArrayList<T>();
        try {
            if (isDataCached(key)) {
                resultList.addAll(Arrays.asList(db.getObjectArray(key, resultClass)));
            } else {
                return resultList;
            }
        } catch (SnappydbException e) {
            snappyErrorHandler.handlerError(e);
        }
        return resultList;
    }

    public void setValue(String key, List object) {
        try {
            db.put(key, object.toArray());
        } catch (SnappydbException e) {
            snappyErrorHandler.handlerError(e);
        }
    }

    public void setValue(String key, Object o) {
        try {
            db.put(key, o);
        } catch (SnappydbException e) {
            snappyErrorHandler.handlerError(e);
        }
    }

    public <T> T getObject(String key, Class<T> resultClass) {
        try {
            return db.getObject(key, resultClass);
        } catch (SnappydbException e) {
            snappyErrorHandler.handlerError(e);
        }
        return null;
    }

}