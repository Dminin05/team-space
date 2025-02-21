package com.minin.infrastructure

import com.amazonaws.SdkClientException
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.minin.WebStarter
import com.minin.app.controller.*
import com.minin.app.repository.*
import com.minin.app.service.*
import com.minin.infrastructure.config.AppConfig
import com.minin.infrastructure.config.Controller
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import org.kodein.di.*

internal val controllers = DI.Module("controllers") {
    bindSet<Controller> {
        bind { singleton { AuthController(instance()) } }
        bind { singleton { CompanyController(instance()) } }
        bind { singleton { WorkspaceController(instance()) } }
        bind { singleton { InvitationController(instance(), instance()) } }
        bind { singleton { ProjectController(instance()) } }
    }
}

internal val repositories = DI.Module("repositories") {
    bind<UserRepository> { singleton { UserRepositoryImpl() } }
    bind<CompanyRepository> { singleton { CompanyRepositoryImpl() } }
    bind<WorkspaceRepository> {  singleton { WorkspaceRepositoryImpl() } }
    bind<InvitationRepository> {  singleton { InvitationRepositoryImpl() } }
    bind<ProjectRepository> {  singleton { ProjectRepositoryImpl() } }
}


internal val services = DI.Module("services") {
    bind<AuthService> { singleton { AuthServiceImpl(instance(), instance(), instance()) } }
    bind<CompanyService> { singleton { CompanyServiceImpl(instance(), instance()) } }
    bind<WorkspaceService> { singleton { WorkspaceServiceImpl(instance()) } }
    bind<InvitationService> { singleton { InvitationServiceImpl(instance(), instance(), instance()) } }
    bind<MailSender> { singleton { MailSenderImpl(instance()) } }
    bind<ProjectService> { singleton { ProjectServiceImpl(instance()) } }
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
    importOnce(services)
    importOnce(repositories)
    bind { singleton { ConfigFactory.load().extract<AppConfig>("app") } }
    bind<WebStarter>() with singleton { WebStarter(instance(), instance()) }
}
