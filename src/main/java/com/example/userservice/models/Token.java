package com.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
public class Token extends BaseModel{

    private String value;
    @ManyToOne
    private User user;
    private Date expiryAt;
    private boolean deleted;
}
