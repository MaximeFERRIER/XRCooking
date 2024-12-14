package fr.droidfactory.xrcooking.ui.presentation.mealsbycategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.droidfactory.xrcooking.data.MealDbRepository
import fr.droidfactory.xrcooking.domain.models.CategoryMealDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import fr.droidfactory.xrcooking.ui.presentation.MealsByCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MealsByCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mealDbRepository: MealDbRepository
): ViewModel() {

    private val mealsByCategoryArguments = savedStateHandle.toRoute<MealsByCategory>()
    private val _meals = MutableStateFlow<ResultState<List<CategoryMealDTO>>>(ResultState.Uninitialized)
    val meals = _meals.asStateFlow()

    init {
        viewModelScope.launch {
            _meals.update { ResultState.Loading }
            mealDbRepository.getMealsByCategory(categoryName = mealsByCategoryArguments.categoryName).onSuccess { mealsByCategory ->
                _meals.update { ResultState.Success(mealsByCategory) }
            }.onFailure { error ->
                _meals.update { ResultState.Failure(error) }
            }
        }
    }
}