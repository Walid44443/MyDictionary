package com.buildinglink.dictionary.ui.screen.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buildinglink.dictionary.R
import com.buildinglink.dictionary.core.exception.backend.NotFoundException
import com.buildinglink.dictionary.core.exception.backend.ServerErrorException
import com.buildinglink.dictionary.domain.data.model.Phonetic
import com.buildinglink.dictionary.domain.usecase.SearchDefinitionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchDefinitionUseCase: SearchDefinitionUseCase,
    private val context: Context
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val _isNotFoundItems = MutableStateFlow(false)
    val isNotFoundItems: StateFlow<Boolean> = _isNotFoundItems


    private val _isNetworkError = MutableStateFlow(false)
    val isNetworkError: StateFlow<Boolean> = _isNetworkError

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _definition = MutableStateFlow("")
    val definition: StateFlow<String> = _definition

    private val _phonetics = MutableStateFlow<List<Phonetic>>(emptyList())
    val phonetics: StateFlow<List<Phonetic>> = _phonetics


    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchDefinition() {
        viewModelScope.launch {
            combine(
                _searchQuery.filter { it.isNotEmpty() },
                flow { emit(Unit) }) { query, _ -> query }
                .conflate()
                .onEach {
                    _isLoading.value = true
                }.onEach { query ->
                    searchDefinitionUseCase(query)
                        .collect { wordResult ->
                            resetResult()
                            wordResult.onFailure {
                                when (val exception = wordResult.exceptionOrNull()) {
                                    is NotFoundException -> {
                                        _isNotFoundItems.value = true
                                        _errorMessage.value =
                                            context.resources.getString(R.string.error_message_not_found)
                                    }

                                    is ServerErrorException -> {
                                        _isNetworkError.value = true
                                        _errorMessage.value =
                                            context.resources.getString(R.string.error_message_server)
                                    }

                                    else -> {
                                        _errorMessage.value = exception?.message ?: ""
                                    }

                                }
                            }
                            wordResult.onSuccess { word ->
                                _definition.value = word.value
                                _phonetics.value = word.phoneticList
                            }

                        }

                }
                .catch { resetResult() }
                .launchIn(viewModelScope)
        }
    }


    fun resetResult() {
        _isNotFoundItems.value = false
        _isLoading.value = false
        _isNetworkError.value = false
        _errorMessage.value = ""
    }
}
