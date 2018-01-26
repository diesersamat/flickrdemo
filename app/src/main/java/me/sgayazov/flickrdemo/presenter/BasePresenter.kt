package me.sgayazov.flickrdemo.presenter

import io.reactivex.disposables.Disposable
import me.sgayazov.flickrdemo.dataprovider.Interactor

abstract class BasePresenter {
    var interactor: Interactor = Interactor()
    private val listOfDisposable: MutableList<Disposable> = mutableListOf()

    fun onStop() {
        listOfDisposable.forEach({ it.dispose() })
    }

    protected fun addSubscription(disposable: Disposable) {
        listOfDisposable.add(disposable)
    }
}