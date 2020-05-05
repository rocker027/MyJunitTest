package com.example.myjunittest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myjunittest.R
import com.example.myjunittest.api.ServiceApi
import com.example.myjunittest.ui.home.HomeRepository
import com.example.myjunittest.ui.home.ProductAPI
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import okhttp3.OkHttpClient
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

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
            callWebSocket()
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


    private fun callWebSocket() {
        val url =
            "ws://10.200.252.185:17803/register-mobile?token=da9a7a0c-968d-4aed-86c1-4f2dda2dc193"
        val lifecycle = AndroidLifecycle.ofApplicationForeground(activity!!.application)
        val backoffStrategy = ExponentialWithJitterBackoffStrategy(5000, 5000)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        val socket = Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(url))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .backoffStrategy(backoffStrategy)
            .lifecycle(lifecycle)
            .build()
            .create<SocketService>()

        // Observe the WebSocket connection
        socket.observeWebSocketEvent()
//            .filter { it is WebSocket.Event.OnConnectionOpened<*> }
            .subscribe({
                println("websocket --> doing")
                if (it is WebSocket.Event.OnConnectionOpened<*>) {
                }
                when (it) {
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        println("websocket subscribe")
                        socket.sendSubscribe(RegisterSubscribe(1, "register"))
                        println("WebSocket.Event.OnConnectionOpened")}
                    is WebSocket.Event.OnConnectionClosed -> {println("WebSocket.Event.OnConnectionClosed")}
                    is WebSocket.Event.OnConnectionClosing -> {println("WebSocket.Event.OnConnectionClosing")}
                    is WebSocket.Event.OnConnectionFailed -> {println("WebSocket.Event.OnConnectionOnConnectionFailedOpened")}
                    is WebSocket.Event.OnMessageReceived -> {println("WebSocket.Event.OnMessageReceived")}
                }
            }, { error ->
                print("websocket Error while observing socket ${error}")
            })

// Observe the ticker channel
        socket.observeCaptcha()
            .subscribe({
                print("websocket New Bitcoin price: ${print(it.data)}")
            }, { error ->
                print("websocket Error while observing ticker ${error.cause}")
            })
    }
}


interface SocketService {
    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>

    @Send
    fun sendSubscribe(subscribe: RegisterSubscribe)

    @Receive
    fun observeCaptcha(): Flowable<ValidateDataClass>

}

@JsonClass(generateAdapter = true)
data class ValidateDataClass(
    val `data`: Data,
    val topic: String
)

@JsonClass(generateAdapter = true)
data class Data(
    val id: Int,
    val type: Int,
    val url: String
)

@JsonClass(generateAdapter = true)
data class RegisterSubscribe(
    @Json(name = "status") val status: Int,
    @Json(name = "topic") val topic: String
)
