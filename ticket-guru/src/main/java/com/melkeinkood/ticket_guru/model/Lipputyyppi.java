package com.melkeinkood.ticket_guru.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "lipputyyppi")
public class Lipputyyppi {

        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lipputyyppiid")
    private Long lipputyyppi_id;

    @Column(name = "alennusprosentti")
    private double alennusprosentti;

    @NotNull
    @Size(min=1 , max=100)
    @Column(name = "lipputyyppiselite")
    private String lipputyyppiSelite;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lipputyyppi")
    private List<Lippu> liput;

    public Lipputyyppi(double alennusprosentti, @NotNull @Size(min = 1, max = 100) String lipputyyppiSelite,
            List<Lippu> liput) {
        this.alennusprosentti = alennusprosentti;
        this.lipputyyppiSelite = lipputyyppiSelite;
        this.liput = liput;
    }



    public Lipputyyppi(Long lipputyyppi_id, double alennusprosentti,
            @NotNull @Size(min = 1, max = 100) String lipputyyppiSelite) {
        this.lipputyyppi_id = lipputyyppi_id;
        this.alennusprosentti = alennusprosentti;
        this.lipputyyppiSelite = lipputyyppiSelite;
    }



    public Lipputyyppi(double alennusprosentti, @NotNull @Size(min = 1, max = 100) String lipputyyppiSelite) {
        this.alennusprosentti = alennusprosentti;
        this.lipputyyppiSelite = lipputyyppiSelite;
    }



    public Lipputyyppi() {
        super();
    }

    

    public Long getLipputyyppi_id() {
        return lipputyyppi_id;
    }

    public void setLipputyyppi_id(Long lipputyyppi_id) {
        this.lipputyyppi_id = lipputyyppi_id;
    }

    public double getAlennusprosentti() {
        return alennusprosentti;
    }

    public void setAlennusprosentti(double alennusprosentti) {
        this.alennusprosentti = alennusprosentti;
    }

    public String getLipputyyppiSelite() {
        return lipputyyppiSelite;
    }

    public void setLipputyyppiSelite(String lipputyyppiSelite) {
        this.lipputyyppiSelite = lipputyyppiSelite;
    }

    @Override
    public String toString() {
        return "Lipputyyppi [lipputyyppi_id=" + lipputyyppi_id + ", alennusprosentti=" + alennusprosentti
                + ", lipputyyppiSelite=" + lipputyyppiSelite + "]";
    }
    
}
