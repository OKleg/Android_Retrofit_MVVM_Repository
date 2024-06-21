package mmcs.okleg.retrofit.data.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mmcs.okleg.retrofit.model.character.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg characters: Character)
    @Query("SELECT * FROM character")
    suspend fun getAll(): List<Character>

    @Query("SELECT * FROM character")
    fun getAllLiveData(): LiveData<List<Character>>
}