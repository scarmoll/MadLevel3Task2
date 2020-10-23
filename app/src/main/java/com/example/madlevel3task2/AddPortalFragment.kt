package com.example.madlevel3task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_portal.*

const val PORTAL_REQUEST_KEY = "req_portal"
const val BUNDLE_PORTAL_TITLE_KEY = "bundle_portal_title"
const val BUNDLE_PORTAL_URL_KEY = "bundle_portal_url"

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddPortalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_portal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_add_portal.setOnClickListener {
            addPortal()
        }
    }

    private fun addPortal() {
        val title = ti_title.text.toString()
        val url = ti_url.text.toString()

        if (title.isNotBlank() && url.isNotBlank()) {
            setFragmentResult(
                PORTAL_REQUEST_KEY, bundleOf(
                    Pair(BUNDLE_PORTAL_TITLE_KEY, title), Pair(
                        BUNDLE_PORTAL_URL_KEY, url
                    )
                )
            )

            //"pop" the backstack, this means we destroy
            //this fragment and go back to the RemindersFragment
            findNavController().popBackStack()

        } else {
            Toast.makeText(
                activity,
                R.string.not_valid_reminder, Toast.LENGTH_SHORT
            ).show()
        }
    }

}