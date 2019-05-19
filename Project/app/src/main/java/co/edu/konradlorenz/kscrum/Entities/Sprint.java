package co.edu.konradlorenz.kscrum.Entities;

import java.io.Serializable;

public class Sprint implements Serializable {
    private String titulo;
    private String UIProject;
    private String descripcion;
    private String imageSprint;

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
