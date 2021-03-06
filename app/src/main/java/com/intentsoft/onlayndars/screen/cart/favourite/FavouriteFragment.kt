package com.intentsoft.onlayndars.screen.cart.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.intentsoft.onlayndars.R
import com.intentsoft.onlayndars.screen.cart.MainViewModel
import com.intentsoft.onlayndars.utils.PrefUtils
import com.intentsoft.onlayndars.view.ProductAdapter
import kotlinx.android.synthetic.main.fragment_favourite.*

class FavouriteFragment : Fragment() {

    lateinit var viewMdel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         viewMdel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewMdel.productsData.observe(this, Observer {
            recyclerProduct.adapter = ProductAdapter(it)
        })

        viewMdel.error.observe(this, Observer {
            Toast.makeText(requireActivity(), "Internet tekshiring!", Toast.LENGTH_SHORT).show()
        })

        viewMdel.progress.observe(this, Observer {
            swipeCart.isRefreshing = it
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerProduct.layoutManager = LinearLayoutManager(requireActivity())
        swipeCart.setOnRefreshListener {
            loadData()
        }
        loadData()
    }
    fun loadData() {
        viewMdel.getProductsByIds(PrefUtils.getFavoritesList() )
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteFragment()
    }
}