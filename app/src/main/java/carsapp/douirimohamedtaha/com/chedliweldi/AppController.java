package carsapp.douirimohamedtaha.com.chedliweldi;

/**
 * Created by oussama_2 on 11/19/2017.
 */


import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
 /*

    public static String SERVER_ADRESS="http://192.168.137.1/rest/v1/";
    public static String IMAGE_SERVER_ADRESS="http://192.168.137.1/images/";
    */

    public static String SERVER_ADRESS="http://192.168.43.114/rest/v1/";
    public static String TAHA_ADRESS="http://192.168.43.114/chedliweldi/";
    public static String IMAGE_SERVER_ADRESS="http://192.168.43.114/images/";



    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}