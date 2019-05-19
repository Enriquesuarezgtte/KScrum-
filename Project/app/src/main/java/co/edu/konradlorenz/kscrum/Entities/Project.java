package co.edu.konradlorenz.kscrum.Entities;


import java.io.Serializable;

public class Project implements Serializable {

    private String projectDescription;
    private String projectDisplayName;
    private String projectLanguaje;
    private String projectPhotoURL;
    private  String id;

    public Project(String id,String projectDescription, String projectDisplayName, String projectLanguaje, String projectPhotoURL) {
        this.projectDescription = projectDescription;
        this.projectDisplayName = projectDisplayName;
        this.projectLanguaje = projectLanguaje;
        this.projectPhotoURL = projectPhotoURL;
        this.id=id;
    }

    public Project() {

    }

    public Project(Project toObject) {
        this.projectDescription = toObject.projectDescription;
        this.projectDisplayName = toObject.projectDisplayName;
        this.projectLanguaje = toObject.projectLanguaje;
        this.projectPhotoURL = toObject.projectPhotoURL;
        this.id=toObject.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectDisplayName() {
        return projectDisplayName;
    }

    public void setProjectDisplayName(String projectDisplayName) {
        this.projectDisplayName = projectDisplayName;
    }

    public String getProjectLanguaje() {
        return projectLanguaje;
    }

    public void setProjectLanguaje(String projectLanguaje) {
        this.projectLanguaje = projectLanguaje;
    }

    public String getProjectPhotoURL() {
        return projectPhotoURL;
    }

    public void setProjectPhotoURL(String projectPhotoURL) {
        this.projectPhotoURL = projectPhotoURL;
    }
}
