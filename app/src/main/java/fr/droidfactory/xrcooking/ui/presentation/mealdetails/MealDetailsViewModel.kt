package fr.droidfactory.xrcooking.ui.presentation.mealdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.droidfactory.xrcooking.data.MealDbRepository
import fr.droidfactory.xrcooking.domain.models.MealDetailsDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import fr.droidfactory.xrcooking.ui.presentation.MealDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MealDetailsViewModel @Inject constructor(
    private val mealDbRepository: MealDbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mealDetailsArguments = savedStateHandle.toRoute<MealDetails>()
    private val _mealState = MutableStateFlow<ResultState<MealDetailsDTO>>(ResultState.Uninitialized)
    val mealState = _mealState.asStateFlow()

    init {
        viewModelScope.launch {
            _mealState.update { ResultState.Loading }
            mealDbRepository.getMealById(mealId = mealDetailsArguments.mealId).onSuccess { meal ->
                _mealState.update { ResultState.Success(meal) }
            }.onFailure { exception ->
                _mealState.update { ResultState.Failure(exception) }
            }
        }
    }
}