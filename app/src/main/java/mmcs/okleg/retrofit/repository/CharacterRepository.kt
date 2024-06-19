package mmcs.okleg.retrofit.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import mmcs.okleg.retrofit.data.api.ApiClient
import mmcs.okleg.retrofit.model.character.ApiModel
import mmcs.okleg.retrofit.model.character.Character
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface Repository{
     fun getCharacters(): Unit
}


object CharacterRepository {
    private val _list = MutableLiveData<List<Character>>().apply {
        value = emptyList()
    }
    private var instance: CharacterRepository? = null
    fun getInstance(): CharacterRepository {
       if(instance == null)
           instance = CharacterRepository
        return instance!!
    }

    var list: MutableLiveData<List<Character>> = _list
    fun getItem(characterId: Long):Character{
        val item = list.value!!.find { character -> character._id==characterId }
        if (item!= null)
            return item
        else
            return list.value!![0]
    }
     fun getCharacters( input_list: MutableLiveData<List<Character>>)
    {
        val call = ApiClient.apiService.getCharacters()
        call.enqueue(object : Callback<ApiModel> {
            override fun onResponse(call: Call<ApiModel>, response: Response<ApiModel>) {
                Log.e(CharacterRepository::class.java.simpleName, "My Log onResponse: " + response.message().toString())
                if (response.isSuccessful && response.body()!= null) {
                    val responseList = response.body()
                    if (responseList != null) {
                        input_list.value = responseList.data
                    }
                    Log.e(CharacterRepository::class.java.simpleName, "My Log isSuccessful: " + list.value.toString())

                    // Handle the retrieved model data
                } else {
                    // Handle error
                    Log.e(CharacterRepository::class.java.simpleName, "My Log isError: " + response.errorBody().toString())
                    //Toast.makeText(context,"Api Response Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiModel>, t: Throwable) {
                // Handle failure
                Log.e(CharacterRepository::class.java.simpleName,"My Log onFailure: " + t.toString())
                //Toast.makeText(context,"Api onFailure ", Toast.LENGTH_SHORT).show()
            }
        })
        list = input_list

    }
}