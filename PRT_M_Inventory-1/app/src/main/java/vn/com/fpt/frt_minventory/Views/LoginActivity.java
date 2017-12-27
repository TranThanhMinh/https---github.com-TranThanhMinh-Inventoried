package vn.com.fpt.frt_minventory.Views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vn.com.fpt.frt_minventory.Libs.UIHelper;
import vn.com.fpt.frt_minventory.MainActivity;
import vn.com.fpt.frt_minventory.Model.DataUserLogin;
import vn.com.fpt.frt_minventory.Model.LoginResult;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Services.APIService;

public class LoginActivity extends AppCompatActivity {

    EditText edtUs, edtPass;
    Button btn_login;
    Context context;
    public static String user;
    public static String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       context = this;

        edtUs = (EditText)findViewById(R.id.edt_User);
        edtPass = (EditText)findViewById(R.id.edt_Pass);
        btn_login = (Button)findViewById(R.id.btn_submit);
        edtUs.setText(DataUserLogin.Username);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        edtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login()
    {
        final String username = edtUs.getText().toString();
        final String password = edtPass.getText().toString();
        user= edtUs.getText().toString();
        // final String passwordCrypted = Authentication.encryptPassword(password);

        if (username.length() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Error!");
            alertDialog.setMessage("Vui lòng nhập Tên đăng nhập");
            alertDialog.setIcon(R.drawable.ic_dialog_close_light);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }
//          Kiem tra password
//        if (password.length() == 0) {
//            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
//            alertDialog.setTitle("Error!");
//            alertDialog.setMessage("Vui lòng nhập Password");
//            alertDialog.setIcon(R.drawable.ic_dialog_close_light);
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();
//           return;
//        }

        /** Process login */
       final ProgressDialog dialog = ProgressDialog.show(context, "Xin chờ", "Đang xử lý đăng nhập....", true, false);
          dialog.setCancelable(false);

        new AsyncTask<String, Void, LoginResult>() {
            @Override
            protected LoginResult doInBackground(String... params) {
                LoginResult results = APIService.getLogin(username, password);
                return results;
            }

            @Override
            protected void onPostExecute(LoginResult results) {
                dialog.dismiss();

                if (results != null) {
                    if (results.getResult() == 0) {
                        DataUserLogin.Shopcode = "30808";
                        userName = results.getEmployeeName();
                        /** Login success */
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //Authentication.saveLoginInfo(LoginActivity.this,results);

                        intent.putExtra("EmployeeName", results.getEmployeeName());
                        intent.putExtra("ShopName", results.getShopName());
                        intent.putExtra("JobTitle_name", results.getJobTitle_name());
                        DataUserLogin.Username = edtUs.getText().toString();
                        DataUserLogin.EmployeeName = results.getEmployeeName();
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    UIHelper.showAlertDialog(context, "Đăng nhập", "Máy chủ không phản hồi!", "Đóng", R.drawable.ic_dialog_close_light);
                }
            }
        }.execute();
    }
}
