package mmcs.okleg.retrofit.model.character

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class Character (
    @PrimaryKey(autoGenerate = true)
    var _id: Long?,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "sourceUrl")
    var sourceUrl: String?,
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String?
)
