package com.qq.youtu.youtuyundemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.youtu.Youtu;

import org.json.JSONObject;

import java.io.File;

public class MainActivity extends Activity {

    public static final String APP_ID = "10002784";
    public static final String SECRET_ID = "AKIDFb4p8doJtrnfgieKpQlvEV0BE4Sa6F6Z";
    public static final String SECRET_KEY = "cEScbaS7MrKs7boBnKW06PUnpn4ET1P6";
    private final String LOG_TAG = MainActivity.class.getName();
    private Bitmap theSelectedImage = null;
    private BitmapFactory.Options opts = null;
    private Button mLocalPicButton;
    private Button mremotePicButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocalPicButton = (Button)findViewById(R.id.localTest);
        mremotePicButton = (Button)findViewById(R.id.remoteTest);
        opts = new BitmapFactory.Options();
        opts.inDensity = this.getResources().getDisplayMetrics().densityDpi;
        opts.inTargetDensity = this.getResources().getDisplayMetrics().densityDpi;


        mLocalPicButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        mremotePicButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT);
                            JSONObject respose = faceYoutu.FaceCompareUrl("http://open.youtu.qq.com/content/img/slide-1.jpg", "http://open.youtu.qq.com/content/img/slide-1.jpg");
                            Log.d(LOG_TAG, respose.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        testImageOcr();
//        testcase1();
    }

    void testImage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, API_TENCENTYUN_END_POINT);
//                Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT);
                Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, "http://10.198.5.146/youtu/");

                try {
                    Log.d(LOG_TAG, "=====================================");
                    Log.d(LOG_TAG, "imagePorn");
                    Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.id, opts);
                    JSONObject respose = faceYoutu.ImagePorn(selectedImage);
                    Log.d(LOG_TAG, respose.toString());
                    if(null != selectedImage) {
                        selectedImage.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void testImageOcr() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, API_TENCENTYUN_END_POINT);
