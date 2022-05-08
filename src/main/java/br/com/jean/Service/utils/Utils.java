package br.com.jean.Service.utils;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SesException;


public class Utils {
	
	public static AwsCredentialsProvider getAWSCredentials() {
		return new AwsCredentialsProvider() {
			@Override
			public AwsCredentials resolveCredentials() {
				return new AwsCredentials() {
					@Override
					public String accessKeyId() {
						return System.getenv("AWS_ACCESS_KEY");
					}

					@Override
					public String secretAccessKey() {
						return System.getenv("AWS_SECRET_KEY");
					}
				};
			}
		};
	}
		
	public static SesClient getSesClient() {
		try {
			AwsCredentialsProvider credentialsProvider = getAWSCredentials();
			return SesClient.builder().region(Region.US_EAST_1).credentialsProvider(credentialsProvider).build();
		} catch (SesException ex) {
			System.err.println(ex.awsErrorDetails().errorMessage());
			return null;
		}
	}
	
	
}
