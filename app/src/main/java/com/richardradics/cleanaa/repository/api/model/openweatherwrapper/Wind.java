
package com.richardradics.cleanaa.repository.api.model.openweatherwrapper;


import com.google.gson.annotations.Expose;


public class Wind {

    @Expose
    private Double speed;
    @Expose
    private Double deg;

    /**
     * 
     * @return
     *     The speed
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     * 
     * @param speed
     *     The speed
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    /**
     * 
     * @return
     *     The deg
     */
    public Double getDeg() {
        return deg;
    }

    /**
     * 
     * @param deg
     *     The deg
     */
    public void setDeg(Double deg) {
        this.deg = deg;
    }

}
