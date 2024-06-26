package mmcs.okleg.retrofit.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import mmcs.okleg.retrofit.databinding.FragmentHomeBinding
import mmcs.okleg.retrofit.ui.home.adapter.MyAdapter

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    private var _binding: FragmentHomeBinding? = null
    private val viewModel : HomeViewModel by viewModels { HomeViewModelFactory(requireContext()) }

    //TODO

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager = LinearLayoutManager(context)
        viewModel.getCharacters()

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.viewModelScope.launch {
            recyclerView =
                binding.recyclerView.apply {
                    myAdapter = MyAdapter(viewModel.list)
                    layoutManager = manager
                    adapter = myAdapter
                }
        }

        //Internet begin
        viewModel.viewModelScope.launch {
            viewModel.list.observe(viewLifecycleOwner) {
                myAdapter.notifyDataSetChanged()
            }
        }
//        recyclerView =
//            binding.recyclerView.apply {
//                myAdapter = MyAdapter(viewModel.list.value!!)
//                layoutManager = manager
//                adapter = myAdapter
//            }

//        val call = ApiClient.apiService.getCharacters()
//
//        call.enqueue(object : Callback<ApiModel> {
//            override fun onResponse(call: Call<ApiModel>, response: Response<ApiModel>) {
//                if (response.isSuccessful && response.body()!= null) {
//                    val list = response.body()
//                    if (list != null) {
//                        homeViewModel.list.value = list.data
//                        recyclerView =
//                            activity!!.findViewById<RecyclerView>(R.id.recycler_view).apply {
//                                myAdapter = MyAdapter(homeViewModel.list.value!!)
//                                layoutManager = manager
//                                adapter = myAdapter
//                            }
//                    }
//
//                    // Handle the retrieved post data
//                } else {
//                    // Handle error
//                    Toast.makeText(context,"Api Response Error",Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ApiModel>, t: Throwable) {
//                // Handle failure
//                Log.e(HomeFragment::class.java.simpleName, t.toString())
//                Toast.makeText(context,"Api onFailure ",Toast.LENGTH_SHORT).show()
//            }
//        })
        //Internet end
//        homeViewModel.text.observe(viewLifecycleOwner) {
//        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}