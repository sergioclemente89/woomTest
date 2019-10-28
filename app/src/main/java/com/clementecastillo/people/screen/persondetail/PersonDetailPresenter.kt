package com.clementecastillo.people.screen.persondetail

import com.clementecastillo.core.client.transaction.Transaction
import com.clementecastillo.people.presenter.Presenter
import com.clementecastillo.people.screen.controller.LoadingController
import com.clementecastillo.people.usecase.GetPersonByUuidUseCase
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PersonDetailPresenter @Inject constructor(
    private val loadingController: LoadingController,
    private val getPersonByUuidUseCase: GetPersonByUuidUseCase
) : Presenter<PersonDetailView>() {

    override fun init(view: PersonDetailView) {
        view.run {
            getPersonByUuidUseCase.bind(GetPersonByUuidUseCase.Params(getPersonUuid()))
                .doOnSubscribe { loadingController.showLoading() }
                .doOnSuccess { loadingController.hideLoading() }.subscribe { it ->
                    when (it) {
                        is Transaction.Success -> bindPerson(it.data)
                        is Transaction.Fail -> {
                        }
                    }
                }.addTo(disposables)
        }

    }

}