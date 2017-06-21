package vn.ntlogistics.app.ordermanagement.Commons.Location.GeocodeMaps;

import android.os.AsyncTask;

import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.GetJsonFromUrl;


/**
 * Created by minhtan2908 on 9/27/16.
 */

public class GeocodeAPI extends AsyncTask<Void,Void,String> {
    GetJsonFromUrl getJsonFromUrl;
    String url;
    public GeocodeAPI(String url){
        this.url = url;
    }

    @Override
    protected String doInBackground(Void... params) {
        String response = null;
        try {
            getJsonFromUrl = new GetJsonFromUrl(url, "");
            response = getJsonFromUrl.getResponseStringGET();
        } catch (Exception e) {
            response = null;
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        onPost(s);
        cancel(true);
    }

    public void onPost(String s){

    }
}
