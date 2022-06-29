package com.top1shvetsvadim1.footballivestream.presentation.fragment.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.top1shvetsvadim1.footballivestream.R
import com.top1shvetsvadim1.footballivestream.databinding.FragmentRegisterBinding
import com.top1shvetsvadim1.footballivestream.presentation.Process
import com.top1shvetsvadim1.footballivestream.presentation.viewModel.RegisterViewModel
import com.top1shvetsvadim1.footballivestream.presentation.Register
import com.top1shvetsvadim1.footballivestream.presentation.activity.InfoActivity
import java.lang.RuntimeException


class SingUpFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        viewModelObserves()
        myOnBackPressed()
    }

    private fun viewModelObserves() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is Register -> {
                    if (it.isSuccess) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.succesfull_singup),
                            Toast.LENGTH_SHORT
                        ).show()
                        Intent(requireContext(), InfoActivity::class.java).apply {
                            startActivity(this)
                        }
                    }
                }
                is Process -> {
                    if (it.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else if (!it.isLoading) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.email_already_registered),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.errorInputPassword.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.less_characters)
            } else {
                null
            }
            binding.editTextPassword.error = message
        }
        viewModel.errorInputEmail.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.invalid_email_or_already_registered)
            } else {
                null
            }
            binding.editTextEmail.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner){
            val message = if(it){
                getString(R.string.the_field_is_empty)
            } else {
                null
            }
            binding.editTextName.error = message
        }
    }

    private fun setListeners() {
        binding.tvHaveAnAccount.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_main_activity, LogInFragment.newInstance())
                .addToBackStack("Login")
                .commit()
        }
        binding.buttonRegister.setOnClickListener {
            viewModel.validateInputRegister(
                binding.editTextName.text.toString().trim(),
                binding.editTextEmail.text.toString().trim(),
                binding.editTextPassword.text.toString().trim()
            )
            if (binding.editTextName.text.toString().trim().isNotEmpty() &&
                binding.editTextEmail.text.toString().trim().isNotEmpty() &&
                binding.editTextPassword.text.toString().trim().isNotEmpty()
            ) {
                viewModel.createNewAccount(
                    requireActivity(),
                    binding.editTextName.text.toString().trim(),
                    binding.editTextEmail.text.toString().trim(),
                    binding.editTextPassword.text.toString().trim()
                )
            }
        }
    }

    private fun myOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    companion object {

        @JvmStatic
        fun newInstance() = SingUpFragment()
    }
}