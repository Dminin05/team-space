package com.minin.infrastructure

import com.amazonaws.SdkClientException
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.minin.WebStarter
import com.minin.infrastructure.config.AppConfig
import com.minin.infrastructure.config.Controller
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import org.kodein.di.*

internal val controllers = DI.Module("controllers") {
    bindSet<Controller> {
    }
}

internal val s3 = DI.Module("s3") {
    bind<AmazonS3>() with singleton {
        val s3Properties = instance<AppConfig>().s3
        val amazonS3: AmazonS3
        try {
            amazonS3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                    AwsClientBuilder.EndpointConfiguration(s3Properties.serviceEndpoint, s3Properties.region)
                )
                .withCredentials(
                    AWSStaticCredentialsProvider(
                        BasicAWSCredentials(
                            s3Properties.accessKeyId,
                            s3Properties.secretAccessKey
                        )
                    )
                )
                .build()
        } catch (e: SdkClientException) {
            throw SdkClientException(e.message)
        }

        return@singleton amazonS3
    }
}

val kodein = DI {
    importOnce(s3)
    importOnce(controllers)
    bind { singleton { ConfigFactory.load().extract<AppConfig>("app") } }
    bind<WebStarter>() with singleton { WebStarter(instance(), instance()) }
}
