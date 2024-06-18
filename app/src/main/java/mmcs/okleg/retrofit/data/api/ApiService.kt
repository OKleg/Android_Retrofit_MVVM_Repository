package mmcs.okleg.retrofit.data.api

import mmcs.okleg.retrofit.model.ApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
//    @GET("posts/{id}")
//    fun getPostById(@Path("id") postId: Int): Call<ApiModel>
//    @GET("posts")
//    fun getPosts(): Call<List<ApiModel>>
    @GET("character")
    fun getCharacters(): Call<ApiModel>
}