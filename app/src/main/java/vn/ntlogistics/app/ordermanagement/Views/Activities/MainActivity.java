package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.BillFailActivity;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.ChangePassActivity;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.InfoProductActivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.MainVMs.MainActivityVM;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding         binding;
    private MainActivityVM              viewModel;


    public static void startIntentActivity(Context context){
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainActivityVM(this, binding);
        setSupportActionBar(binding.toolbarMain);

        binding.setViewModel(viewModel);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //MenuInflater inflater = menu.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnTTSP:
                if (existsUser()) {
                    Intent i = new Intent(this, InfoProductActivity.class);
                    startActivity(i);
                    //finish();
                }
                break;
            case R.id.acChangePass:
                if (existsUser()) {
                    Intent i = new Intent(this, ChangePassActivity.class);
                    startActivity(i);
                    //finish();
                }
                break;
            case R.id.mnBillFail:
                if (existsUser()) {
                    Intent i = new Intent(this, BillFailActivity.class);
                    startActivity(i);
                    //finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean existsUser() {
        if (SCurrentUser.getCurrentUser(this).getPublickey() == null) {
            Message.makeToastError(this,
                    getString(R.string.error_block_account));
            return false;
        } else
            return true;
    }
}
