package java_assignments;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventaryManagement {

	public static void main(String[] args) {
		InventaryManage  inventaryManage = new InventaryManage(); 
		
		while(true) {
			System.out.println("-- welcome to inventary store --");
			System.out.println("1 add item");
			System.out.println("2 remove item");
			System.out.println("3 update item");
			System.out.println("4 sell item");
			System.out.println("5 view items");
			System.out.println("6 sells report");
			System.out.println("7 reset today sells");
			System.out.println("8 save and exit");
			System.out.println("enter your choice");
			
			Scanner s = new Scanner(System.in);
			int choice = s.nextInt();
			s.nextLine();
			
			switch(choice) {
			case 1:
				System.out.println("enter item id: ");
				int id = s.nextInt();
				s.nextLine();
				System.out.println("enter item name: ");
				String itemName = s.nextLine();
				System.out.println("enter quantity: ");
				int quantity = s.nextInt();
				s.nextLine();
				System.out.println("enter item price: ");
				int price = s.nextInt();
				s.nextLine();
				int soldUnits = 0;

				inventaryManage.addItem(new Inventary(itemName , id, quantity, price, soldUnits));
				break;
				
			case 2:
				System.out.println("enter id to remove");
				id=s.nextInt();
				s.nextLine();
				inventaryManage.removeItem(id);
				break;
				
			case 3:
				System.out.println("enter id to update");
				id=s.nextInt();
				s.nextLine();
				inventaryManage.updateItem(id);
				break;
				
			case 4:
				System.out.println("enter id");
				id=s.nextInt();
				s.nextLine();
				System.out.println("enter required quantity");
				int reqQuantity=s.nextInt();
	
				s.nextLine();
				inventaryManage.sellItem(id, reqQuantity);
				break;
				
			case 5:
				inventaryManage.displayStock();
				break;
				
			case 6:
				inventaryManage.reportSells();
				break;
				
			case 7:
				inventaryManage.resetSells();
				break;
			
				
			case 8:
				inventaryManage.saveItem();
				return;
				
			default:
				System.out.println("invalid choice try again");
				
			
				
			
			
				
				
			}
			
		}
	}
}
class InventaryManage{
	private List<Inventary> items;
	private static final String File_Name = "Inventary_data.ser";
	
	Scanner sc = new Scanner(System.in);
	
	public InventaryManage() {
		items=loadItems();
	}
	
	public void addItem(Inventary item) {
		for(Inventary existingItem:items) {
			if(existingItem.getId()==item.getId()) {
				System.out.println("item is already present");
				return;
			}
		}
		items.add(item);
		System.out.println("item added succesfully");
	}
	public void removeItem(int id) {
		for(Inventary item : items) {
			if(item.getId()==id) {
				items.remove(item);
				System.out.println("item removed succesfully");
				return;
			}
		}
		System.out.println("item not found");
		
	}
	public void updateItem(int id) {
		for(Inventary item : items) {
			if(item.getId()==id) {
				
				System.out.println("add stock");
				int quantity = sc.nextInt();
				item.setQuantity(item.getQuantity()+quantity);
				System.out.println("item update succesfully");
				return;
			}
		}
		System.out.println("item not found");
	}
	public void sellItem(int id,int reqQuantity) {
		
		for(Inventary item : items) {
			if(item.getId()==id) {
				if(reqQuantity>item.getQuantity()) {
					System.out.println("stock not enough");
					return;
				
				}
				item.setQuantity(item.getQuantity()-reqQuantity);
				item.setSoldUnits(reqQuantity);
				System.out.println(reqQuantity+" "+item.getItemName()+"'s sold succesfully");
				return;
			}
		}
		System.out.println("item not found with id: "+id);
		
	}
	public void displayStock() {
		if(items.isEmpty()) {
			System.out.println("no items available here");
		}else {
			for(Inventary item : items) {
				System.out.println(item);
			}
			
		}
	}
	public void reportSells() {
		System.out.println("today sells report");
		
		boolean sellMade=false;
		for(Inventary item : items) {
			if(item.getSoldUnits()>0) {
				int revenue = item.getSoldUnits()*item.getPrice();
				System.out.println("[item: "+item.getItemName()+" , units sold: "+item.getSoldUnits()+" , total revenue: "+revenue+"]");
				sellMade=true;
			}
			
			
		}
		if(!sellMade) {
			System.out.println("no sell");
		}
		
	}
	public void resetSells() {
	   boolean isReset = false;
	   
	    for (Inventary item : items) {
	        if (item.getSoldUnits() > 0) {
	            item.setSoldUnits(0);
	            isReset =true;
	        }
	    }
	    if(!isReset) {
	    	 System.out.println("no sell");
	    }else {
	    	 System.out.println("All sold units have been reset to zero.");
	    }
	} 
	
	
	
	public void saveItem() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(File_Name))){
			oos.writeObject(items);
			System.out.println("items saved succesfully");
			System.out.println("good bye!");
		}catch(IOException e) {
			System.out.println("unable to save the items check the directory once "+e.getMessage());
		}
	}
	public List<Inventary> loadItems(){
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(File_Name))){
			return(List<Inventary>) ois.readObject();
			
		}catch(IOException | ClassNotFoundException e) {
			System.out.println("unable to load the file"+e.getMessage());
			return new ArrayList<>();
		}
	}
}
class Inventary implements Serializable{
	
	private String itemName;
	private int id;
	private int quantity;
	private int price;
	private int soldUnits;
	
	
	public Inventary(String itemName, int id, int quantity, int price, int soldUnits) {
		super();
		this.itemName = itemName;
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.soldUnits = soldUnits;
	}

	public int getSoldUnits() {
		return soldUnits;
	}

	public void setSoldUnits(int soldUnits) {
		this.soldUnits= soldUnits;
	}

	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Inventary [itemName=" + itemName + ", id=" + id + ", quantity=" + quantity + ", price=" + price
				 + ", soldUnits=" + soldUnits + "]";
	}
	
	
	
}
