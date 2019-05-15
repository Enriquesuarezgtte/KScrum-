package co.edu.konradlorenz.kscrum.Entities;


import android.content.res.TypedArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Project implements Serializable {

    private String projectDescription;
    private String projectDisplayName;
    private String projectLanguaje;
    private String projectPhotoURL;

    public Project(String projectDescription, String projectDisplayName, String projectLanguaje, String projectPhotoURL) {
        this.projectDescription = projectDescription;
        this.projectDisplayName = projectDisplayName;
        this.projectLanguaje = projectLanguaje;
        this.projectPhotoURL = projectPhotoURL;
    }

    public Project() {

    }

    public Project(Project toObject) {
        this.projectDescription = toObject.projectDescription;
        this.projectDisplayName = toObject.projectDisplayName;
        this.projectLanguaje = toObject.projectLanguaje;
        this.projectPhotoURL = toObject.projectPhotoURL;
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
