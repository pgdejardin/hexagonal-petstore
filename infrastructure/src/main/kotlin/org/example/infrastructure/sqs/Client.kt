package org.example.infrastructure.sqs

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import org.example.infrastructure.pets.aws.AwsConfig
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import java.net.URI

object Client {
  private val awsConfig = ConfigFactory.load().extract<AwsConfig>("aws")
  private val clientBuilder = SqsClient.builder()
    .region(Region.EU_WEST_1)
    .credentialsProvider(AnonymousCredentialsProvider.create())

  val sqsClient: SqsClient =
    if (awsConfig.endpoint != null) clientBuilder.endpointOverride(URI.create(awsConfig.endpoint)).build() else clientBuilder.build()
}
