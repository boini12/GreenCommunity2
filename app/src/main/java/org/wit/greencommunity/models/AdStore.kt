package org.wit.greencommunity.models

interface AdStore {
    fun findAll(): List<AdModel>
    fun create(ad: AdModel)
    fun update(ad: AdModel)
}