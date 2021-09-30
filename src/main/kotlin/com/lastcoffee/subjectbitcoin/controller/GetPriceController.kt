package com.lastcoffee.subjectbitcoin.controller

import com.lastcoffee.subjectbitcoin.bean.NewPriceBean
import com.lastcoffee.subjectbitcoin.http.ApiService
import com.lastcoffee.subjectbitcoin.http.getApiServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class GetPriceController {
    val mNeedSeeCoinArray = arrayListOf<String>("MATIC-USDT","SAND-USDT")
//    @Scheduled(cron = "0 0 8,12,15-22 * *")
    @RequestMapping("/getPrice")
    fun getPrice(): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Chongqing"))
        val nowString = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)}" +
                "-${calendar.get(Calendar.DAY_OF_MONTH)}:${calendar.get(Calendar.HOUR_OF_DAY)}"
    var mReturnString = "当前时间为:${nowString} \n "

    var mSeeCoinName = ""
        mNeedSeeCoinArray.forEach {
            mSeeCoinName += it.split("-")[0]+","
            val newPriceBean =  runBlocking(Dispatchers.IO) {

                return@runBlocking getApiServer().getCoinNewPrice(it).data

            }
            mReturnString+="当前的${it}价格为：${newPriceBean[0].idxPx}/USDT"
        }
        val dropLast = mSeeCoinName.dropLast(1)
        mSeeCoinName=dropLast+"的实时价格检测"
        runBlocking(Dispatchers.IO) {
            val sendNotification = getApiServer().sendNotification(mSeeCoinName, mReturnString)
            println("发送通知的结果：${sendNotification}")
        }
        println("title是：${mSeeCoinName}.内容是：${mReturnString}")
        return mReturnString
    }

    @GetMapping("/test")
    fun test():String{
        return "哈哈哈哈"
    }
}