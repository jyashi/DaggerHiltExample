package com.example.daggerhiltexample

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggerhiltexample.model.ApiDetailResponse
import com.example.daggerhiltexample.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repositoryInterface: RepositoryInterface,
    private val provideString: String
) : ViewModel() {
    private val _data =
        mutableStateOf(
            ApiDetailResponse(
                id = 0,
                name = "name",
                types = emptyList(),
                sprites = Any()
            )
        )
    var data: MutableState<ApiDetailResponse> = _data


    init {
        getPokemonDetails("1")
    }

    fun testString() = provideString

    fun getPokemonDetails(id: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val result = repositoryInterface.netWorkGetRequest(id).body()
            println(
                "From view model response --> $result"
            )
            
            _data.value = repositoryInterface.netWorkGetRequest(id).body()!!
        }
    }

}