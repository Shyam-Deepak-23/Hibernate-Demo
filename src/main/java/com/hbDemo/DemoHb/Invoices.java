package com.hbDemo.DemoHb;


import org.hibernate.Session;
import javax.persistence.*;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Invoices {
	@Id @GeneratedValue(generator = "increment")
	private int invoice_no;
	
	private double total_amount;
	private String dt;
	@ManyToOne
	private Customers customers = new Customers();
	
	@OneToMany(mappedBy = "invoice", cascade = {CascadeType.ALL})
	private List<Invoice_Items> invoice_items = new ArrayList<Invoice_Items>();
	
	
	
	public List<Invoice_Items> getInvoice_items() {
		return invoice_items;
	}

	public void setInvoice_items(List<Invoice_Items> invoice_items) {
		this.invoice_items = invoice_items;
	}

	public Customers getCustomers() {
		return customers;
	}

	public void setCustomers(Customers customers) {
		this.customers = customers;
	}

	public static int getInvoice_no(Session session) {
		Query q = session.createQuery("from Invoices");
	    List<Invoices> invoice = q.list();
        int invoice_no = 0;
        
        
        for(Invoices i : invoice)
        {
        	
        	invoice_no = i.invoice_no;
        }
        
       
   
        return invoice_no+1;
	}
	
	
	public int get_invoice_no()
	{
		return invoice_no;
	}
	
	
	public static void display(Session session)
	{
		

	    Query q = session.createQuery("from Invoices");
	    List<Invoices> invoice = q.list();
	    
	    for(Invoices in : invoice)
	    {
	    	System.out.println(in);
	    }
   
	}
	
	public static void display(Session session, int invoice_no)
	{
		Query q = session.createQuery("from Invoice_Items where invoice_invoice_no = :invoice_no");
		q.setParameter("invoice_no", invoice_no);
		List<Invoice_Items> ii = q.list();
		for(Invoice_Items i : ii)
		{
			System.out.println(i);
		}
		
//		session.get(Invoice_Items.class, invoice_no);
	}


	


	

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public String getDate() {
		return dt;
	}

	public void setDate(String dt) {
		this.dt = dt;
	}

	

}
