package vn.ntlogistics.app.shipper.Commons.AbstractClass;

import android.os.AsyncTask;

/**
 * Created by Zanty on 27/07/2016.
 */
public abstract class BaseAsystask extends AsyncTask<Void, Void, Void> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onPre();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        doInBG();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onPost();
    }
    public abstract void onPre();
    public abstract void doInBG();
    public abstract void onPost();
}
