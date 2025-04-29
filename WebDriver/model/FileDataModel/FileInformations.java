package model.FileDataModel;

public class FileInformations {
    private final int id;
    private final FileData fileData;
    private String fileName;
    private String fileType;


    public FileInformations(int id, String fileName, fileType type, FileData fileData){
        this.id = id;
        this.fileName = fileName;
        this.fileType = type.toString();
        this.fileData = fileData;
    }

    public int getFileId() { return this.id; }
    public String getFileName() { return this.fileName; }
    public String getFileType() { return this.fileType; }
    
    public int getFileSize() { return this.fileData.getFileSize(); }
    public String getFileLocation() { return this.fileData.getFileLocation(); }
    public String getFileUrl() { return this.fileData.getFileUrl(); }
    public String getFileLastRelease() { return this.fileData.getFileLastRelease(); }

    public enum fileType{
        TEXT
    }
}
