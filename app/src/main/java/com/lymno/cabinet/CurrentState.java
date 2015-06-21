package com.lymno.cabinet;

/**
 * Created by Rhinrei on 21.06.2015.
 */
public class CurrentState {
    Integer label;
    Integer kid;

    public CurrentState(Integer label, Integer kid) {
        this.label = label;
        this.kid = kid;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public Integer getKid() {
        return kid;
    }

    public void setKid(Integer kid) {
        this.kid = kid;
    }
}
