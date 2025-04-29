package model.FileDataModel;

import java.sql.Date;

public class FileData {
    private final FileAddress fileAddress;
    private int fileSize;
    private Date fileReleaseDate;

    public FileData(int fileSize, Date fileReleaseDate, FileAddress fileAddress){
        this.fileSize = fileSize;
        this.fileAddress = fileAddress;
        this.fileReleaseDate = fileReleaseDate;
    }

    public int getFileSize() { return this.fileSize; }
    public String getFileLocation() { return this.fileAddress.getFileLocation(); }
    public String getFileUrl() { return this.fileAddress.getFileUrl(); }
    public String getFileLastRelease() { return this.fileReleaseDate.toString(); }
}
