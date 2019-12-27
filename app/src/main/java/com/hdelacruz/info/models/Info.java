package com.hdelacruz.info.models;

public class Info {

    private Long id_if;

    private String dia_if;

    private String area_if;

    private String trabajador_if;

    private String descripcion_if;

    private String imagen;

    private String fecha_ini;

    private String fecha_fin;

    public Long getId_if() {
        return id_if;
    }

    public void setId_if(Long id_if) {
        this.id_if = id_if;
    }

    public String getDia_if() {
        return dia_if;
    }

    public void setDia_if(String dia_if) {
        this.dia_if = dia_if;
    }

    public String getArea_if() {
        return area_if;
    }

    public void setArea_if(String area_if) {
        this.area_if = area_if;
    }

    public String getTrabajador_if() {
        return trabajador_if;
    }

    public void setTrabajador_if(String trabajador_if) {
        this.trabajador_if = trabajador_if;
    }

    public String getDescripcion_if() {
        return descripcion_if;
    }

    public void setDescripcion_if(String descripcion_if) {
        this.descripcion_if = descripcion_if;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha_ini() {
        return fecha_ini;
    }

    public void setFecha_ini(String fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id_if=" + id_if +
                ", dia_if='" + dia_if + '\'' +
                ", area_if='" + area_if + '\'' +
                ", trabajador_if='" + trabajador_if + '\'' +
                ", descripcion_if='" + descripcion_if + '\'' +
                ", imagen='" + imagen + '\'' +
                ", fecha_ini='" + fecha_ini + '\'' +
                ", fecha_fin='" + fecha_fin + '\'' +
                '}';
    }
}
