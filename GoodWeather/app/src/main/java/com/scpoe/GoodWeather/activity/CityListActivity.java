package com.scpoe.GoodWeather.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.scpoe.GoodWeather.other.MyApplication;
import com.scpoe.GoodWeather.R;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    public static final String CREATE_ALARM_SQL = "create table CityList (" +
            "_id integer primary key autoincrement," +
            "province text," +
            "city text," +
            "district text)";

    List<String> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        //init database
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this, "CityList.db3", null, 1);
        db = mySQLiteHelper.getReadableDatabase();
        if (!db.query("CityList", null, null, null, null, null, null, null).moveToNext()) {
            getCity();
        }
        //search
        ImageButton search = (ImageButton) findViewById(R.id.city_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText) findViewById(R.id.input_city_name);
                String citySearchName = input.getText().toString();
                searchCity(citySearchName);
            }
        });
        //cancel
        ImageButton cancel = (ImageButton) findViewById(R.id.city_select_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class MySQLiteHelper extends SQLiteOpenHelper {

        public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ALARM_SQL);
            System.out.println(">>> create City List SQLite success <<<");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public void getCity() {
        db.delete("CityList", null, null);
        JuheData.executeWithAPI(this, 39, "http://v.juhe.cn/weather/citys", JuheData.GET, new Parameters(), new DataCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    JSONObject result = new JSONObject(s);
                    JSONArray cityList = result.getJSONArray("result");
                    for (int j = 0; j < cityList.length(); j++) {
                        JSONObject list = cityList.getJSONObject(j);
                        String province = list.getString("province");
                        String city = list.getString("city");
                        String district = list.getString("district");
                        ContentValues values = new ContentValues();
                        values.put("province", province);
                        values.put("city", city);
                        values.put("district", district);
                        db.insert("CityList", null, values);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                System.out.println(">>> insert City List SQLite success <<<");
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                if (i == 30002) {
                    Toast.makeText(MyApplication.getContext(), "没有检测到网络", Toast.LENGTH_SHORT).show();
                } else if (i == 30003) {
                    Toast.makeText(MyApplication.getContext(), "没有进行初始化", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApplication.getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void searchCity(String city) {
        Cursor cursor = db.query("CityList", null, "district = ?", new String[]{city}, null, null, null, null);
        cityList = new ArrayList<>();
        if (cursor.moveToNext()) {
            cityList.add(cursor.getString(cursor.getColumnIndex("district")));
        } else {
            Toast.makeText(this, "没有这个城市", Toast.LENGTH_SHORT).show();
        }
        ListView listView = (ListView) findViewById(R.id.city_select_list);
        listView.setDividerHeight(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("select_city", cityList.get(position));
                setResult(7, intent);
                finish();
            }
        });
        cursor.close();
    }

}
