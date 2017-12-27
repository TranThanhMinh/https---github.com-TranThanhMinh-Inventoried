package vn.com.fpt.frt_minventory.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.fpt.frt_minventory.Adapter.Adapter_Camera;
import vn.com.fpt.frt_minventory.Adapter.Notify;
import vn.com.fpt.frt_minventory.Model.BarcodeResult;
import vn.com.fpt.frt_minventory.Model.DataUserLogin;
import vn.com.fpt.frt_minventory.Model.Image;
import vn.com.fpt.frt_minventory.Model.InsertImage;
import vn.com.fpt.frt_minventory.Model.ListInventoriedResult;
import vn.com.fpt.frt_minventory.Model.MPhoto;
import vn.com.fpt.frt_minventory.Model.Photo;
import vn.com.fpt.frt_minventory.Model.upload;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Scanner.BarcodeCaptureActivity;
import vn.com.fpt.frt_minventory.Services.APIService;
import vn.com.fpt.frt_minventory.Services.KeyApi;
import vn.com.fpt.frt_minventory.Services.Url;

public class InventoryDetail extends AppCompatActivity {
    Boolean Saved = false;
    Boolean Whs_code = false;  /*kiem ma kho da chon hay chua*/
    boolean tontai = false;
    String Docentry;
    String docstatus;
    String CreateBy;
    String Mansernum;
    int Sys_Quantity = 0;
    int LoaiSPCode = 0;
    boolean scan = false;
    boolean save = false;
    int ii = 0;
    ImageView imgCamera;
    TextView txt_SoPhieu, txt_MaKho;
    List<Photo> listCamera = new ArrayList<Photo>();
    Context context = this;
    ImageButton img_barcode, img_list;
    ImageView img_warehouse, btn_back;
    EditText Ten_SP, Serial, txt_SoLuong, Note, txt_MaSP;
    Button btn_Save, btn_Refresh, btn_Exit;
    Realm myOtherRealm;
    public static List<ListInventoriedResult> arrItem = new ArrayList<>();
    public static List<BarcodeResult> arrBarcodeItem = new ArrayList<>();
    private static final int RC_BARCODE_CAPTURE = 9001;
    public static final int WAREHOUSE_CODE = 9002;
    String edt_MaKho_Detail = "";
    private Retrofit retrofit;
    private List<InsertImage> list = new ArrayList<>();
    private List<upload> listUp = new ArrayList<>();
    private Realm mRealm;
    boolean check_list = false;

    ProgressDialog progressDialog;
    String checkBacode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        Realm.init(context);
        myOtherRealm = Realm.getDefaultInstance();
        mRealm = Realm.getDefaultInstance();
        retrofit = getConnect();
        imgCamera = (ImageView) findViewById(R.id.imgCamera);
        txt_MaSP = (EditText) findViewById(R.id.txt_MaSP_Detail);
        txt_SoPhieu = (TextView) findViewById(R.id.txt_So_Phieu_Detail);
        img_barcode = (ImageButton) findViewById(R.id.imgScan);
        img_list = (ImageButton) findViewById(R.id.img_List_inventoried);
        img_warehouse = (ImageView) findViewById(R.id.img_Warehouse);
        btn_back = (ImageView) findViewById(R.id.btn_back_inventory_detail);
        Ten_SP = (EditText) findViewById(R.id.edt_TenSP_Detail);
        Serial = (EditText) findViewById(R.id.edt_Serial_Detail);
        txt_SoLuong = (EditText) findViewById(R.id.edt_SoLuong_Detail);
        txt_MaKho = (TextView) findViewById(R.id.txt_MaKho_Detail);
        btn_Save = (Button) findViewById(R.id.btn_Save_Detail);
        btn_Refresh = (Button) findViewById(R.id.btn_Frefesh_Detail);
        btn_Exit = (Button) findViewById(R.id.btn_Exit_Detail);
        Note = (EditText) findViewById(R.id.edt_note);

        final Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("bundle");
        Docentry = bundle.getString("sophieu");
        LoaiSPCode = bundle.getInt("LoaiSPCode");
        docstatus = bundle.getString("trangthai");
        CreateBy = bundle.getString("CreateBy");
        txt_SoPhieu.setText("Số phiếu: " + bundle.getString("sophieu"));
        Log.e("minhrfom", listCamera.size() + "");
        if (docstatus.equals("F") || docstatus.equals("C")) {
            txt_MaSP.setEnabled(false);
            Ten_SP.setEnabled(false);
            Serial.setEnabled(false);
            txt_SoLuong.setEnabled(false);
            img_warehouse.setEnabled(false);
            Note.setEnabled(false);
            img_barcode.setEnabled(false);
            btn_Save.setEnabled(false);
            btn_Exit.setEnabled(false);


            Saved = true;

        }
        getListImage();
        loadlistinventoried();
        String code = i.getStringExtra("WarehouseCode");

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_MaSP.getText().toString().equals("") || txt_SoLuong.getText().toString().equals("")) {
                    Toasty.info(InventoryDetail.this, "Phiếu này chưa có thông tin!", 1).show();
                    return;
                }
