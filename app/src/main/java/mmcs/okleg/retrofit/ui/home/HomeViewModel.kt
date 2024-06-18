package mmcs.okleg.retrofit.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mmcs.okleg.retrofit.model.ApiModel
import mmcs.okleg.retrofit.model.Character

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: MutableLiveData<String> = _text

    private val _list = MutableLiveData<List<Character>>().apply {
        value = emptyList()
    }
    val list: MutableLiveData<List<Character>> = _list
}