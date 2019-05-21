package co.edu.konradlorenz.kscrum.Entities;



import java.io.Serializable;
import java.util.Date;

public class Sprint implements Serializable {
    private String title;
    private String  projectId;
    private String percentage;
    private Date lastDate;
    private String imagen;
    private String extraInfo;
    private Date creationDate;
    private String id;

    public Sprint(Sprint toObject) {
        this.title = toObject.title;
        this.projectId = toObject.projectId;
        this.percentage = toObject.percentage;
        this.lastDate = toObject.lastDate;
        this.imagen =toObject.imagen;
        this.extraInfo = toObject.extraInfo;
        this.creationDate = toObject.creationDate;
        this.id=toObject.id;
    }

    public Sprint() {
    }

    public Sprint(String title, String projectId, String percentage, Date lastDate, String imagen, String extraInfo, Date creationDate
    ,String id) {
        this.title = title;
        this.projectId = projectId;
        this.percentage = percentage;
        this.lastDate = lastDate;
        this.imagen = imagen;
        this.extraInfo = extraInfo;
        this.creationDate = creationDate;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
