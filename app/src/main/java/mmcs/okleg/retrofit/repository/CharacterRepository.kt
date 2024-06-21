package mmcs.okleg.retrofit.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import mmcs.okleg.retrofit.data.api.ApiClient
import mmcs.okleg.retrofit.data.room.AppDatabase
import mmcs.okleg.retrofit.data.room.CharacterDao
import mmcs.okleg.retrofit.model.character.Character
import mmcs.okleg.retrofit.model.character.CharacterApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class CharacterRepository(private val context: Context) {
    private val _list = MutableLiveData<List<Character>>().apply {
        value = emptyList()
    }
    val list: MutableLiveData<List<Character>> = _list
    companion object : SingletonHolder<CharacterRepository, Context>(::CharacterRepository)

    private val characterDao = AppDatabase.getDatabase(context).characterDao()
    fun getItem(characterId: Long):Character {
        // if not found return first
        return list.value!!.find { character -> character._id == characterId } ?: list.value!![0]
    }

    suspend fun getCharactersFromRoom() {
        list.postValue(characterDao.getAll())
    }

    suspend fun insertCharacter(character : Character) =  characterDao.insertAll(character)
    suspend fun insertCharactersInRoom(){list.value!!.forEach {characterDao.insertAll(it)  }}

    suspend fun getCharactersFromApi() : Boolean
    {
        val call = ApiClient.apiService.getCharacters()
        return suspendCoroutine { cont ->
            call.enqueue(object : Callback<CharacterApiModel> {
                override fun onResponse(
                    call: Call<CharacterApiModel>,
                    response: Response<CharacterApiModel>
                ) {
                    Log.e(
                        CharacterRepository::class.java.simpleName,
                        "My Log onResponse: " + response.message().toString()
                    )
                    if (response.isSuccessful && response.body() != null) {
                        val responseList = response.body()
                        if (responseList != null) {
                            list.postValue(responseList.data)
                            Log.i(
                                CharacterRepository::class.java.simpleName,
                                "MyLogSuccessful:" + list.value!!
                            )
                            cont.resume(true)
                            //list.value!!.forEach { characterDao.insertAll(it) }
//                        characterDao.insertAll(*list.value!!.toTypedArray())
                        }
                        Log.e(
                            CharacterRepository::class.java.simpleName,
                            "MyLogSuccessful/isNullOrEmpty:" + list.value.isNullOrEmpty()
                        )
                        // Handle the retrieved model data
                    } else {
                        // Handle error
                        Log.e(
                            CharacterRepository::class.java.simpleName,
                            "MyLogError: " + response.errorBody().toString()
                        )
                        //list.value = characterDao.getAll()

                        Toast.makeText(context, "Api Response Error", Toast.LENGTH_SHORT).show()
                        cont.resume(false)

                    }
                }

                override fun onFailure(call: Call<CharacterApiModel>, t: Throwable) {
                    // Handle failure
                    Log.e(
                        CharacterRepository::class.java.simpleName,
                        "MyLogFailure: " + t.toString()
                    )
//                    list.value = characterDao.getAll()

                    Toast.makeText(context, "Api Connect Error", Toast.LENGTH_SHORT).show()
                    cont.resume(false)

                }
            })
        }
    }
}