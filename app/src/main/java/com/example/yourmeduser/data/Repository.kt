package com.example.yourmeduser.data

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class Repository {var mFirebaseAuth: FirebaseAuth
    var mFirebaseDb : FirebaseFirestore
    // var mFirebaseStore: FirebaseStorage

    init {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseDb = FirebaseFirestore.getInstance()
        // mFirebaseStore = FirebaseStorage.getInstance()

    }

    fun getServices(country: String, name: String): MutableLiveData<QueryResults>{
        var queryResults = MutableLiveData<QueryResults>()
        var myResults = QueryResults()
         val serviceRef = mFirebaseDb.collectionGroup("service")

        serviceRef.whereEqualTo("careAdmin.country", country)
                  .whereEqualTo("serviceName", name)
                  .get()
            .addOnSuccessListener {

                if(!it.isEmpty) {
                    myResults.status = "success"
                    myResults.statusValue = "success"
                    for (snapshot in it) {
                        val service = snapshot.toObject(Service::class.java)

                        myResults.services.add(service)

                    }
                }else{
                    myResults.status = "success"
                    myResults.statusValue = "failed"
                }
                queryResults.postValue(myResults)
            }.addOnFailureListener{
                myResults.status = "failed"
                myResults.statusValue = it.toString()

                queryResults.postValue(myResults)
            }

        return queryResults
    }




    fun getProduct(country:  String, scientificName: String, genericName: String ) : MutableLiveData<QueryResults>{

        var queryResults = MutableLiveData<QueryResults>()
        var myResults = QueryResults()
        val tak1 = mFirebaseDb.collectionGroup("medicine").
                whereEqualTo("CareAdmin.country", country).
                whereEqualTo("scientificName",scientificName ).
                get()

        val tak2 = mFirebaseDb.collectionGroup("medicine").
                    whereEqualTo("CareAdmin.country", country).
                     whereEqualTo("genericName",genericName ).
                     get()

        val allData = Tasks.whenAllSuccess<List<QuerySnapshot>>(tak1, tak2)
        allData.addOnCompleteListener(OnCompleteListener {

                    if (  it.isSuccessful  ){
                        for(query in it.result!!){
                            if (!query.isEmpty()){
                                myResults.status = "success"
                                myResults.statusValue = "success"
                                for(document in query){
                                    for (snapshot in document){
                                        val medicine = snapshot.toObject(Medicine::class.java)
                                        myResults.medicines.add(medicine)
                                    }

                                }
                            }else{
                                myResults.status = "success"
                                myResults.statusValue = "failed"
                            }

                        }

                    }else{
                        myResults.status = "failed"
                        myResults.statusValue = it.exception.toString()
                    }

            queryResults.postValue(myResults)
        }).addOnFailureListener{
            myResults.status = "failed"
            myResults.statusValue = it.toString()
            queryResults.postValue(myResults)
        }

        return queryResults

    }

}