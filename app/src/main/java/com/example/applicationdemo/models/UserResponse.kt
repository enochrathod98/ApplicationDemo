package com.example.applicationdemo.models

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("skip")
    val skip: Int? = null,

    @field:SerializedName("users")
    val users: List<UsersItem>
)

data class Bank(

    @field:SerializedName("iban")
    val iban: String? = null,

    @field:SerializedName("cardExpire")
    val cardExpire: String? = null,

    @field:SerializedName("cardType")
    val cardType: String? = null,

    @field:SerializedName("currency")
    val currency: String? = null,

    @field:SerializedName("cardNumber")
    val cardNumber: String? = null
)

data class Crypto(

    @field:SerializedName("wallet")
    val wallet: String? = null,

    @field:SerializedName("coin")
    val coin: String? = null,

    @field:SerializedName("network")
    val network: String? = null
)

data class Hair(

    @field:SerializedName("color")
    val color: String? = null,

    @field:SerializedName("type")
    val type: String? = null
)

data class Coordinates(

    @field:SerializedName("lng")
    val lng: Any? = null,

    @field:SerializedName("lat")
    val lat: Any? = null
)

data class Company(

    @field:SerializedName("address")
    val address: Address? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("department")
    val department: String? = null,

    @field:SerializedName("title")
    val title: String? = null
)

data class UsersItem(

    @field:SerializedName("lastName")
    val lastName: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("university")
    val university: String? = null,

    @field:SerializedName("maidenName")
    val maidenName: String? = null,

    @field:SerializedName("ein")
    val ein: String? = null,

    @field:SerializedName("ssn")
    val ssn: String? = null,

    @field:SerializedName("bloodGroup")
    val bloodGroup: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("hair")
    val hair: Hair? = null,

    @field:SerializedName("bank")
    val bank: Bank? = null,

    @field:SerializedName("eyeColor")
    val eyeColor: String? = null,

    @field:SerializedName("company")
    val company: Company? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("height")
    val height: Int? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("address")
    val address: Address? = null,

    @field:SerializedName("ip")
    val ip: String? = null,

    @field:SerializedName("weight")
    val weight: Any? = null,

    @field:SerializedName("userAgent")
    val userAgent: String? = null,

    @field:SerializedName("birthDate")
    val birthDate: String? = null,

    @field:SerializedName("crypto")
    val crypto: Crypto? = null,

    @field:SerializedName("firstName")
    val firstName: String? = null,

    @field:SerializedName("macAddress")
    val macAddress: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("domain")
    val domain: String? = null,

    @field:SerializedName("age")
    val age: Int? = null,

    @field:SerializedName("username")
    val username: String? = null
)

data class Address(

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("postalCode")
    val postalCode: String? = null,

    @field:SerializedName("coordinates")
    val coordinates: Coordinates? = null,

    @field:SerializedName("state")
    val state: String? = null
)
