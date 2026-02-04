package com.avidata.api.application.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.avidata.api.domain.port.out.IAzureBlobStorage;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;

@Service
public class AzureBlobService implements IAzureBlobStorage {
  private final BlobServiceClient blobServiceClient;

  public AzureBlobService(@Value("${blob.storage.connection-string}") String connectionString) {
    this.blobServiceClient = new BlobServiceClientBuilder()
        .connectionString(connectionString)
        .buildClient();
  }
  
  private BlobContainerClient getContainerClient(String containerName) {
    return blobServiceClient.getBlobContainerClient(containerName);
  }

  public String uploadFile(String containerName, byte[] data, String fileName) {
    // 1. Gera nome único
    String uniqueFileName = generateBlobFileName(fileName);
    String contentTypeLogo = getMediaType(uniqueFileName).toString();
    // 2. Envia para o Blob Storage
    BlobContainerClient containerClient = getContainerClient(containerName);
    BlobClient blobClient = containerClient.getBlobClient(uniqueFileName);
    blobClient.upload(new ByteArrayInputStream(data), data.length, true);
    blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType(contentTypeLogo));
    return blobClient.getBlobUrl();
  }

  public String updateFile(String containerName, String fileName, byte[] data) {
    return uploadFile(containerName, data, fileName);
  }

  public byte[] downloadFile(String containerName, String fileName) {
    BlobContainerClient containerClient = getContainerClient(containerName);
    BlobClient blobClient = containerClient.getBlobClient(fileName);
    try {
      return blobClient.openInputStream().readAllBytes();
    } catch (IOException e) {
      throw new RuntimeException("Erro ao baixar o arquivo: " + e.getMessage(), e);
    }
  }

  public void deleteFile(String containerName, String fileName) {
    BlobContainerClient containerClient = getContainerClient(containerName);
    BlobClient blobClient = containerClient.getBlobClient(fileName);
    blobClient.delete();
  }


  private MediaType getMediaType(String fileName) {
    if (fileName.endsWith(".png")) return MediaType.IMAGE_PNG;
    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) return MediaType.IMAGE_JPEG;
    if (fileName.endsWith(".gif")) return MediaType.IMAGE_GIF;
    return MediaType.APPLICATION_OCTET_STREAM;
  }

  private String generateBlobFileName(String originalName) {
    String ext = "";
    int idx = originalName.lastIndexOf(".");
    if (idx > 0) {
      ext = originalName.substring(idx);
    }
    return UUID.randomUUID().toString() + ext;
  }
}
