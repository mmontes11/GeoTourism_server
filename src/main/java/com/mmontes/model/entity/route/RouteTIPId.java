package com.mmontes.model.entity.route;

import com.mmontes.model.entity.TIP.TIP;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class RouteTIPId implements java.io.Serializable {

    private Route route;
    private TIP tip;

    public RouteTIPId() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routeid")
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipid")
    public TIP getTip() {
        return tip;
    }

    public void setTip(TIP tip) {
        this.tip = tip;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteTIPId that = (RouteTIPId) o;
        return route != null ? route.equals(that.route) : that.route == null && (tip != null ? tip.equals(that.tip) : that.tip == null);
    }

    public int hashCode() {
        int result;
        result = (route != null ? route.hashCode() : 0);
        result = 31 * result + (tip != null ? tip.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RouteTIPId{" +
                "route=" + route +
                ", tip=" + tip +
                '}';
    }
}
