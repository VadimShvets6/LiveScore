package com.top1shvetsvadim1.footballivestream.presentation.fragment.matchInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.FragmentMatchMainBinding
import com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn.User
import com.top1shvetsvadim1.footballivestream.presentation.adapters.MatchInfoAdapter
import com.top1shvetsvadim1.footballivestream.presentation.fragment.profile.ProfileUserFragment
import com.top1shvetsvadim1.footballivestream.presentation.viewModel.InfoViewModel
import java.util.*

class MatchMainFragment : Fragment() {


    private var _binding: FragmentMatchMainBinding? = null
    private val binding: FragmentMatchMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMatchMainBinding == null")


    private val viewModel by lazy {
        ViewModelProvider(this)[InfoViewModel::class.java]
    }

    private lateinit var listAdapter: MatchInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModelMethods()
        viewModelObserves()
        setupOnClickListeners()
        userDate()
        myOnBackPressed()
    }

    private fun userDate() {
        val user = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        val userId = user?.uid ?: ""
        reference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile = snapshot.getValue(User::class.java)
                if (userProfile != null) {
                    binding.tvLabelWelcome.text = String.format(
                        Locale.getDefault(),
                        getString(R.string.welcome),
                        userProfile.name
                    )
                    binding.tvNameUser.text = userProfile.name
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Something wrong happende", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupOnClickListeners(){
        listAdapter.onMatchItemClickListener = {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_info_activity, DetailMatchInfoFragment.newInstance(it.matchviewUrl))
                .addToBackStack(null)
                .commit()
        }
        binding.ivIconProfile.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_info_activity, ProfileUserFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun myOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                    requireActivity().moveTaskToBack(true)
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    private fun viewModelObserves(){
        viewModel.matchInformation.observe(viewLifecycleOwner){
            listAdapter.submitList(it)
        }
    }

    private fun viewModelMethods(){
        viewModel.downloadingMatchData()
    }

    private fun setupRecyclerView() {
        with(binding.rvMatchInfo) {
            listAdapter = MatchInfoAdapter()
            adapter = listAdapter
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = MatchMainFragment()
    }
}