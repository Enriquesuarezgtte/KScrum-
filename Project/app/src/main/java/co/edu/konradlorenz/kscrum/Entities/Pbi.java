package co.edu.konradlorenz.kscrum.Entities;

import java.io.Serializable;

public class Pbi implements Serializable {

    private String pbiTitle;
    private String pbiDescription;
    private String pbiImage;
    private String sprintId;
    private String pbiEstado;
    private String pbiFile;

    public Pbi() {
    }

    public Pbi(String pbiTitle, String pbiDescription, String pbiImage, String sprintId, String pbiEstado, String pbiFile) {
        this.pbiTitle = pbiTitle;
        this.pbiDescription = pbiDescription;
        this.pbiImage = pbiImage;
        this.sprintId = sprintId;
        this.pbiEstado = pbiEstado;
        this.pbiFile = pbiFile;
    }
    public Pbi (Pbi toObject){
        this.pbiTitle = toObject.pbiTitle;
        this.pbiDescription = toObject.pbiDescription;
        this.pbiImage = toObject.pbiImage;
        this.sprintId = toObject.sprintId;
        this.pbiEstado = toObject.pbiEstado;
        this.pbiFile = toObject.pbiFile;
    }

    public String getPbiTitle() {
        return pbiTitle;
    }

    public void setPbiTitle(String pbiTitle) {
        this.pbiTitle = pbiTitle;
    }

    public String getPbiDescription() {
        return pbiDescription;
    }

    public void setPbiDescription(String pbiDescription) {
        this.pbiDescription = pbiDescription;
    }

    public String getPbiImage() {
        return pbiImage;
    }

    public void setPbiImage(String pbiImage) {
        this.pbiImage = pbiImage;
    }

    public String getSprintId() {
        return sprintId;
    }

    public void setSprintId(String sprintId) {
        this.sprintId = sprintId;
    }

    public String getPbiEstado() {
        return pbiEstado;
    }

    public void setPbiEstado(String pbiEstado) {
        this.pbiEstado = pbiEstado;
    }

    public String getPbiFile() {
        return pbiFile;
    }

    public void setPbiFile(String pbiFile) {
        this.pbiFile = pbiFile;
    }
}
            
