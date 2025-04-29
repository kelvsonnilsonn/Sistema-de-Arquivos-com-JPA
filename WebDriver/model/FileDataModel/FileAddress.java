package model.FileDataModel;

public class FileAddress {
    private String fileLocation;
    private String fileUrl;

    public FileAddress(String fileLocation, String fileUrl){
        this.fileLocation = fileLocation;
        this.fileUrl = fileUrl;
    }

    public String getFileLocation() { return this.fileLocation; }
    public String getFileUrl() { return this.fileUrl; }
}
