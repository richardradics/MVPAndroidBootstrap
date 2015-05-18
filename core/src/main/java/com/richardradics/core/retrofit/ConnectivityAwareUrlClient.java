package com.richardradics.core.retrofit;

import android.content.Context;
import android.util.Log;

import com.richardradics.commons.util.ConnectivityUtil;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Created by mark on 2015. 03. 30..
 */
@EBean
public class ConnectivityAwareUrlClient implements Client {

    private static final String TAG = "BaseRetrofitClient";
    protected AtomicInteger retries = new AtomicInteger(0);

    @RootContext
    Context context;

    private BaseRetryHandler retryHandler;

    private Client wrappedClient;

    public Client getWrappedClient() {
        return wrappedClient;
    }

    public void setWrappedClient(Client wrappedClient) {
        this.wrappedClient = wrappedClient;
    }

    public BaseRetryHandler getRetryHandler() {
        return retryHandler;
    }

    public void setRetryHandler(BaseRetryHandler retryHandler) {
        this.retryHandler = retryHandler;
    }

    @Override
    public Response execute(Request request) throws IOException {
        try {
            if (!ConnectivityUtil.isConnected(context)) {
                throw RetrofitError.unexpectedError("Nincs internet", new NoConnectivityException("No Internet"));
            } else {

                Response r = wrappedClient.execute(request);

                checkResult(r);

                return r;
            }
        } catch (RetrofitError retrofitError) {
            if (retry(retrofitError, retries)) {
                return execute(request);
            } else {
                throw new ConnectionError();
            }
        } catch (Exception e) {
            throw new ConnectionError();
        }
    }

    protected void checkResult(Response response) {
        int statusCode = response.getStatus();
        if (statusCode == 404 || statusCode == 400 || statusCode == 500 || statusCode == 403 || statusCode == 401) {
            throw RetrofitError.httpError(response.getUrl(), response, null, null);
        }
    }

    protected boolean retry(RetrofitError error, AtomicInteger retries) {
        //increment retries
        retries.incrementAndGet();
        Log.i(TAG, "request retry check: " + retries.get());

        //no internet connection
        if (error.getCause() instanceof NoConnectivityException) {
            Log.v(TAG, "No internet!");
            retryHandler.onNoInternet();
            return false;
        }

        Response r = error.getResponse();

        if (r != null && r.getStatus() == 400) {
            Log.v(TAG, "400 error! bad request!");
            if (retries.get() < retryHandler.RETRY_400_BADREQUEST) {

                retryHandler.on400();

                return true;

            } else {
                return false;
            }
        }

        if (r != null && r.getStatus() == 500) {
            Log.v(TAG, "500 error! internal server error!");
            if (retries.get() < retryHandler.RETRY_500_ISE) {

                retryHandler.on500();

                return true;

            } else {
                return false;
            }
        }

        if (r != null && r.getStatus() == 401) {
            Log.v(TAG, "401 error! unauthorized");
            if (retries.get() < retryHandler.RETRY_401_UNAUTHORIZED) {

                retryHandler.on401();

                return true;

            } else {
                return false;
            }
        }

        if (r != null && r.getStatus() == 403) {
            Log.v(TAG, "403 error! forbidden!");
            if (retries.get() < retryHandler.RETRY_403_FORBIDDEN) {

                retryHandler.on403();

                return true;

            } else {
                return false;
            }
        }

        if (r != null && r.getStatus() == 404) {
            Log.v(TAG, "404 hiba!");
            if (retries.get() < retryHandler.RETRY_404_NOTFOUND) { // retry
                retryHandler.on404();
                return true;
            } else { // no more
                retries.set(0);
                return false;
            }
        }

        //TODO other error check
        if (error.isNetworkError()) { //network error
            if (error.getCause() instanceof SocketTimeoutException) {//connection timeout check
                Log.v(TAG, "retry - socket timeout exception!");
                if (retries.get() < retryHandler.DEFAULT_RETRY_COUNT) { // retry
                    // retryHandler.onSocketTimeout();
                    return true;
                } else { // no more
                    retries.set(0);
                    return false;
                }
            } else {//no connection check
                Log.v(TAG, "retry - no connection check!");
                if (retries.get() < retryHandler.DEFAULT_RETRY_COUNT) { // retry
                    //  retryHandler.onNoConnectivity();
                    return true;
                } else { // no more
                    retries.set(0);
                    return false;
                }
            }
        } else { //non network error check
            Log.v(TAG, "retry - non error check!");
            retries.set(0);
            return false;
        }
    }


}
