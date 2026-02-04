package com.avidata.api.domain.port.out;

public interface IAzureBlobStorage {
    String uploadFile(String containerName, byte[] data, String fileName);
    String updateFile(String containerName, String fileName, byte[] data);
    byte[] downloadFile(String containerName, String fileName);
    void deleteFile(String containerName, String fileName);
}
