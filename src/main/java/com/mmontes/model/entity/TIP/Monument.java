package com.mmontes.model.entity.TIP;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "M")
public class Monument extends TIP{
}
