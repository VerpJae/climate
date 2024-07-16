package com.ywhs.climate

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Color.parseColor
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random

class MainActivity : AppCompatActivity() {

    /* TODO:
        디자인: activity_main.xml에
            오늘의 미션을 나타내줄 수 있는 텍스트 뷰 추가
            미션을 성공했을 시 누를 수 있는 버튼 추가
                  ``     포인트를 제공해주므로 현재 포인트를 나타낼 수 있는 텍스트뷰 추가
            이외의 미적인 부분을 고려하여 디자인 요소 추가
        코드:
            onCreate 함수 속 TODO를 참고하여 코드 작성,
            activity_main.xml에 텍스트 뷰와 버튼이 만들어졌으면
                onCreate에서 텍스트뷰를 불러와서 텍스트 설정 -> arr중 하나 랜덤으로 뽑아서 설정
                            버튼을 불러와서 클릭시 -> 그냥 클릭 이벤트만 구현
     */

    val arr: ArrayList<String> = arrayListOf()

    override fun onStart() {
        super.onStart()

        val sharedPrefs = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE)

        var lastCompleted = sharedPrefs.getString("lastCompleted", "00000000")
        var score = sharedPrefs.getInt("score", 0)



        val mission = findViewById<TextView>(R.id.mission)
        val button = findViewById<Button>(R.id.button)
        val scoreTextView = findViewById<TextView>(R.id.score)
        scoreTextView.text = "현재 점수: $score"

        val now = Date()
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(now)
        val random = Random(today.toLong()).nextInt(arr.size) //today 를 seed로 설정함 -> 매일 미션 동일

        mission.text = arr[random]

        if(lastCompleted == today) { //미션 완료 상태
            // 현재 상태 설정
            mission.background = parseColor("#84EF84").toDrawable()
            button.text = "되돌리기"
        }
        else { //미션 미완료 상태
            // 기본값이 미완료지만 혹시 모르니까 다시 설정
            button.text = "미션완료"
            mission.background = parseColor("#B3B3B3").toDrawable()
        }

        button.setOnClickListener {
            lastCompleted = sharedPrefs.getString("lastCompleted", "0000000") // 귀찮아서..

            if(lastCompleted == today) { // 완료 상태 -> 미완료
                sharedPrefs.edit().putString("lastCompleted", "00000000").apply()

                sharedPrefs.edit().putInt("score", score-1).apply()
                score = sharedPrefs.getInt("score", 0) //귀찮아서..

                scoreTextView.text = "현재 점수: $score"

                button.text = "미션완료"
                mission.background = parseColor("#B3B3B3").toDrawable()
            }
            else { // 미완료 상태 -> 완료
                sharedPrefs.edit().putString("lastCompleted", today).apply()

                sharedPrefs.edit().putInt("score", score+1).apply()
                score = sharedPrefs.getInt("score", 0)
                scoreTextView.text = "현재 점수: $score"

                button.text = "되돌리기"
                mission.background = parseColor("#84EF84").toDrawable()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        arr.add("오늘 날씨 체크하기")
        arr.add("대중교통 이용하기")
        arr.add("재활용 쓰레기로 작품 만들기")
        arr.add("하루에 3만 걸음 걷기")
        arr.add("재사용 가능한 물병 및 컵 사용")
        arr.add("자연 탐방하기")
        arr.add("디지털 기기 사용 9시간 이하")
        arr.add("음식물 쓰레기 만들지 않기")
        arr.add("공공장소 청소하기")
        arr.add("화분 집에 설치하기")
        arr.add("남은 음식을 이용한 새로운 요리 레시피 만들기")
        arr.add("SNS에 친환경 활동을 공유하기")
        arr.add("포장재가 없는 제품만 구매하기")
        arr.add("샤워 시간을 20분 이하로 줄이기.")
        arr.add("화학 물질 없는 천연 청소 용품 사용하기")
        arr.add("재생 용지로 업무 및 과제 처리하기")

        arr.add("쓰레기 재활용 1개 이상 하기")

        for (i in 1..10) {
            arr.add("일회용품 ${i}개 이하 사용하기")
        }

        for (i in 1..5) {
            arr.add("쓰레기 ${i}개 줍기")
            arr.add("식물 ${i}개 심기")
            arr.add("동물성 음식 ${i}개 이하 소비하기")
        }


        // 알람 설정
        val serviceIntent = Intent(this@MainActivity, AlarmService::class.java)
        startService(serviceIntent)
    }

}
