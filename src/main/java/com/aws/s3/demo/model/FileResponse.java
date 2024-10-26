package com.aws.s3.demo.model;

public class FileResponse {
    private String fileName;
    private long size;
    private String url;

    public FileResponse(String fileName, long size, String url) {
        this.fileName = fileName;
        this.size = size;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}