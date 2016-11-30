package bg.elsys.ip.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CarService {
	private static CarService instance;

	public static CarService getInstance() {
		if (instance == null) {
			instance = new CarService();
		}
		return instance;
	}

	private List<Car> carList = new ArrayList<>();

	public CarService() {
		for (int i = 0; i < 50; i++) {
			carList.add(new Car("Nissan", 200, 2014));
			carList.add(new Car("Toyota", 220, 2008));
			carList.add(new Car("Volkswagen", 180, 2011));
			carList.add(new Car("Ferrari", 250, 2004));
			carList.add(new Car("Honda", 280, 2005));
			carList.add(new Car("Subaru", 80, 1998));
		}
	}

	public List<Car> getCars() {
		return Collections.unmodifiableList(carList);
	}

	public void addCar(Car car) {
		carList.add(car);
	}

	public PagedResponse getUsersInPagesFiltered(int page, int perPage, String withModel) {
		long previousEntries = page * perPage;
		List<Car> pageOfCars = carList.stream()
				.filter((u) -> u.getModel().equals(withModel) || withModel == null || "".equals(withModel))
				.skip(previousEntries).limit(perPage).collect(Collectors.toList());

		int totalPages = (int) Math.ceil(((double) carList.size()) / perPage);
		PagedResponse response = new PagedResponse(pageOfCars, page, totalPages);

		return response;
	}

	public List<String> getAllDistinctCarModels() {
		return carList.stream()
				.map((u) -> u.getModel())
				.distinct()
				.collect(Collectors.toList());
	}
}
