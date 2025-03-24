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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "asiakastyyppi")
public class Asiakastyyppi {

        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "asiakastyyppiId")
    private Long asiakastyyppiId;


    @NotNull
    @Column(name = "asiakastyyppi")
    private String asiakastyyppi;

    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asiakastyyppi")
    @JsonIgnore
    private List<TapahtumaLipputyyppi> tapahtumaLipputyyppi;

    public Asiakastyyppi(@NotNull @Size(min = 1, max = 100) String asiakastyyppi) {
        this.asiakastyyppi = asiakastyyppi;
    }  



    public Asiakastyyppi(Long asiakastyyppiId, @NotNull @Size(min = 1, max = 100) String asiakastyyppi,
            List<TapahtumaLipputyyppi> tapahtumaLipputyyppi) {
        this.asiakastyyppiId = asiakastyyppiId;
        this.asiakastyyppi = asiakastyyppi;
        this.tapahtumaLipputyyppi = tapahtumaLipputyyppi;
    }




    public Asiakastyyppi(@NotNull @Size(min = 1, max = 100) String asiakastyyppi,
            List<TapahtumaLipputyyppi> tapahtumaLipputyyppi) {
        this.asiakastyyppi = asiakastyyppi;
        this.tapahtumaLipputyyppi = tapahtumaLipputyyppi;
    }




    public Asiakastyyppi() {
        super();
    }

    

    public Long getAsiakastyyppiId() {
        return asiakastyyppiId;
    }

    public void setAsiakastyyppiId(Long asiakastyyppiId) {
        this.asiakastyyppiId = asiakastyyppiId;
    }
    public String getAsiakastyyppi() {
        return asiakastyyppi;
    }



    public void setAsiakastyyppi(String asiakastyyppi) {
        this.asiakastyyppi = asiakastyyppi;
    }
    
    public List<TapahtumaLipputyyppi> getTapahtumaLipputyyppi() {
        return tapahtumaLipputyyppi;
    }

}
