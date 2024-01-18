package implementations;

import java.util.List;

import entities.EnumTripType;
import entities.Trip;
import exception.LogicException;

public class TripManagerImplementation  implements interfaces.TripManager{

	@Override
	public Trip findTripById(Integer id) throws LogicException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findTripById'");
	}

	@Override
	public void createTrip(Trip trip) throws LogicException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createTrip'");
	}

	@Override
	public void updateTrip(Trip trip) throws LogicException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateTrip'");
	}

	@Override
	public void deleteTrip(Trip trip) throws LogicException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteTrip'");
	}

	@Override
	public List<Trip> findAllTrips() throws LogicException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAllTrips'");
	}

	@Override
	public List<Trip> findTripsByTripType(EnumTripType tripType) throws LogicException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findTripsByTripType'");
	}
	
}
