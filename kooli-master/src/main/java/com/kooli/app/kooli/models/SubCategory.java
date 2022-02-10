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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.kooli.app.kooli.models.audit.DateAudit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;




@Entity
@Table(name = "sub_category")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubCategory extends DateAudit {

	private static final long serialVersionUID = -8361939544099438297L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "Subcategoryid", nullable = true)
    private Category category;

    @NotBlank
	@Size(max = 40)
	@Column(name = "subcategory", nullable = false)
	private String name;

    @Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private RecordStatus status = RecordStatus.ACTIVE;

}

