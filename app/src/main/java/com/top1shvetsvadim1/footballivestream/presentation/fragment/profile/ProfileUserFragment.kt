package com.top1shvetsvadim1.footballivestream.presentation.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.FragmentProfileUserBinding
import com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn.User
import com.top1shvetsvadim1.footballivestream.presentation.activity.InfoActivity
import com.top1shvetsvadim1.footballivestream.presentation.activity.RegisterActivity
import com.top1shvetsvadim1.footballivestream.presentation.fragment.matchInfo.MatchMainFragment
import com.top1shvetsvadim1.footballivestream.presentation.fragment.register.ResetPasswordFragment


class ProfileUserFragment : Fragment() {


    private var _binding: FragmentProfileUserBinding? = null
    private val binding: FragmentProfileUserBinding
        get() = _binding ?: throw RuntimeException("FragmentProfileUserBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDate()
        binding.tvChangePassword.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_info_activity, ResetPasswordFromProfileFragment.newInstance())
                .commit()
        }
    }

    private fun userDate() {
        val user = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        val userId = user?.uid ?: ""
        reference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile = snapshot.getValue(User::class.java)
                if (userProfile != null) {
                    binding.tvNameProfile.text = userProfile.name
                    binding.tvEmailProfile.text = userProfile.email
                    binding.tvExit.setOnClickListener {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.exit_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                        Firebase.auth.signOut()
                        Intent(requireContext(), RegisterActivity::class.java).apply {
                            startActivity(this)
                        }
                    }
                    binding.innerToolbarMenu.setNavigationOnClickListener {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Something wrong happende", Toast.LENGTH_SHORT).show()
            }
        })
    }



    companion object {

        @JvmStatic
        fun newInstance() = ProfileUserFragment()
    }
}