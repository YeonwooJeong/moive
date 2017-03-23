package com.yonoo.movie;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    String[] split = null;
    // 웹사이트 주소를 저장할 변수
    String urlAddress = "http://www.cgv.co.kr/culture-event/event/?menu=2#1";
    Handler handler = new Handler(); // 화면에 그려주기 위한 객체
    String text="";
    String event="";
    int start,end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                    System.out.println("시작"+start);
                    System.out.println("끝"+end);

                    String event = text.substring(start, end-1);
                    System.out.println("event  "+event);
//
//                    System.out.println(text);
                } catch (IOException e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.
                    e.printStackTrace();
                }

                try {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(text.substring(start, end-1));
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
