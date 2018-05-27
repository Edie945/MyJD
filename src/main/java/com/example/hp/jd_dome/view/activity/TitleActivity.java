package com.example.hp.jd_dome.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.jd_dome.R;

import java.util.ArrayList;
import java.util.List;

public class TitleActivity extends AppCompatActivity {

    private ListView lv;
    private MytitleActivity mtl;
    private Button bt;
    private XCFlowLayout mFlowLayout;
    private String mNames[] = {
            "welcome","android","TextView",
            "apple","jamy","kobe bryant",
            "jordan","layout","viewgroup",
            "margin","padding","text",
            "name","type","search","logcat"
    };

    private List<String> list=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        mFlowLayout = (XCFlowLayout) findViewById(R.id.flowlayout);
        lv = (ListView) findViewById(R.id.lv);
        mtl = (MytitleActivity) findViewById(R.id.mtl);
        bt = (Button) findViewById(R.id.bt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                lv.setAdapter(new myBaseAdapter());

            }
        });
        initChildViews();

        jilu();
    }
    public  void initChildViews() {
        // TODO Auto-generated method stub


        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for( int i = 0; i < mNames.length; i ++){
            TextView view = new TextView(this);
            view.setText(mNames[i]);
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape));
            mFlowLayout.addView(view,lp);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.add(mNames[finalI]);
                    lv.setAdapter(new myBaseAdapter());
                }
            });
        }
    }
    public void jilu(){

        mtl.setJiekou(new MytitleActivity.onsetHuida() {
            @Override
            public void huida(String aa) {
                Toast.makeText(TitleActivity.this, aa+"", Toast.LENGTH_SHORT).show();
                list.add(aa);
                lv.setAdapter(new myBaseAdapter());
            }

        });

        lv.setAdapter(new myBaseAdapter());

    }

    class myBaseAdapter  extends BaseAdapter {


        @Override
        public int getCount() {
            return list.size();
        }


        @Override
        public Object getItem(int position) {
            return null;
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHold hold=null;
            if(convertView==null){
                if(hold==null){
                    hold=new ViewHold();
                    convertView=View.inflate(TitleActivity.this,R.layout.resou_layout,null);
                    hold.tv = convertView.findViewById(R.id.tvv1);
                }
                convertView.setTag(hold);
            }else{
                hold = (ViewHold) convertView.getTag();
            }
            hold.tv.setText(list.get(position));

            return convertView;
        }
    }
    class ViewHold{
        TextView tv;
    }
}
