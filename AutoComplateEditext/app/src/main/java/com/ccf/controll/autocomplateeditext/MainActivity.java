package com.ccf.controll.autocomplateeditext;

import android.content.ContentValues;
import static com.ccf.controll.autocomplateeditext.AppSqliteHelp.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase autoDB;
    AutoCompleteTextView actv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppSqliteHelp help=new AppSqliteHelp(MainActivity.this,DBNAME,null,1);
        autoDB=help.getWritableDatabase();
        final List<String> keywords=new ArrayList<String>();
        if(autoDB.isOpen()&&!autoDB.isReadOnly()){
            //read from keywords
            Cursor cursor=autoDB.query(TABLENAME,
                    new String[]{"keywords"},
                    null,null,null,null,null
            );
            if(cursor.moveToFirst()){
                keywords.add(cursor.getString(0));
                while(cursor.moveToNext()) {
                    keywords.add(cursor.getString(0));
                }
            }
        }
//        Toast.makeText(MainActivity.this,"keywords"+keywords.size(),Toast.LENGTH_LONG).show();
        actv=(AutoCompleteTextView)findViewById(R.id.actv01);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,
                R.layout.support_simple_spinner_dropdown_item,keywords);
        actv.setAdapter(adapter);

        Button btnAuto=(Button)findViewById(R.id.btnAuto);
        btnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = actv.getText().toString().trim();
                boolean isRepect =false;
                for(String keyword:keywords){
                    if(text.equalsIgnoreCase(keyword)){
                        isRepect=true;
                        break;
                    }
                }
                if(!isRepect){//check no repect
                    ContentValues record = new ContentValues();
                    record.put("keywords", text);
                    long r = autoDB.insert(AppSqliteHelp.TABLENAME, null, record);
                    if (r > 0) {
                        Toast.makeText(MainActivity.this, "success" + r, Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
//
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

}