//              Kiem tra phan ghi chu doi vơi nhung sp khong ton kho
                else if (Mansernum.equals("")) {
                    if (txt_MaKho.getText().toString().equals("")) {
                        Toasty.info(InventoryDetail.this, "Vui lòng chọn mã kho!", 1).show();
                        return;
                    } else if (Note.getText().toString().equals("") || Note.getText().toString().contains("\n")) {
                        Toasty.warning(InventoryDetail.this, "Chưa nhập ghi chú!").show();
                        return;
                    } else if (txt_SoLuong.getText().toString().equals("") || txt_SoLuong.getText().toString().equals("0") || txt_SoLuong.getText().toString().equals("00") || txt_SoLuong.getText().toString().equals("000") || txt_SoLuong.getText().toString().equals("0000") || txt_SoLuong.getText().toString().equals("00000")) {
                        Toasty.warning(InventoryDetail.this, "Sai số lượng").show();
                        return;
                    } else if (listCamera.size() == 0) {
                        Toasty.warning(InventoryDetail.this, "Vui lòng chụp hình ảnh").show();
                    } else {
                        if (checkBacode.equals("030") || checkBacode.equals("031")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(InventoryDetail.this);
                            builder.setMessage(R.string.Noti_shop);
                            builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog
                                    check_list = false;
                                    scan = false;
                                    save = true;
                                    UpListService();

                                    dialog.dismiss();
                                }
                            });

                            builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        } else {
                            check_list = false;
                            scan = false;
                            save = true;
                            UpListService();
                        }
                    }
                } else if (Mansernum.equals("N")) {
                    if (listCamera.size() == 0) {
                        Toasty.warning(InventoryDetail.this, "Vui lòng chụp hình ảnh").show();
                    } else {
                        if (checkBacode.equals("030") || checkBacode.equals("031")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(InventoryDetail.this);
                            builder.setMessage(R.string.Noti_shop);
                            builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog
                                    check_list = false;
                                    scan = false;
                                    save = true;
                                    UpListService();

                                    dialog.dismiss();
                                }
                            });

                            builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        } else {
                            UpListService();
                        }
                    }
                }


            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clean();
            }
        });

        img_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ii = 0;
                if (scan == false) {
                    imgCamera.setVisibility(View.GONE);
                    Intent intent = new Intent(InventoryDetail.this, BarcodeCaptureActivity.class);
                    intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                    startActivityForResult(intent, RC_BARCODE_CAPTURE);

                } else {
                    if (txt_MaSP.getText().toString().equals("") || txt_SoLuong.getText().toString().equals("")) {
                        Toasty.info(InventoryDetail.this, "Phiếu này chưa có thông tin!", 1).show();
                        return;
                    }
//              Kiem tra phan ghi chu doi vơi nhung sp khong ton kho
                    else if (Mansernum.equals("")) {
                        if (txt_MaKho.getText().toString().equals("")) {
                            Toasty.info(InventoryDetail.this, "Vui lòng chọn mã kho!", 1).show();
                            return;
                        } else if (Note.getText().toString().equals("") || Note.getText().toString().contains("\n")) {
                            Toasty.warning(InventoryDetail.this, "Chưa nhập ghi chú!").show();
                            return;
                        } else if (txt_SoLuong.getText().toString().equals("") || txt_SoLuong.getText().toString().equals("0") || txt_SoLuong.getText().toString().equals("00") || txt_SoLuong.getText().toString().equals("000") || txt_SoLuong.getText().toString().equals("0000") || txt_SoLuong.getText().toString().equals("00000")) {
                            Toasty.warning(InventoryDetail.this, "Sai số lượng").show();
                            return;
                        } else if (listCamera.size() == 0) {
                            Toasty.warning(InventoryDetail.this, "Vui lòng chụp hình ảnh").show();
                            return;
                        } else {
                            if (checkBacode.equals("030") || checkBacode.equals("031")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(InventoryDetail.this);
                                builder.setMessage(R.string.Noti_shop);
                                builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        UpListService();
                                        check_list = false;
                                        dialog.dismiss();
                                    }
                                });

                                builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing
                                        dialog.dismiss();
                                        imgCamera.setVisibility(View.GONE);
                                        Intent intent = new Intent(InventoryDetail.this, BarcodeCaptureActivity.class);
                                        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                                        startActivityForResult(intent, RC_BARCODE_CAPTURE);
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else {
                                UpListService();
                            }
                            return;
                        }

                    } else if (Mansernum.equals("N")) {
                        if (listCamera.size() == 0) {
                            Toasty.warning(InventoryDetail.this, "Vui lòng chụp hình ảnh").show();
                        } else {
                            if (checkBacode.equals("030") || checkBacode.equals("031")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(InventoryDetail.this);
                                builder.setMessage(R.string.Noti_shop);
                                builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        check_list = false;
                                        scan = false;
                                        save = true;
                                        UpListService();

                                        dialog.dismiss();
                                    }
                                });

                                builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else {
                                UpListService();
                            }
                        }
                    }

                }

            }
        });
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
        });
        img_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ii = 0;
                if (scan == true) {
                    if (txt_MaSP.getText().toString().equals("") || txt_SoLuong.getText().toString().equals("")) {
                        Toasty.info(InventoryDetail.this, "Phiếu này chưa có thông tin!", 1).show();
                        return;
                    }
//              Kiem tra phan ghi chu doi vơi nhung sp khong ton kho
                    else if (Mansernum.equals("")) {
                        if (txt_MaKho.getText().toString().equals("")) {
                            Toasty.info(InventoryDetail.this, "Vui lòng chọn mã kho!", 1).show();
                            return;
                        } else if (Note.getText().toString().equals("") || Note.getText().toString().contains("\n")) {
                            Toasty.warning(InventoryDetail.this, "Chưa nhập ghi chú!").show();
                            return;
                        } else if (txt_SoLuong.getText().toString().equals("") || txt_SoLuong.getText().toString().equals("0") || txt_SoLuong.getText().toString().equals("00") || txt_SoLuong.getText().toString().equals("000") || txt_SoLuong.getText().toString().equals("0000") || txt_SoLuong.getText().toString().equals("00000")) {
                            Toasty.warning(InventoryDetail.this, "Sai số lượng").show();
                            return;
                        } else if (listCamera.size() == 0) {
                            Toasty.warning(InventoryDetail.this, "Vui lòng chụp hình ảnh").show();
                        } else {
                            imgCamera.setVisibility(View.GONE);

                            if (checkBacode.equals("030") || checkBacode.equals("031")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(InventoryDetail.this);
                                builder.setMessage(R.string.Noti_shop);
                                builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        check_list = true;
                                        scan = false;
                                        UpListService();
                                        dialog.dismiss();
                                    }
                                });

                                builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else {
                                check_list = true;
                                scan = false;
                                UpListService();
                            }
                        }
                    } else if (Mansernum.equals("N")) {
                        if (listCamera.size() == 0) {
                            Toasty.warning(InventoryDetail.this, "Vui lòng chụp hình ảnh").show();
                        } else {
                            if (checkBacode != null && checkBacode.length() > 0) {
                                if (checkBacode.equals("030") || checkBacode.equals("031")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(InventoryDetail.this);
                                    builder.setMessage(R.string.Noti_shop);
                                    builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do nothing but close the dialog
                                            check_list = false;
                                            scan = false;
                                            save = true;
                                            UpListService();

                                            dialog.dismiss();
                                        }
                                    });

                                    builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do nothing
                                            dialog.dismiss();
                                        }
                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                } else {
                                    UpListService();
                                }
                            }else UpListService();
                        }
                    }

                } else {
                    Intent intent = new Intent(InventoryDetail.this, ListInventoried.class);
                    intent.putExtra("docentry", Docentry);
                    intent.putExtra("docstatus", docstatus);
                    startActivity(intent);
                    Clean();
                }
            }
        });

        img_warehouse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(InventoryDetail.this, SelectWarehouseCode.class);
                Whs_code = true;
                startActivityForResult(intent, WAREHOUSE_CODE);
            }
        });
        txt_MaKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryDetail.this, SelectWarehouseCode.class);
                startActivityForResult(intent, WAREHOUSE_CODE);
                Whs_code = true;
            }
        });
        btn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Saved) {
                    Toasty.warning(InventoryDetail.this, "Vui lòng nhấn lưu trước khi kết thúc phiếu!", 1).show();
                    return;
                }
                progressDialog = ProgressDialog.show(context, "Xin chờ", "Đang up thông tin...", true, false);

                Upload(listUp, listUp.size() - 1);


            }
        });

        txt_MaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt = (EditText) v;
                if (!edt.getText().toString().equals("")) {
                    Find_SanPham(edt.getText().toString());
                }
            }
        });

        Serial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText edt = (EditText) v;
                if (!hasFocus && !edt.getText().toString().equals("")) {
                    Find_SanPham(edt.getText().toString());
                }

            }
        });
        Note.setImeOptions(EditorInfo.IME_ACTION_DONE);
        Note.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }

    protected void onStop() {
        Log.e("", "onStop");
        super.onStop();
    }

    public void getListImage() {
        RealmResults<MPhoto> books = mRealm.where(MPhoto.class).equalTo("DocEntry", Docentry).findAll();
        listUp = new ArrayList<>();
        if (books.isLoaded()) {

            for (MPhoto p : books) {
                upload up = new upload();
                up.setDeviceType("1");//thietbi 1 adroid , 2 ios
                up.setDocEntry(Docentry);//so phieu
                up.setFileByte(p.getFileByte());//base64
                up.setSerial(p.getSerial());//base64
                up.setImageName(p.getImageName());//name.jpg
                up.setItemCode(p.getItemCode());//ma san pham
                up.setDeviceType("1");//1
                listUp.add(up);

            }
        }
    }

    private void ketthucKK(String DocEntry, String Createby, final String image) {
        final String _DocEntry = Docentry;
        final String _CreateBy = Createby;
        final String _image = image;


        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {

                    String tableResult = APIService.getKetThucKK(_DocEntry, "F", _CreateBy, _image);
                    return tableResult;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(String inventorydetail) {

                progressDialog.dismiss();
                if (inventorydetail != null) {
                    if (inventorydetail.contains("1")) {
                        Toasty.success(InventoryDetail.this, "Kiểm kê thành công!").show();
                        finish();
                    } else
                        Toasty.success(InventoryDetail.this, "Kiểm kê thất bại!").show();
                    Log.e("inventorydetail", inventorydetail);

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(InventoryDetail.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Kết nối internet bị gián đoạn");
                    alertDialog.setIcon(R.drawable.ic_dialog_close_light);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

    }

    public void Upload(List<upload> list, final int pos) {
        KeyApi api = retrofit.create(KeyApi.class);
        upload up = new upload();
        up.setDeviceType(list.get(pos).getDeviceType());
        up.setDocEntry(list.get(pos).getDocEntry());
        if (list.get(pos).getFileByte() != null && list.get(pos).getFileByte().length() > 0)
            up.setFileByte(list.get(pos).getFileByte());
        else up.setFileByte("1");
        up.setImageName(list.get(pos).getImageName());
        up.setItemCode(list.get(pos).getItemCode());
        up.setDeviceType("1");
        Call<Image> call = api.InvetoryUploadImage(up);
        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                List<InsertImage> list = response.body().getInsertImage();
                if (list.get(0).getResult() == 0) {//0
                    progressDialog.dismiss();
                    Toasty.success(InventoryDetail.this, "Tải hình ảnh thất bại !").show();
//                    if (pos == 0) {
//                        Toasty.success(InventoryDetail.this, "Kiểm kê  thành công!").show();
//                        Intent intent = getIntent();
//                        setResult(InventoryOnDay.RC_IVNENTORY_FINISH, intent);
//                        finish();
//                        RealmResults<MPhoto> books = mRealm.where(MPhoto.class).equalTo("DocEntry",Docentry).findAll();
//                        if (!books.isEmpty()) {
//                            for (int i = books.size() - 1; i >= 0; i--) {
//                                mRealm.beginTransaction();
//                                books.get(i).deleteFromRealm();
//                                mRealm.commitTransaction();
//                            }
//                        } else Log.d("thong bao", "khong co");
//                    } else {
////                        Upload(listUp, pos - 1);
//                    }
//                    Log.d("getDeviceType", list.get(0).getDeviceType() + "");
//                    Log.d("getImageName", list.get(0).getImageName() + "");
//                    Log.d("getDocentry", list.get(0).getDocentry() + "");
//                    Log.d("getResult", list.get(0).getResult() + "");
                    //  Toast.makeText(MainActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                } else {//1
                    if (pos == 0) {
                        Intent intent = getIntent();
                        setResult(InventoryOnDay.RC_IVNENTORY_FINISH, intent);

                        RealmResults<MPhoto> books = mRealm.where(MPhoto.class).equalTo("DocEntry", Docentry).findAll();

                        String l_XmlDetails = "<DocumentElement>";
                        for (upload item : listUp) {
                            l_XmlDetails += "<DATA>";
                            l_XmlDetails += "<Docentry>" + item.getDocEntry() + "</Docentry>";
                            l_XmlDetails += "<ItemCode>" + item.getItemCode() + "</ItemCode>";
                            l_XmlDetails += "<Serial>" + item.getSerial() + "</Serial>";
                            l_XmlDetails += "<ImageName>" + item.getImageName() + "</ImageName>";
                            l_XmlDetails += "</DATA>";
                        }
                        l_XmlDetails += "</DocumentElement>";
                        Log.e("xml", l_XmlDetails);

                        if (!books.isEmpty()) {
                            for (int i = books.size() - 1; i >= 0; i--) {
                                mRealm.beginTransaction();
                                books.get(i).deleteFromRealm();
                                mRealm.commitTransaction();
                            }
                        } else Log.d("thong bao", "khong co");
                        ketthucKK(Docentry, CreateBy, l_XmlDetails);
                    } else {
                        Upload(listUp, pos - 1);
                        Log.e("im up", listUp.get(pos - 1).getImageName());
                    }
                    // Toast.makeText(MainActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {

            }
        });
    }

    public static String ConvertBitmapToString(Bitmap bitmap) {
        String encodedImage = "";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        return encodedImage;
    }

    private void UpListService() {
        ii = 0;
        try {
            if (txt_SoLuong.getText().toString().length() > 0) {

                for (ListInventoriedResult listInventoriedResult : arrItem) {

                    if (listInventoriedResult.getItemCode().equals(txt_MaSP.getText().toString())) {

///                         nếu sản phẩm đã có trong kho thì cộng dồn số lượng thực tế
                        if (Mansernum.equals("N")) {
                            listInventoriedResult.setR_Quantity(listInventoriedResult.getR_Quantity() + Integer.parseInt(txt_SoLuong.getText().toString()));
                        }

                        break;
                    }

                }
                if (!tontai && (Mansernum.equals("Y") && Serial.getText().toString().length() > 0) || (!tontai && Mansernum.equals("N")) || (!tontai && Mansernum.equals(""))) {
                    ListInventoriedResult listInventoriedResult = new ListInventoriedResult();
                    listInventoriedResult.setItemCode(txt_MaSP.getText().toString());
                    listInventoriedResult.setItemName(Ten_SP.getText().toString());
                    listInventoriedResult.setWhs_Code(txt_MaKho.getText().toString());
                    listInventoriedResult.setSerial(Serial.getText().toString());
                    listInventoriedResult.setSys_Quantiy(0);
                    listInventoriedResult.setGhiChu(Note.getText().toString());
                    listInventoriedResult.setLineNum(getmaxLinenum(arrItem) + 1);
                    listInventoriedResult.setR_Quantity(Integer.parseInt(txt_SoLuong.getText().toString()));
                    if (Mansernum.equals("") && !Serial.getText().toString().equals("")) {
                        Mansernum = "Y";
                    }
                    listInventoriedResult.setManserNum(Mansernum);
                    listInventoriedResult.setUpdateBy(DataUserLogin.Username);
                    arrItem.add(listInventoriedResult);

                }

            }

            if (arrItem.size() > 0) {
                AddToRealm(arrItem);
                String l_XmlDetails = "<DocumentElement>";
                for (ListInventoriedResult item : arrItem) {
                    l_XmlDetails += "<DATA>";
                    l_XmlDetails += "<Docentry>" + Docentry + "</Docentry>";
                    l_XmlDetails += "<ItemCode>" + item.getItemCode() + "</ItemCode>";
                    l_XmlDetails += "<ItemName>" + item.getItemName() + "</ItemName>";
                    l_XmlDetails += "<WhsCode>" + item.getWhs_Code() + "</WhsCode>";
                    l_XmlDetails += "<Updateby>" + item.getUpdateBy() + "</Updateby>";
                    l_XmlDetails += "<GhiChu>" + item.getGhiChu() + "</GhiChu>";
                    l_XmlDetails += "<Sys_Quantity>" + item.getSys_Quantiy() + "</Sys_Quantity>";
                    l_XmlDetails += "<Mansernum>" + item.getManserNum() + "</Mansernum>";
                    l_XmlDetails += "<DistNumber>" + item.getSerial() + "</DistNumber>";
                    l_XmlDetails += "<lineNum>" + item.getLineNum() + "</lineNum>"; // ko dc trung nhau
                    l_XmlDetails += "<R_Quantity>" + item.getR_Quantity() + "</R_Quantity>";
                    l_XmlDetails += "<ShopCode>" + DataUserLogin.Shopcode + "</ShopCode>";
                    l_XmlDetails += "</DATA>";
                }
                l_XmlDetails += "</DocumentElement>";
                inventorydetail(l_XmlDetails);
            }
        } catch (Exception ex) {
            Toasty.error(InventoryDetail.this, "Lỗi").show();
        }
    }

    private void AddToRealm(List<ListInventoriedResult> listInventoriedResult) {
        myOtherRealm.beginTransaction();
        try {
            if (listInventoriedResult == null || listInventoriedResult.size() <= 0) {
                myOtherRealm.commitTransaction();
                return;
            }

            RealmResults<ListInventoriedResult> realmResults = myOtherRealm.where(ListInventoriedResult.class).findAll();
            realmResults.deleteAllFromRealm();
            for (ListInventoriedResult item : listInventoriedResult) {
                myOtherRealm.copyToRealm(item);
            }

        } catch (Exception e) {

        }
        myOtherRealm.commitTransaction();
    }

    private List<ListInventoriedResult> LoadListfromLocal() {
        RealmResults<ListInventoriedResult> realmResults = myOtherRealm.where(ListInventoriedResult.class).findAll();
        arrItem.clear();
        for (ListInventoriedResult item : realmResults) {
            arrItem.add(item);
        }
        return arrItem;

    }

    private int getmaxLinenum(List<ListInventoriedResult> _list) {
        int max = 0;
        for (int i = 0; i < _list.size(); i++) {
            if (_list.get(i).getLineNum() > max) {
                max = _list.get(i).getLineNum();
            }
        }
        return max;
    }

    protected void addlist(ArrayList<ListInventoriedResult> _list) {
        arrItem = _list;
    }

    @Override
    protected void onResume() {
        super.onResume();



    }


    //    Kiem tra sp qua activity_camera barcode
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            Load_list_barcode();
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    final Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    final ProgressDialog progressDialog = ProgressDialog.show(context, "Xin chờ", "Đang kiểm tra thông tin...",
                            true, false);

                    new AsyncTask<Void, Void, List<BarcodeResult>>() {
                        @Override
                        protected List<BarcodeResult> doInBackground(Void... params) {
                            try {

                                List<BarcodeResult> arrItem = APIService.getInventoryDetail(barcode.displayValue, txt_MaKho.getText().toString(), LoaiSPCode);
                                return arrItem;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(List<BarcodeResult> inventorydetail) {

                            progressDialog.dismiss();
                            if (inventorydetail != null && inventorydetail.size() > 0) {
                                Mansernum = inventorydetail.get(0).getMansernum();
//chup hinh,-
                                if (Mansernum.equals("")) {

                                    scan = true;
                                    imgCamera.setVisibility(View.VISIBLE);
                                    Toasty.info(InventoryDetail.this, "Vui lòng nhập ghi chú").show();
                                    txt_MaSP.setText(inventorydetail.get(0).getItemCode());
                                    Ten_SP.setText("Sản phẩm không tồn kho");
                                    Serial.setText(txt_MaSP.getText());
                                    txt_SoLuong.setText("1");
                                    txt_SoLuong.setEnabled(false);
                                    Note.requestFocus();
                                } else if (Mansernum.equals("N")) {
                                    scan = true;
                                    Toasty.info(InventoryDetail.this, "Vui lòng nhập số lượng").show();
                                    txt_MaSP.setText(barcode.displayValue);
                                    imgCamera.setVisibility(View.VISIBLE);
                                    // txt_MaSP.setText(inventorydetail.get(0).getItemCode());
                                    Ten_SP.setText(inventorydetail.get(0).getItemName());

                                    Serial.setText(inventorydetail.get(0).getDistNumber());
                                    Log.e("Thong tin", Mansernum + " " + inventorydetail.get(0).getItemCode() + " " + inventorydetail.get(0).getItemName() + " " + inventorydetail.get(0).getDistNumber());
                                    Ten_SP.setEnabled(false);
                                    Serial.setEnabled(false);
                                    Note.setEnabled(false);
                                    txt_SoLuong.requestFocus();
                                } else if (Mansernum.equals("E")) {
                                    Toasty.info(InventoryDetail.this, inventorydetail.get(0).getItemName(), 1).show();
                                    txt_MaSP.setText(inventorydetail.get(0).getItemCode());
                                    txt_MaSP.setEnabled(false);
                                    //Ten_SP.setText(inventorydetail.get(0).getItemName());
                                    Ten_SP.setEnabled(false);
                                    Serial.setText(inventorydetail.get(0).getDistNumber());
                                    Serial.setEnabled(false);
                                    Note.setEnabled(false);
                                    txt_SoLuong.setEnabled(false);
                                } else if (Mansernum.equals("Y")) {
                                    txt_MaSP.setText(inventorydetail.get(0).getItemCode());
                                    Ten_SP.setText(inventorydetail.get(0).getItemName());
                                    Serial.setText(inventorydetail.get(0).getDistNumber());
                                    inventorydetail.get(0).setLineNum(getmaxLinenum(arrItem) + 1);
                                    Toasty.success(InventoryDetail.this, "Checked").show();
                                    UpListService();
                                    Intent intent = new Intent(InventoryDetail.this, BarcodeCaptureActivity.class);
                                    intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                                    startActivityForResult(intent, RC_BARCODE_CAPTURE);

                                } else {
                                }
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(InventoryDetail.this).create();
                                alertDialog.setTitle("Error!");
                                alertDialog.setMessage("Kết nối internet bị gián đoạn");
                                alertDialog.setIcon(R.drawable.ic_dialog_close_light);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }

                        }

                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    progressDialog.dismiss();
                } else {

                }

            }
        } else if (requestCode == WAREHOUSE_CODE) {
            if (data != null) {
                String code = data.getStringExtra("WarehouseCode");
                checkBacode = code.substring(code.length() - 3);


                txt_MaKho.setText(code);
            }
        } else {

            if (ii < 3 && resultCode == Activity.RESULT_OK && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                String encodedImage = ConvertBitmapToString(photo);
                String url = "";
                String url1 = "";
                String[] a = encodedImage.split("\\n");
                for (String line : a) {
                    url = url + "" + line;
                }
                String[] b = url.split(" ");
                for (String line : b) {
                    url1 = url1 + "" + line;
                }
                Log.d("basehuhu", url1);

                Uri selectImage = data.getData();

                Photo ph = new Photo();
                ph.setId(txt_MaSP.getText().toString());
                ph.setUri(selectImage.toString());
                ph.setBitmap(selectImage.toString());
                listCamera.add(ph);

                //save data
                mRealm.beginTransaction();
                MPhoto book = mRealm.createObject(MPhoto.class);
                book.setId(txt_MaSP.getText().toString());
                book.setBitmap(selectImage.toString());
                book.setUri(url1);
                book.setDeviceType("1");//thietbi 1 adroid , 2 ios
                book.setDocEntry(Docentry);//so phieu
                book.setFileByte(url1);//base64
                book.setSerial(Serial.getText().toString());//base64
                Log.e("serial", Serial.getText().toString());
                book.setImageName(txt_MaSP.getText().toString() + ii + ".jpg");//name.jpg
                book.setItemCode(txt_MaSP.getText().toString());//ma san pham
                book.setDeviceType("1");//1
                mRealm.commitTransaction();


//add
                upload up = new upload();
                up.setDeviceType("1");//thietbi 1 adroid , 2 ios
                up.setDocEntry(Docentry);//so phieu
                up.setFileByte(url1);//base64
                up.setSerial(Serial.getText().toString());//base64
                //Log.d("base64",p.getFileByte());
                up.setImageName(txt_MaSP.getText().toString() + ii + ".jpg");//name.jpg
                up.setItemCode(txt_MaSP.getText().toString());//ma san pham
                up.setDeviceType("1");//1
                listUp.add(up);
                ii++;
                Log.e("minh to", listCamera.size() + "");

            } else if (ii == 3) {
                Toast.makeText(this, "Bạn chỉ chụp tối đa 3 hình ", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void Load_list_barcode() {
        Ten_SP.setText("");
        Serial.setText("");
        txt_SoLuong.setText("");
        txt_MaSP.setText("");
        Note.setText("");

        Ten_SP.setEnabled(true);
        Serial.setEnabled(true);
        txt_SoLuong.setEnabled(true);
        txt_MaSP.setEnabled(true);
        Note.setEnabled(true);
    }

    protected void inventorydetail(String l_XmlDetails) {
        final String _XmlDetails = l_XmlDetails;
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Xin chờ", "Đang up thông tin...", true, false);
        new AsyncTask<Void, Void, Notify>() {
            @Override
            protected Notify doInBackground(Void... params) {
                try {

                    Notify tableResult = APIService.saveSelectWarehouseCodes(_XmlDetails);
                    return tableResult;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(Notify inventorydetail) {

                progressDialog.dismiss();
                if (inventorydetail != null) {
                    if (inventorydetail.getResult() == 1) {
                        Toasty.success(InventoryDetail.this, "Lưu thành công!").show();
                        Saved = true;
                        checkBacode = null;
                        listCamera = new ArrayList<>();
                        if (check_list == true) {
                            Intent intent = new Intent(InventoryDetail.this, ListInventoried.class);
                            intent.putExtra("docentry", Docentry);
                            intent.putExtra("docstatus", docstatus);
                            startActivity(intent);
                        } else if (scan == true) {
                            imgCamera.setVisibility(View.GONE);
                            Intent intent = new Intent(InventoryDetail.this, BarcodeCaptureActivity.class);
                            intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                            startActivityForResult(intent, RC_BARCODE_CAPTURE);

                        } else {

                        }
                        check_list = false;
                        scan = false;
                        save = false;
                        Clean();
                    } else {
                        Toasty.error(InventoryDetail.this, "Lưu không thành công, Message: " + inventorydetail.getMessage()).show();
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(InventoryDetail.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Kết nối internet bị gián đoạn");
                    alertDialog.setIcon(R.drawable.ic_dialog_close_light);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    //    kiem tra sp qua nhap tay tren man hinh
    private void Find_SanPham(String itemcode) {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Xin chờ", "Đang lấy thông tin...",
                true, false);
        final String _item = itemcode;

        new AsyncTask<Void, Void, List<BarcodeResult>>() {
            @Override
            protected List<BarcodeResult> doInBackground(Void... params) {
                try {

                    List<BarcodeResult> arrItem = APIService.getInventoryDetail(_item, txt_MaKho.getText().toString(), LoaiSPCode);
                    return arrItem;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<BarcodeResult> inventorydetail) {

                progressDialog.dismiss();
                if (inventorydetail != null && inventorydetail.size() > 0) {

                    Mansernum = inventorydetail.get(0).getMansernum();

                    if (Mansernum.equals("")) {
                        scan = true;
                        imgCamera.setVisibility(View.VISIBLE);
                        Toasty.info(InventoryDetail.this, "Vui lòng nhập ghi chú").show();
                        txt_MaSP.setText(inventorydetail.get(0).getItemCode());

                        Ten_SP.setText("Sản phẩm không tồn kho");
                        Serial.setText(txt_MaSP.getText());
                        txt_SoLuong.setText("1");
                        txt_SoLuong.setEnabled(false);
                        Note.requestFocus();

                    } else if (Mansernum.equals("N")) {
                        scan = true;
                        Toasty.info(InventoryDetail.this, "Vui lòng nhập số lượng").show();
                        // Ten_SP.setText(inventorydetail.get(0).getItemName());
                        txt_MaSP.setText(Serial.getText().toString());
//                        Serial.setText(inventorydetail.get(0).getDistNumber());
                        Log.e("Thong tin", Mansernum + " " + inventorydetail.get(0).getItemCode() + " " + inventorydetail.get(0).getItemName() + " " + inventorydetail.get(0).getDistNumber());

                        Ten_SP.setEnabled(false);
                        Serial.setEnabled(false);
                        Note.setEnabled(false);
                        txt_SoLuong.requestFocus();
                        imgCamera.setVisibility(View.VISIBLE);

                    }

//                    barcode da co tren phieu khac || barcode bi trung
                    else if (Mansernum.equals("E")) {
                        Toasty.info(InventoryDetail.this, inventorydetail.get(0).getItemName(), 1).show();
                        txt_MaSP.setText(inventorydetail.get(0).getItemCode());
                        txt_MaSP.setEnabled(false);
                        Ten_SP.setEnabled(false);
                        Serial.setText(inventorydetail.get(0).getDistNumber());
                        Serial.setEnabled(false);
                        Note.setEnabled(false);
                        txt_SoLuong.setEnabled(false);

                    } else if (Mansernum.equals("Y") && LoaiSPCode == 2) {
                        if (Sys_Quantity > 0) {
                            Toasty.info(InventoryDetail.this, "Vui lòng nhập số lượng").show();
                            Ten_SP.setText(inventorydetail.get(0).getItemName());
                            Serial.setText(inventorydetail.get(0).getDistNumber());
                            Ten_SP.setEnabled(false);
                            Serial.setEnabled(false);
                            Note.setEnabled(false);
                            txt_SoLuong.requestFocus();
                        } else if (Sys_Quantity == 0) {
                            Toasty.info(InventoryDetail.this, "Vui lòng nhập ghi chú").show();
                            txt_MaSP.setText(inventorydetail.get(0).getItemCode());
                            Ten_SP.setText("Sản phẩm không tồn kho");
                            Serial.setText(txt_MaSP.getText());
                            txt_SoLuong.setText("1");
                            Note.requestFocus();
                        }
                    } else if (Mansernum.equals("Y")) {

                        txt_MaSP.setText(inventorydetail.get(0).getItemCode());
                        Ten_SP.setText(inventorydetail.get(0).getItemName());
                        Serial.setText(inventorydetail.get(0).getDistNumber());
                        inventorydetail.get(0).setLineNum(getmaxLinenum(arrItem) + 1);
                        Toasty.success(InventoryDetail.this, "Checked").show();
                        Intent intent = new Intent(InventoryDetail.this, BarcodeCaptureActivity.class);
                        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                        startActivityForResult(intent, RC_BARCODE_CAPTURE);
                        UpListService();
                    } else {
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(InventoryDetail.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Kết nối internet bị gián đoạn");
                    alertDialog.setIcon(R.drawable.ic_dialog_close_light);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        progressDialog.dismiss();
    }

    protected void loadlistinventoried() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Xin chờ", "Đang tải thông tin...", true, false);
        new AsyncTask<Void, Void, List<ListInventoriedResult>>() {
            @Override
            protected List<ListInventoriedResult> doInBackground(Void... params) {
                try {
                    List<ListInventoriedResult> arrItem = APIService.getListInventoried(Docentry);
                    return arrItem;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(List<ListInventoriedResult> listInventoriedResults) {
                arrItem.clear();
                progressDialog.dismiss();
                if (listInventoriedResults != null) {
                    if (listInventoriedResults.size() > 0) {
                        arrItem.addAll(listInventoriedResults);
                    } else {
//                        AlertDialog alertDialog = new AlertDialog.Builder(InventoryDetail.this).create();
//                        alertDialog.setTitle("Thông báo!");
//                        alertDialog.setMessage("Không có danh sách!");
//                        alertDialog.setIcon(R.drawable.ic_dialog_close_light);
//                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                        alertDialog.show();
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(InventoryDetail.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Kết nối internet bị gián đoạn");
                    alertDialog.setIcon(R.drawable.ic_dialog_close_light);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void Clean() {
        Ten_SP.setText("");
        Serial.setText("");
        txt_SoLuong.setText("");
        txt_MaKho.setText("");
        txt_MaSP.setText("");
        Note.setText("");

        Ten_SP.setEnabled(true);
        Serial.setEnabled(true);
        txt_SoLuong.setEnabled(true);
        txt_MaSP.setEnabled(true);
        Note.setEnabled(true);
    }

}
