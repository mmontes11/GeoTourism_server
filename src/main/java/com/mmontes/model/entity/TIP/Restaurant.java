package com.mmontes.model.entity.TIP;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "R")
public class Restaurant extends TIP{
}
