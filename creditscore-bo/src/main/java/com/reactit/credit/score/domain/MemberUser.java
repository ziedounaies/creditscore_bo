package com.reactit.credit.score.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactit.credit.score.domain.enumeration.AcountType;
import com.reactit.credit.score.domain.enumeration.IdentifierType;
import com.reactit.credit.score.domain.enumeration.Role;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A MemberUser.
 */
@Entity
@Table(name = "member_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "birth_date")
    private Instant birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "acount_type")
    private AcountType acountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "identifier_type")
    private IdentifierType identifierType;

    @Column(name = "identifier_value")
    private String identifierValue;

    @Column(name = "employers_reported")
    private String employersReported;

    @Column(name = "income")
    private String income;

    @Column(name = "expenses")
    private String expenses;

    @Column(name = "gross_profit")
    private String grossProfit;

    @Column(name = "net_profit_margin")
    private String netProfitMargin;

    @Column(name = "debts_obligations")
    private String debtsObligations;

    @Column(name = "enabled")
    private Boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "created_at")
    private Instant createdAt;

    @JsonIgnoreProperties(value = { "memberUser", "invoices" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "memberUser")
    private CreditRapport creditRapport;

    @JsonIgnoreProperties(value = { "memberUser", "products", "payments", "creditRapport" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "memberUser")
    private Invoice invoice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberUser")
    @JsonIgnoreProperties(value = { "memberUser", "banks", "agencies" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberUser")
    @JsonIgnoreProperties(value = { "memberUser", "products", "invoice" }, allowSetters = true)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberUser")
    @JsonIgnoreProperties(value = { "memberUser" }, allowSetters = true)
    private Set<Claim> claims = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberUser")
    @JsonIgnoreProperties(value = { "memberUser" }, allowSetters = true)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "memberUser")
    @JsonIgnoreProperties(value = { "memberUser", "banks", "agencies" }, allowSetters = true)
    private Set<Contact> contacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MemberUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public MemberUser userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public MemberUser firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public MemberUser lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBusinessName() {
        return this.businessName;
    }

    public MemberUser businessName(String businessName) {
        this.setBusinessName(businessName);
        return this;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Instant getBirthDate() {
        return this.birthDate;
    }

    public MemberUser birthDate(Instant birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public AcountType getAcountType() {
        return this.acountType;
    }

    public MemberUser acountType(AcountType acountType) {
        this.setAcountType(acountType);
        return this;
    }

    public void setAcountType(AcountType acountType) {
        this.acountType = acountType;
    }

    public IdentifierType getIdentifierType() {
        return this.identifierType;
    }

    public MemberUser identifierType(IdentifierType identifierType) {
        this.setIdentifierType(identifierType);
        return this;
    }

    public void setIdentifierType(IdentifierType identifierType) {
        this.identifierType = identifierType;
    }

    public String getIdentifierValue() {
        return this.identifierValue;
    }

    public MemberUser identifierValue(String identifierValue) {
        this.setIdentifierValue(identifierValue);
        return this;
    }

    public void setIdentifierValue(String identifierValue) {
        this.identifierValue = identifierValue;
    }

    public String getEmployersReported() {
        return this.employersReported;
    }

    public MemberUser employersReported(String employersReported) {
        this.setEmployersReported(employersReported);
        return this;
    }

    public void setEmployersReported(String employersReported) {
        this.employersReported = employersReported;
    }

    public String getIncome() {
        return this.income;
    }

    public MemberUser income(String income) {
        this.setIncome(income);
        return this;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpenses() {
        return this.expenses;
    }

    public MemberUser expenses(String expenses) {
        this.setExpenses(expenses);
        return this;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getGrossProfit() {
        return this.grossProfit;
    }

    public MemberUser grossProfit(String grossProfit) {
        this.setGrossProfit(grossProfit);
        return this;
    }

    public void setGrossProfit(String grossProfit) {
        this.grossProfit = grossProfit;
    }

    public String getNetProfitMargin() {
        return this.netProfitMargin;
    }

    public MemberUser netProfitMargin(String netProfitMargin) {
        this.setNetProfitMargin(netProfitMargin);
        return this;
    }

    public void setNetProfitMargin(String netProfitMargin) {
        this.netProfitMargin = netProfitMargin;
    }

    public String getDebtsObligations() {
        return this.debtsObligations;
    }

    public MemberUser debtsObligations(String debtsObligations) {
        this.setDebtsObligations(debtsObligations);
        return this;
    }

    public void setDebtsObligations(String debtsObligations) {
        this.debtsObligations = debtsObligations;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public MemberUser enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return this.role;
    }

    public MemberUser role(Role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public MemberUser createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public CreditRapport getCreditRapport() {
        return this.creditRapport;
    }

    public void setCreditRapport(CreditRapport creditRapport) {
        if (this.creditRapport != null) {
            this.creditRapport.setMemberUser(null);
        }
        if (creditRapport != null) {
            creditRapport.setMemberUser(this);
        }
        this.creditRapport = creditRapport;
    }

    public MemberUser creditRapport(CreditRapport creditRapport) {
        this.setCreditRapport(creditRapport);
        return this;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        if (this.invoice != null) {
            this.invoice.setMemberUser(null);
        }
        if (invoice != null) {
            invoice.setMemberUser(this);
        }
        this.invoice = invoice;
    }

    public MemberUser invoice(Invoice invoice) {
        this.setInvoice(invoice);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setMemberUser(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setMemberUser(this));
        }
        this.addresses = addresses;
    }

    public MemberUser addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public MemberUser addAddress(Address address) {
        this.addresses.add(address);
        address.setMemberUser(this);
        return this;
    }

    public MemberUser removeAddress(Address address) {
        this.addresses.remove(address);
        address.setMemberUser(null);
        return this;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setMemberUser(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setMemberUser(this));
        }
        this.payments = payments;
    }

    public MemberUser payments(Set<Payment> payments) {
        this.setPayments(payments);
        return this;
    }

    public MemberUser addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setMemberUser(this);
        return this;
    }

    public MemberUser removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setMemberUser(null);
        return this;
    }

    public Set<Claim> getClaims() {
        return this.claims;
    }

    public void setClaims(Set<Claim> claims) {
        if (this.claims != null) {
            this.claims.forEach(i -> i.setMemberUser(null));
        }
        if (claims != null) {
            claims.forEach(i -> i.setMemberUser(this));
        }
        this.claims = claims;
    }

    public MemberUser claims(Set<Claim> claims) {
        this.setClaims(claims);
        return this;
    }

    public MemberUser addClaim(Claim claim) {
        this.claims.add(claim);
        claim.setMemberUser(this);
        return this;
    }

    public MemberUser removeClaim(Claim claim) {
        this.claims.remove(claim);
        claim.setMemberUser(null);
        return this;
    }

    public Set<Notification> getNotifications() {
        return this.notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        if (this.notifications != null) {
            this.notifications.forEach(i -> i.setMemberUser(null));
        }
        if (notifications != null) {
            notifications.forEach(i -> i.setMemberUser(this));
        }
        this.notifications = notifications;
    }

    public MemberUser notifications(Set<Notification> notifications) {
        this.setNotifications(notifications);
        return this;
    }

    public MemberUser addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setMemberUser(this);
        return this;
    }

    public MemberUser removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setMemberUser(null);
        return this;
    }

    public Set<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        if (this.contacts != null) {
            this.contacts.forEach(i -> i.setMemberUser(null));
        }
        if (contacts != null) {
            contacts.forEach(i -> i.setMemberUser(this));
        }
        this.contacts = contacts;
    }

    public MemberUser contacts(Set<Contact> contacts) {
        this.setContacts(contacts);
        return this;
    }

    public MemberUser addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setMemberUser(this);
        return this;
    }

    public MemberUser removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setMemberUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberUser)) {
            return false;
        }
        return getId() != null && getId().equals(((MemberUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberUser{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", businessName='" + getBusinessName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", acountType='" + getAcountType() + "'" +
            ", identifierType='" + getIdentifierType() + "'" +
            ", identifierValue='" + getIdentifierValue() + "'" +
            ", employersReported='" + getEmployersReported() + "'" +
            ", income='" + getIncome() + "'" +
            ", expenses='" + getExpenses() + "'" +
            ", grossProfit='" + getGrossProfit() + "'" +
            ", netProfitMargin='" + getNetProfitMargin() + "'" +
            ", debtsObligations='" + getDebtsObligations() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", role='" + getRole() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
