package com.edutech.progressive.dto;

public class LoginResponse {
   
   private String token;
   private String roles;
   private Integer userId;
   private Integer patientId;
   private Integer doctorId;
   private String nameValue;
   public LoginResponse(String token, String roles, Integer userId, Integer patientId, Integer doctorId,String nameValue) {
      this.nameValue=nameValue;
      this.token = token;
      this.roles = roles;
      this.userId = userId;
      this.patientId = patientId;
      this.doctorId = doctorId;
   }
   
   public String getToken() {
      return token;
   }
   public void setToken(String token) {
      this.token = token;
   }
   public String getRoles() {
      return roles;
   }
   public void setRoles(String roles) {
      this.roles = roles;
   }
   public Integer getUserId() {
      return userId;
   }
   public void setUserId(Integer userId) {
      this.userId = userId;
   }
   public Integer getPatientId() {
      return patientId;
   }
   public void setPatientId(Integer patientId) {
      this.patientId = patientId;
   }
   public Integer getDoctorId() {
      return doctorId;
   }
   public void setDoctorId(Integer doctorId) {
      this.doctorId = doctorId;
   }

   public String getNameValue() {
      return nameValue;
   }

   public void setNameValue(String nameValue) {
      this.nameValue = nameValue;
   }

   
}
