package com.richardradics.commons.helper;
/*
 * Copyright (C) 2014 VenomVendor <info@VenomVendor.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;

/**
 * Modified version of {@link SharedPreferences}.<br>
 * Initiate <b>"SimpleSharedPreferences"</b> by passing {@linkplain Context}.
 * <p/>
 * <pre>
 * SimpleSharedPreferences mPreferences = new SimpleSharedPreferences(getApplicationContext());
 * </pre>
 * <p/>
 * <u>Start <b>Modifying / Accessing</b> data in SimpleSharedPreferences.</u>
 * <p/>
 * <pre>
 * mPreferences.putString(key, value);
 * mPreferences.getString(key, defaultValue);
 * </pre>
 * <p/>
 * <b>DO NOT</b> to use {@link SharedPreferences#edit()} or {@link Editor#commit()} <br>
 * <br>
 * <em> Note:
 * <ul>
 * <li>Usage of <b>deprecated</b> methods throws {@linkplain InstantiationError}.</li>
 * <li>{@link #apply()} is available only after API-9</li>
 * <li>Throws <b>"ClassCastException"</b> if wrong key is passed</li>
 * </ul>
 * </em>
 *
 * @see Context#getSharedPreferences
 * @see SharedPreferences
 */
public class SimpleSharedPreferences implements SharedPreferences, Editor {

    private final String tag = getClass().getSimpleName().toString();
    private SharedPreferences mSharedPreferences;
    private Editor mEditor;
    private final Context mContext;
    private boolean enableLog = false;

    /**
     * @param {@link Context} context
     */
    public SimpleSharedPreferences(Context context) {
        mContext = context;
        mSharedPreferences = null;
    }

    /**
     * @return the status of Log
     */
    public boolean isLogEnabled() {
        return enableLog;
    }

    /**
     * Enables/Disables printing LogValues.
     *
     * @param enableLog
     */
    public void setLogEnabled(boolean enableLog) {
        this.enableLog = enableLog;
    }

