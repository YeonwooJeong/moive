package com.yonoo.movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    // 웹사이트 주소를 저장할 변수
    String text="";
    String event ="";
    int start,end,list_cnt;
    ListView listview ;
    ListViewAdapter adapter;
    public static MainActivity main = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.textView1);
        Button b = (Button)findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventArray = null;
                try {
                    eventArray = loadHtml();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("결과물 : "+eventArray);
                try {
                    JSONArray array = new JSONArray(eventArray); //[]
                    list_cnt=array.length();
                    System.out.println("list_cnt "+list_cnt);

                    String[] getDescription=new String[list_cnt];
                    String[] getLink=new String[list_cnt];
                    String[] getImageUrl=new String[list_cnt];

                    for(int i=0;i<list_cnt;i++){

                        JSONObject jsonObject=array.getJSONObject(i);
                        Log.i("JSON Object",jsonObject+"");
                        getDescription[i]=jsonObject.getString("description");
                        getLink[i]=jsonObject.getString("link");
                        getImageUrl[i]=jsonObject.getString("imageUrl");
                        Log.i("JsonParsing",getDescription[i]+","+getLink[i]+","+getImageUrl[i]);

                        // Adapter 생성
                        adapter = new ListViewAdapter() ;
                        // 리스트뷰 참조 및 Adapter달기
                        listview = (ListView) findViewById(R.id.listview1);
                        adapter.addItem(getDescription[i]) ;
                    }
//                            tv.setText(eventArray);
                    for(int i=0;i<list_cnt;i++){
                        listview.setAdapter(adapter);
                        // 첫 번째 아이템 추가.

                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView parent, View v, int position, long id) {
                                // get item
                                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                                String titleStr = item.getTitle() ;
                            }
                        }) ;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    } // end of onCreate
    String loadHtml() throws InterruptedException { // 웹에서 html 읽어오기==
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("http://www.cgv.co.kr/culture-event/event/?menu=2#1").get(); //웹에서 내용을 가져온다.
                    Element script = doc.select("body").first();
                    text = script.html(); //원하는 부분은 Elements형태로 되어 있으므로 이를 String 형태로 바꾸어 준다.
                    start = text.indexOf("var jsonData");
                    end = text.indexOf("$(\".evt");

                    main.event= text.substring(start+15, end-16);

                } catch (IOException e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.
                    e.printStackTrace();
                }
            }
        });
        t.start(); // 쓰레드 시작
        t.join(); //쓰레드 완료까지 대기
        return main.event;
    }

}