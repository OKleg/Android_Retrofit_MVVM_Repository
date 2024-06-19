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


class CharacterRepository() {
//    private val _list = MutableLiveData<List<Character>>().apply {
//        value = emptyList()
//    }
    //val list: MutableLiveData<List<Character>> = _list

     fun getCharacters(list: MutableLiveData<List<Character>>)
    {
        val call = ApiClient.apiService.getCharacters()
        call.enqueue(object : Callback<ApiModel> {
            override fun onResponse(call: Call<ApiModel>, response: Response<ApiModel>) {
                Log.e(CharacterRepository::class.java.simpleName, "My Log onResponse: " + response.message().toString())
                if (response.isSuccessful && response.body()!= null) {
                    val responseList = response.body()
                    if (responseList != null) {
                        list.value = responseList.data
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
        return
    }
}