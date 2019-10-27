package com.clementecastillo.people.screen.peoplelist

import com.clementecastillo.core.client.transaction.Transaction
import com.clementecastillo.people.presenter.Presenter
import com.clementecastillo.people.screen.controller.LoadingController
import com.clementecastillo.people.usecase.GetMorePeopleUseCase
import com.clementecastillo.people.usecase.GetPeopleUseCase
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PeopleListPresenter @Inject constructor(
    private val loadingController: LoadingController,
    private val getPeopleUseCase: GetPeopleUseCase,
    private val getMorePeopleUseCase: GetMorePeopleUseCase
) : Presenter<PeopleListView>() {

    override fun init(view: PeopleListView) {
        getPeopleUseCase.bind()
            .doOnSubscribe { loadingController.showLoading() }
            .doOnSuccess { loadingController.hideLoading() }
            .subscribe { it ->
                when (it) {
                    is Transaction.Success -> {
                        view.addPeople(it.data)
                    }
                    is Transaction.Fail -> {
                        // TODO: Show error
                    }
                }

            }.addTo(disposables)

        view.onRequestNextPage().subscribe {
            getMorePeopleUseCase.bind(GetMorePeopleUseCase.Params(it))
                .subscribe { it ->
                    when (it) {
                        is Transaction.Success -> {
                            view.addPeople(it.data)
                        }
                        is Transaction.Fail -> {
                            // TODO: Show error
                        }
                    }
                }.addTo(disposables)
        }.addTo(disposables)
    }

}