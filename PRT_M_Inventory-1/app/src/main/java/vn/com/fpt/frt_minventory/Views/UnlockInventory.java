package vn.com.fpt.frt_minventory.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import es.dmoral.toasty.Toasty;
import vn.com.fpt.frt_minventory.Model.UnlockInventoryResult;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Services.APIService;

/**
 * Created by ADMIN on 12/20/2017.
 */

public class UnlockInventory extends Activity {

    Button btn_unlock;
    ImageView img_back;
    EditText edt_unlock;
    String Unlock_DocEntry = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unlock_inventory);
        img_back = (ImageView) findViewById(R.id.img_back_unlock);
        btn_unlock = (Button) findViewById(R.id.btn_unlock);
        edt_unlock = (EditText) findViewById(R.id.edt_unlock);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fncUnlock();
            }
        });
    }


    private void fncUnlock() {
        final String Unlock_DocEntry = edt_unlock.getText().toString();

        final ProgressDialog progressDialog = ProgressDialog.show(UnlockInventory.this,
                "Xin chờ", "Đang mở khóa phiếu....", true, false);

        new AsyncTask<Void, Void, UnlockInventoryResult>() {

            @Override
            protected UnlockInventoryResult doInBackground(Void... voids) {
                try {
                    UnlockInventoryResult unlockInventoryResult = APIService.getUnlockInventoryResult(Unlock_DocEntry + "");
                    return unlockInventoryResult;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(UnlockInventoryResult unlockInventoryResult) {
                progressDialog.dismiss();
                if (unlockInventoryResult != null) {
                    if (unlockInventoryResult.getResult().equals("O")) {
                        Toasty.success(UnlockInventory.this, "Phiếu " + Unlock_DocEntry + " đã được mở!" ).show();
                        finish();
                    } else {
                        Toasty.info(UnlockInventory.this,
                                "Mở khóa phiếu thất bại" + "\n" + "vui lòng nhập chính xác mã phiếu", 1).show();
                    }

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(UnlockInventory.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Kết nối internet bị gián đoạn");
                    alertDialog.setIcon(R.drawable.ic_dialog_close_light);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    });
                    alertDialog.show();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
