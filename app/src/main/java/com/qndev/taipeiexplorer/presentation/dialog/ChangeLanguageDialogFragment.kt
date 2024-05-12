package com.qndev.taipeiexplorer.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.qndev.taipeiexplorer.R
import com.qndev.taipeiexplorer.databinding.DialogChangeLanguageBinding

class ChangeLanguageDialogFragment : DialogFragment() {

    companion object {
        private const val TAG = "ChangeLanguageDialogFragment"
        private var onItemClickListener: ((String) -> Unit)? = null

        fun show(
            fragmentManager: FragmentManager,
            onItemClick: ((String) -> Unit)
        ) {
            val fragment = ChangeLanguageDialogFragment()
            onItemClickListener = onItemClick
            fragment.show(fragmentManager, TAG)
        }
    }

    private lateinit var binding: DialogChangeLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogChangeLanguageBinding.inflate(inflater)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListView()
    }


    private fun setUpListView() {
        val languageList = arrayOf("zh-tw", "zh-cn", "en", "ja", "ko", "es", "th", "vi")
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_language,
            R.id.languageTitle,
            languageList
        )
        binding.languageList.adapter = adapter
        binding.languageList.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val selectedLanguage = parent.getItemAtPosition(position) as String
                onItemClickListener?.invoke(selectedLanguage)
                dismissAllowingStateLoss()
            }
    }

}