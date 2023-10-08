package com.bengkel.booking.services;

import java.util.List;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.Vehicle;

public class PrintService {
	
	public static void printMenu(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";
		
		System.out.printf("%-25s %n", title);
		System.out.println(line);
		
		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			}else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}
	
	public static void printVechicle(List<Vehicle> listVehicle) {
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s | %-5s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi", "Tahun", "Tipe Kendaraan");
	    System.out.format(line);
	    int number = 1;
	    String vehicleType = "";
	    for (Vehicle vehicle : listVehicle) {
	    	if (vehicle instanceof Car) {
				vehicleType = "Mobil";
			}else {
				vehicleType = "Motor";
			}
	    	System.out.format(formatTable, number, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getBrand(), vehicle.getTransmisionType(), vehicle.getYearRelease(), vehicleType);
	    	number++;
	    }
	    System.out.printf(line);
	}
	
	//Silahkan Tambahkan function print sesuai dengan kebutuhan.
	public static void printService(List<ItemService> listAllService) {
		String formatTable = "| %-2s | %-15s | %-15s | %-15s | %-15s |%n";
		String line = "+----+-----------------+-----------------+-----------------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Service Id", "Nama Service", "Tipe Kendaraan", "Harga");
	    System.out.format(line);

	    int number = 1;
	    for (ItemService item : listAllService) {
	    	System.out.format(formatTable, number, item.getServiceId(), item.getServiceName(), item.getVehicleType(), String.format("%,d", (int)item.getPrice()));
	    	number++;
	    }
	    System.out.printf(line);
	}

	public String printItemService(List<ItemService> listAllService){
		String result = "";

		for (ItemService itemService : listAllService) {
			result += (itemService.getServiceName() + ", ");
		}

		result = result.substring(0, result.length()-2);

		return result;
	}

	public void printBookingOrder(List<BookingOrder> listAllBookingOrder){
		String formatTable = "| %-2s | %-20s | %-15s | %-15s | %-15s | %-15s | %-25s |%n";
		String line = "+----+----------------------+-----------------+-----------------+-----------------+-----------------+---------------------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Booking ID", "Nama Customer", "Payment Method", "Total Service", "Total Payment", "List Service");
	    System.out.format(line);

	    int number = 1;
	    for (BookingOrder booking : listAllBookingOrder) {
	    	System.out.format(formatTable, number, booking.getBookingId(), booking.getCustomer().getName(), booking.getPaymentMethod(), String.format("%,d",(int)booking.getTotalServicePrice()), String.format("%,d",(int)booking.getTotalPayment()), printItemService(booking.getServices()));
	    	number++;
	    }
	    System.out.printf(line);
	}
	
}
