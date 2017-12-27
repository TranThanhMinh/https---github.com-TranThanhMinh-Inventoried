package vn.com.fpt.frt_minventory.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.Serializable;
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
import vn.com.fpt.frt_minventory.Adapter.AdapterListInventoried;
import vn.com.fpt.frt_minventory.Adapter.AdapterViewImage;
import vn.com.fpt.frt_minventory.Adapter.Notify;
import vn.com.fpt.frt_minventory.Model.DataUserLogin;
import vn.com.fpt.frt_minventory.Model.Downloadimage;
import vn.com.fpt.frt_minventory.Model.ListInventoriedResult;
import vn.com.fpt.frt_minventory.Model.MPhoto;
import vn.com.fpt.frt_minventory.Model.Photo;
import vn.com.fpt.frt_minventory.Model.SearchImage;
import vn.com.fpt.frt_minventory.Model.ViewImage;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Services.APIService;
import vn.com.fpt.frt_minventory.Services.KeyApi;
import vn.com.fpt.frt_minventory.Services.Url;

import static android.provider.MediaStore.Video.Thumbnails.getThumbnail;

/**
 * Created by ADMIN on 11/28/2017.
 */

public class ListInventoried extends Activity {
    List<ListInventoriedResult> arrItemInventoriedResult = new ArrayList<>();
    List<ListInventoriedResult> arr = new ArrayList<>();
    AdapterListInventoried adapterListInventoried;
    Context context = this;
    String Docentry;
    String docstatus;
    RealmResults<MPhoto> books;
    ListView lv;
    String id_, serial;
    ImageView img_delete_lis, btn_update_list_inventoried, btn_back, imSearch;
    EditText search_list;
    Boolean Saved = false;
    Boolean check = false;
    public static int LIST_INVENTORIED = 121;
    private Realm mRealm;
    Retrofit retrofit;
    List<ViewImage> images;
    List<ListInventoriedResult> sort = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_inventoried);
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();
        retrofit = getConnect();
        Intent intent = getIntent();
        Docentry = intent.getStringExtra("docentry");
        docstatus = intent.getStringExtra("docstatus");
        //loadlistinventoried();
        imSearch = (ImageView) findViewById(R.id.imSearch);
        img_delete_lis = (ImageView) findViewById(R.id.img_delete_item_list_inventoried);
        //imSearch = (ImageView) findViewById(R.id.imSearch);
        lv = (ListView) findViewById(R.id.lv_list_inventoried);
        search_list = (EditText) findViewById(R.id.search_list_inventoried);
        btn_update_list_inventoried = (ImageView) findViewById(R.id.btn_update_list_inventoried);
        btn_back = (ImageView) findViewById(R.id.btn_back_list_inventoried);

        if (docstatus.equals("O")) {
            btn_update_list_inventoried.setVisibility(View.VISIBLE);
            imSearch.setVisibility(View.VISIBLE);
            imSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (check == false) {
                        sort = new ArrayList<>();
                        for (ListInventoriedResult result : arr) {
                            if (result.isCheck() == true) {
                                sort.add(result);
                            }
                        }
                        arrItemInventoriedResult = sort;
                        adapterListInventoried = new AdapterListInventoried(context, -1, arrItemInventoriedResult, docstatus);
                        lv.setAdapter(adapterListInventoried);

                        check = true;
                    } else {
                        sort = new ArrayList<>();
                        for (ListInventoriedResult result : arr) {
                            sort.add(result);
                        }
                        arrItemInventoriedResult = sort;
                        adapterListInventoried = new AdapterListInventoried(context, -1, arrItemInventoriedResult, docstatus);
                        lv.setAdapter(adapterListInventoried);

                        check = false;
                    }
                }
            });
        } else {
            imSearch.setVisibility(View.VISIBLE);
            imSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (check == false) {
                        sort = new ArrayList<>();
                        for (ListInventoriedResult result : arr) {
                            if (result.isCheck() == true) {
                                sort.add(result);
                            }
                        }
                        adapterListInventoried = new AdapterListInventoried(context, -1, sort, docstatus);
                        lv.setAdapter(adapterListInventoried);

                        check = true;
                    } else {
                        sort = new ArrayList<>();
                        for (ListInventoriedResult result : arr) {
                            sort.add(result);
                        }
                        adapterListInventoried = new AdapterListInventoried(context, -1, sort, docstatus);
                        lv.setAdapter(adapterListInventoried);

                        check = false;
                    }
                }
            });
            btn_update_list_inventoried.setVisibility(View.GONE);


        }
        if (docstatus.equals("O")) {

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    id_ = arrItemInventoriedResult.get(i).getItemCode();
                    serial = arrItemInventoriedResult.get(i).getSerial();
                    RealmResults<MPhoto> books = mRealm.where(MPhoto.class).equalTo("id_", id_).findAll();
                    List<Photo> list = new ArrayList<>();
                    if (books.isLoaded()) {
                        for (MPhoto p : books) {
                            Photo photo = new Photo();
                            photo.setId(p.getId());
                            photo.setUri(p.getFileByte());
                            photo.setBitmap(p.getBitmap());
                            list.add(photo);
                        }
                    }
                    Intent intent = new Intent(ListInventoried.this, CameraActivity.class).putExtra("Docentry", Docentry).putExtra("ManseNum", docstatus).putExtra("camera", (Serializable) list).putExtra("id", arrItemInventoriedResult.get(i).getItemCode());
                    startActivityForResult(intent, LIST_INVENTORIED);

                }
            });
        } else {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (check == true) {
                        id_ = arr.get(i).getItemCode();
                        serial = arr.get(i).getSerial();
                        Intent intent = new Intent(ListInventoried.this, CameraActivity.class).putExtra("Docentry", Docentry).putExtra("ManseNum", docstatus).putExtra("camera", (Serializable) new ArrayList<>()).putExtra("id", sort.get(i).getItemCode());
                        startActivityForResult(intent, LIST_INVENTORIED);
                    } else {
                        id_ = arrItemInventoriedResult.get(i).getItemCode();
                        serial = arrItemInventoriedResult.get(i).getSerial();
                        Intent intent = new Intent(ListInventoried.this, CameraActivity.class).putExtra("Docentry", Docentry).putExtra("ManseNum", docstatus).putExtra("camera", (Serializable) new ArrayList<>()).putExtra("id", sort.get(i).getItemCode());
                        startActivityForResult(intent, LIST_INVENTORIED);
                    }

                }
            });
        }
        search_list.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String find = search_list.getText().toString();
                adapterListInventoried.filter(find);
            }
        });
        getImage();
        btn_back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        btn_update_list_inventoried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update_List();
            }
        });


        //        an ban phim tren man hinh
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

    }

    public void getImage() {
        images = new ArrayList<>();
        KeyApi api = retrofit.create(KeyApi.class);
        Downloadimage up = new Downloadimage();
        Log.e("Docentry", Docentry);
        up.setDocentry(Integer.parseInt(Docentry));
        Call<SearchImage> call = api.InvetorySearchImage(up);
        call.enqueue(new Callback<SearchImage>() {
            @Override
            public void onResponse(Call<SearchImage> call, Response<SearchImage> response) {
                images = response.body().getSearchImage();

                loadlistinventoried();

            }

            @Override
            public void onFailure(Call<SearchImage> call, Throwable t) {

            }
        });


    }

    protected void Update_List() {
        String l_XmlDetails = "<DocumentElement>";
        for (ListInventoriedResult item : arrItemInventoriedResult) {
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

    protected void inventorydetail(String l_XmlDetails) {
        final String _XmlDetails = l_XmlDetails;
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Xin chờ", "Đang xử lý....", true, false);
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
                        Toasty.success(ListInventoried.this, "Đồng bộ thành công!").show();
                        Saved = true;
                        RealmResults<MPhoto> books = mRealm.where(MPhoto.class).findAll();
                        if (books.isLoaded()) {

                            for (int i = 0; i < books.size(); i++) {
                                for (int j = 0; j < arrItemInventoriedResult.size(); j++) {
                                    if (arrItemInventoriedResult.get(j).getSerial().equals(books.get(i).getSerial()) || arrItemInventoriedResult.get(j).getItemCode().equals(books.get(i).getItemCode())) {
                                        i = books.size();
                                    } else {
                                        if (i == books.size() - 1) {
                                            mRealm.beginTransaction();
                                            books.get(i).deleteFromRealm();
                                            mRealm.commitTransaction();

                                        }
                                    }

                                }

                            }

                        }
                    } else {
                        Toasty.error(ListInventoried.this, "Đồng bộ không thành công, Message: " + inventorydetail.getMessage()).show();
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(ListInventoried.this).create();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (docstatus.equals("O")) {
            getImage();
        }
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
                arrItemInventoriedResult.clear();
                progressDialog.dismiss();
                if (listInventoriedResults != null) {
                    if (listInventoriedResults.size() > 0) {
                        arrItemInventoriedResult.addAll(listInventoriedResults);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(ListInventoried.this).create();
                        alertDialog.setTitle("Thông báo!");
                        alertDialog.setMessage("Không có danh sách!");
                        alertDialog.setIcon(R.drawable.ic_dialog_close_light);
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    if (images != null && images.size() > 0) {
                        for (int j = arrItemInventoriedResult.size() - 1; j >= 0; j--) {
                            for (int i = 0; i < images.size(); i++) {
                                if (images.get(i).getSerial().equals(arrItemInventoriedResult.get(j).getSerial()) || arrItemInventoriedResult.get(j).getItemCode().equals(books.get(i).getItemCode())) {
                                    arrItemInventoriedResult.get(j).setCheck(true);
                                    i = images.size();
                                } else {
                                    if (i == images.size() - 1)
                                        arrItemInventoriedResult.get(j).setCheck(false);

                                }

                            }
                            arr.add(arrItemInventoriedResult.get(j));
                            sort.add(arrItemInventoriedResult.get(j));
                            adapterListInventoried = new AdapterListInventoried(context, -1, sort, docstatus);
                            lv.setAdapter(adapterListInventoried);

                        }
                        adapterListInventoried = new AdapterListInventoried(context, -1, arr, docstatus);
                        lv.setAdapter(adapterListInventoried);
                    } else {
                        RealmResults<MPhoto> books = mRealm.where(MPhoto.class).findAll();
                        if (books.isLoaded()) {
                            List<ListInventoriedResult> sort = new ArrayList<>();
                            for (int j = arrItemInventoriedResult.size() - 1; j >= 0; j--) {
                                for (int i = 0; i < books.size(); i++) {
                                    if (arrItemInventoriedResult.get(j).getSerial().equals(books.get(i).getSerial()) || arrItemInventoriedResult.get(j).getItemCode().equals(books.get(i).getItemCode())) {
                                        arrItemInventoriedResult.get(j).setCheck(true);
                                        i = books.size();
                                    } else {
                                        if (i == books.size() - 1)
                                            arrItemInventoriedResult.get(j).setCheck(false);
                                    }

                                }
                                sort.add(arrItemInventoriedResult.get(j));
                            }
                            arrItemInventoriedResult = sort;
                            arr = sort;
                            adapterListInventoried = new AdapterListInventoried(context, -1, arrItemInventoriedResult, docstatus);
                            lv.setAdapter(adapterListInventoried);
                        } else {
                            arr = arrItemInventoriedResult;
                            adapterListInventoried = new AdapterListInventoried(context, -1, arrItemInventoriedResult, docstatus);
                            lv.setAdapter(adapterListInventoried);
                        }
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(ListInventoried.this).create();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LIST_INVENTORIED) {

            RealmResults<MPhoto> books = mRealm.where(MPhoto.class).equalTo("id_", id_).findAll();
            if (!books.isEmpty()) {
                for (int i = books.size() - 1; i >= 0; i--) {
                    mRealm.beginTransaction();
                    books.get(i).deleteFromRealm();
                    mRealm.commitTransaction();
                }
            } else Log.d("thong bao", "khong co");
            List<Photo> list = (List<Photo>) data.getSerializableExtra("camera");

            for (int i = 0; i < list.size(); i++) {
                //Log.d("image",list.get(i).getId()+" - "+list.get(i).getUri());

                mRealm.beginTransaction();
                MPhoto book = mRealm.createObject(MPhoto.class);
                book.setId(list.get(i).getId());
                book.setBitmap(list.get(i).getBitmap());
                book.setDeviceType("1");//thietbi 1 adroid , 2 ios
                book.setDocEntry(Docentry);//so phieu
                book.setFileByte(list.get(i).getUri());//base64
                book.setSerial(serial);//base64
                Log.e("image", list.get(i).getUri() + "a");
                Log.e("name", id_ + i + ".jpg");
                book.setImageName(id_ + i + ".jpg");//name.jpg
                book.setItemCode(id_);//ma san pham
                book.setDeviceType("1");//1
                mRealm.commitTransaction();
            }
        }

    }


}