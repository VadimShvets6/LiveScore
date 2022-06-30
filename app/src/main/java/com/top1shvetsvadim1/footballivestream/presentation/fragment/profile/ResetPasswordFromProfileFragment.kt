package com.top1shvetsvadim1.footballivestream.presentation.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.FragmentResetPasswordFromProfileBinding
import com.top1shvetsvadim1.footballivestream.presentation.ResetPassword
import com.top1shvetsvadim1.footballivestream.presentation.fragment.matchInfo.MatchMainFragment
import com.top1shvetsvadim1.footballivestream.presentation.fragment.register.LogInFragment
import com.top1shvetsvadim1.footballivestream.presentation.viewModel.RegisterViewModel


class ResetPasswordFromProfileFragment : Fragment() {
    private var _binding: FragmentResetPasswordFromProfileBinding? = null
    private val binding: FragmentResetPasswordFromProfileBinding
        get() = _binding ?: throw RuntimeException("FragmentResetPasswordFromProfileBinding == null")


    private val viewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordFromProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListeners()
        viewModelObserves()
        myOnBackPressed()
    }

    private fun onClickListeners() {
        binding.buttonBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container_info_activity, MatchMainFragment.newInstance()
            ).commit()
        }
        binding.buttonReset.setOnClickListener {
            viewModel.resetPassword(requireActivity(), binding.editTextEmail.text.toString().trim())
        }
    }

    override fun onPause() {
        super.onPause()
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun viewModelObserves() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ResetPassword -> {
                    if (it.isReset) {
                        Firebase.auth.signOut()
                        Toast.makeText(
                            requireContext(),
                            "Письмо для сброса пароля отправлено вам на почту",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (!it.isReset) {
                        Toast.makeText(
                            requireContext(),
                            "Возникла ошибка!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Возникла ошибка!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun myOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_info_activity, ProfileUserFragment.newInstance())
                    .commit()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ResetPasswordFromProfileFragment()
    }
}