package com.mmontes.model.entity.TIP;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tip")
@DiscriminatorValue(value = "R")
public class Restaurant extends TIP{
}
