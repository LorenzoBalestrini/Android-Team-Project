package co.develhope.meteoapp.searchscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.FragmentSearchScreenBinding
import co.develhope.meteoapp.homescreen.DataSource
import co.develhope.meteoapp.searchscreen.SearchSource.getSearchCitiesList
import kotlinx.coroutines.launch

class SearchScreen : Fragment() {

    private lateinit var binding: FragmentSearchScreenBinding
    val adapter = SearchAdapter(
        getSearchCitiesList()


    ) {
        DataSource.setSelectedCity(it)
        findNavController().navigate(R.id.searchScreenToHomeScreen)
    }
    private lateinit var searchView: SearchView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchScreenBinding.inflate(inflater, container, false)
        binding.search.layoutManager = LinearLayoutManager(requireContext())
        binding.search.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retryCall()

        //  searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        //      override fun onQueryTextSubmit(query: String?): Boolean {
        //          // on below line we are checking
        //          // if query exist or not.
        //          if () {
        //              // if query exist within list we
        //              // are filtering our list adapter.
        //              adapter.filter().filter(query)
        //          } else {
        //              // if query is not present we are displaying
        //              // a toast message as no  data found..
        //              Toast.makeText(
        //                  requireContext(),
        //                  "Not found ...",
        //                  Toast.LENGTH_LONG
        //              ).show()
        //          }
        //          return false
        //      }

        //      override fun onQueryTextChange(newText: String?): Boolean {
        //          // if query text is change in that case we
        //          // are filtering our adapter with
        //          // new text on below line.
        //          adapter.filter().filter(newText)
        //          return false
        //      }
        //  })
    }


    fun retryCall() {


        viewLifecycleOwner.lifecycleScope.launch {
            try {
                RetrofitInstance().getHourlyForecast()

            } catch (e: Exception) {

                Log.d("SearchScreen", "${e.message}, ${e.cause}")

             //  Toast.makeText(
             //      requireContext(),
             //      "Error",
             //      Toast.LENGTH_LONG
             //  ).show()

            }
        }
    }
}