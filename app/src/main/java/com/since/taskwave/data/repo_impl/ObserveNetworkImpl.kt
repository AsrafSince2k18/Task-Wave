package com.since.taskwave.data.repo_impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Handler
import android.os.Looper
import com.since.taskwave.domain.repo.network.ObserveNetwork
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ObserveNetworkImpl(
    private val context : Context
) : ObserveNetwork {

    private val networkService = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val observeNetwork: Flow<Boolean>
        get() = callbackFlow {
            val callBack = object : ConnectivityManager.NetworkCallback(){
                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(false)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(false)
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    val validateInternet = networkCapabilities.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_VALIDATED
                    )
                    trySend(validateInternet)
                }
            }
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
             networkService.registerNetworkCallback(request,callBack, Handler(Looper.getMainLooper()))

            awaitClose {
                networkService.unregisterNetworkCallback(callBack)
            }

        }

}