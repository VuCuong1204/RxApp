package vn.example.rxapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.delay

class HomeActivity : AppCompatActivity() {

    companion object {
        const val TAG = "HomeActivity"
    }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        val list = listOf("hello", "hihi")
//        val list1 = Observable.just("hihi", "hello")
//
//        val observable = Single.create<String> { emitter ->
//            emitter.onSuccess("success")
//            emitter.onError(Throwable())
//        }
//
//        observable
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : SingleObserver<String> {
//                override fun onSubscribe(d: Disposable) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onSuccess(t: String) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onError(e: Throwable) {
//                    TODO("Not yet implemented")
//                }
//            })
//        runBlocking {
//            Log.d(TAG, "Main thread: ${Thread.currentThread().name}")
//
//            val deferred2 = async { fetchData2() }
//            val deferred1 = async { fetchData1() }
//
//            // await() để lấy giá trị từ Deferred
//            val result2 = deferred2.await()
//            val result1 = deferred1.await()
//
//            Log.d(TAG, "Result 2: $result2")
//            Log.d(TAG, "Result 1: $result1")
//        }

        val publicSubject = PublishSubject.create<String>()
        val observer1 = createObserver()
        publicSubject
            .subscribe(observer1)

        publicSubject.onNext("Hello event1")
        publicSubject.subscribe(observer1)
        publicSubject.onNext("Hello event2")
    }

    suspend fun fetchData1(): String {
        delay(1000) // Giả định công việc tải dữ liệu mất 1 giây
        return "Data from source 1"
    }

    suspend fun fetchData2(): String {
        delay(2000) // Giả định công việc tải dữ liệu mất 1.5 giây
        return "Data from source 2"
    }

    private fun createObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe: ")
            }

            override fun onNext(t: String) {
                Log.d(TAG, "onNext: $t")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: ")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete: ")
            }
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
