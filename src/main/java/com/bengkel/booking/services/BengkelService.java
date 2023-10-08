package com.bengkel.booking.services;

import java.util.ArrayList;
import java.util.List;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Motorcyle;
import com.bengkel.booking.models.Vehicle;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class BengkelService {
	
	//Silahkan tambahkan fitur-fitur utama aplikasi disini
	
	//Login
	public static boolean login(List<Customer> listAllCustomer){
		boolean isLogin = true;
		boolean isLooping = true;

		System.out.println("+------------------------LOGIN------------------------+");
		System.out.println();

		//Validasi customer ID
		String inputCustomerID = "";
		int countLoopingInputCustomerID = 0;
		int indexListCustomer = 0; //untuk getter list Customer
		do {
			isLooping = true;
			int indexToFind = -1;
			int index = 0;

			inputCustomerID = Validation.withoutValidation("Masukkan ID Customer : ");
			for (Customer listCustomer : listAllCustomer) {
				if (inputCustomerID.contains(listCustomer.getCustomerId())) {
					indexListCustomer = index;
					indexToFind++;
					break;
				}

				index++;
			}

			if (indexToFind != -1) {
				System.out.println("Selamat!!!, ID Customer yang anda cari telah terverifikasi.");
				System.out.println();
				isLooping = false;
			} else {
				System.out.println("Mohon maaf!!!, ID Customer tidak ditemukan!!!.");
				System.out.println();
				countLoopingInputCustomerID++;
			}
	
		} while (isLooping);

		//Validasi passwor dari customer ID
		String inputPassword = "";
		int countLoopingInputPassword = 0;
		do {
			isLooping = true;
			int indexToFind = -1;

			inputPassword = Validation.withoutValidation("Masukkan password : ");
			for (Customer listCustomer : listAllCustomer) {
				if (inputCustomerID.contains(listCustomer.getCustomerId()) && inputPassword.contains(listCustomer.getPassword())) {
					indexToFind++;
					break;
				}
			}

			if (indexToFind != -1) {
				System.out.println("Selamat!!!, Password telah terverifikasi.");
				System.out.println();
				isLooping = false;
			} else {
				System.out.println("Mohon maaf!!!, Password yang anda masukkan Salah!!!.");
				System.out.println();
				countLoopingInputPassword++;
			}
	
		} while (isLooping);

		//Validasi pengulangan kesalahan
		if (countLoopingInputCustomerID >= 3 || countLoopingInputPassword >= 3) {
			System.out.println("Mohon maaf!!!, inputan anda telah terjadi kesalahan pada ID Customer atau Password sebanyak 3 kali inputan.");
			System.out.println("Maka secara otomatis, Aplikasi ini akan berhenti!!!.");
			System.out.println();
			isLogin = false;
		} else if (countLoopingInputCustomerID < 3 || countLoopingInputPassword < 3){
			System.out.println("Selamat!!!, ID Customer dan Password yang anda input berhasil dilakukan.");
			System.out.println("Maka secara otomatis, Aplikasi ini akan berjalan.");
			System.out.println();
			List<Customer> listCustomer = new ArrayList<Customer>();
			listCustomer.add(listAllCustomer.get(indexListCustomer));
			MenuService.setListAllCustomer(listCustomer);
		}

		return isLogin;
	}

	//Info Customer
	public static void infoOfCustomer(List<Customer> listAllCustomer){
		System.out.println("+----------------------------------------Customer Profile----------------------------------------+");
		System.out.println();
		System.out.println("Customer ID     : " + listAllCustomer.get(0).getCustomerId());
		System.out.println("Nama            : " + listAllCustomer.get(0).getName());
		
		if (listAllCustomer.get(0) instanceof MemberCustomer) {
			System.out.println("Customer Status : Member");
			System.out.println("Alamat          : " + listAllCustomer.get(0).getAddress());
			System.out.println("Saldo Koin      : " + String.format("%,d", (int)((MemberCustomer)listAllCustomer.get(0)).getSaldoCoin()) + "\n");
		} else {
			System.out.println("Customer Status : Non Member");
			System.out.println("Alamat          : " + listAllCustomer.get(0).getAddress() + "\n");
		}

		System.out.println("List Kendaraan");
		PrintService.printVechicle(listAllCustomer.get(0).getVehicles());
		System.out.println();
	}
	
	//Booking atau Reservation
	public static List<BookingOrder> addBookingOrder(List<Customer> listCustomer, List<ItemService> listAllItemService, int id){
		listAllItemService = ItemServiceRepository.getAllItemService(); //Renew Ulang item service jika mau add Booking lagi

		List<BookingOrder> result = new ArrayList<BookingOrder>();
		boolean isLooping = true;

		String inputVehicleID = "";
		List<ItemService> listServiceByVehicle = new ArrayList<ItemService>();
		do {
			isLooping = true;
			int indexToFind = -1;

			PrintService.printVechicle(listCustomer.get(0).getVehicles());
			inputVehicleID = Validation.withoutValidation("Masukkan Vehicle ID : ");
			for (Vehicle vehicle : listCustomer.get(0).getVehicles()) {
				if (inputVehicleID.contains(vehicle.getVehiclesId())) {
					System.out.println("Kendaraan telah ditemukan!!!.");
					
					//Memfilter layanan service sesuai jenis kendaraan apakah mobil atau motor
					if (vehicle instanceof Car) {
						for (ItemService listItem : listAllItemService) {
							if (listItem.getVehicleType().equals("Car")) {
								listServiceByVehicle.add(listItem);
							}
						}

						indexToFind++;
						break;

					} else if(vehicle instanceof Motorcyle) {
						for (ItemService listItem : listAllItemService) {
							if (listItem.getVehicleType().equals("Motorcyle")) {
								listServiceByVehicle.add(listItem);
							}
						}

						indexToFind++;
						break;
					}
				}
			}

			if (indexToFind != -1) {
				System.out.println();
				isLooping = false;
			} else {
				System.out.println("Kendaraan tidak ditemukan!!!.");
				System.out.println("Silahkan input ulang!!!.\n");
			}

		} while (isLooping);

		System.out.println("List servis yang tersedia: ");
		PrintService.printService(listServiceByVehicle);
		System.out.println();

		//Melakukan pemilihan item service
		String inputServiceID = "";
		int countLooping = 0;
		double countPay = 0;

		List<ItemService> listChoiceServiceByVehicle = new ArrayList<ItemService>();
		do {
			int indexToFind = -1;
			isLooping = true;
			int index = 0;

			inputServiceID = Validation.withoutValidation("Silahkan masukkan Service ID : ");
			for (ItemService listService: listServiceByVehicle) {
				if (inputServiceID.contains(listService.getServiceId())) {
					System.out.println("ID Service berhasil dipilih.\n");
					countPay += listService.getPrice();
					listChoiceServiceByVehicle.add(listServiceByVehicle.get(index));
					
					if (listCustomer.get(0) instanceof MemberCustomer) {
						String input = Validation.validasiInput("Apakah anda ingin menambah Service lainya? (Y/T)\n", "Pilihan hanya 'Y' jika ingin menambah atau 'T' jika sudah.", "Y|y|T|t");

						if (input.matches("Y|y")) {
							countLooping++;
							indexToFind++;
							break;
						} else {
							indexToFind++;
							isLooping = false;
							break;
						}

					} else {
						isLooping = false;
						indexToFind++;
						break;
					}
				}

				index++;
			}

			if (indexToFind != -1 && countLooping >= 2) {
				System.out.println("Pilihan tambahan service hanya bisa dilakukan 2 item. Mohon maaf tidak bisa mengajukan tambahan lagi!!!.\n");
				isLooping = false;
			} else if(indexToFind != -1 && countLooping < 2){	
			}else if(indexToFind != -1){
			}else{
				System.out.println("Mohon maaf, ID service tidak ditemukan.\n");
			}

		} while (isLooping);

		//Set list item service
		MenuService.setListAllItemService(listChoiceServiceByVehicle);
		
		//Proses penambahan manual total payment booking pada countpay dan kebutuhan variable selanjutnya
		BookingOrder booking = new BookingOrder();
		booking.setTotalServicePrice(countPay);

		//Proses Customer memilih Metode Pembayaran dan total payment
		String methodPayment = "";
		if (listCustomer.get(0) instanceof MemberCustomer) {
			methodPayment = Validation.validasiInput("Silahkan pilih metode pembayaran (Saldo Coin / Cash) : \n ", "Mohon maaf, pilihan pembayaran hanya 2 (Saldo Coin / Cash)", "Saldo Coin|SALDO COIN|saldo coin|Cash|CASH|cash");

			if (methodPayment.equalsIgnoreCase("Saldo Coin")) {
				if (countPay <= ((MemberCustomer)listCustomer.get(0)).getSaldoCoin()) {
					booking.setPaymentMethod("Saldo Coin");			
				} else if (countPay > ((MemberCustomer)listCustomer.get(0)).getSaldoCoin()){
					System.out.println("Mohon maaf, Saldo koinmu kurang jadi diharuskan pakai Cash saja");
					booking.setPaymentMethod("Cash");
				}		
			} else {
				booking.setPaymentMethod("Cash");
			}

		} else {
			booking.setPaymentMethod("Cash");
		}

		//Menghitung total payment sesuai payment methodnya
		booking.calculatePayment();
		//Pengurangan Saldo Coin bagi Member Customer
		if (listCustomer.get(0) instanceof MemberCustomer && booking.getPaymentMethod().equalsIgnoreCase("Saldo Coin")) {
			((MemberCustomer)listCustomer.get(0)).setSaldoCoin(((MemberCustomer)listCustomer.get(0)).getSaldoCoin() - booking.getTotalPayment());
		} 
		
		//Buat Booking ID
		String bookingID = "Book-Cust" + String.format("-%03d", id) + listCustomer.get(0).getCustomerId().substring(listCustomer.get(0).getCustomerId().indexOf('-'));

		//Buat List Booking Order
		result.add(new BookingOrder(bookingID, listCustomer.get(0), listChoiceServiceByVehicle, booking.getPaymentMethod(), booking.getTotalServicePrice(), booking.getTotalPayment()));
		
		//Buat Pernyataan Booking Berhasil
		System.out.println();
		System.out.println("Booking Berhasil!!!.");
		System.out.println("Total harga service : " + String.format("%,d",(int)booking.getTotalServicePrice()));
		System.out.println("Total pembayaran    : " + String.format("%,d",(int)booking.getTotalPayment()));
		System.out.println();

		return result;
	}
	
	//Top Up Saldo Coin Untuk Member Customer
	public static void topUpSaldoCoin(List<Customer> listCustomer){
		System.out.println("+----------------------------------------Top Up Saldo Coin----------------------------------------+\n");
		double inputTopUp = Double.valueOf(Validation.validasiInput("Masukkan besaran Top Up : ", "Mohon maaf, input hanya berupa angka!!!.", "^[0-9]+$"));
		((MemberCustomer)listCustomer.get(0)).setSaldoCoin(((MemberCustomer)listCustomer.get(0)).getSaldoCoin() + inputTopUp);
	}
	//Logout
}
