package vn.com.fpt.frt_minventory.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.fpt.frt_minventory.Adapter.AdapterCamera;
import vn.com.fpt.frt_minventory.Adapter.AdapterViewImage;
import vn.com.fpt.frt_minventory.Adapter.Adapter_Camera;
import vn.com.fpt.frt_minventory.Model.CameraResult;
import vn.com.fpt.frt_minventory.Model.Downloadimage;
import vn.com.fpt.frt_minventory.Model.Image;
import vn.com.fpt.frt_minventory.Model.MPhoto;
import vn.com.fpt.frt_minventory.Model.Photo;
import vn.com.fpt.frt_minventory.Model.SearchImage;
import vn.com.fpt.frt_minventory.Model.ViewImage;
import vn.com.fpt.frt_minventory.Model.upload;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Services.KeyApi;
import vn.com.fpt.frt_minventory.Services.Url;

import static vn.com.fpt.frt_minventory.Views.ListInventoried.LIST_INVENTORIED;

/**
 * Created by ADMIN on 12/7/2017.
 */

public class CameraActivity extends Activity implements Adapter_Camera.funcDelete, View.OnClickListener {


    ImageView btnTake, img_view, EndCamera;
    Adapter_Camera adapter;
    AdapterViewImage adapterView;
    String encodedImage, stringbase64, Docentry;
    String ManseNum;
    GridView lvPhoto;

    String id;
    List<CameraResult> list_photo = new ArrayList<CameraResult>();
    List<Photo> listCamera = new ArrayList<Photo>();
    List<ViewImage> searchImages = new ArrayList<>();
    List<ViewImage> sort = new ArrayList<>();

