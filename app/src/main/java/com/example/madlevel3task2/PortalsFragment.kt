package com.example.madlevel3task2

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_portals.*

class PortalsFragment : Fragment() {
    private val portals = arrayListOf<Portal>()
    private val portalAdapter = PortalAdapter(portals) { portal: Portal -> portalClick(portal) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        rv_portals.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_portals.adapter = portalAdapter

        observeAddPortalResult()
    }

    private fun observeAddPortalResult() {
        var portalTitle: String? = null
        setFragmentResultListener(PORTAL_REQUEST_KEY) { _, bundle ->
            bundle.getString(
                BUNDLE_PORTAL_TITLE_KEY
            )?.let {
                portalTitle = it
                bundle.getString(
                    BUNDLE_PORTAL_URL_KEY
                )?.let {
                    val newPortal = Portal(portalTitle!!, it)
                    portals.add(newPortal)
                    portalAdapter.notifyDataSetChanged()
                } ?: Log.e("PortalFragment", "Request triggered but empty portal url!")
            } ?: Log.e("PortalFragment", "Request triggered, but empty portal title!")
        }
    }

    /**
     * This function opens a custom chrome tab when the user clicks on a portal
     */
    private fun portalClick(portal: Portal) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(portal.url))
    }
}