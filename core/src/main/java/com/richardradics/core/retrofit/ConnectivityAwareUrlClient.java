package com.richardradics.core.retrofit;

import android.content.Context;

import com.richardradics.commons.util.ConnectivityUtil;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;

import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Created by mark on 2015. 03. 30..
 */
@EBean
public class ConnectivityAwareUrlClient implements Client {


    @RootContext
    Context context;

    private Client wrappedClient;

    @Override
    public Response execute(Request request) throws IOException {
        if (!ConnectivityUtil.isConnected(context)) {
            throw RetrofitError.unexpectedError("Nincs internet", new NoConnectivityException("No Internet"));
        } else {
            return wrappedClient.execute(request);
        }
    }

    public Client getWrappedClient() {
        return wrappedClient;
    }

    public void setWrappedClient(Client wrappedClient) {
        this.wrappedClient = wrappedClient;
    }

}
