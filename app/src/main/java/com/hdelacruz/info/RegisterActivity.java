package com.hdelacruz.info;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.hdelacruz.info.models.Info;
import com.hdelacruz.info.services.ApiService;
import com.hdelacruz.info.services.ApiServiceGenerator;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;



public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private ImageView imagenPreview;

    private EditText fechaInput;
    private EditText areaInput;
    private EditText rubroInput;
    private EditText detallesInput;
    private EditText fechainiInput;
    private EditText fechafinInput;

    private Button fechaButton, horaButton, horafiButton;
    private int dd, mm, yy,time,min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imagenPreview = findViewById(R.id.imagen_preview);
        fechaInput = findViewById(R.id.fecha_input);
        areaInput = findViewById(R.id.area_input);
        rubroInput = findViewById(R.id.rubro_input);
        detallesInput = findViewById(R.id.detalles_input);
        fechainiInput = findViewById(R.id.fechaini_input);
        fechafinInput = findViewById(R.id.fechafin_input);


        fechaButton = findViewById(R.id.fecha_button);
        horaButton = findViewById(R.id.hora_button);
        horafiButton = findViewById(R.id.horafi_button);

        fechaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    dd = c.get(Calendar.DAY_OF_MONTH);
                    mm = c.get(Calendar.MONTH);
                    yy = c.get(Calendar.YEAR);

                    DatePickerDialog datePicker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            fechaInput.setText(year+"-"+month+"-"+dayOfMonth);
                        }
                    },yy,mm,dd);
                    datePicker.show();

            }
        });

        horaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final Calendar c = Calendar.getInstance();

            time = c.get(Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);

                TimePickerDialog ponerhoraini = new TimePickerDialog(RegisterActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        fechainiInput.setText(hourOfDay+":"+minute);
                    }
                },time,min,false);
                ponerhoraini.show();
            }
        });

        horafiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                time = c.get(Calendar.HOUR_OF_DAY);
                min = c.get(Calendar.MINUTE);
                TimePickerDialog ponerhorafin = new TimePickerDialog(RegisterActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        fechafinInput.setText(hourOfDay+":"+minute);
                    }
                },time,min,false);
                ponerhorafin.show();
            }
        });
    }


    private static final int REQUEST_CAMERA = 100;

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = scaleBitmapDown(bitmap, 800);  // Redimensionar
                imagenPreview.setImageBitmap(bitmap);
            }
        }
    }

    public void callRegister(View view){
        String dia_if = fechaInput.getText().toString();
        String area_if = areaInput.getText().toString();
        String trabajador_if = rubroInput.getText().toString();
        String descripcion_if = detallesInput.getText().toString();
        String fecha_ini = fechainiInput.getText().toString();
        String fecha_fin = fechafinInput.getText().toString();

        if (dia_if.isEmpty() || area_if.isEmpty() || trabajador_if.isEmpty() || descripcion_if.isEmpty() || fecha_ini.isEmpty() || fecha_fin.isEmpty()) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }


        ApiService service = ApiServiceGenerator.createService(this, ApiService.class);

        Call<Info> call;

        if(bitmap == null){
            call = service.createInfo(dia_if, area_if, trabajador_if, descripcion_if, fecha_ini, fecha_fin);
        } else {

            // De bitmap a ByteArray
            // De bitmap a ByteArray
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // ByteArray a MultiPart
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "photo.jpg", requestFile);
            //Toast.makeText(this, "Imagen necesaria", Toast.LENGTH_SHORT).show();

            // Paramestros a Part
            RequestBody fechaPart = RequestBody.create(MultipartBody.FORM, dia_if);
            RequestBody areaPart = RequestBody.create(MultipartBody.FORM, area_if);
            RequestBody rubroPart = RequestBody.create(MultipartBody.FORM, trabajador_if);
            RequestBody detallesPart = RequestBody.create(MultipartBody.FORM, descripcion_if);
            RequestBody fechainiPart = RequestBody.create(MultipartBody.FORM, fecha_ini);
            RequestBody fechafinPart = RequestBody.create(MultipartBody.FORM, fecha_fin);

            call = service.createInfo(fechaPart, areaPart, detallesPart, rubroPart, fechainiPart, fechafinPart, imagenPart);
        }
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(@NonNull Call<Info> call, @NonNull Response<Info> response) {
                try {
                    if(response.isSuccessful()) {

                        Info info = response.body();

                        Log.d(TAG, "info: " + info);

                        Toast.makeText(RegisterActivity.this, "Registro guardado satisfactoriamente", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);

                        finish();

                    }else{
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }
                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<Info> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }

    // Redimensionar una imagen bitmap
    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

}















