package be.mathias.sessiondemo.network

import be.mathias.sessiondemo.proto.SessionServiceClient
import com.squareup.wire.GrpcClient
import okhttp3.OkHttpClient
import okhttp3.Protocol

/**
 * This object (singleton) is used to access the grpcClient
 * this works the same as the REST example from the codelab
 */
object SessionApi {
    //by lazy --> delegate for lazy loading
    val grpcClient: SessionServiceClient by lazy {
        GrpcClient.Builder() //builder pattern
            .client(OkHttpClient.Builder().protocols(listOf(Protocol.H2_PRIOR_KNOWLEDGE)).build())
            .baseUrl("http://192.168.0.108:5152/") //URL to access the server
            .build().create(SessionServiceClient::class) //Returns a service that makes gRPC calls using this client.
    }
}
