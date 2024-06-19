package mmcs.okleg.retrofit.ui.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mmcs.okleg.retrofit.repository.CharacterRepository
import mmcs.okleg.retrofit.ui.home.HomeViewModel
import mmcs.okleg.retrofit.model.character.Character

class DetailsViewModel(private val repository: CharacterRepository) : ViewModel() {


    var character = MutableLiveData<Character>()

     fun setDetaisView(id : Long){
         character.value = repository.getItem((id))
     }
}

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = CharacterRepository.getInstance()
        return DetailsViewModel(repository) as T
    }
}