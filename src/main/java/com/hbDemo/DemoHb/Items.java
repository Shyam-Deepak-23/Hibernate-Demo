package com.hbDemo.DemoHb;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.query.Query;


@Entity
public class Items {
	@Id @GeneratedValue(generator = "increment")
    private int pid;
    private String pname;
    private double price;
    private int qty;
    
    @OneToMany(mappedBy = "items", cascade = {CascadeType.ALL})
    private List<Invoice_Items> invoice_items = new ArrayList<Invoice_Items>();
    
   

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {

        this.qty = qty;
    }
    
    
    public String toString() {
    	return "Item[pid = " + pid + " ,pname = " + pname + ", price =  " + price + ", qty = " + qty + "]";
    }
    
    public static void display(Session session)
	{

    	Query qu = session.createQuery("from Items");
	    List<Items> items = qu.list();
	    
	    for(Items i : items)
	    {
	    	System.out.println(i);
	    }
   
	}
    
    public static void add_qty(Session session, int pid, int new_qty, int old_qty)
    {
    	Query q = session.createQuery("Update Items set qty = :nq where pid = :pid");
    	q.setParameter("nq", new_qty + old_qty);
    	q.setParameter("pid", pid);
    	q.executeUpdate();
    }
    
    public static void update_price(Session session, int pid, double price)
    {
    	Query q = session.createQuery("Update Items set price = :price where pid = :pid");
    	q.setParameter("price", price);
    	q.setParameter("pid", pid);
    	q.executeUpdate();
    }
    
    public static void delete_item(Session session, int pid)
    {
    	Query q = session.createQuery("Delete from Items where pid = :pid");
    	q.setParameter("pid", pid);
    	q.executeUpdate();
    }
    
    
}