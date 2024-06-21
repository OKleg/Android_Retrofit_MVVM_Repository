package mmcs.okleg.retrofit.ui.home.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mmcs.okleg.retrofit.R
import mmcs.okleg.retrofit.model.character.Character

class MyAdapter(private val data: MutableLiveData<List<Character>>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun bind(property: Character){
            val title = view.findViewById<TextView>(R.id.tvTitle)
            val imageView = view.findViewById<ImageView>(R.id.imageView)
           // val description = view.findViewById<TextView>(R.id.tvDescription)

            title.text = property.name
            //description.text = property.sourceUrl

            Glide.with(view.context).load(property.imageUrl).centerCrop().into(imageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false)
        val holder = MyViewHolder(v)
        holder.itemView.setOnClickListener {
            val id = data.value!![holder.bindingAdapterPosition]._id
            val args = Bundle()
            if (id != null)
                args.putLong("id",id)
            it.findNavController().navigate(R.id.navigation_details,args)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return if (data.value !=null)  data.value!!.size
        else 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data.value!![position] )
    }


}