    /**
     * Initiate's SharedPreferences.
     */
    private void initiateSharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        }
    }

    /**
     * Initiates SharedPreferences & Creates a new Editor for these preferences
     */
    private void initAndEdit() {
        initiateSharedPreferences();
        edit();
    }

    /**
     * Increment's App opened count by <b>1</b>
     *
     * @return <b>true</b> on success
     */
    public boolean incrementAppOpenedCount() {
        initAndEdit();
        final int appOpenedCount = getAppOpenedCount();
        if (enableLog) {
            Log.d(tag, "Count before updating " + appOpenedCount);
        }
        mEditor.putInt(KEYS.OPENED_TIMES_COUNT, (appOpenedCount + 1));
        return commit();
    }

    /**
     * @return The number of times the was opened. This will not autoupdate, everytime the app is opened.<br>
     * You should call {@link #incrementAppOpenedCount()} on {@link Activity#onStart()} to update this.
     */
    public int getAppOpenedCount() {
        initiateSharedPreferences();
        return mSharedPreferences.getInt(KEYS.OPENED_TIMES_COUNT, 0);
    }

    @Override
    public Editor putString(String key, String value) {
        initAndEdit();
        mEditor.putString(key, value);
        commit();
        return this;
    }

    @Override
    public Editor putStringSet(String key, Set<String> values) throws Error {
        final JSONArray jArray = new JSONArray();
        for (final String value : values) {
            jArray.put(value);
        }

        final JSONObject json = new JSONObject();
        try {
            json.put(key, jArray);
            putString(key, json.toString());
            if (enableLog) {
                Log.i(key, json.toString());
            }
        } catch (final JSONException e) {
            throw new Error(e);
        }
        return this;
    }

    @Override
    public Editor putInt(String key, int value) {
        initAndEdit();
        mEditor.putInt(key, value);
        commit();
        return this;
    }

    @Override
    public Editor putLong(String key, long value) {
        initAndEdit();
        mEditor.putLong(key, value);
        commit();
        return this;
    }

    @Override
    public Editor putFloat(String key, float value) {
        initAndEdit();
        mEditor.putFloat(key, value);
        commit();
        return this;
    }

    @Override
    public Editor putBoolean(String key, boolean value) {
        initAndEdit();
        mEditor.putBoolean(key, value);
        commit();
        return this;
    }

    @Override
    public Editor remove(String key) {
        initAndEdit();
        mEditor.remove(key);
        commit();
        return this;
    }

    @Override
    public Editor clear() {
        initAndEdit();
        mEditor.clear();
        commit();
        return this;
    }

    /**
     * <h1><u>Do not use this method</u></h1> <br>
     */
    @Deprecated
    @Override
    public boolean commit() throws InstantiationError {
        if (mEditor != null) {
            return mEditor.commit();
        }
        throw new InstantiationError("\n ======================================== \nError : " + "Do not call " + tag + "'s `commit()`."
                + "\n This method is not supported directly." + " \n ======================================== \n");

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void apply() throws NoSuchMethodError {
        initAndEdit();
        try {
            mEditor.apply();
        } catch (final NoSuchMethodError e) {
            throw new NoSuchMethodError("\n ======================================== \nError : "
                    + "This method is supported only from API-9 { i.e only above GINGERBREAD }" + " \n ======================================== \n");
        }
    }

    @Override
    public Map<String, ?> getAll() throws ClassCastException {
        initiateSharedPreferences();
        return mSharedPreferences.getAll();
    }

    @Override
    public String getString(String key, String defValue) throws ClassCastException {
        initiateSharedPreferences();
        try {
            return mSharedPreferences.getString(key, defValue);
        } catch (final ClassCastException e) {
            final String returnType = new Object() {
            }.getClass().getEnclosingMethod().getReturnType().toString();
            throw new ClassCastException("\n ======================================== \nClassCastException : " + key + "'s value is not a "
                    + returnType + " \n ======================================== \n");
        }
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) throws ClassCastException {
        initiateSharedPreferences();

        final String jsonPref = getString(key, null);

        if (jsonPref == null) {
            return defValues;
        }

        final List<String> prefValues = new ArrayList<String>();

        try {
            final JSONArray mJsonArray = new JSONObject(jsonPref).getJSONArray(key);
            for (int i = 0; i < mJsonArray.length(); i++) {
                prefValues.add(mJsonArray.getString(i));
            }
        } catch (final JSONException e) {
            e.printStackTrace();
        }

        try {
            return new HashSet<String>(prefValues);
        } catch (final ClassCastException e) {
            final String returnType = new Object() {
            }.getClass().getEnclosingMethod().getReturnType().toString();
            throw new ClassCastException("\n ======================================== \nClassCastException : " + key + "'s value is not a "
                    + returnType + " \n ======================================== \n");
        } catch (final Exception e) {
            return defValues;
        }
    }

    @Override
    public int getInt(String key, int defValue) throws ClassCastException {
        initiateSharedPreferences();
        try {
            return mSharedPreferences.getInt(key, defValue);
        } catch (final ClassCastException e) {
            final String returnType = new Object() {
            }.getClass().getEnclosingMethod().getReturnType().toString();
            throw new ClassCastException("\n ======================================== \nClassCastException : " + key + "'s value is not an "
                    + returnType + " \n ======================================== \n");
        }
    }

    @Override
    public long getLong(String key, long defValue) throws ClassCastException {
        initiateSharedPreferences();
        try {
            return mSharedPreferences.getLong(key, defValue);
        } catch (final ClassCastException e) {
            final String returnType = new Object() {
            }.getClass().getEnclosingMethod().getReturnType().toString();
            throw new ClassCastException("\n ======================================== \nClassCastException : " + key + "'s value is not a "
                    + returnType + " \n ======================================== \n");
        }
    }

    @Override
    public float getFloat(String key, float defValue) throws ClassCastException {
        initiateSharedPreferences();
        try {
            return mSharedPreferences.getFloat(key, defValue);
        } catch (final ClassCastException e) {
            final String returnType = new Object() {
            }.getClass().getEnclosingMethod().getReturnType().toString();
            throw new ClassCastException("\n ======================================== \nClassCastException : " + key + "'s value is not a "
                    + returnType + " \n ======================================== \n");
        }
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) throws ClassCastException {
        initiateSharedPreferences();
        try {
            return mSharedPreferences.getBoolean(key, defValue);
        } catch (final ClassCastException e) {
            final String returnType = new Object() {
            }.getClass().getEnclosingMethod().getReturnType().toString();
            throw new ClassCastException("\n ======================================== \nClassCastException : " + key + "'s value is not a "
                    + returnType + " \n ======================================== \n");
        }
    }

    @Override
    public boolean contains(String key) {
        initiateSharedPreferences();
        return mSharedPreferences.contains(key);
    }

    /**
     * <h1><u>Do not use this method</u></h1> <br>
     */
    @Deprecated
    @Override
    public Editor edit() throws InstantiationError {
        if (mEditor != null) {
            return mEditor;
        }

        if (mSharedPreferences == null) {
            throw new InstantiationError("\n ======================================== \nError : " + "Do not call " + tag + "'s `edit()`."
                    + "\n This method is not supported directly." + " \n ======================================== \n");
        }
        mEditor = mSharedPreferences.edit();
        return mEditor;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        initiateSharedPreferences();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        initiateSharedPreferences();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    /**
     * Holds Constants for Keys
     */
    class KEYS {
        /**
         * The Constant Key for App Opened Count.
         */
        final static String OPENED_TIMES_COUNT = "VEE_OPENEDTIMESCOUNT";
    }
}