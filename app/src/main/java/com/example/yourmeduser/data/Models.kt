package com.example.yourmeduser.data

import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.parcel.Parcelize


data class CareAdmin(
    var uId: String? = "",
    var name: String? = "",
    var phone: Long = 0L,
    var email: String = "",
    var license: String ="",
    var document: String = "",
    var country: String ="",
    var town: String = "",
    var coordinate: GeoPoint? = null,
    var serviceOffered: String = "",
    var details: String ="",

    )

@Parcelize
data class Service(
    var serviceId: String = "",
    var serviceName: String? = "",
    var identifier : String? = "",
    var serviceDetails: String ="",
    var price: Double =0.0,
    var priceDescription: String = "",
    var availability: String = "",
    var provider: CareAdmin? = null

): Parcelable

@Parcelize
data class Medicine(
    var scientificName: String? = "",
    var genericName: String = "",
    var countryMade: String = "",
    var detailsMed: String = "",
    var availability: String = "",
    var medUid: String = "",
    var price: Double = 0.0,
    var provider: CareAdmin? = null
): Parcelable

data class QueryResults(
    var status: String = "",
    var statusValue: String = "",
    var medicines: MutableList<Medicine> = emptyList<Medicine>() as MutableList<Medicine>,
    var services: MutableList<Service> = emptyList<Service>() as MutableList<Service>


)