package com.rci.omega2.ejb.bo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.rci.omega2.ejb.exception.UnexpectedException;

@Singleton
public class S3AccessBO extends BaseBO{

    
    private static final Logger LOGGER = LogManager.getLogger(S3AccessBO.class);
    
    @EJB
    private ConfigFileBO configFile;
    
    /* 
    * @param key
    * @return
    * @throws Exception
    */
   public InputStream getObjectFromS3(String key) throws UnexpectedException {
       LOGGER.debug(" >> INIT getObjectFromS3 ");
       
       BasicAWSCredentials awsCreds = new BasicAWSCredentials(configFile.getProperty("amazon.s3.access.key"), configFile.getProperty("amazon.s3.access.secret.key"));
       AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                               .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                               .withRegion(Regions.US_EAST_1)
                               .build();
       
       InputStream temp = s3Client.getObject(configFile.getProperty("amazon.s3.bucket"), configFile.getProperty("amazon.s3.file.prefix.name") + key).getObjectContent();
       
       LOGGER.debug(" >> END getObjectFromS3 ");
       return temp;
   }
   
   /**
    * 
    * @param key
    * @param file
    * @return
    * @throws Exception
    */
   public PutObjectResult putObjectFromS3(String key, File file) throws UnexpectedException {
       LOGGER.debug(" >> INIT putObjectFromS3 ");
       
       BasicAWSCredentials awsCreds = new BasicAWSCredentials(configFile.getProperty("amazon.s3.access.key"), configFile.getProperty("amazon.s3.access.secret.key"));
       AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                               .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                               .withRegion(Regions.US_EAST_1)
                               .build();
       PutObjectResult temp =  s3Client.putObject(configFile.getProperty("amazon.s3.bucket"), configFile.getProperty("amazon.s3.file.prefix.name") + key, file);
       
       LOGGER.debug(" >> END putObjectFromS3 ");
       return temp;
   }
   
   /**
    * 
    * @param key
    * @param file
    * @return
    * @throws Exception
    */
   public PutObjectResult putObjectFromS3(String key, String imgBase64) throws UnexpectedException {
       LOGGER.debug(" >> INIT putObjectFromS3 ");
       
       BasicAWSCredentials awsCreds = new BasicAWSCredentials(configFile.getProperty("amazon.s3.access.key"), configFile.getProperty("amazon.s3.access.secret.key"));
       AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                               .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                               .withRegion(Regions.US_EAST_1)
                               .build();
       PutObjectResult temp = s3Client.putObject(configFile.getProperty("amazon.s3.bucket"), configFile.getProperty("amazon.s3.file.prefix.name") + key, imgBase64);
       
       LOGGER.debug(" >> END putObjectFromS3 ");
       
       return temp;
   }
   
   /**
    * 
    * @param key
    * @param imgBase64
    * @return
    * @throws Exception
    */
   public PutObjectResult putObjectFromS3(String key, byte[] imgBase64) throws Exception {
       
       LOGGER.debug(" >> INIT putObjectFromS3 ");
       
       BasicAWSCredentials awsCreds = new BasicAWSCredentials(configFile.getProperty("amazon.s3.access.key"),  configFile.getProperty("amazon.s3.access.secret.key"));
       AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                               .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                               .withRegion(Regions.US_EAST_1)
                               .build();
       
       InputStream stream = new ByteArrayInputStream(imgBase64);
       ObjectMetadata meta = new ObjectMetadata();
       meta.setContentLength(imgBase64.length);
      
       PutObjectResult temp = s3Client.putObject(configFile.getProperty("amazon.s3.bucket"), configFile.getProperty("amazon.s3.file.prefix.name") + key, stream, meta);
       LOGGER.debug(" >> END putObjectFromS3 ");
       
       return temp;
   }
   
   
   /**
    * 
    * @param key
    * @throws Exception
    */
   public void deleteObjectFromS3(String key) throws UnexpectedException {
       
       LOGGER.debug(" >> INIT deleteObjectFromS3 ");
       
       BasicAWSCredentials awsCreds = new BasicAWSCredentials(configFile.getProperty("amazon.s3.access.key"), configFile.getProperty("amazon.s3.access.secret.key"));
       AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                               .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                               .withRegion(Regions.US_EAST_1)
                               .build();
       
       s3Client.deleteObject(configFile.getProperty("amazon.s3.bucket"), configFile.getProperty("amazon.s3.file.prefix.name") + key);
       
       LOGGER.debug(" >> END deleteObjectFromS3 ");
   }
  
  
   /**
    * 
    * @return
    * @throws Exception
    */
   public List<S3ObjectSummary> listObjectsFromS3() throws UnexpectedException {
       LOGGER.debug(" >> INIT listObjectsFromS3 ");
       
       BasicAWSCredentials awsCreds = new BasicAWSCredentials(configFile.getProperty("amazon.s3.access.key"), configFile.getProperty("amazon.s3.access.secret.key"));
       AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                               .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                               .withRegion(Regions.US_EAST_1)
                               .build();
       List<S3ObjectSummary> temp = s3Client.listObjects(configFile.getProperty("amazon.s3.bucket"), configFile.getProperty("amazon.s3.file.prefix.name")).getObjectSummaries();
       
       LOGGER.debug(" >> END listObjectsFromS3 ");
       return temp;
   }
   
    
    
}
