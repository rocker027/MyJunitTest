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
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import okhttp3.OkHttpClient
import org.koin.android.ext.android.inject
import timber.log.Timber
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
//        val BASE_URL = "wss://echo.websocket.org"
        val BASE_URL = "ws://10.200.252.185:17803/register-mobile?token=da9a7a0c-968d-4aed-86c1-4f2dda2dc193"


        val okHttpClient =
            OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()

        val scarlet = Scarlet.Builder()
            // If the connection fails how and when should we retry to reconnect? -> backoffStrategy
            .backoffStrategy(ExponentialBackoffStrategy(2000, 4000))
            // We can declare our adapter: it adapts our custom messages to Message.
            // In this app I use Msg (my custom simple class), just as an example.
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory())
            // We can declare our adapter: it adapts our custom streams to Stream
            // In this app I use Flowable, just as an example.
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            // This actually creates the WebSocket: it is the only compulsory parameter!
            .webSocketFactory(okHttpClient.newWebSocketFactory(BASE_URL))
            // WebSocket will be automatically closed when app is not in foreground. We could combine it
            // with other life cycles to stop the WebSocket connection at some point (e.g. when a user logs out)
            // I have put an example of in the lifecycle package, even though I don't use it in this app. (feel free to try it)
//            .lifecycle(AndroidLifecycle.ofApplicationForeground(activity!!.application))
            .build()

        val socketService = scarlet.create(SocketService::class.java)

//        socketService.sendText(
//            Msg(
//                "test"
//            )
//        )

        socketService.observeWebSocketEvent().subscribe(
            {
                println("${it}")
                if (it is WebSocket.Event.OnConnectionOpened<*>) {
                    println("ready to send")
                    socketService.sendText(Msg("test"))
                }
            }, { e ->
                println("${e.message}")

            }
        )

        socketService.observeText().subscribe(
            { println(it.message) }, { e ->
                println("${e.message}")
            }
        )

//        socketService.observeWebSocketEvent().map{
//            when(it){
//                is WebSocket.Event.OnConnectionOpened<*> -> Timber.d("Connection opened")
//                is WebSocket.Event.OnMessageReceived -> Timber.d("Message received")
//                is WebSocket.Event.OnConnectionClosing -> Timber.d("Connection closing")
//                is WebSocket.Event.OnConnectionClosed -> Timber.d("Connection closed")
//                is WebSocket.Event.OnConnectionFailed -> Timber.d("Connection failed")
//            }
//            (it is WebSocket.Event.OnConnectionOpened<*> || it is WebSocket.Event.OnMessageReceived)
//        }
//
//        socketService.observeWebSocketEvent()
//            .subscribe {
//                when(it){
//                    is WebSocket.Event.OnConnectionOpened<*> -> Timber.d("Connection opened")
//                    is WebSocket.Event.OnMessageReceived -> Timber.d("Message received")
//                    is WebSocket.Event.OnConnectionClosing -> Timber.d("Connection closing")
//                    is WebSocket.Event.OnConnectionClosed -> Timber.d("Connection closed")
//                    is WebSocket.Event.OnConnectionFailed -> Timber.d("Connection failed")
//                }
//            }

    }

}

interface SocketService {
    @Receive
    fun observeText(): Flowable<Msg>

    @Send
    fun sendText(text: Msg)

    @Receive
    fun observeWebSocketEvent():
            Flowable<WebSocket.Event>
}

@JsonClass(generateAdapter = true)
data class Msg(
    @Json(name = "message") var message: String = ""
)