//                Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT);
                Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, "http://101.226.76.164:18082/youtu/");

                try {
                    Log.d(LOG_TAG, "=====================================");
                    Log.d(LOG_TAG, "idcardocr");
                    Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.id, opts);
                    JSONObject respose = faceYoutu.IdcardOcr(selectedImage, 0);
                    Log.d(LOG_TAG, respose.toString());
                    if(null != selectedImage) {
                        selectedImage.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Log.d(LOG_TAG, "=====================================");
                    Log.d(LOG_TAG, "namecardocr");
                    Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.namecard, opts);
                    JSONObject respose = faceYoutu.NamecardOcr(selectedImage);
                    Log.d(LOG_TAG, respose.toString());
                    if(null != selectedImage) {
                        selectedImage.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    void testcase1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, API_TENCENTYUN_END_POINT);
                Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT);

                Context context = getApplicationContext();
                Resources res = context.getResources();

                Uri noface = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.noface) + "/"
                        + res.getResourceTypeName(R.drawable.noface) + "/"
                        + res.getResourceEntryName(R.drawable.noface));
                Uri sameface1 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.geyou_1) + "/"
                        + res.getResourceTypeName(R.drawable.geyou_1) + "/"
                        + res.getResourceEntryName(R.drawable.geyou_1));
                Uri sameface2 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.geyou_2) + "/"
                        + res.getResourceTypeName(R.drawable.geyou_2) + "/"
                        + res.getResourceEntryName(R.drawable.geyou_2));
                Uri multiface = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.nose_tip_871_254) + "/"
                        + res.getResourceTypeName(R.drawable.nose_tip_871_254) + "/"
                        + res.getResourceEntryName(R.drawable.nose_tip_871_254));
                Uri multiface2 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.multi1) + "/"
                        + res.getResourceTypeName(R.drawable.multi1) + "/"
                        + res.getResourceEntryName(R.drawable.multi1));
                Uri onface_in_multiface = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.oneface_femal) + "/"
                        + res.getResourceTypeName(R.drawable.oneface_femal) + "/"
                        + res.getResourceEntryName(R.drawable.oneface_femal));
                Uri broken_image1 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.broken1) + "/"
                        + res.getResourceTypeName(R.drawable.broken1) + "/"
                        + res.getResourceEntryName(R.drawable.broken1));
                Uri broken_image2 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.bad) + "/"
                        + res.getResourceTypeName(R.drawable.bad) + "/"
                        + res.getResourceEntryName(R.drawable.bad));
                Uri broken_image3 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.broken2) + "/"
                        + res.getResourceTypeName(R.drawable.broken2) + "/"
                        + res.getResourceEntryName(R.drawable.broken2));
                Uri food = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.food01) + "/"
                        + res.getResourceTypeName(R.drawable.food01) + "/"
                        + res.getResourceEntryName(R.drawable.food01));
                Uri fuzzy_pic = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.fuzzy) + "/"
                        + res.getResourceTypeName(R.drawable.fuzzy) + "/"
                        + res.getResourceEntryName(R.drawable.fuzzy));
                Uri not_fuzzy_pic = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + res.getResourcePackageName(R.drawable.good_pic) + "/"
                        + res.getResourceTypeName(R.drawable.good_pic) + "/"
                        + res.getResourceEntryName(R.drawable.geyou_2));

                try {

                    Log.d(LOG_TAG, "=====================================");
                    Log.d(LOG_TAG, "detectFace");
                    Log.d(LOG_TAG, "-------------------------------------");
                    Log.d(LOG_TAG, "detect face mode 0");
                    Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.geyou_1, opts);
                    JSONObject respose = faceYoutu.DetectFace(selectedImage, 0);
                    Log.d(LOG_TAG, respose.toString());
                    if(null != selectedImage) {
                        selectedImage.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Log.d(LOG_TAG, "-------------------------------------");
                    Log.d(LOG_TAG, "detect face mode 1");
                    Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.geyou_1, opts);
                    JSONObject respose = faceYoutu.DetectFace(selectedImage, 1);
                    Log.d(LOG_TAG, respose.toString());
                    if(null != selectedImage) {
                        selectedImage.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Log.d(LOG_TAG, "-------------------------------------");
                    Log.d(LOG_TAG, "detect multiface mode 0");
                    Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.nose_tip_871_254, opts);
                    JSONObject respose = faceYoutu.DetectFace(selectedImage, 0);
                    // get respose
                    Log.d(LOG_TAG, respose.toString());
                    if(null != selectedImage) {
                        selectedImage.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Log.d(LOG_TAG, "-------------------------------------");
                    Log.d(LOG_TAG, "detect multiface mode 1");
                    Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.multi2, opts);
                    JSONObject respose = faceYoutu.DetectFace(selectedImage, 1);
                    // get respose
                    Log.d(LOG_TAG, respose.toString());
                    if(null != selectedImage) {
                        selectedImage.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Log.d(LOG_TAG, "-------------------------------------");
                    Log.d(LOG_TAG, "detect noface mode 0");
                    Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.noface, opts);
                    JSONObject respose = faceYoutu.DetectFace(selectedImage, 0);
                    // get respose
                    Log.d(LOG_TAG, respose.toString());
                    if(null != selectedImage) {
                        selectedImage.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Log.d(LOG_TAG, "-------------------------------------");
                    Log.d(LOG_TAG, "detect face image is illegal");
                    Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.broken1, opts);
                    JSONObject respose = faceYoutu.DetectFace(selectedImage, 0);
                    // get respose
                    Log.d(LOG_TAG, respose.toString());

                    Bitmap selectedImage2 = BitmapFactory.decodeResource(getResources(), R.drawable.broken2, opts);
                    JSONObject ret2 = faceYoutu.DetectFace(selectedImage2, 0);
                    Log.d(LOG_TAG, ret2.toString());

                    Bitmap selectedImage3 = BitmapFactory.decodeResource(getResources(), R.drawable.bad, opts);
                    JSONObject ret3 = faceYoutu.DetectFace(selectedImage3, 0);
                    Log.d(LOG_TAG, ret3.toString());
                    if(null != selectedImage) {
                        selectedImage.recycle();
                    }
                    if(null != selectedImage2) {
                        selectedImage2.recycle();
                    }
                    if(null != selectedImage3){
                        selectedImage3.recycle();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Log.d(LOG_TAG, "=====================================");
                    Log.d(LOG_TAG, "faceshape");
                    Log.d(LOG_TAG, "-------------------------------------");
                    Log.d(LOG_TAG, "faceshape mode 0");
                    Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.geyou_2 , opts);
                    JSONObject respose = faceYoutu.FaceShape(selectedImage, 0);
                    // get respose
                    Log.d(LOG_TAG, respose.toString());

                    if(null != selectedImage) {
                        selectedImage.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Log.d(LOG_TAG, "-------------------------------------");
                    Log.d(LOG_TAG, "FaceShape mode 1");
                    Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.geyou_2, opts);
                    JSONObject respose = faceYoutu.FaceShape(selectedImage, 1);
                    // get respose
                    Log.d(LOG_TAG, respose.toString());
                    if(null != selectedImage) {
                        selectedImage.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            try {

                Log.d(LOG_TAG, "-------------------------------------");
                Log.d(LOG_TAG, "shape multiface mode 0");
                Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.multi2, opts);;
                JSONObject respose = faceYoutu.FaceShape(selectedImage, 0);
                // get respose
                Log.d(LOG_TAG, respose.toString());
                if(null != selectedImage) {
                    selectedImage.recycle();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                Log.d(LOG_TAG, "-------------------------------------");
                Log.d(LOG_TAG, "shape multiface mode 1");
                Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.multi2, opts);
                JSONObject respose = faceYoutu.FaceShape(selectedImage, 1);
                // get respose
                Log.d(LOG_TAG, respose.toString());
                if(null != selectedImage) {
                    selectedImage.recycle();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                Log.d(LOG_TAG, "-------------------------------------");
                Log.d(LOG_TAG, "shape noface mode 0");
                Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.noface, opts);
                JSONObject respose = faceYoutu.FaceShape(selectedImage, 0);
                // get respose
                Log.d(LOG_TAG, respose.toString());
                if(null != selectedImage) {
                    selectedImage.recycle();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                Log.d(LOG_TAG, "-------------------------------------");
                Log.d(LOG_TAG, "shape face image is illegal");
                Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.broken1, opts);
                JSONObject respose = faceYoutu.FaceShape(selectedImage, 0);
                Log.d(LOG_TAG, respose.toString());

                Bitmap selectedImage2 = BitmapFactory.decodeResource(getResources(), R.drawable.broken2, opts);
                JSONObject ret2 = faceYoutu.FaceShape(selectedImage2, 0);
                Log.d(LOG_TAG, ret2.toString());

                Bitmap selectedImage3 = BitmapFactory.decodeResource(getResources(), R.drawable.bad, opts);
                JSONObject ret3 = faceYoutu.FaceShape(selectedImage3, 0);
                Log.d(LOG_TAG, ret3.toString());
                if(null != selectedImage) {
                    selectedImage.recycle();
                }
                if(null != selectedImage2) {
                    selectedImage2.recycle();
                }
                if(null != selectedImage3){
                    selectedImage3.recycle();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                Log.d(LOG_TAG, "=====================================");
                Log.d(LOG_TAG, "FaceCompare");
                Log.d(LOG_TAG, "-------------------------------------");
                Log.d(LOG_TAG, "FaceCompare both face only one");
                Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.geyou_2, opts);
                Bitmap selectedImage2 = BitmapFactory.decodeResource(getResources(), R.drawable.geyou_1, opts);
                ;
                JSONObject respose = faceYoutu.FaceCompare(selectedImage, selectedImage2);
                Log.d(LOG_TAG, respose.toString());
                if(null != selectedImage) {
                    selectedImage.recycle();
                }
                if(null != selectedImage2) {
                    selectedImage2.recycle();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                Log.d(LOG_TAG, "-------------------------------------");
                Log.d(LOG_TAG, "FaceCompare A multiface face B multiface");
                Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.multi2, opts);
                Bitmap selectedImage2 = BitmapFactory.decodeResource(getResources(), R.drawable.multi3, opts);

                JSONObject respose = faceYoutu.FaceCompare(selectedImage, selectedImage2);
                // get respose
                Log.d(LOG_TAG, respose.toString());
                if(null != selectedImage) {
                    selectedImage.recycle();
                }
                if(null != selectedImage2) {
                    selectedImage2.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                Log.d(LOG_TAG, "-------------------------------------");
                Log.d(LOG_TAG, "FaceCompare A face B no");
                Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.geyou_2, opts);
                Bitmap selectedImage2 = BitmapFactory.decodeResource(getResources(), R.drawable.noface, opts);

                JSONObject respose = faceYoutu.FaceCompare(selectedImage, selectedImage2);
                // get respose
                Log.d(LOG_TAG, respose.toString());
                if(null != selectedImage) {
                    selectedImage.recycle();
                }
                if(null != selectedImage2) {
                    selectedImage2.recycle();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                Log.d(LOG_TAG, "-------------------------------------");
                Log.d(LOG_TAG, "FaceCompare A face B broken");
                Bitmap selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.geyou_2, opts);
                Bitmap selectedImage2 = BitmapFactory.decodeResource(getResources(), R.drawable.broken1, opts);
                JSONObject respose = faceYoutu.FaceCompare(selectedImage, selectedImage2);
                // get respose
                Log.d(LOG_TAG, respose.toString());

                Bitmap selectedImage3= BitmapFactory.decodeResource(getResources(), R.drawable.broken2, opts);
                JSONObject ret2 = faceYoutu.FaceCompare(selectedImage, selectedImage3);
                Log.d(LOG_TAG, ret2.toString());

                Bitmap selectedImage4 = BitmapFactory.decodeResource(getResources(), R.drawable.bad, opts);
                JSONObject ret3 = faceYoutu.FaceCompare(selectedImage, selectedImage4);
                Log.d(LOG_TAG, ret3.toString());
                if(null != selectedImage) {
                    selectedImage.recycle();
                }
                if(null != selectedImage2) {
                    selectedImage2.recycle();
                }
                if(null != selectedImage3) {
                    selectedImage3.recycle();
                }
                if(null != selectedImage4) {
                    selectedImage4.recycle();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
                }
            }).start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            try {
                String path = getPath(uri);
                theSelectedImage = getBitmap(path, 1000, 1000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (theSelectedImage != null) {
                            try {
                                Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT);
                                JSONObject respose = faceYoutu.DetectFace(theSelectedImage, 1);
                                Log.d(LOG_TAG, respose.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            } catch ( Exception e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public String uri2Path(Uri uri) {
        String path = null;
        if(uri == null) {
            return path;
        }

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if(!isKitKat) {
            //android 版本小于4.4
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if(null != cursor) {
                cursor.moveToFirst();
                path = cursor.getString(1);
                cursor.close();
            }
        }else{
            //android 版本大于等于4.4
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            }

            final String selection = "_id=?";
            final String[] selectionArgs = new String[] { split[1] };

            path =  getDataColumn(this, contentUri, selection, selectionArgs);

        }
        return path;
    }


    public String getPath( final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(this, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(this, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(this, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    private static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private Bitmap getBitmap(String path , int maxWidth, int maxHeight){
        //先解析图片边框的大小
        Bitmap bm = null;
        File file = new File(path);
        if(file.exists()) {
            BitmapFactory.Options ops = new BitmapFactory.Options();
            ops.inJustDecodeBounds = true;
            ops.inSampleSize = 1;
            BitmapFactory.decodeFile(path, ops);
            int oHeight = ops.outHeight;
            int oWidth = ops.outWidth;

            //控制压缩比
            int contentHeight = maxWidth;
            int contentWidth = maxHeight;
            if (((float) oHeight / contentHeight) < ((float) oWidth / contentWidth)) {
                ops.inSampleSize = (int) Math.ceil((float) oWidth / contentWidth);
            } else {
                ops.inSampleSize = (int) Math.ceil((float) oHeight / contentHeight);
            }
            ops.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(path, ops);

        }

        return bm;
    }


}


