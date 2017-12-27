package vn.com.fpt.frt_minventory.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import vn.com.fpt.frt_minventory.Model.Area_CalendarResult;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Services.APIService;

/**
 * Created by ADMIN on 12/11/2017.
 */

public class Calendar_Inventory extends Activity implements View.OnClickListener {
    Context context = this;

    ImageView imgback, imgreset;
    TextView txt_LoaiPhieu, txtFromDate, txtToDate, txtFromTime, txtToTime, txt_vung, txt_mien, txt_khuvuc, txt_shop;
    EditText edt_SL;
    Button btn_SP_1, btn_SP_2, btn_SP_4, btn_finish;
    public static final int AREA_CODE = 9090;
    public static final int SL_PHIEU_KK = 3001;
    public static final int TYPE_INVENTORY = 10;
    public static int FINISH_CALENDAR = 107;
    String id_vung = "", id_mien = "", id_khuvuc = "", id_shop = "", nameArea = "", from_date = "", to_date = "", from_time = "", to_time = "";
    int amount;
    ImageView imT, imD, imV;
    boolean T, D, V;
    TextView tvT, tvD, tvV;
    int check = 1;
    String IDSHOP = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_inventory_activity);

        Intent i = getIntent();
        String code = i.getStringExtra("Vung");
        T = true;
        D = false;
        V = false;
        imT = (ImageView) findViewById(R.id.imT);
        imD = (ImageView) findViewById(R.id.imD);
        imV = (ImageView) findViewById(R.id.imV);
        imT.setOnClickListener(this);
        imD.setOnClickListener(this);
        imV.setOnClickListener(this);
        imgback = (ImageView) findViewById(R.id.btn_back_calendar);
        imgreset = (ImageView) findViewById(R.id.img_reload_calendar);
        edt_SL = (EditText) findViewById(R.id.txt_SL_Phieu_KK);
        txt_LoaiPhieu = (TextView) findViewById(R.id.txt_chon_loai_phieu);
        tvT = (TextView) findViewById(R.id.tvT);
        tvD = (TextView) findViewById(R.id.tvD);
        tvV = (TextView) findViewById(R.id.tvV);
        txtFromDate = (TextView) findViewById(R.id.from_date_calendar_inventory);
        txtToDate = (TextView) findViewById(R.id.to_date_calendar_inventory);
        txtFromTime = (TextView) findViewById(R.id.from_time_calendar);
        txtToTime = (TextView) findViewById(R.id.to_time_calendar);
        txt_vung = (TextView) findViewById(R.id.txt_AreaCalendar);
        txt_mien = (TextView) findViewById(R.id.txt_AreaRegional_Calendar);
        txt_khuvuc = (TextView) findViewById(R.id.txt_KhuVuc_Calendar);
        txt_shop = (TextView) findViewById(R.id.txt_Shop_Calendar);
        btn_SP_1 = (Button) findViewById(R.id.btn_SP_Thuong);
        btn_SP_2 = (Button) findViewById(R.id.btn_SP_Demo);
        btn_SP_4 = (Button) findViewById(R.id.btn_SP_VPKM);
        btn_finish = (Button) findViewById(R.id.btn_save_calendar);


        edt_SL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edt_SL.removeTextChangedListener(this);
                if (s.toString() != null && s.toString().length() > 0) {
                    amount = Integer.parseInt(s.toString());
                    if (amount > 10) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_Number_Max)).show();
                        edt_SL.setText("");
                    } else if (amount == 0) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_Number_Min)).show();
                    } else {
                    }
                    {
                        edt_SL.setText(amount + "");
                    }
                } else {
                    edt_SL.setText("");
                }
                edt_SL.addTextChangedListener(this);
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fncReset();
            }
        });

        /*edt_SL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendar_Inventory.this, SL_Phieu_KK.class);
                startActivityForResult(intent, SL_PHIEU_KK);
            }
        });*/


        btn_SP_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn_SP_1) {
                    btn_SP_1.setBackgroundResource(R.drawable.button_green);
                    btn_SP_2.setBackgroundResource(R.drawable.button_orange);
                    btn_SP_4.setBackgroundResource(R.drawable.button_orange);
                    txt_LoaiPhieu.setText("1");
                }
            }
        });
        btn_SP_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn_SP_2) {
                    btn_SP_2.setBackgroundResource(R.drawable.button_green);
                    btn_SP_1.setBackgroundResource(R.drawable.button_orange);
                    btn_SP_4.setBackgroundResource(R.drawable.button_orange);
                    txt_LoaiPhieu.setText("2");
                }
            }
        });

        btn_SP_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn_SP_4) {
                    btn_SP_4.setBackgroundResource(R.drawable.button_green);
                    btn_SP_1.setBackgroundResource(R.drawable.button_orange);
                    btn_SP_2.setBackgroundResource(R.drawable.button_orange);
                    txt_LoaiPhieu.setText("4");
                }
            }
        });


        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                From_date();
            }
        });

        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                To_date();
            }
        });

        txtFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                From_time();
            }
        });

        txtToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                To_time();
            }
        });

        txt_vung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendar_Inventory.this, SelectAreaCalendar.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 1);
                bundle.putString("ID", id_vung);
                bundle.putString("NAME", nameArea);
                bundle.putString("rootID", "00001");
                intent.putExtra("myBundle", bundle);
                startActivityForResult(intent, AREA_CODE);
            }
        });

        txt_mien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendar_Inventory.this, SelectAreaCalendar.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 2);
                bundle.putString("ID", id_mien);
                bundle.putString("NAME", nameArea);
                bundle.putString("rootID", id_vung);
                intent.putExtra("myBundle", bundle);
                startActivityForResult(intent, AREA_CODE);
            }
        });

        txt_khuvuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendar_Inventory.this, SelectAreaCalendar.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 3);
                bundle.putString("ID", id_khuvuc);
                bundle.putString("NAME", nameArea);
                bundle.putString("rootID", id_mien);
                intent.putExtra("myBundle", bundle);
                startActivityForResult(intent, AREA_CODE);
            }
        });

        txt_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendar_Inventory.this, SelectAreaCalendar.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Type", 4);
                bundle.putString("ID", id_shop);
                bundle.putString("NAME", nameArea);
                bundle.putString("rootID", id_khuvuc);
                intent.putExtra("myBundle", bundle);
                startActivityForResult(intent, AREA_CODE);
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Sl = edt_SL.getText().toString();
                if (Sl.length() != 0) {
                    if (Integer.parseInt(edt_SL.getText().toString()) > 10) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_Number_Max)).show();
                        edt_SL.setText("1");
                    } else if (Integer.parseInt(edt_SL.getText().toString()) == 0) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_Number_Min)).show();
                    } else if (txtFromDate.getText().toString().equals("")) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_FromDate)).show();
                    } else if (txtToDate.getText().toString().equals("")) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_ToDate)).show();
                    } else if (txtFromTime.getText().toString().equals("")) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_FromTime)).show();
                    } else if (txtToTime.getText().toString().equals("")) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_ToTime)).show();
                    } else if (txt_vung.getText().toString().equals("")) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_Vung)).show();
                    } else if (txt_mien.getText().toString().equals("")) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_Mien)).show();
                    } else if (txt_khuvuc.getText().toString().equals("")) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_KhuVuc)).show();
                    } else if (txt_shop.getText().toString().equals("")) {
                        Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_Shop)).show();
                    } else if (check > 0) {
                        if (T == true) {
                            IDSHOP = "";
                            String idShop[] = id_shop.split(",");
                            for (int i = 0; i < idShop.length; i++) {
                                if(idShop[i].contains("_")){
                                    String[] id = idShop[i].split("_");
                                    IDSHOP = IDSHOP + id[1] + ",";
                                }else {
                                    IDSHOP = IDSHOP + idShop[i] + ",";
                                }

                                //Log.d("IDSHOP",IDSHOP);
                            }
                            Finish_calendar("1");
                        }
                        if (D == true) {
                            Finish_calendar("2");
                        }
                        if (V == true) {
                            Finish_calendar("4");
                        }

                    }else {
                        Toasty.info(Calendar_Inventory.this, "Chọn loại sản phẩm").show();
                    }
                } else {
                    Toasty.info(Calendar_Inventory.this, getString(R.string.Noti_Number_Empty)).show();
                }
            }
        });

        //        an ban phim tren man hinh
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void From_date() {
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");

                txtFromDate.setText(simpleDateFormat.format(calendar.getTime()));
                from_date = (simpleDate.format(calendar.getTime()));
            }
        }, date, month, year);
        datePickerDialog.updateDate(year, month, date);
        datePickerDialog.show();
    }

    private void To_date() {
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                txtToDate.setText(simpleDateFormat.format(calendar.getTime()));
                SimpleDateFormat simple_to_day = new SimpleDateFormat("yyyyMMdd");
                to_date = (simple_to_day.format(calendar.getTime()));
            }
        }, date, month, year);
        datePickerDialog.updateDate(year, month, date);
        datePickerDialog.show();
    }

    private void From_time() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String time;
                if (selectedMinute < 10) {
                    time = "0" + selectedMinute;
                } else {
                    time = "" + selectedMinute;
                }
                from_time = (selectedHour + ":" + time);
                txtFromTime.setText(selectedHour + ":" + time);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.updateTime(hour, minute);
        mTimePicker.show();

    }

    private void To_time() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String time;
                if (selectedMinute < 10) {
                    time = "0" + selectedMinute;
                } else {
                    time = "" + selectedMinute;
                }
                to_time = (selectedHour + ":" + time);
                txtToTime.setText(selectedHour + ":" + time);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.updateTime(hour, minute);
        mTimePicker.show();

    }

    private void Finish_calendar(final String type) {
        // cat chuoi id_shop
//         IDSHOP = "";
//        String idShop[] = id_shop.split(",");
//        for (int i = 0; i < idShop.length; i++) {
//            String[] id = idShop[i].split("_");
//            IDSHOP = IDSHOP + id[1] + ",";
//            Log.d("IDSHOP",IDSHOP);
//        }
//

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Xin chờ", "Đang hoàn tất đặt lịch...", true, false);

        new AsyncTask<Void, Void, Area_CalendarResult>() {
            @Override
            protected Area_CalendarResult doInBackground(Void... params) {
                try {
                    Area_CalendarResult area_calendarResults = APIService.getFinishCalendar(IDSHOP, edt_SL.getText().toString(), from_date, to_date, from_time, to_time, type);
                    return area_calendarResults;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(Area_CalendarResult area_calendarResults) {
                progressDialog.dismiss();
                if (area_calendarResults != null) {
                    if (area_calendarResults.getResult() == 1) {
//                        Toasty.success(Calendar_Inventory.this, "Đặt lịch thành công! "+type).show();
//                        Intent intent = getIntent();
//                        setResult(Calendar_Inventory.FINISH_CALENDAR, intent);
                        if (check == 1) {
                            Toasty.success(Calendar_Inventory.this, "Đặt lịch thành công!").show();
                            Intent intent = getIntent();
                            setResult(Calendar_Inventory.FINISH_CALENDAR, intent);
                           // finish();
                        }
                        else
                            check = check - 1;

                    } else {
                        Toasty.info(Calendar_Inventory.this, "Đặt lịch không thành công!" + "\n" + area_calendarResults.getU_Desc(), 1).show();
                    }

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Calendar_Inventory.this).create();
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

    private void fncReset() {
        edt_SL.setText("");
        txtFromTime.setText("");
        txtToTime.setText("");
        txtFromDate.setText("");
        txtToDate.setText("");
        txt_vung.setText("");
        txt_mien.setText("");
        txt_khuvuc.setText("");
        txt_shop.setText("");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AREA_CODE) {

            if (data != null) {
                Bundle bundle = data.getBundleExtra("myBundle");
                String _Name = bundle.getString("Name");
                nameArea = bundle.getString("Name");
                String _ID = bundle.getString("ID");
                int type = bundle.getInt("Type");

                if (type == 1) {
                    txt_vung.setText(_Name);
                    id_vung = _ID;
                } else if (type == 2) {
                    txt_mien.setText(_Name);
                    id_mien = _ID;
                } else if (type == 3) {
                    txt_khuvuc.setText(_Name);
                    id_khuvuc = _ID;
                } else {
                    txt_shop.setText(_Name);
                    id_shop = _ID;
                }
            }
        }

       /* if (requestCode == SL_PHIEU_KK) {
            if (data != null) {
                String sl = data.getStringExtra("SoPhieu");
                edt_SL.setText(sl);
            }
        }*/


        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == imT) {
            if (T == false) {
                imT.setImageDrawable(getResources().getDrawable(R.drawable.letter_t));
                tvT.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                T = true;
                check = check + 1;
            } else {
                imT.setImageDrawable(getResources().getDrawable(R.drawable.letter_t1));
                tvT.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                T = false;
                check = check - 1;
            }

        } else if (view == imD) {
            if (D == false) {
                imD.setImageDrawable(getResources().getDrawable(R.drawable.letter_d));
                tvD.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                D = true;
                check = check + 1;
            } else {
                imD.setImageDrawable(getResources().getDrawable(R.drawable.letter_d1));
                tvD.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                D = false;
                check = check + 1;
            }

        } else if (view == imV) {
            if (V == false) {
                imV.setImageDrawable(getResources().getDrawable(R.drawable.letter_v));
                tvV.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                V = true;
                check = check + 1;
            } else {
                imV.setImageDrawable(getResources().getDrawable(R.drawable.letter_v1));
                tvV.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                V = false;
                check = check + 1;
            }
        }
    }
}
