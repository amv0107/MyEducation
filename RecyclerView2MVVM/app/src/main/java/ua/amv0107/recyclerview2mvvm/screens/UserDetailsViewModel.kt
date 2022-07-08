package ua.amv0107.recyclerview2mvvm.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.amv0107.recyclerview2mvvm.R
import ua.amv0107.recyclerview2mvvm.UserNotFoundExceptions
import ua.amv0107.recyclerview2mvvm.model.UserDetails
import ua.amv0107.recyclerview2mvvm.model.UsersService
import ua.amv0107.recyclerview2mvvm.tasks.EmptyResult
import ua.amv0107.recyclerview2mvvm.tasks.PendingResult
import ua.amv0107.recyclerview2mvvm.tasks.SuccessResult
import ua.amv0107.recyclerview2mvvm.tasks.Result

class UserDetailsViewModel(
    private val usersService: UsersService
) : BaseViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _actionShowToast = MutableLiveData<Event<Int>>()
    val actionShowToast: LiveData<Event<Int>> = _actionShowToast

    private val _actionGoBack = MutableLiveData<Event<Unit>>()
    val actionGoBack: LiveData<Event<Unit>> = _actionGoBack

    private val currentState: State get() = state.value!!

    init {
        _state.value = State(
            userDetailsResult = EmptyResult(),
            deletingInProcess = false
        )
    }

    fun loadUser(userId: Long) {
        if (currentState.userDetailsResult is SuccessResult) return
        _state.value = currentState.copy(userDetailsResult = PendingResult())
        usersService.getById(userId)
            .onSuccess {
                _state.value = currentState.copy(userDetailsResult = SuccessResult(it))
            }
            .onError {
                _actionShowToast.value = Event(R.string.cant_load_user_details)
                _actionGoBack.value = Event(Unit)
            }
            .autoCancel()
    }

    fun deleteUser() {
        val userDetailsResult = currentState.userDetailsResult
        if (userDetailsResult !is SuccessResult) return
        _state.value = currentState.copy(deletingInProcess = true)
        usersService.deleteUser(userDetailsResult.data.user)
            .onSuccess {
                _actionShowToast.value = Event(R.string.user_has_been_deleted)
                _actionGoBack.value = Event(Unit)
            }
            .onError {
                _state.value = currentState.copy(deletingInProcess = false)
                _actionShowToast.value = Event(R.string.cant_delete_user)
            }
            .autoCancel()
    }

    data class State(
        val userDetailsResult: Result<UserDetails>,
        private val deletingInProcess: Boolean
    ) {
        val showContent: Boolean get() = userDetailsResult is SuccessResult
        val showProgress: Boolean get() = userDetailsResult is PendingResult || deletingInProcess
        val enableDeleteButton: Boolean get() = !deletingInProcess
    }
}