package com.cloud.marom.safetytours.objects;

import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.VolleyError;
//import com.cloud.marom.safetytours.MainActivity;
import com.cloud.marom.safetytours.Safetyapp;
import com.cloud.marom.safetytours.server.Consts;
import com.cloud.marom.safetytours.server.PostRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Marom on 12/03/2018.
 */

public class DataTourSafe {

    private ArrayList<GeneralData> generalData;
    // Initializing a String Array
    final public String[] RAZIFIM = new String[]{
            "בחר רציף ...",
            "רציף 1",
            "רציף 9",
            "רציף 12",
            "רציף 25",
            "רציף 24",
            "רציף 27",
    };
    final public String[] MENAIM = new String[]{
            "בחר מנע ...",
            "יורם סוויסה",
            "חזן שוני",
            "אריק אסולין",
            "דודו אפריאט",
            "לוי אילן",
            "יצחק סוויסה",
            "אשר נהון"
    };
    final public  String[] SHIPS = new String[]{
            " בחר אניה ...",
            "OASSIS",
            "ZIM ",
            "ALALUF",
            "VENUS"};
    final public  String[] MITANIM = new String[]{
            "בחר סוג מטען ...",
            "מטען כללי",
            "גופרית",
            "מכולות",
            "תפזורת"};
    final public  String[] MIMZAIM = new String[]{
            "בחר ממצא ...",
            "ממצא מס. 1",
            "ממצא מס. 2",
            "ממצא מס. 3",
            "ממצא מס. 4"};
    final public  String[] HAMLAZOT = new String[]{
            "בחר המלצה ...",
            "המלצה מס. 1",
            "המלצה מס. 2",
            "המלצה מס. 3",
            "המלצה מס. 4"};
    final public  String[] ACHRAI_MESIMA = new String[]{
            "בחר אח' משימה ...",
            "רמח ציוד",
            "רמח בינוי",
            "רמח תפעול",
            "רמח מכולות",
            "מהנדס בטיחות",
            "ק.בטיחות בתעבורה"};

    private int id = 0;
    private Date time = null;
    private Date date = null;
    private String user_name;
    private String RAZIF;
    private String MENA;
    private String ship;
    private String MITAN;
    private String MIMTZA;
    private Bitmap photo;
    private String HAMLATZA;
    private String A_MESIMA;
    private String Remark;

    public int getId() {  return this.id; }
    public void setId(int id) { this.id = id; }

    public Date getTime() { return this.time;}
    public void setTime(Date time) { this.time = time; }

    public Date getDate() { return this.date; }
    public void setDate(Date date) { this.date = date;}

    public String getUser_name() { return this.user_name; }
    public void setUser_name(String userName){ this.user_name = userName; }

    public String getRAZIF(){ return this.RAZIF; }
    public void setRAZIF(String razif){ this.RAZIF= razif; }

    public String getMENA(){ return MENA; }
    public void setMENA(String mena){ this.MENA= mena; }

    public String getShip(){ return this.ship; }
    public void setShips(String ship){ this.ship= ship; }

    public String getMITAN(){ return this.MITAN; }
    public void setMITAN(String mitan){ this.MITAN= mitan; }

    public String getMIMTZA(){ return this.MIMTZA; }
    public void setMIMTZA(String mimtza){ this.MIMTZA= mimtza; }

    public String getHAMLATZA(){ return this.HAMLATZA; }
    public void setHAMLATZA(String hamlatza){ this.HAMLATZA= hamlatza; }

    public String getA_MESIMA(){ return this.A_MESIMA; }
    public void setA_MESIMA(String a_MESIMA){ this.A_MESIMA= a_MESIMA; }

    public String getRemark(){ return this.Remark; }
    public void setRemark(String remark){ this.Remark= remark; }

    public Bitmap getPhoto(){ return this.photo; }
    public void setPhoto(Bitmap photo){ this.photo= photo; }

}

/*final public String[] plants = new String[]{
            "Select an item...",
            "California sycamore",
            "Mountain mahogany",
            "Butterfly weed",
            "Carrot weed"
    };*/


// pressonshipmessage.setVisibility(View.VISIBLE);
// pd.dismiss();
// [Mena] [Razif] [Ships] [cargo]
