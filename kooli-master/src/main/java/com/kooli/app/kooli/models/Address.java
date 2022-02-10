package com.kooli.app.kooli.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kooli.app.kooli.models.audit.DateAudit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Address extends DateAudit {

    private static final long serialVersionUID = -8361939544099438297L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_1", nullable = true)
    private String line_1 = "";

    @Column(name = "line_2", nullable = true)
    private String line_2 = "";

    @Column(name = "pincode", nullable = true)
    private String pincode = "";

    @Column(name = "city", nullable = true)
    private String city = "";

    @Column(name = "state", nullable = true)
    private String state = "";

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RecordStatus status = RecordStatus.ACTIVE;

    @Column(name = "type", nullable = true)
    private String type = "";

    @Column(name = "building_name", nullable = true)
    private String buildingName;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user", nullable = true)
    private User user;

}
