package com.example.myjunittest.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myjunittest.R
import com.example.myjunittest.api.ServiceApi
import com.example.myjunittest.ui.home.HomeRepository
import com.example.myjunittest.ui.home.ProductAPI
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFactory
import com.neovisionaries.ws.client.WebSocketFrame
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import org.koin.android.ext.android.inject

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    val serviceAPI by inject<ServiceApi>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        root.btn1.setOnClickListener {
            println("click")
            Observable.just("1")
                .subscribeOn(Schedulers.io())
                .subscribe({
                    println("start send")
                    callWebSocketV2()
                }, { e -> println(e.message) })

        }

        root.btn2.setOnClickListener {
            callApiTest()
        }

        return root
    }

    private fun callApiTest() {
        val homeRepository = HomeRepository(ProductAPI())
        dashboardViewModel = DashboardViewModel(homeRepository)
        serviceAPI.getProduct()
            .subscribeOn(Schedulers.io())
            .subscribe({ t -> println(t.body()) })
    }

    //
    private fun callWebSocketV2() {
        // Create a WebSocket with a socket connection timeout value.
        val ws = WebSocketFactory().createSocket(
            "ws://10.200.252.185:17803/register-mobile?token=da9a7a0c-968d-4aed-86c1-4f2dda2dc193",
            5000
        )

        // Register a listener to receive WebSocket events.
        ws.addListener(object : WebSocketAdapter() {
            override fun onTextMessage(websocket: WebSocket?, text: String?) {
                super.onTextMessage(websocket, text)
                Log.v("WSS", text)
            }

            override fun onCloseFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
                super.onCloseFrame(websocket, frame)
                Log.v("WSS", "closing socket")
            }
        })

        ws.connect()
        ws.sendText("hello !")
        ws.disconnect()
    }

}