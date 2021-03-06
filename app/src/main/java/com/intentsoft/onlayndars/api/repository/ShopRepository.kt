package com.intentsoft.onlayndars.api.repository

import androidx.lifecycle.MutableLiveData
import com.intentsoft.onlayndars.api.NetvorkManager
import com.intentsoft.onlayndars.model.BaseResposne
import com.intentsoft.onlayndars.model.CategoryModel
import com.intentsoft.onlayndars.model.OfferModel
import com.intentsoft.onlayndars.model.ProductModel
import com.intentsoft.onlayndars.model.request.GetProductsByIdsRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ShopRepository {

    val compasiteDisposable = CompositeDisposable()

    fun getOffers(error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<List<OfferModel>>){
         progress.value = true
         compasiteDisposable.add(NetvorkManager
                 .getApiService().getOffers()
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeWith(object : DisposableObserver<BaseResposne<List<OfferModel>>>(){
                 override fun onNext(t: BaseResposne<List<OfferModel>>) {
                     progress.value = false
                     if (t.success) {
                         success.value = t.data
                     }else{
                         error.value = t.message
                     }
                 }

                 override fun onError(e: Throwable) {
                     progress.value = true
                     error.value = e.localizedMessage
                 }

                 override fun onComplete() {
                 }
             })
         )


     }
    fun getCattegories(error: MutableLiveData<String>,success: MutableLiveData<List<CategoryModel>>){
         compasiteDisposable.add(NetvorkManager
             .getApiService().getCategories()
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeWith(object : DisposableObserver<BaseResposne<List<CategoryModel>>>(){
                 override fun onNext(t: BaseResposne<List<CategoryModel>>) {
                     if (t.success) {
                         success.value = t.data
                     }else{
                         error.value = t.message
                     }
                 }

                 override fun onError(e: Throwable) {
                     error.value = e.localizedMessage
                 }

                 override fun onComplete() {
                 }
             })
         )
     }
    fun getTopProducts(error: MutableLiveData<String>,success: MutableLiveData<List<ProductModel>>){
         compasiteDisposable.add(NetvorkManager
             .getApiService().getTopProduct()
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeWith(object : DisposableObserver<BaseResposne<List<ProductModel>>>(){
                 override fun onNext(t: BaseResposne<List<ProductModel>>) {
                     if (t.success) {
                         success.value = t.data
                     }else{
                         error.value = t.message
                     }
                 }

                 override fun onError(e: Throwable) {
                     error.value = e.localizedMessage
                 }

                 override fun onComplete() {
                 }
             })
         )
     }
    fun getProductsByCategory(id: Int,error: MutableLiveData<String>,success: MutableLiveData<List<ProductModel>>){
         compasiteDisposable.add(NetvorkManager
             .getApiService().getCategoryProduct(id)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeWith(object : DisposableObserver<BaseResposne<List<ProductModel>>>(){
                 override fun onNext(t: BaseResposne<List<ProductModel>>) {
                     if (t.success) {
                         success.value = t.data
                     }else{
                         error.value = t.message
                     }
                 }

                 override fun onError(e: Throwable) {
                     error.value = e.localizedMessage
                 }

                 override fun onComplete() {
                 }
             })
         )
     }
    fun getProductsById(ids: List<Int>, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<List<ProductModel>>){
        progress.value = true
         compasiteDisposable.add(NetvorkManager
             .getApiService().getProductsByIds(GetProductsByIdsRequest(ids))
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeWith(object : DisposableObserver<BaseResposne<List<ProductModel>>>(){
                 override fun onNext(t: BaseResposne<List<ProductModel>>) {
                     progress.value = false
                     if (t.success) {
                         success.value = t.data
                     }else{
                         error.value = t.message
                     }
                 }

                 override fun onError(e: Throwable) {
                     error.value = e.localizedMessage
                     progress.value = true
                 }

                 override fun onComplete() {
                 }
             })
         )
     }
 }