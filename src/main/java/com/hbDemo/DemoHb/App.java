package com.hbDemo.DemoHb;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.hibernate.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.*;

import java.util.List;
import java.util.Scanner;


public class App 
{
    public static void main(String[] args)
    {
   
    	Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String dt = formatter.format(date);
    	
        
        
    	
    	
    	
    	
    	Configuration con = new Configuration().configure().addAnnotatedClass(Invoice_Items.class).addAnnotatedClass(Items.class).addAnnotatedClass(Customers.class).addAnnotatedClass(Invoices.class);
      
        ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
        SessionFactory sf = con.buildSessionFactory(reg);
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();
        
//        Invoices.display(session);
        Scanner sc = new Scanner(System.in);
        int go_to_menu = '\0';
        do
        {
        	


            int customer_id = 0;

            System.out.println("1. Generate Invoice.\n 2. Search By Invoice. \n 3. Search By Customer. \n 4. Search For Item Quantity. \n  5. Search Customer By Items \n 6. Add/View/Edit Items \n 7. Add/View/Edit Customer \n ");
            System.out.print("Enter Your Choice : ");
            int choice = sc.nextInt();
        
            if(choice == 1)
            {
            	int invoice_no = Invoices.getInvoice_no(session);
                double price = 0;
                double amount = 0;
                double total_amount = 0;
                char ch = '\0';
                int pid = 0;
                String pname = "";

            	System.out.println("Enter Customer's name:");
            	String name = sc.next();
            	
                System.out.print("Enter Customer's Number:");
                String mobile = sc.next();
                
                
                
                
                Customers cust = null;
                
                Query q = session.createQuery("from Customers");
        	    List<Customers> customers = q.list();
        	    
        	                    
                for(Customers customer : customers)
                {
                	if(mobile.equals(customer.getC_mobile_number()))
                	{
                		int cid = customer.getCid();
                		cust = session.get(Customers.class, cid);
                		break;
                	}
                }
                
//                Customers cust = session.get(Customers.class, mobile);

                if(cust == null)
                {
                	Customers c = new Customers();
                	System.out.println("New Customer.Customer id generated.");
                	c.setC_mobile_number(mobile);
                	c.setCname(name);
                	customer_id = c.getCid();
//                	cust.setCid(c.getCid());
                	session.save(c);
                	cust = session.get(Customers.class, c.getCid());
                	
                }
                else
                {
                	customer_id = cust.getCid();
                	
                	System.out.println("Existing Customer.");
                }
                
                Invoices in = new Invoices();
                in.setDate(dt);
                in.setTotal_amount(total_amount);
                in.setCustomers(cust);
                session.save(in);

               
                do
                {
                    Items.display(session);
                	
                    
                    System.out.println("Search Product by:- \n 1. Product name \n 2. Product ID");
                    int search_product_choice = '\0';
                    
                    System.out.println("Enter your choice: ");
                    search_product_choice = sc.nextInt();
                    if(search_product_choice == 1)
                    {
                        System.out.println("Enter the name of the product : ");
                        String product_name = sc.next();
                        
                        Query q2 = session.createQuery("from Items");
                        List<Items> items1 = q2.list();
                        
                        boolean is_product_present = false;
                        System.out.println("The items related to your query are:- ");
                        for(Items it : items1)
                        {
                        	if(it.getPname().contains(product_name))
                        	{
                        		is_product_present = true;
                        		System.out.println(it);
                        	}
                        }




                        if(is_product_present)
                        {
                            System.out.println("Enter your product ID from the above items : ");
                            pid = sc.nextInt();
                            
                            Items it = session.get(Items.class, pid);
                            
                            
                            
                                    System.out.print("Enter Quantity:");
                                    int qty = sc.nextInt();




                                    if(it.getQty()>= qty)
                                    {
                                    	Invoice_Items invoice = new Invoice_Items();
                                        it.setQty(it.getQty() - qty);
                                        price = it.getPrice();
                                        
                                        pname = it.getPname();
                                        amount = price * qty;
                                        total_amount += amount;
                                        
                                        in.setTotal_amount(total_amount);
                                        
                                        invoice.setPname(pname);
                                        invoice.setPrice(price);
                                        invoice.setQty(qty);
                                        invoice.setTotal_price(price * qty);
                                        invoice.setInvoice(in);
                                        invoice.setItems(it);
                                        
                                        
                                        session.save(invoice);

                                        System.out.println("Item has been added to the Invoice.");
                                    }
                                    else{
                                        
                                        price = 0;
                                        System.out.println("Out of Stock.");
                                    }
                                    
                          
                                

                        }     
                        
                        else
                        {
                            System.out.println("No Such Products Available.");
                        }


                    }

                    else if(search_product_choice == 2)
                    {
                        System.out.print("Enter Product ID:");
                        pid = sc.nextInt();
                        
                        Items it = null;
                        
                        boolean is_item_present = false;

                        Query q3 = session.createQuery("from Items");
                        List<Items> items1 = q3.list();
                        
                        for(Items item: items1)
                        {
                        	if(item.getPid() == pid)
                        	{
                        		is_item_present = true;
                        		break;
                        	}
                        }
                        
                        if(is_item_present)
                        {
                        	it = session.get(Items.class, pid);
                        	System.out.print("Enter Quantity:");
                            int qty = sc.nextInt();
                            if(it.getQty()>= qty)
                            {
                            	Invoice_Items invoice = new Invoice_Items();
                                it.setQty(it.getQty() - qty);
                                price = it.getPrice();
                                
                                pname = it.getPname();
                                amount = price * qty;

                                total_amount += amount;
                                in.setTotal_amount(total_amount);
                                
                                invoice.setPname(pname);
                                invoice.setPrice(price);
                                invoice.setQty(qty);
                                invoice.setTotal_price(price * qty);
                                invoice.setInvoice(in);
                                invoice.setItems(it);
                                
                                invoice.setInvoice(in);
                                
                                session.save(invoice);
                                System.out.println("Item has been added to the Invoice.");
                            }
                            else{
                                
                                price = 0;
                                System.out.println("Out of Stock.");
                            }
                            
                           

                        }
                        
                        else
                        {
                        	System.out.println("No such item present.");
                        }
                       


                        
                    }

                    System.out.println("Do you want to add more items (y/n): ");
                    ch = sc.next().charAt(0);
                }
                while(ch == 'y' || ch == 'Y');


                if(total_amount == 0)
                {
                    System.out.println("No items were added. No Invoices Generated. ");
                }
                else
                {
                	
                    

                	

                    System.out.println("\t\t\t\t\t\t\t\t\t\tTotal Amount:" + total_amount);

                    
                }
                
            }

            
            
            
            else if(choice == 2)
            {
                System.out.println("Enter Invoice Number:");
                int invoice_no = sc.nextInt();
                Invoices.display(session, invoice_no);
                
            }
        
//            else if(choice == 3)
//            {
//                System.out.println("Available Customers:-");
//                Items.display(session);
//                System.out.println("Enter Customer id: ");
//                customer_id = sc.nextInt();
//                Customer.display_customer_details(con, stmt, customer_id);
//            }

//            else if(choice == 4)
//            {
//                Items.display(con);
//                System.out.println("Enter item id: ");
//                int pid = sc.nextInt();
//                Items.item_qty(con, stmt, pid);
//            }

//            else if(choice == 5)
//            {
//                System.out.println("Find customers by the items they bought:-");
//                System.out.println("-----------------------------------------");
//                Items.display(con);
//                System.out.println("Enter the item id : ");
//                int pid = sc.nextInt();
//                Customer.display_customer_by_item(con, stmt, pid);
//
//
//            }

            else if(choice == 6)
            {
                char add_view_edit = '\0';
                do{
                	

                    System.out.println(" 1. Add a new item. \n 2. Edit an item \n 3. View all items");
                    System.out.println("Enter your choice: ");
                    int item_choice = sc.nextInt();
                    if(item_choice == 1)
                    {
                    	Items i = new Items();
                        System.out.println("Enter Product Name: ");
                        String pname = sc.next();
                        i.setPname(pname);
                        
                        System.out.println("Enter Product Price per unit: ");
                        double price = sc.nextDouble();
                        i.setPrice(price);
                        
                        System.out.println("Enter Product Quantity: ");
                        int qty = sc.nextInt();
                        i.setQty(qty);
                        
                        session.save(i);

                        
                    }

                    else if(item_choice == 2)
                    {
                        Items.display(session);
                        System.out.println("Enter the product ID you want to edit : ");
                        int pid = sc.nextInt();
                        Items it = session.get(Items.class, pid);
                        

                        System.out.println(" 1. Add Quantity \n 2. Update Price \n 3. Delete the item");
                        int edit_choice = sc.nextInt();

                        if(edit_choice == 1)
                        {
                            System.out.println("No. of Quantity to be added : ");
                            int new_qty = sc.nextInt();
                            int old_qty = it.getQty();
                            it.setQty(new_qty + old_qty); 
//                            Items.add_qty(session, pid, new_qty,old_qty);

                        }
//
                        else if(edit_choice == 2)
                        {
                            System.out.println("Enter new Price : ");
                            double new_price = sc.nextDouble();
                            it.setPrice(new_price);

//                            Items.update_price(session, pid, new_price);

                        }
//
                        else if(edit_choice == 3)
                        {


                            Items.delete_item(session, pid);
                        }
                    }

                    else if(item_choice == 3)
                    {
                        Items.display(session);
                    }
                    System.out.println("Do you want to continue editing (y/n) : ");
                    add_view_edit = sc.next().charAt(0);
                }
                while(add_view_edit == 'y');
                   
            }
            
            else if(choice == 7)
            {
            	
                char add_view_edit = '\0';
                do{
                	
                	

                    System.out.println(" 1. Add a new customer. \n 2. Edit a customer \n 3. View all customers");
                    System.out.println("Enter your choice: ");
                    int customer_choice = sc.nextInt();
                    if(customer_choice == 1)
                    {
                    	Customers c = new Customers();
                        System.out.println("Enter customer Name: ");
                        String cname = sc.next();
                        c.setCname(cname);
                        
                        System.out.println("Enter customer mobile no.: ");
                        String mobile = sc.next();
                        c.setC_mobile_number(mobile);
                                              
                        session.save(c);

                        
                    }

                    else if(customer_choice == 2)
                    {
                        Customers.display(session);
                        System.out.println("Enter the customer ID you want to edit : ");
                        int cid = sc.nextInt();
                        Customers cust = session.get(Customers.class, cid);
                        

                        System.out.println(" 1. Change name \n 2. Change mobile number \n 3. Delete the Customer");
                        int edit_choice = sc.nextInt();

                        if(edit_choice == 1)
                        {
                            System.out.println("Enter the new name:");
                            String new_name = sc.next();
                            
                            cust.setCname(new_name); 
//                          

                        }
//
                        else if(edit_choice == 2)
                        {
                            System.out.println("Enter the new  mobile number:");
                            String new_mobile = sc.next();
                            cust.setC_mobile_number(new_mobile);

                        }
//
                        else if(edit_choice == 3)
                        {


                            Customers.delete(session, cid);
                        }
                    }

                    else if(customer_choice == 3)
                    {
                        Customers.display(session);
                    }
                    System.out.println("Do you want to continue editing (y/n) : ");
                    add_view_edit = sc.next().charAt(0);
                }
                while(add_view_edit == 'y');
                   
            }
            
            System.out.print("do you want to continue:");
            go_to_menu = sc.next().charAt(0);
            }
        	while(go_to_menu == 'y');
        
        
        
        
        
        
        
        
        tx.commit();
        
        System.out.println("Thank You...");

    }
    
}
