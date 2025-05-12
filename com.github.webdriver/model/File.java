package model;

import model.filedatas.FileInformations;

public class File {
    private final FileInformations fileInformations;
    private final permissions[] filePermissions;
    
    public File(FileInformations fileInformations, permissions[] filePermissions){
        this.fileInformations = fileInformations;
        this.filePermissions = filePermissions;
    }

    public int getFileId() { return this.fileInformations.getFileId(); }
    public String getFileName() { return this.fileInformations.getFileName(); }
    public String getFileType() { return this.fileInformations.getFileType(); }

    public int getFileSize() { return this.fileInformations.getFileSize(); }
    public String getFileLocation() { return this.fileInformations.getFileLocation(); }
    public String getFileUrl() { return this.fileInformations.getFileUrl(); }
    public String getFileLastRelease() { return this.fileInformations.getFileLastRelease(); }

    public enum permissions{
        A, B, C, D
    }

}