package com.bengkel.booking.services;

import java.util.ArrayList;
import java.util.List;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.repositories.CustomerRepository;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class MenuService {
	private static List<Customer> listAllCustomer;
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	private static List<BookingOrder> listAllBookingOrder = new ArrayList<BookingOrder>();
	private static int bookingID = 1;

	//Getter dan Setter
	public static List<Customer> getListAllCustomer() {
		return listAllCustomer;
	}
	public static void setListAllCustomer(List<Customer> listCustomer) {
		listAllCustomer = listCustomer;
	}
	public static List<ItemService> getListAllItemService() {
		return listAllItemService;
	}
	public static void setListAllItemService(List<ItemService> listItemService) {
		listAllItemService = listItemService;
	}


	public static void run() {
		boolean isLooping = true;
		do {
			listAllCustomer =  CustomerRepository.getAllCustomer();
			boolean isLogin = BengkelService.login(listAllCustomer);

			if (isLogin == true) {
				mainMenu();
			} else {
				isLooping = false;
			}
			
		} while (isLooping);
	}

	public static void mainMenu() {
		String[] listMenu = {"Informasi Customer", "Booking Bengkel", "Top Up Bengkel Coin", "Informasi Booking", "Logout"};
		int menuChoice = 0;
		boolean isLooping = true;
		
		do {
			PrintService.printMenu(listMenu, "Booking Bengkel Menu");
			menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", listMenu.length-1, 0);
			System.out.println(menuChoice);
			
			switch (menuChoice) {
			case 1:
				//panggil fitur Informasi Customer
				BengkelService.infoOfCustomer(listAllCustomer);
				break;
			case 2:
				//panggil fitur Booking Bengkel
				listAllBookingOrder.addAll(BengkelService.addBookingOrder(listAllCustomer, listAllItemService, bookingID));
				bookingID++;
				break;
			case 3:
				//panggil fitur Top Up Saldo Coin
				if (listAllCustomer.get(0) instanceof MemberCustomer) {
					BengkelService.topUpSaldoCoin(listAllCustomer);
				} else {
					System.out.println("Mohon maaf, Fitur ini hanya tersedia bagi Member Saja");
				}
				
				break;
			case 4:
				//panggil fitur Informasi Booking Order
				PrintService print = new PrintService();
				print.printBookingOrder(listAllBookingOrder);
				break;
			default:
				System.out.println("Logout\n");
				isLooping = false;
				break;
			}
		} while (isLooping);
		
		
	}
	
	//Silahkan tambahkan kodingan untuk keperluan Menu Aplikasi
}
