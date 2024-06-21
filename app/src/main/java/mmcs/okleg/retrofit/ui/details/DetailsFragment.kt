package mmcs.okleg.retrofit.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import mmcs.okleg.retrofit.R
import mmcs.okleg.retrofit.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val viewModel : DetailsViewModel by viewModels { DetailsViewModelFactory(requireContext()) }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val id = arguments?.getLong("id",112)!!
        viewModel.setDetaisView(id)

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tvName: TextView = binding.tvTitle
        val tvSourceUrl: TextView = binding.tvSourseUrl
        val imageView: ImageView = binding.imageView
        val btnBack: Button = binding.btnBack
        btnBack.setOnClickListener {
            it.findNavController().navigate(R.id.navigation_home)
        }

        viewModel.character.observe(viewLifecycleOwner) {
            tvName.text = it.name
            tvSourceUrl.text = it.sourceUrl
            Glide.with(requireView().context).load(it.imageUrl).into(imageView)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}