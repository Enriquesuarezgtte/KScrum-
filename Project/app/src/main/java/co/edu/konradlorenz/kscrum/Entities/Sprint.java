package co.edu.konradlorenz.kscrum.Entities;

import java.io.Serializable;

public class Sprint implements Serializable {
    private String titulo;
    private String UIProject;
    private String descripcion;
    private String imageSprint;

    public Sprint(Sprint toObject) {
        this.titulo = toObject.titulo;
        this.UIProject = toObject.UIProject;
        this.descripcion = toObject.descripcion;
        this.imageSprint = toObject.imageSprint;
    }

    public Sprint(String titulo, String UIProject, String descripcion, String imageSprint) {
        this.titulo = titulo;
        this.UIProject = UIProject;
        this.descripcion = descripcion;
        this.imageSprint = imageSprint;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUIProject() {
        return UIProject;
    }

    public void setUIProject(String UIProject) {
        this.UIProject = UIProject;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImageSprint() {
        return imageSprint;
    }

    public void setImageSprint(String imageSprint) {
        this.imageSprint = imageSprint;
    }
}
