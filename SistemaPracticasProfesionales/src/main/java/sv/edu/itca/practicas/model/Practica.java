package sv.edu.itca.practicas.model;

import java.time.LocalDate;

public class Practica {

    private int id;

    private Alumno alumno;
    private Maestro supervisor;
    private Empresa empresa;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private int horasRequeridas;
    private double horasAcumuladas;

    private EstadoPractica estado;

    public Practica() {
    }

    public Practica(int id,
                    Alumno alumno,
                    Maestro supervisor,
                    Empresa empresa,
                    LocalDate fechaInicio,
                    LocalDate fechaFin,
                    int horasRequeridas,
                    double horasAcumuladas,
                    EstadoPractica estado) {

        this.id = id;
        this.alumno = alumno;
        this.supervisor = supervisor;
        this.empresa = empresa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horasRequeridas = horasRequeridas;
        this.horasAcumuladas = horasAcumuladas;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Maestro getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Maestro supervisor) {
        this.supervisor = supervisor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getHorasRequeridas() {
        return horasRequeridas;
    }

    public void setHorasRequeridas(int horasRequeridas) {
        this.horasRequeridas = horasRequeridas;
    }

    public double getHorasAcumuladas() {
        return horasAcumuladas;
    }

    public void setHorasAcumuladas(double horasAcumuladas) {
        this.horasAcumuladas = horasAcumuladas;
    }

    public EstadoPractica getEstado() {
        return estado;
    }

    public void setEstado(EstadoPractica estado) {
        this.estado = estado;
    }

    public double getPorcentajeAvance() {

        if (horasRequeridas <= 0) {
            return 0;
        }

        double porcentaje =
                (horasAcumuladas / horasRequeridas) * 100;

        return Math.min(porcentaje, 100);
    }

    public double getHorasRestantes() {

        double restantes =
                horasRequeridas - horasAcumuladas;

        return Math.max(restantes, 0);
    }

    public boolean estaFinalizada() {
        return horasAcumuladas >= horasRequeridas;
    }
}