    private final int IMG_PICK = 0;
    Retrofit retrofit;
    boolean camera = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        retrofit = getConnect();
        EndCamera = (ImageView) findViewById(R.id.img_turn_off_camera);
        btnTake = (ImageView) findViewById(R.id.img_take_photo);
        img_view = (ImageView) findViewById(R.id.photo_from_sdcard);
        lvPhoto = (GridView) findViewById(R.id.lv_Camera);
        EndCamera.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        btnTake.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera));
        img_view.setImageDrawable(getResources().getDrawable(R.drawable.gallery));
        ManseNum = (String) getIntent().getSerializableExtra("ManseNum");
        Docentry = (String) getIntent().getSerializableExtra("Docentry");
        id = (String) getIntent().getSerializableExtra("id");
        if (ManseNum.equals("F")) {
            getImage();
            btnTake.setVisibility(View.GONE);
            img_view.setVisibility(View.GONE);
        } else {
            listCamera = (List<Photo>) getIntent().getSerializableExtra("camera");
            adapter = new Adapter_Camera(this, listCamera, this);
            lvPhoto.setAdapter(adapter);
            lvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(new Intent(CameraActivity.this, ActivityShowImage.class).putExtra("uri", (Serializable) listCamera.get(i).getBitmap()));
                }
            });
        }


        EndCamera.setOnClickListener(this);
        btnTake.setOnClickListener(this);
        img_view.setOnClickListener(this);

    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

    }

    public void getImage() {
        KeyApi api = retrofit.create(KeyApi.class);
        Downloadimage up = new Downloadimage();
        Log.e("Docentry", Docentry);
        Log.e("id", id);
        up.setDocentry(Integer.parseInt(Docentry));
        Call<SearchImage> call = api.InvetorySearchImage(up);
        call.enqueue(new Callback<SearchImage>() {
            @Override
            public void onResponse(Call<SearchImage> call, Response<SearchImage> response) {
                searchImages = response.body().getSearchImage();
                for (ViewImage view : searchImages) {
                    Log.d("so sanh", view.getSerial() + "=" + id);
                    if (view.getSerial().equals(id)) {
                        view.getDomain();

                        sort.add(view);
                    } else {
                        Log.d("so sanh", view.getSerial() + "=" + id);
                    }
                }
                adapterView = new AdapterViewImage(CameraActivity.this, sort);
                lvPhoto.setAdapter(adapterView);
                lvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        startActivity(new Intent(CameraActivity.this, ActivityShowImage.class).putExtra("uri", (Serializable) sort.get(i).getDomain()));
                    }
                });
            }

            @Override
            public void onFailure(Call<SearchImage> call, Throwable t) {

            }
        });

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void capturePicture() {
        // Kiểm tra CameraActivity trong thiết bị
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // Mở activity_camera mặc định
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Tiến hành gọi Capture Image intent
            startActivityForResult(intent, 100);
        } else {
            Toast.makeText(getApplication(), "CameraActivity không được hỗ trợ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        //Lấy ảnh từ intent CameraActivity của mình về dưới dạng bitmap và hiển thị lên listview của mình
//        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//        CameraResult cameraResult = new CameraResult();
//        cameraResult.setGallery(bitmap);
//        list_photo.add(cameraResult);
//
        // lấy ảnh từ thư viện của thết bị
        if (resultCode == RESULT_OK && requestCode == IMG_PICK) {

            String imagePath = "";
            String[] imgData = {MediaStore.Images.Media.DATA};
            Uri pickedUri = data.getData();
            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Cursor imgCursor = managedQuery(pickedUri, imgData, null, null, null);
            if (imgCursor != null) {
                int index = imgCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                imgCursor.moveToFirst();
                imagePath = imgCursor.getString(index);
            } else
                imagePath = pickedUri.getPath();
            Log.e("imagePath", imagePath);
            encodedImage = ConvertBitmapToString(photo);
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

            stringbase64 = url1;

            Photo ph = new Photo();
            ph.setId(id);
            ph.setUri(stringbase64);
            ph.setBitmap(pickedUri.toString());
            listCamera.add(ph);
            adapter = new Adapter_Camera(this, listCamera, this);
            lvPhoto.setAdapter(adapter);

        } else if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            camera = true;
            if (listCamera.size() < 3) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                encodedImage = ConvertBitmapToString(photo);
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
                stringbase64 = url1;
                Uri selectImage = data.getData();
                Log.d("im", selectImage.toString());
                Photo ph = new Photo();
                ph.setId(id);
                ph.setUri(stringbase64);
                ph.setBitmap(selectImage.toString());
                listCamera.add(ph);
                adapter = new Adapter_Camera(this, listCamera, this);
                lvPhoto.setAdapter(adapter);
                Intent cameraIntent1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent1, 1);
            } else {
                Toast.makeText(this, "Bạn chỉ chụp tối đa 3 hình ", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            camera = true;
            if (listCamera.size() < 3) {
                Uri selectedImage = data.getData();
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                Bitmap bitmap = decodeFile(picturePath);
                cursor.close();
                encodedImage = ConvertBitmapToString(bitmap);
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
                stringbase64 = url1;
                Uri selectImage = data.getData();
                Log.d("im", selectedImage.toString());
                Photo ph = new Photo();
                ph.setId(id);
                ph.setUri(stringbase64);
                ph.setBitmap(selectImage.toString());
                listCamera.add(ph);
                adapter = new Adapter_Camera(this, listCamera, this);
                lvPhoto.setAdapter(adapter);

            } else {
                Toast.makeText(this, "Bạn chỉ chụp tối đa 3 hình ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Bitmap decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);
        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024 * 5;
        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 1;
            height_tmp /= 1;
            scale *= 1;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, o2);
        return bitmap;
    }

    public static String ConvertBitmapToString(Bitmap bitmap) {
        String encodedImage = "";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void click(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Bạn có muốn xoá hình ảnh ");

        builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                listCamera.remove(i);
                adapter.notifyDataSetChanged();
                lvPhoto.setAdapter(adapter);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                camera = true;
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(LIST_INVENTORIED, new Intent().putExtra("camera", (Serializable) listCamera));
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == EndCamera) {
            if (camera = true) {
                setResult(LIST_INVENTORIED, new Intent().putExtra("camera", (Serializable) listCamera));
                finish();

            }
        } else if (view == btnTake) {

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 1);
        } else if (view == img_view) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
//            Intent pickIntent = new Intent();
//            pickIntent.setType("image/*");
//            pickIntent.setAction(Intent.ACTION_GET_CONTENT);
//            //we will handle the returned data in onActivityResult
//            startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), IMG_PICK);

        }

    }
}

