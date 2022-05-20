package com.hbDemo.DemoHb;

import java.util.*;

import javax.persistence.*;

import org.hibernate.query.Query;

import org.hibernate.Session;

@Entity
public class Customers
{	
	@Id
	@GeneratedValue(generator = "increment")
    private int cid;
    private String cname;
    private String c_mobile_number;
    @OneToMany(mappedBy="customers", cascade = {CascadeType.ALL})
    private List<Invoices> invoices = new ArrayList<Invoices>();
    
    
	public List<Invoices> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<Invoices> invoices) {
		this.invoices = invoices;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getC_mobile_number() {
		return c_mobile_number;
	}
	public void setC_mobile_number(String c_mobile_number) {
		this.c_mobile_number = c_mobile_number;
	}
	
	public String toString() {
    	return "Customer[cid = " + cid + " cname = " + cname + " c mobile =  " + c_mobile_number + " ] ";
    }
    
	public static void display(Session session)
	{

	    Query q = session.createQuery("from Customers");
	    List<Customers> Customer = q.list();
	    
	    for(Customers c : Customer)
	    {
	    	System.out.println(c);
	    }
   
	}
    
	
	public static void delete(Session session, int cid)
    {
    	Query q = session.createQuery("Delete from Customers where cid = :cid");
    	q.setParameter("cid", cid);
    	q.executeUpdate();
    }
    

    


}