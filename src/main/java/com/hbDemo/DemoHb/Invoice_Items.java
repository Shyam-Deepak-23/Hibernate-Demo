package com.hbDemo.DemoHb;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Invoice_Items{
	
	
	
	@Id @GeneratedValue(generator = "increment")
	private int sno;
    
    private int qty;
    private String pname;
    private double price;
    private double total_price;
    
    
    @ManyToOne
    private Invoices invoice;
    
    @ManyToOne
    private Items items;
    
    
    
	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public Invoices getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoices invoice) {
		this.invoice = invoice;
	}

	
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public String getPname() {
		return pname;
	}
	
	public void setPname(String pname) {
		this.pname = pname;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getTotal_price() {
		return total_price;
	}
	
	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}
	
	
    
	
	public static void display(Session session) {
		
		Query q = session.createQuery("from Invoice_Items");
		List<Invoice_Items> ii = q.list();
	    
	    for(Invoice_Items i : ii)
	    {
	    	System.out.println(i);
	    }
   
	}
    
	
	public String toString() {
    	return "Invoice_Item[pid = " + getItems().getPid() + " pname = " + pname + " price =  " + price + " qty = " + qty + "total Price = " + total_price + "invoice no = " + getInvoice().get_invoice_no() + "]";
    }

}
