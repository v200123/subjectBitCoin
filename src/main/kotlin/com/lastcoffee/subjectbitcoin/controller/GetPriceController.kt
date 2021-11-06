package com.lastcoffee.subjectbitcoin.controller

import com.lastcoffee.subjectbitcoin.bean.NewPriceBean
import com.lastcoffee.subjectbitcoin.div
import com.lastcoffee.subjectbitcoin.http.ApiService
import com.lastcoffee.subjectbitcoin.http.getApiServer
import com.lastcoffee.subjectbitcoin.toBig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.RoundingMode
import java.util.*

@Component
//@RestController
class GetPriceController {
    val mOldPrice = mutableListOf<Double>(0.0, 0.0)
    val mNeedSeeCoinArray = arrayListOf<String>("MATIC-USDT", "SAND-USDT")
    @Scheduled(cron = "0 0 8,12,15-23 * * *")
//    @RequestMapping("/getPerice")
    fun getPrice(): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Chongqing"))
        val nowString = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)}" +
                "-${calendar.get(Calendar.DAY_OF_MONTH)}:${calendar.get(Calendar.HOUR_OF_DAY)}"
        var mReturnString = "当前时间为:${nowString} \n "

        var mSeeCoinName = ""
        var mIsUp = false
        mNeedSeeCoinArray.forEachIndexed { i: Int, s: String ->
            mSeeCoinName += s.split("-")[0] + ","
            val newPriceBean = runBlocking(Dispatchers.IO) {
                return@runBlocking getApiServer().getCoinNewPrice(s).data
            }
            val nowPrice = newPriceBean[0].idxPx
            if (mOldPrice.isNotEmpty() && mOldPrice[i] != 0.0) {
                mIsUp = mOldPrice[i] < nowPrice

                val bigDecimal = (((nowPrice.toBig() - mOldPrice[i].toBig())/( nowPrice.toBig()))*( 100.0.toBig()))
                    .setScale(2, RoundingMode.HALF_UP)
                mReturnString += "当前的${s}价格为：$nowPrice/USDT 相较上一个时段是：${if (mIsUp) "涨" else "跌"},幅度为：${bigDecimal}%"
            } else
                mReturnString += "当前的${s}价格为：$nowPrice/USDT"
            mOldPrice[i] = nowPrice
        }
        val dropLast = mSeeCoinName.dropLast(1)
        mSeeCoinName = dropLast + "的实时价格检测"
        runBlocking(Dispatchers.IO) {
            val sendNotification = getApiServer().sendNotification(mSeeCoinName, mReturnString)
            println("发送通知的结果：${sendNotification}")
        }
        println("title是：${mSeeCoinName}.内容是：${mReturnString}")
        return mReturnString
    }

    @GetMapping("/test")
    fun test(): String {
        return "哈哈哈哈"
    }
}