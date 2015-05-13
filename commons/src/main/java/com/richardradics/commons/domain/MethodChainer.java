package com.richardradics.commons.domain;

import com.richardradics.commons.helper.DataHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by radicsrichard on 15. 04. 20..
 */
public class MethodChainer {

    public interface ProgressListener{
        void onMethodProgressStart();
        void onMethodProgressChanged(int percent, int tasksRemaining, int tasksDone);
        void onMethodProgressFinish();
    }

    private  int tempTaskCount = 0;
    private int taskCount = 0;
    private ProgressListener mListener;


    public ProgressListener getProgressListener() {
        return mListener;
    }

    public void setProgressListener(ProgressListener mListener) {
        this.mListener = mListener;
    }

    Map<String, Boolean> methodCalledMap;

    public MethodChainer(){
         this.methodCalledMap  = new HashMap<String, Boolean>();
    }

    public void addMethod(String methodName){
        taskCount++;
        methodCalledMap.put(methodName,Boolean.FALSE);
    }

    public void start(){

        if(mListener != null){
            mListener.onMethodProgressStart();
        }

        for(Map.Entry<String, Boolean> b : methodCalledMap.entrySet()){
            b.setValue(Boolean.FALSE);
        }

    }

    public synchronized void setCalled(String method){
        methodCalledMap.put(method, Boolean.TRUE);

        if(mListener != null) {
            if (isAllCalled()) {
                mListener.onMethodProgressFinish();
            }else{
                notifyProgressChange();
            }
        }
    }

    private void notifyProgressChange(){
        if(mListener != null){
            int taskready = taskCount-tempTaskCount;
            mListener.onMethodProgressChanged(DataHelper.getPercent(taskready, taskCount),tempTaskCount, taskready);
        }
    }

    public Boolean isCalled(String method){
        return methodCalledMap.get(method);
    }

    public synchronized Boolean isAllCalled(){
        Boolean result = Boolean.TRUE;
        tempTaskCount = 0;
        for(Map.Entry<String, Boolean> b : methodCalledMap.entrySet()){
            if(!b.getValue()){
                tempTaskCount++;
                result = Boolean.FALSE;
            }
        }



        return result;
    }


}
