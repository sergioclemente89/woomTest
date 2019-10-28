package com.clementecastillo.people.screen.peoplelist

import com.clementecastillo.core.client.transaction.Transaction
import com.clementecastillo.people.extension.throttleDefault
import com.clementecastillo.people.presenter.Presenter
import com.clementecastillo.people.screen.controller.LoadingController
import com.clementecastillo.people.screen.controller.RouterController
import com.clementecastillo.people.usecase.GetMorePeopleUseCase
import com.clementecastillo.people.usecase.GetPeopleUseCase
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PeopleListPresenter @Inject constructor(
    private val loadingController: LoadingController,
    private val getPeopleUseCase: GetPeopleUseCase,
    private val getMorePeopleUseCase: GetMorePeopleUseCase,
    private val routerController: RouterController
) : Presenter<PeopleListView>() {

    override fun init(view: PeopleListView) {
        view.run {
            getPeopleUseCase.bind()
                .doOnSubscribe { loadingController.showLoading() }
                .doOnSuccess { loadingController.hideLoading() }
                .subscribe { it ->
                    when (it) {
                        is Transaction.Success -> {
                            addPeople(it.data)
                        }
                        is Transaction.Fail -> {
                            // TODO: Show error
                        }
                    }

                }.addTo(disposables)

            onRequestNextPage().subscribe {
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

            onPersonClick().throttleDefault().subscribe {
                routerController.routeToPersonDetail(it)
            }.addTo(disposables)
        }

    }

}