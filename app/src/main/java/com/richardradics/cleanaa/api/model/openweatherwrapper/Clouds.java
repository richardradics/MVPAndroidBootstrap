
package com.richardradics.cleanaa.api.model.openweatherwrapper;


import com.google.gson.annotations.Expose;


public class Clouds {

    @Expose
    private Integer all;

    /**
     * 
     * @return
     *     The all
     */
    public Integer getAll() {
        return all;
    }

    /**
     * 
     * @param all
     *     The all
     */
    public void setAll(Integer all) {
        this.all = all;
    }

}
