package fr.droidfactory.xrcooking.ui.presentation.categorysearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.droidfactory.xrcooking.data.MealDbRepository
import fr.droidfactory.xrcooking.domain.models.CategoryDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CategorySearchViewModel @Inject constructor(
    private val mealDbRepository: MealDbRepository
): ViewModel() {

    private val _categories = MutableStateFlow<ResultState<List<CategoryDTO>>>(ResultState.Uninitialized)
    val categories = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            _categories.update { ResultState.Loading }
            mealDbRepository.getCategories().onSuccess { categories ->
                _categories.update { ResultState.Success(categories) }
            }.onFailure { exception ->
                _categories.update { ResultState.Failure(exception) }
            }
        }
    }
}