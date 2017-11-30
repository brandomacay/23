package solange.amor.my_love.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import solange.amor.my_love.R;
import solange.amor.my_love.connection.FirebaseConnection;
import solange.amor.my_love.dao.NewsDao;
import solange.amor.my_love.util.DateUtil;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AddFragment extends Fragment implements View.OnClickListener {


    private EditText edHeader;
    private ImageButton btnAddImage;
    private Button btnSave;
    private Button btnUpdate;
    private Button btnDelete;
    private ImageView imgView;
    private TextView tvPath;
    private String id;
    private EditText edContent;
    private String header;
    private String content;
    private FirebaseConnection firebase;
    Uri imageHoldUri = null;

    private NewsDao dao;
    private DateUtil dateUtil;
    private ProgressBar cargando;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    private static final int REQUEST_CAMERA = 4;
    private static final int SELECT_FILE = 5;
    private Uri mImageCaptureUri;
    private Bitmap bitmapCrop;
    private String path;


    public static final int MY_REQUEST_CAMERA   = 10;
    public static final int MY_REQUEST_WRITE_CAMERA   = 11;
    public static final int CAPTURE_CAMERA   = 12;

    public static final int MY_REQUEST_READ_GALLERY   = 13;
    public static final int MY_REQUEST_WRITE_GALLERY   = 14;
    public static final int MY_REQUEST_GALLERY   = 15;

    private SimpleDraweeView swView;

    public File filen = null;

    public AddFragment() {
        super();
    }

    public static AddFragment newInstance(NewsDao dao) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putParcelable("dao", dao);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = getArguments().getParcelable("dao");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        initInstances(rootView);
        checkData();
        swView = (SimpleDraweeView) rootView.findViewById(R.id.img1);
        return rootView;
    }

    private void initInstances(View rootView) {
        getActivity().setTitle("Add News");
        firebase = new FirebaseConnection();
        dateUtil = new DateUtil();
        edHeader = rootView.findViewById(R.id.edHeader);
        edContent = rootView.findViewById(R.id.edContent);
        btnSave = rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnUpdate = rootView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        btnDelete = rootView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        imgView = rootView.findViewById(R.id.imgView);
        imgView.setOnClickListener(this);
        tvPath = rootView.findViewById(R.id.tvPath);
        cargando = rootView.findViewById(R.id.progressBar2);

    }

    private void checkData(){
        dao = getArguments().getParcelable("dao");
        dao.setDate(DateUtil.currentTimeStamp());
        id = dao.getNewsId();
        header = dao.getHeader();
        content = dao.getContent();
        path = dao.getImagePath();

        if (!TextUtils.isEmpty(header)) {
            getActivity().setTitle(header);
            edHeader.setText(header);
            edContent.setText(content);
            tvPath.setText(path);

            Glide.with(getContext())
                    .load(path)
                    .asBitmap()
                    //.placeholder(default1)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                    .into(imgView);
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

        } else {
            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    @Override
    public void onClick(View view) {
        if (view == imgView) {
            //Intent intent = new Intent(getContext(),ShareActivity.class);
            //startActivity(intent);
            profilePicSelection();
        } else if (view == btnSave) {
            cargando.setVisibility(View.VISIBLE);
            addData();
        } else if (view == btnUpdate) {
            updateData();
        } else if (view == btnDelete) {
            deleteData();
        }
    }


    /*private void addImage() {
        final String[] items = new String[]{"Tomar foto", "Elegir desde galeria"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Opciones");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                //pick from camera
                if (item == 0) {
                    Log.d("camera", "camera");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    try {
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    //pick from file
                    if (Build.VERSION.SDK_INT < 19) {
                        Intent intent = new Intent();

                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent, "Abrir"), PICK_FROM_FILE);

                    } else {

                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");

                        startActivityForResult(Intent.createChooser(intent, "Abrir"), PICK_FROM_FILE);
                    }
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }
     /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //SAVE URI FROM GALLERY
        if(requestCode == SELECT_FILE && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start((Activity) getContext());

        }else if ( requestCode == REQUEST_CAMERA && resultCode == RESULT_OK ){
            //SAVE URI FROM CAMERA

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start((Activity) getContext());

        }
        //image crop library code
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageHoldUri = result.getUri();

                imgView.setImageURI(imageHoldUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    */


    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                mImageCaptureUri = data.getData();
                doCrop();
                break;

            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();
                doCrop();
                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();

                if (extras != null) {
                    bitmapCrop = extras.getParcelable("data");
                    imgView.setImageBitmap(bitmapCrop);
                }

                break;
        }
    }

    private void doCrop() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);

        int size = list.size();

        if (size == 100) {
            Toast.makeText(getActivity(), "Can not find image crop app", Toast.LENGTH_SHORT).show();
        } else {
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 600);
            intent.putExtra("outputY", 600);
            intent.putExtra("aspectX", 600);
            intent.putExtra("aspectY", 600);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

            startActivityForResult(i, CROP_FROM_CAMERA);
        }
    }
