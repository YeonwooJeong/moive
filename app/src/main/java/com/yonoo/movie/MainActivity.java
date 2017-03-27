package com.yonoo.movie;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    String[] split = null;
    // 웹사이트 주소를 저장할 변수
    String urlAddress = "http://www.cgv.co.kr/culture-event/event/?menu=2#1";
    Handler handler = new Handler(); // 화면에 그려주기 위한 객체
    String text="";
    String eventArray ="";
    int start,end,list_cnt;
    ArrayList<String> descriptionKeyList = new ArrayList<>();
    private ListView listview ;
    private ListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        // Adapter 생성
//        adapter = new ListViewAdapter() ;
//
//        // 리스트뷰 참조 및 Adapter달기
//        listview = (ListView) findViewById(R.id.listview1);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                // get item
//                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;
//
//                String titleStr = item.getTitle() ;
//                Drawable iconDrawable = item.getIcon() ;
//
//                // TODO : use item data.
//            }
//        }) ;


//        // 첫 번째 아이템 추가.
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_account_box_black_36dp),
//                "Box", "Account Box Black 36dp") ;
//        // 두 번째 아이템 추가.
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_account_circle_black_36dp),
//                "Circle", "Account Circle Black 36dp") ;

        tv = (TextView)findViewById(R.id.textView1);
        Button b = (Button)findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHtml();
            }
        });
    } // end of onCreate
    void loadHtml() { // 웹에서 html 읽어오기==
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("http://www.cgv.co.kr/culture-event/event/?menu=2#1").get(); //웹에서 내용을 가져온다.
//                    Element contents = doc.select("body").first(); //내용 중에서 원하는 부분을 가져온다.
                    Element script = doc.select("body").first();
                    text = script.html(); //원하는 부분은 Elements형태로 되어 있으므로 이를 String 형태로 바꾸어 준다.
                    start = text.indexOf("var jsonData");
                    end = text.indexOf("$(\".evt");

                    eventArray= text.substring(start+15, end-16);

//                    String jsonData = "{"+"\"jsonData\""+":["+eventList+"]}";
//                    System.out.println(jsonData);
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
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    JsonObject eventJson = new JsonObject(); //{}
//                    array.add(eventList);
//                    eventJson.add("event",array);
//                    System.out.println("eventList "+eventList);
//                    System.out.println("Json 변환 "+eventJson.toString());
                } catch (IOException e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.
                    e.printStackTrace();
                }

                try {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(eventArray);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start(); // 쓰레드 시작
    }



}