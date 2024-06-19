package mmcs.okleg.retrofit.model.character

data class ApiModel (
    var data: List<Character>
)

data class Character(
    var _id: Long,
    var sourceUrl: String,
    var name: String,
    var imageUrl: String

)