package com.ywhs.climate

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

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
     */ override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

        /*
          TODO:
               종류: 쓰레기 N개 줍기
                    일회용품 N개 이하 사용하기
                   ... (임의로 추가)
               N의 범위를 1~10이든 마음대로 설정해서
               for문으로 arr에 다 추가해주기 바람
         */
        val arr = arrayOf<String>()
        arr.plus("오늘 날씨 체크하기")
        arr.plus("대중교통 이용하기")
        arr.plus("재활용 쓰레기로 작품 만들기")
        arr.plus("하루에 3만 걸음 걷기")
        arr.plus("재사용 가능한 물병 및 컵 사용")
        arr.plus("자연 탐방하기")
        arr.plus("디지털 기기 사용 9시간 이하")
        arr.plus("음식물 쓰레기 만들지 않기")
        arr.plus("공공장소 청소하기")
        arr.plus("화분 집에 설치하기")
        arr.plus("남은 음식을 이용한 새로운 요리 레시피 만들기")
        arr.plus("SNS에 친환경 활동을 공유하기")
        arr.plus("포장재가 없는 제품만 구매하기")
        arr.plus("샤워 시간을 20분 이하로 줄이기.")
        arr.plus("화학 물질 없는 천연 청소 용품 사용하기")
        arr.plus("재생 용지로 업무 및 과제 처리하기")







        for(i in 1..10){ //example

            arr.plus("쓰레기 ${5}개 줍기")
            arr.plus("일회용품 ${10}개 이하 사용하기")
            arr.plus("쓰레기 재활용 ${3}개 이상 하기")
            arr.plus("고기 없는 음식 ${1}개 이상 만들기")
            arr.plus("식물 ${5}개 심기")
            arr.plus(" ${5}개 줍기")

        }
        // 알람 설정
        setDailyAlarm(this)
    }

    private fun setDailyAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // 매일 아침 8시에 알람 설정 (원하는 시간으로 변경 가능)
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        // 만약 현재 시간이 8시 이후라면 다음 날로 설정
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        // 매일 반복되는 알람 설정
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}