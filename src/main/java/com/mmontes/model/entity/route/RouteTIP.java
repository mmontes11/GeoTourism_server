package com.mmontes.model.entity.route;

import com.mmontes.model.entity.TIP.TIP;

import javax.persistence.*;

@Entity
@Table(name = "routetip")
@AssociationOverrides({
        @AssociationOverride(name = "pk.route",
                joinColumns = @JoinColumn(name = "routeid")
        ),
        @AssociationOverride(name = "pk.tip",
                joinColumns = @JoinColumn(name = "tipid")
        )
})
public class RouteTIP {

    private int ordination;
    private RouteTIPId pk = new RouteTIPId();

    public RouteTIP() {
    }

    @Column(name = "ordination")
    public int getOrdination() {
        return ordination;
    }

    public void setOrdination(int ordination) {
        this.ordination = ordination;
    }

    @EmbeddedId
    public RouteTIPId getPk() {
        return pk;
    }

    public void setPk(RouteTIPId pk) {
        this.pk = pk;
    }

    @Transient
    public Route getRoute() {
        return getPk().getRoute();
    }

    public void setRoute(Route route) {
        getPk().setRoute(route);
    }

    @Transient
    public TIP getTip() {
        return getPk().getTip();
    }

    public void setTip(TIP tip) {
        getPk().setTip(tip);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RouteTIP that = (RouteTIP) o;
        return getPk() != null ? getPk().equals(that.getPk()) : that.getPk() == null;
    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}
