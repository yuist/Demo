package com.scpoe.GoodWeather.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.scpoe.GoodWeather.R;
import com.scpoe.GoodWeather.adapter.CityAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //toolbar
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //drawer menu
    ListView listView;
    CityAdapter cityAdapter;
    ImageButton addCity;
    List<Fragment> fragmentList = new ArrayList<>();


    //tool
    RelativeLayout relativeLayout;
    FragmentManager fm = getSupportFragmentManager();
    private SQLiteDatabase db;
    public static final String CREATE_ALARM_SQL = "create table CityName (" +
            "_id integer primary key autoincrement," +
            "city text)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init page
        relativeLayout = (RelativeLayout) findViewById(R.id.frame_layout);
        //init database and list
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this, "CityName.db3", null, 1);
        db = mySQLiteHelper.getReadableDatabase();
        cityAdapter = new CityAdapter();
        initList();
        //init toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        //set actionbar toggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_toggle_open, R.string.drawer_toggle_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //set status bar color
        drawerLayout.setStatusBarBackground(R.color.status_bar);
        //set drawer menu
        listView = (ListView) findViewById(R.id.city_list);
        listView.setAdapter(cityAdapter);
        listView.setDividerHeight(0);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                System.out.println(">>> item long click success <<<");
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("城市");
                dialog.setMessage("确定要删除城市吗？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeList(position);
                    }
                });
                dialog.show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(">>> item click success <<<");
                fm.beginTransaction().replace(R.id.frame_layout, fragmentList.get(position)).commit();
                drawerLayout.closeDrawer(findViewById(R.id.drawer_layout_menu));
            }
        });
        addCity = (ImageButton) findViewById(R.id.add_city);
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityListActivity.class);
                startActivityForResult(intent, 1);
                System.out.println(">>> add city click success <<<");
            }
        });
        //init ui
        if (fragmentList.size() != 0) {
            fm.beginTransaction().replace(R.id.frame_layout, fragmentList.get(0)).commit();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 7) {
            String name = data.getStringExtra("select_city");
            addList(name);
            //database
            ContentValues values = new ContentValues();
            values.put("city", name);
            db.insert("CityName", null, values);
            fm.beginTransaction().replace(R.id.frame_layout, fragmentList.get(cityAdapter.getCount() - 1)).commitAllowingStateLoss();
            drawerLayout.closeDrawer(findViewById(R.id.drawer_layout_menu));
        }
    }

    public void initList() {
        Cursor cursor = db.query("CityName", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String cityName = cursor.getString(cursor.getColumnIndex("city"));
            addList(cityName);
        }
        cursor.close();
    }

    public void addList(String cityName) {
        cityAdapter.addCity(cityName);
        //fragment
        Fragment fragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("city", cityName);
        fragment.setArguments(bundle);
        fragmentList.add(fragment);
    }

    public void removeList(int position) {
        db.delete("CityName", "city = ?", new String[]{(String) cityAdapter.getItem(position)});
        cityAdapter.removeCity(position);
        fragmentList.remove(position);
    }

    class MySQLiteHelper extends SQLiteOpenHelper {

        public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ALARM_SQL);
            System.out.println(">>> create City Name SQLite success <<<");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(Gravity.LEFT)) {
            drawerLayout.closeDrawer(findViewById(R.id.drawer_layout_menu));
        } else {
            super.onBackPressed();
        }
    }
}
