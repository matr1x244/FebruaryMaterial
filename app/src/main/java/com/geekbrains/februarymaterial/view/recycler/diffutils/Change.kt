package com.geekbrains.februarymaterial.view.recycler.diffutils

data class Change<out T>(
    val oldData: T,
    val newData: T
)

fun <T> createCombinePayLoads(payLoads: List<Change<T>>):Change<T>{
    val first = payLoads.first()
    val last = payLoads.last()

    return Change(first.oldData,last.newData)
}