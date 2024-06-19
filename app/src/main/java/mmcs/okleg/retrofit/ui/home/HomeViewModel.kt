package mmcs.okleg.retrofit.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mmcs.okleg.retrofit.model.character.Character
import mmcs.okleg.retrofit.repository.CharacterRepository


class HomeViewModel(private val repository: CharacterRepository) : ViewModel() {


    private val _list = MutableLiveData<List<Character>>().apply {
        value = emptyList()
    }
    val list: MutableLiveData<List<Character>> = _list

      fun getCharacters(){
        viewModelScope.launch {
            repository.getCharacters(list)
        }
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val repository = CharacterRepository.getInstance()
            return HomeViewModel(repository) as T
    }
}

//class NoteViewModelFactory( private val repository: CharacterRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return HomeViewModel( repository) as T
//    }
//}
//companion object {
//    val Factory: ViewModelProvider.Factory = viewModelFactory {
//        initializer {
//            val characterRepository = CharacterRepository
//            HomeViewModel(CharacterRepository = marsPhotosRepository)
//        }
//    }
//}