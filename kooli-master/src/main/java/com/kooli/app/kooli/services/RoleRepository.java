package com.kooli.app.kooli.services;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kooli.app.kooli.models.WideRecordStatus;
import com.kooli.app.kooli.models.audit.DateAudit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }), })
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RoleRepository<Category, SubCategory, Role> extends DateAudit {

	private static final long serialVersionUID = -8361939544099438297L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 30)
	@Column(name = "username", nullable = false)
	private String username;

	@JsonIgnore
	@NotBlank
	@Size(max = 100)
	@Column(name = "password", nullable = false)
	private String password;

	@NotBlank
	@Size(max = 40)
	@Column(name = "first_name", nullable = false)
	private String firstname;

	@NotBlank
	@Size(max = 40)
	@Column(name = "last_name", nullable = false)
	private String lastname;

	@Column(name = "gender", nullable = true)
	private String gender = "Male";

	@Size(max = 40)
	@Email
	@Column(name = "email", nullable = false)
	private String email = "";

	@Size(max = 15)
	@Column(name = "mobile", nullable = false)
	private String mobile = "";

	@Size(max = 250)
	@Column(name = "fcm_id", nullable = true)
	private String fcmId;

	@Size(max = 250)
	@Column(name = "company_name", nullable = true)
	private String companyname;

	@Column(name = "verify_mobile", nullable = false)
	private boolean verifymobile = false;

	@Column(name = "verify_email", nullable = false)
	private boolean verifyemail = false;

	@Column(name = "locked", nullable = false)
	private boolean locked = true;

	@Column(name = "enabled", nullable = false)
	private boolean enabled = true;

	@Size(max = 250)
	@Column(name = "title", nullable = true)
	private String title;

	@Size(max = 250)
	@Column(name = "description", nullable = true)
	private String description;

	@Size(max = 250)
	@Column(name = "dob", nullable = true)
	private String dob;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private WideRecordStatus status = WideRecordStatus.ACTIVE;

	@Column(name = "type", nullable = true)
	private String type;

	@Size(max = 200)
	@Column(name = "image", nullable = true)
	private String image = "";

	@Column(name = "secure", nullable = false)
	private boolean secure = false;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "category", nullable = true)
	private Category category;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "sub_category", nullable = true)
	private SubCategory subCategory;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

}