package com.eugene.contractorsearch.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("kpp")
    @Expose
    private String kpp;
    @SerializedName("capital")
    @Expose
    private Object capital;
    @SerializedName("management")
    @Expose
    private Management management;
    @SerializedName("branch_type")
    @Expose
    private String branchType;
    @SerializedName("branch_count")
    @Expose
    private Integer branchCount;
    @SerializedName("source")
    @Expose
    private Object source;
    @SerializedName("qc")
    @Expose
    private Object qc;
    @SerializedName("hid")
    @Expose
    private String hid;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("state")
    @Expose
    private State state;
    @SerializedName("opf")
    @Expose
    private Opf opf;
    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("inn")
    @Expose
    private String inn;
    @SerializedName("ogrn")
    @Expose
    private String ogrn;
    @SerializedName("okpo")
    @Expose
    private Object okpo;
    @SerializedName("okved")
    @Expose
    private String okved;
    @SerializedName("okveds")
    @Expose
    private Object okveds;
    @SerializedName("authorities")
    @Expose
    private Object authorities;
    @SerializedName("documents")
    @Expose
    private Object documents;
    @SerializedName("licenses")
    @Expose
    private Object licenses;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("phones")
    @Expose
    private Object phones;
    @SerializedName("emails")
    @Expose
    private Object emails;
    @SerializedName("ogrn_date")
    @Expose
    private Long ogrnDate;
    @SerializedName("okved_type")
    @Expose
    private String okvedType;

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public Object getCapital() {
        return capital;
    }

    public void setCapital(Object capital) {
        this.capital = capital;
    }

    public Management getManagement() {
        return management;
    }

    public void setManagement(Management management) {
        this.management = management;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public Integer getBranchCount() {
        return branchCount;
    }

    public void setBranchCount(Integer branchCount) {
        this.branchCount = branchCount;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getQc() {
        return qc;
    }

    public void setQc(Object qc) {
        this.qc = qc;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Opf getOpf() {
        return opf;
    }

    public void setOpf(Opf opf) {
        this.opf = opf;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public Object getOkpo() {
        return okpo;
    }

    public void setOkpo(Object okpo) {
        this.okpo = okpo;
    }

    public String getOkved() {
        return okved;
    }

    public void setOkved(String okved) {
        this.okved = okved;
    }

    public Object getOkveds() {
        return okveds;
    }

    public void setOkveds(Object okveds) {
        this.okveds = okveds;
    }

    public Object getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Object authorities) {
        this.authorities = authorities;
    }

    public Object getDocuments() {
        return documents;
    }

    public void setDocuments(Object documents) {
        this.documents = documents;
    }

    public Object getLicenses() {
        return licenses;
    }

    public void setLicenses(Object licenses) {
        this.licenses = licenses;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Object getPhones() {
        return phones;
    }

    public void setPhones(Object phones) {
        this.phones = phones;
    }

    public Object getEmails() {
        return emails;
    }

    public void setEmails(Object emails) {
        this.emails = emails;
    }

    public Long getOgrnDate() {
        return ogrnDate;
    }

    public void setOgrnDate(Long ogrnDate) {
        this.ogrnDate = ogrnDate;
    }

    public String getOkvedType() {
        return okvedType;
    }

    public void setOkvedType(String okvedType) {
        this.okvedType = okvedType;
    }
}
