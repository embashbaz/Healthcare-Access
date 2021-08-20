package com.example.yourmeduser.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourmeduser.data.InDemand
import com.example.yourmeduser.data.QueryResults
import com.example.yourmeduser.data.Repository

class SearchViewModel: ViewModel() {

    val repository = Repository()

    private var _resultFromQuery = MutableLiveData<QueryResults>()
    val resultFromQuery : LiveData<QueryResults>
          get() = _resultFromQuery
    private var _addingServiceOutput = MutableLiveData<HashMap<String, String>>()
    val addingServiceOutput: LiveData<HashMap<String, String>>
        get() = _addingServiceOutput


    fun getData(country:  String, scientificName: String, genericName: String, type: Int ){
        if(type == 1){
            _resultFromQuery = repository.getProduct(country, scientificName, genericName)
        }else if(type == 2){
            _resultFromQuery = repository.getServices(country, scientificName)
        }

    }

    fun addToInDemand(inDemand: InDemand, deviceId: String, contact: String, type: Int){
        if(type == 1){
            _addingServiceOutput = repository.addMedicineToInDemand(inDemand, deviceId, contact)
        }else if(type == 2){
            _addingServiceOutput = repository.addServiceToInDemand(inDemand, deviceId, contact)
        }
    }

}