*/
    private void addData() {
        id = firebase.getDatabase("news").push().getKey();
        header = edHeader.getText().toString();
        content = edContent.getText().toString();

        NewsDao dao = new NewsDao();
        dao.setNewsId(id);
        dao.setHeader(header);
        dao.setContent(content);
        dao.setDate(DateUtil.currentTimeStamp());

        uploadFromDataInMemory(dao);
    }

    private void uploadFromDataInMemory(final NewsDao dao) {
        // Get the data from an ImageView as bytes
        imgView.setDrawingCacheEnabled(true);
        imgView.buildDrawingCache();
        Bitmap bitmap = imgView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = firebase.getStorage("news/" + id + "/" + DateUtil.currentTimeStamp() + ".png").putBytes(data);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d("progress", "Upload is " + progress + "% done");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                tvPath.setText(downloadUrl.toString());
                dao.setImagePath(downloadUrl.toString());
                firebase.getDatabase("news").child(id).setValue(dao);

                Toast.makeText(getActivity(), "Upload Complete", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });

    }

    private void deleteData() {
        //removing
        firebase.getDatabase("news").child(id).removeValue();
        dao.setDate(DateUtil.currentTimeStamp());
        getActivity().finish();
        Toast.makeText(getActivity(), "borrado", Toast.LENGTH_LONG).show();
    }

    private void updateData() {
        if (bitmapCrop != null) {
            NewsDao dao = new NewsDao();
            dao.setNewsId(id);
            dao.setHeader(header);
            dao.setContent(content);
            dao.setDate(DateUtil.currentTimeStamp());

            uploadFromDataInMemory(dao);
        } else {
            Toast.makeText(getActivity(), "actualizado", Toast.LENGTH_LONG).show();

            HashMap<String, Object> postValues = new HashMap<>();
            postValues.put("header", edHeader.getText().toString());
            postValues.put("content", edContent.getText().toString());

            firebase.getDatabase("news").child(id).updateChildren(postValues);

            getActivity().finish();
        }
    }


    private void profilePicSelection() {


        //DISPLAY DIALOG TO CHOOSE CAMERA OR GALLERY

        final CharSequence[] items = {"Tomar foto", "Subir imagen",
                "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Agrega una foto");
        builder.setCancelable(false);

        //SET ITEMS AND THERE LISTENERS
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Tomar foto")) {
                    checkPermissionCW();
                } else if (items[item].equals("Subir imagen")) {
                    checkPermissionRG();
                } else if (items[item].equals("Cancelar")) {
                    checkPermissionRG();
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    private void checkPermissionCA(){
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    (Activity) getContext(), new String[]{Manifest.permission.CAMERA}, MY_REQUEST_CAMERA);
        } else {
            catchPhoto();
        }
    }
    private void checkPermissionCW(){
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    (Activity)getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_REQUEST_WRITE_CAMERA);
        } else {
            checkPermissionCA();
        }
    }
    private void catchPhoto() {
        filen = getFile();
        if(filen!=null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                Uri photocUri = Uri.fromFile(filen);
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photocUri);
                startActivityForResult(intent, CAPTURE_CAMERA);
            } catch (ActivityNotFoundException e) {

            }
        } else {
            Toast.makeText(getContext(), "please check your sdcard status", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermissionRG(){
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    (Activity)getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_REQUEST_READ_GALLERY);
        } else {
            checkPermissionWG();
        }
    }
    private void checkPermissionWG(){
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // int permissionCheck2 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    (Activity)getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_REQUEST_WRITE_GALLERY);
        } else {
            getPhotos();
        }
    }
    private void getPhotos() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, MY_REQUEST_GALLERY);
    }

    public File getFile(){

        File fileDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getApplicationContext().getPackageName()
                + "/Files");

        if (!fileDir.exists()){
            if (!fileDir.mkdirs()){
                return null;
            }
        }


        File mediaFile = new File(fileDir.getPath() + File.separator + "temp.jpg");
        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {



        switch (requestCode) {

            case CAPTURE_CAMERA:

                swView.setImageURI(Uri.parse("file:///" + filen));
                break;


            case MY_REQUEST_GALLERY:
                try {

                    InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                    filen = getFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(filen);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                    fileOutputStream.close();
                    inputStream.close();
                    swView.setImageURI(Uri.parse("file:///" + filen));//fresco library

                } catch (Exception e) {

                    Log.e("", "Error while creating temp file", e);
                }
                break;

        }
    }

    public void onRequestPermissionsResult (int requestCode, String[] permissions,  int[] grantResults)
    {

        switch (requestCode) {
            case MY_REQUEST_CAMERA:
                catchPhoto();
                break;
            case MY_REQUEST_WRITE_CAMERA:
                checkPermissionCA();
                break;
            case MY_REQUEST_READ_GALLERY:
                checkPermissionWG();
                break;
            case MY_REQUEST_WRITE_GALLERY:
                getPhotos();
                break;
        }
    }


}
