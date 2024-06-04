package vn.example.rxapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.operators.completable.CompletableFromObservable

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private var disposable: Disposable? = null
    private lateinit var tvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTitle = findViewById(R.id.tvMainTitle)

        /**
         * case observable , observer
         */
//        val observable = createObservable()
//        val observer = createObserver()
//
//        observable
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(observer)

        /**
         * case single , singleObserver
         */
//        val singleObservable = createSingleObservable()
//        val singleObServer = createSingleObServer()
//        singleObservable
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(singleObServer)

        /**
         *  case Maybe & MaybeObserver
         */
//        val maybeObservable = createMaybeObservable()
//        val maybeObServer = createMaybeServer()
//        maybeObservable
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(maybeObServer)

        /**
         * case Completable & CompletableObserver
         */

//        val completeObservable = createCompletableObservable()
//        val completeObserver = createCompletableObserver()
//
//        completeObservable.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(completeObserver)

        /**
         * case Flowable & Observer
         */
//        val flowableObservable = getFlowableObservable()
//        val observer = getFlowableObserver()
//        flowableObservable.subscribeOn(Schedulers.io()) // Xử lý trên luồng I/O
//            .observeOn(AndroidSchedulers.mainThread()) // .subscribe(
//

        tvTitle.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun createObservable(): Observable<Int> {
        return Observable.create { emitter ->
            try {
                val numbers = listOf(1, 2, 3, 4, 5)
                numbers.forEach {
                    if (!emitter.isDisposed) {
                        emitter.onNext(it)
                    }
                }

                if (!emitter.isDisposed) {
                    emitter.onComplete()
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    private fun createObserver(): Observer<Int> {
        return object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                disposable = d
                Log.d(TAG, "onSubscribe: ")
            }

            override fun onNext(t: Int) {
                val text = tvTitle.text.toString()
                tvTitle.text = "$text - $t"
                Log.d(TAG, "onNext: $t")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: $e")
            }

            override fun onComplete() {
                val text = tvTitle.text.toString()
                tvTitle.text = "$text - onComplete"
                Log.d(TAG, "onComplete: ")
            }
        }
    }

    private fun createSingleObservable(): Single<Int> {
        return Single.create { emitter ->
            try {
                val value = 9
                emitter.onSuccess(value)
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    private fun createSingleObServer(): SingleObserver<Int> {
        return object : SingleObserver<Int> {
            override fun onSubscribe(d: Disposable) {
                disposable = d
                Log.d(TAG, "onSubscribe: ")
            }

            override fun onSuccess(t: Int) {
                tvTitle.text = "$t - complete"
                Log.d(TAG, "onSuccess: $t")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: $e")
            }
        }
    }

    private fun createMaybeObservable(): Maybe<Int> {
        return Maybe.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    emitter.onSuccess(10)
                    emitter.onComplete()
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    private fun createMaybeServer(): MaybeObserver<Int> {
        return object : MaybeObserver<Int> {
            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onSuccess(t: Int) {
                tvTitle.text = "$t"
                Log.d(TAG, "onSuccess: ")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: ")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete: ")
            }
        }
    }

    private fun createCompletableObservable(): Completable {
        return CompletableFromObservable.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    emitter.onComplete()
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    private fun createCompletableObserver(): CompletableObserver {
        return object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onComplete() {
                tvTitle.text = "onComplete"
                Log.d(TAG, "onComplete: ")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: $e")
            }
        }
    }

    private fun getFlowableObservable(): Flowable<Int> {
        return Flowable.create({ emitter ->
            try {
                val numbers = 12
                emitter.onNext(numbers)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }

    private fun getFlowableObserver(): SingleObserver<Int> {
        return object : SingleObserver<Int> {
            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onSuccess(t: Int) {
                val text = tvTitle.text
                tvTitle.text = "$text $t"
                Log.d(TAG, "onNext: ")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: $e")
            }

        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
