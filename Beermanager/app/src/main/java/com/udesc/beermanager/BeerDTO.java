package com.udesc.beermanager;

public class BeerDTO {

    private Long id;
    private String name;
    private String description;
    private double alcoholContent;
    private String type;
    private String origin;
    private String brewery;
    private double price;
    private boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAlcoholContent() {
        return alcoholContent;
    }

    public void setAlcoholContent(double alcoholContent) {
        this.alcoholContent = alcoholContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public CreateBeerDTO toBeerDTO(){
        CreateBeerDTO retorno = new CreateBeerDTO();
        retorno.setName(this.getName());
        retorno.setDescription(this.getDescription());
        retorno.setAlcoholContent(this.getAlcoholContent());
        retorno.setType(this.getType());
        retorno.setOrigin(this.getOrigin());
        retorno.setBrewery(this.getBrewery());
        retorno.setPrice(this.getPrice());
        retorno.setAvailable(this.isAvailable());
        return retorno;
    }
}
