package com.mnuo.forpink.designprinciples.model.builder;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CompanyClient {
	public final String companyName;
	public final String companyAddress;
	
	public final double companyRegfunds;
	public final String mPerson;
	public final String mType;
	
	public CompanyClient(){
		this(new Builder());
	}
	//构造方法
	public CompanyClient(Builder builder){
	    this.companyName = builder.companyName;
	    this.companyAddress = builder.companyAddress;
	    this.companyRegfunds = builder.companyRegfunds;
	    this.mPerson = builder.person;
	    this.mType = builder.type;
	}
	public static class Builder{
		public String companyName;
		public String companyAddress;
		
		public double companyRegfunds;
		public String person;
		public String type;
		
		public String getCompanyName() {
			return companyName;
		}
		public Builder setCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		public String getCompanyAddress() {
			return companyAddress;
		}
		public Builder setCompanyAddress(String companyAddress) {
			this.companyAddress = companyAddress;
			return this;
		}
		public double getCompanyRegfunds() {
			return companyRegfunds;
		}
		public Builder setCompanyRegfunds(double companyRegfunds) {
			this.companyRegfunds = companyRegfunds;
			return this;
		}
		public String getPerson() {
			return person;
		}
		public Builder setPerson(String person) {
			this.person = person;
			return this;
		}
		public String getType() {
			return type;
		}
		public Builder setType(String type) {
			this.type = type;
			return this;
		}
		public Builder(){
//			companyName = companyName;
//		    companyAddress = companyAddress;
//		    companyRegfunds = companyRegfunds;
//		    person = person;
//		    type = type;
		}
		//构造方法
       Builder(CompanyClient companyClient){
           this.companyName = companyClient.companyName;
           this.companyAddress = companyClient.companyAddress;
           this.companyRegfunds = companyClient.companyRegfunds;
           this.person = companyClient.mPerson;
           this.type = companyClient.mType;
       }
       //构建一个实体
       public CompanyClient build() {
           return new CompanyClient(this);
       }
	}
	
	public static void main(String[] args) {
		CompanyClient clent = new CompanyClient.Builder()
			.setCompanyAddress("xxxadress")
			.setCompanyName("yyyy").build();
		System.out.println(clent.toString());
	}
}
