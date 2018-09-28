package ru.pyrovsergey.news.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.presenter.FragmentPresenter
import ru.pyrovsergey.news.presenter.FragmentView

class CategoryPageFragment : MvpAppCompatFragment(), FragmentView {

    @InjectPresenter
    lateinit var presenter: FragmentPresenter

    private var mPage: Int = 0
//    private var data: Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        data = Data.getDataInstance(context)
//        if (arguments != null) {
//            mPage = arguments!!.getInt(ARG_PAGE)
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val recyclerView = inflater.inflate(R.layout.category_list, container, false) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
//        recyclerView.adapter = PlaceListItemRecyclerAdapter(getItemPlaceList(mPage), context)

        return recyclerView
    }

//    private fun getItemPlaceList(mPage: Int): ArrayList<Place>? {
//        if (mPage == 1) {
//            return data!!.visitList
//        }
//        if (mPage == 2) {
//            return data!!.eatList
//        }
//        if (mPage == 3) {
//            return data!!.clubList
//        }
//        return if (mPage == 4) {
//            data!!.hotelList
//        } else null
//    }

    companion object {

        const val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): CategoryPageFragment {
            val bundle = Bundle()
            bundle.putInt(ARG_PAGE, page)
            val fragment = CategoryPageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}