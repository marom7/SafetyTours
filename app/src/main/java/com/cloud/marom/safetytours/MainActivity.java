package com.cloud.marom.safetytours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.cloud.marom.safetytours.objects.UserLogin;
import com.cloud.marom.safetytours.objects.DataTourSafe;
import com.cloud.marom.safetytours.objects.GeneralData;
import com.cloud.marom.safetytours.server.Consts;
import com.cloud.marom.safetytours.server.ImageUtil;
import com.cloud.marom.safetytours.server.PostRequest;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements empDialogLogin.ExampleDialogListener , passwordDialog.ExampleDialogListener {

    ImageView imageView;
    Bitmap bitmap;
    Date date_tour;
    String Mevazea;
    String Remark;
    String emp_id;
    String base64ImageString="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.setTitle(R.string.app_name);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        openUserDialog();
    }
    private void SetDataOnUI(String name, String duty) {

        this.SetDate_Time();
        Safetyapp mapp =Safetyapp.getInstance();

        TextView txtMEVAZEA = findViewById(R.id.txtMEVAZEA);
        TextView txtDuty = findViewById(R.id.txtDuty);
        TextView txtSign = findViewById(R.id.txtSign);

        txtMEVAZEA.setText(name);
        txtDuty.setText(duty);

        LoadDataFromNet("mena");
        LoadDataFromNet("razif");
        LoadDataFromNet("ships");
        LoadDataFromNet("cargo");
        LoadDataFromNet("acrai");
        LoadDataFromNet("halazot");
        LoadDataFromNet("memza");

        imageView = findViewById(R.id.ImageEvent);
        imageView.setImageResource(R.drawable.no_image);
        Mevazea = txtMEVAZEA.getText().toString() + " " + txtDuty.getText().toString();
        txtSign.setText(txtSign.getText()+ " " + Mevazea);

    }
    private void AddSafeTour(DataTourSafe data) {
        try{  // WRITE to db sever.
            HashMap<String, String> map = new HashMap<>();

            map.put("Content-Type", "application/json; charset=utf-8");
            map.put("user",emp_id);
            map.put("password", "");

            map.put("razif", data.getRAZIF().contains("בחר") ? "-": data.getRAZIF());
            map.put("mena", data.getMENA().contains("בחר") ? "-": data.getMENA());
            map.put("ship",data.getShip().contains("בחר") ? "-": data.getShip());
            map.put("cargotype", data.getMITAN().contains("בחר") ? "-": data.getMITAN());
            map.put("memzaim", data.getMIMTZA().contains("בחר") ? "-": data.getMIMTZA());
            String rem = data.getRemark().trim().equals("") ? "אין" :  data.getRemark();
            map.put("remark", rem);
            map.put("pic", base64ImageString);
            map.put("hamlazot", data.getHAMLATZA().contains("בחר") ? "-": data.getHAMLATZA());
            map.put("hacraiMesima", data.getA_MESIMA().contains("בחר") ? "-": data.getA_MESIMA());

            //dataTourSafe
            PostRequest request = new PostRequest(Consts.getFullUrl("/AddSafeTour"),map, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    int retId  = new Gson().fromJson(s, int.class );
                    String msg, id = String.valueOf(retId);
                    if(retId != 0)  {
                        msg =" הסיור נוסף בהצלחה " + "["+id+"]";
                        Toast.makeText(MainActivity.this,  msg  ,Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            int socketTimeout = 60000;  //30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);

            Safetyapp.getInstance().getQueue().add(request);
        }
        catch(Exception ex)
        {

        }

        // ToDo : SendMail(String To , String Subject, String [Html]Body) (To Jaklin)

        //Toast.makeText(this, "Tour send successful", Toast.LENGTH_LONG).show();

    }
    public void onClick_btnPic(View v) {
        Toast.makeText(this, "מצלמה הופעלה", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 0);
    }
    public void onClick_btnConfirm(View v){
        Spinner spinner;
        DataTourSafe dataTourSafe = SetDataOfTour();

        spinner = findViewById(R.id.chooseRazif);
        spinner.setSelection(0);
        spinner = findViewById(R.id.chooseMena);
        spinner.setSelection(0);
        spinner = findViewById(R.id.chooseShip);
        spinner.setSelection(0);
        spinner = findViewById(R.id.chooseMIMZAIM);
        spinner.setSelection(0);
        spinner = findViewById(R.id.chooseMITAN);
        spinner.setSelection(0);
        spinner = findViewById(R.id.chooseHAMLAZOT);
        spinner.setSelection(0);
        spinner = findViewById(R.id.chooseACHRAI);
        spinner.setSelection(0);
        imageView.setImageResource(R.drawable.no_image);
        EditText rem = findViewById(R.id.txtRemark);
        rem.setText("");

        AddSafeTour(dataTourSafe);
        base64ImageString="";
    }
    private DataTourSafe SetDataOfTour() {
        String result;
        EditText rem = findViewById(R.id.txtRemark);
        Remark = rem.getText().toString();

        DataTourSafe data = new DataTourSafe();

        result= ((TextView)((Spinner)findViewById(R.id.chooseRazif)).getSelectedView()).getText().toString();
        data.setRAZIF(result);
        result= ((TextView)((Spinner)findViewById(R.id.chooseMena)).getSelectedView()).getText().toString();
        data.setMENA(result);
        result= ((TextView)((Spinner)findViewById(R.id.chooseShip)).getSelectedView()).getText().toString();
        data.setShips(result);
        result= ((TextView)((Spinner)findViewById(R.id.chooseMITAN)).getSelectedView()).getText().toString();
        data.setMITAN(result);
        result= ((TextView)((Spinner)findViewById(R.id.chooseMIMZAIM)).getSelectedView()).getText().toString();
        data.setMIMTZA(result);
        result= ((TextView)((Spinner)findViewById(R.id.chooseHAMLAZOT)).getSelectedView()).getText().toString();
        data.setHAMLATZA(result);
        result= ((TextView)((Spinner)findViewById(R.id.chooseACHRAI)).getSelectedView()).getText().toString();
        data.setA_MESIMA(result);

        data.setPhoto(bitmap);
        data.setDate(date_tour);
        data.setUser_name(Mevazea);
        data.setRemark(Remark);

        return data;
    }
    void setSpinners(int spinner ,int array) {
        //an adapter is linked to array and a layout
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                array,
                android.R.layout.simple_spinner_dropdown_item);
        //link the adapter to the spinner
        Spinner Choice =  findViewById(spinner);
        Choice.setAdapter(adapter);
    }
    public void LoadDataFromNet(final String meta_data) {
        // READ data from db server.
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("Content-Type", "application/json; charset=utf-8");
        map.put("user", "48652");
        map.put("password", "050-4057453");
        map.put("metadata", meta_data);

        final PostRequest request2 = new PostRequest(Consts.getFullUrl("/GetGenalDataToSafteyTowers"), map, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //  Get MENA from Json :
                ArrayList<GeneralData>  generalData = new ArrayList<>((Arrays.asList(new Gson().fromJson(response, GeneralData[].class))));
                switch (meta_data) {
                    case "mena":
                        setSpinners(R.id.chooseMena, generalData);
                        break;
                    case "razif":
                        setSpinners(R.id.chooseRazif, generalData);
                        break;
                    case "ships":
                        setSpinners(R.id.chooseShip, generalData);
                        break;
                    case "cargo":
                        setSpinners(R.id.chooseMITAN, generalData);
                        break;
                    case "acrai":
                        setSpinners(R.id.chooseACHRAI, generalData);
                        break;
                    case "halazot":
                        setSpinners(R.id.chooseHAMLAZOT, generalData);
                        break;
                    case "memza":
                        setSpinners(R.id.chooseMIMZAIM, generalData);
                        break;
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         Toast toast = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                         toast.show();
                    }
                });

        Safetyapp.getInstance().getQueue().add(request2);
    }
    void setSpinners(int id_spinner , ArrayList<GeneralData> listOfItems) {
        // Get reference of widgets from XML layout
        final Spinner spinner =  findViewById(id_spinner);
        final List<GeneralData> pList =listOfItems;

        ArrayAdapter<GeneralData> dataAdapter = new ArrayAdapter<GeneralData>(this, android.R.layout.simple_spinner_item, pList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray
                if (position == 0)
                    ((TextView) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hint_text));
                 else
                    ((TextView) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_text));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    void SetDate_Time(){
        // set TIME of tour
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        String time = dateFormat.format(calendar.getTime());
        TextView txtTime = findViewById(R.id.txtTime);
        date_tour = calendar.getTime();
        txtTime.setText(time);
        // set DATE of tour
        dateFormat = new SimpleDateFormat("dd-MM-yy");
        String date = dateFormat.format(calendar.getTime());
        TextView txtDate = findViewById(R.id.txtDate);
        txtDate.setText(date);
    }
    public void openUserDialog() {
        empDialogLogin empDialogLogin = new empDialogLogin();
        empDialogLogin.setCancelable(false);
        empDialogLogin.show(getSupportFragmentManager(), "User Dialog");
    }
    public void openPasswordDialog() {
        passwordDialog passwordDialog = new passwordDialog();
        passwordDialog.setCancelable(false);
        passwordDialog.show(getSupportFragmentManager(), "Password Dialog");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data==null)
            return;
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);

        base64ImageString = ImageUtil.convert(bitmap);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // put your code here...
        //Toast.makeText(this, "נא לאשר את הפעולה", Toast.LENGTH_LONG).show();
    }
    @Override
    public void applyTextEmpid(String empId) {
        Send_SMS(empId);
        emp_id = empId;
    }
    @Override
    public void applyTextPass(String password) {
        GetUserLogIn(emp_id, password);
    }
    private void GetUserLogIn(final String user, String pass) {
        // READ data from db server.
        HashMap<String, String> map = new HashMap<>();

        map.put("Content-Type", "application/json; charset=utf-8");
        map.put("empNumber", user);
        map.put("pass", pass);

        final PostRequest request2 = new PostRequest(Consts.getFullUrl("/SafeTourLogIn"), map, new Response.Listener<String>() {
            UserLogin userLogin;
            @Override
            public void onResponse(String response) {

                userLogin = new Gson().fromJson(response, UserLogin.class);
                if(userLogin!=null && userLogin.isLogIn())
                    SetDataOnUI(userLogin.getName(), userLogin.getDuty());
                else
                {
                    Toast.makeText(getApplicationContext(), "סיסמא לא נכונה", Toast.LENGTH_LONG).show();
                    openPasswordDialog();
                    // finish();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                );

        Safetyapp.getInstance().getQueue().add(request2);
    }
    private void Send_SMS(String empNum) {
        //WRITE to db server:
        try{
            HashMap<String, String> map = new HashMap<>();

            map.put("Content-Type", "application/json; charset=utf-8");
            map.put("user", "");
            map.put("password", "");
            map.put("empNumber", empNum);

            PostRequest request = new PostRequest(Consts.getFullUrl("/SafeTourGeneratePassword"),map, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String ret_msg  = new Gson().fromJson(s, String.class );
                    Toast.makeText(MainActivity.this, ret_msg, Toast.LENGTH_LONG).show();
                    if(ret_msg.contains("הסיסמה נשלחה"))
                        openPasswordDialog();
                    else
                        openUserDialog();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            int socketTimeout = 60000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);


            Safetyapp.getInstance().getQueue().add(request);
        }
        catch(Exception ex)

        {

        }
    }
}

//<editor-fold desc="Description">
//Safetyapp.getInstance().mUser = new User();

//Safetyapp.getInstance().mUser.DUTY = userLogin.getDuty();
//Safetyapp.getInstance().mUser.EMPL_NUM = user;
//Safetyapp.getInstance().mUser.FIRST_NAME = userLogin.getName();
//Safetyapp.getInstance().mUser.ENABLE ="True";
// Toast.makeText(this,password+" : " +password, Toast.LENGTH_LONG);

//if (MainActivity.IsUserGetSMS)

//}
//else {
//    Toast.makeText(this,"עובד לא מורשה להכנס למערכת", Toast.LENGTH_LONG).show();
//openUserDialog();
//}
//GetUserLogIn(empId, password);
//if (CheckUserLogIn(empId, password))
//SetDataOnUI();
//else
// finish();
//openUserDialog();
//Toast.makeText(this,"עובד לא מורשה להכנס למערכת", Toast.LENGTH_LONG).show();
//Toast.makeText(this,MainActivity.uset_info, Toast.LENGTH_LONG).show();
//        //if (CheckUserLogIn(empId, password))
//        //SetDataOnUI();
//        //else
//        // finish();
//        //openUserDialog();
//Toast.makeText(this,"עובד לא מורשה להכנס למערכת", Toast.LENGTH_LONG).show();
//progressBar.setVisibility(View.GONE);
// Toast.makeText(getActivity(),getString(R.string.network_errow),Toast.LENGTH_SHORT).show();
// onClick(null);
                    /* mRunDotAnimation=false;
                     dot.setVisibility(View.INVISIBLE);

                     enter_container_number_mesage.setVisibility(View.VISIBLE);
                     mRunUpDown=true;
                     StartShowEnterContainerAnimation();*/
//</editor-fold>