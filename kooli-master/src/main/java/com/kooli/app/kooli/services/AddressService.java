package com.kooli.app.kooli.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid; 

import com.kooli.app.kooli.models.Address;
import com.kooli.app.kooli.models.RecordStatus;
import com.kooli.app.kooli.payloads.requests.AddressRequest;
import com.kooli.app.kooli.payloads.response.ApiResponse;
import com.kooli.app.kooli.repositories.AddressRepository;
import com.kooli.app.kooli.repositories.UserRepository;
import com.kooli.app.kooli.securities.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public ResponseEntity<?> save(UserPrincipal currentuser, AddressRequest addressRequest) {
		try {
			Address address = new Address();

			Optional<Address> oldAddress = addressRepository.findByUserIdAndType(currentuser.getId(),
					addressRequest.getType());

			if (oldAddress.isPresent()) {
				address = oldAddress.get();
			} else {
				address.setId((long) 0);
			}

			address.setLine_1(addressRequest.getLine_1());
			address.setLine_2(addressRequest.getLine_2());
			address.setPincode(addressRequest.getPincode());
			address.setCity(addressRequest.getCity());
			address.setState(addressRequest.getState());
			address.setType(addressRequest.getType());
			address.setBuildingName(addressRequest.getBuildingName());
			address.setUser(userRepository.findById(currentuser.getId()).get());
			address.setStatus(RecordStatus.ACTIVE);

			address = this.addressRepository.save(address);

			return new ResponseEntity<>(new ApiResponse(true, "Saved successfully", address), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}

	}

	@Transactional
	public ResponseEntity<?> createByUserId(AddressRequest addressRequest, long id) {
		try {
			Address address = new Address();

			Optional<Address> oldAddress = addressRepository.findByUserIdAndType(id,
					addressRequest.getType());

			if (oldAddress.isPresent()) {
				address = oldAddress.get();
			} else {
				address.setId((long) 0);
			}

			address.setLine_1(addressRequest.getLine_1());
			address.setLine_2(addressRequest.getLine_2());
			address.setPincode(addressRequest.getPincode());
			address.setCity(addressRequest.getCity());
			address.setState(addressRequest.getState());
			address.setType(addressRequest.getType());
			address.setBuildingName(addressRequest.getBuildingName());
			address.setUser(userRepository.findById(id).get());
			address.setStatus(RecordStatus.ACTIVE);

			address = this.addressRepository.save(address);

			return new ResponseEntity<>(new ApiResponse(true, "Saved successfully", address), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}

	}

	@Transactional
	public ResponseEntity<?> list() {
		try {
			List<Address> list = this.addressRepository.findAll();
			return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> listById(long id) {
		try {
			Optional<Address> address = this.addressRepository.findById(id);

			if (!address.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			return new ResponseEntity<>(new ApiResponse(true, "", address), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> listByUserId(long id) {
		return new ResponseEntity<>(new ApiResponse(true, "", this.addressRepository.findByUserId(id)), HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<?> listByUserIdAndType(long id, String type) {
		return new ResponseEntity<>(
				new ApiResponse(true, "", this.addressRepository.findByUserIdAndType(id, type).get()), HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<?> listByStatus(String status) {
		try {
			RecordStatus recordStatus = RecordStatus.ACTIVE;

			switch (status) {
				case "INACTIVE":
					recordStatus = RecordStatus.INACTIVE;
					break;
				case "DELETED":
					recordStatus = RecordStatus.DELETED;
					break;
				default:
					recordStatus = RecordStatus.ACTIVE;
					break;
			}

			List<Address> list = this.addressRepository.findByStatus(recordStatus);
			return new ResponseEntity<>(new ApiResponse(true, "", list), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> update(long id, @Valid AddressRequest addressRequest) {
		try {
			Optional<Address> address = this.addressRepository.findById(id);

			if (!address.isPresent())
				return new ResponseEntity<>(new ApiResponse(false, "Item not found"), HttpStatus.BAD_REQUEST);

			address.get().setLine_1(addressRequest.getLine_1());
			address.get().setLine_2(addressRequest.getLine_2());
			address.get().setPincode(addressRequest.getPincode());
			address.get().setCity(addressRequest.getCity());
			address.get().setState(addressRequest.getState());
			address.get().setStatus(RecordStatus.ACTIVE);

			return new ResponseEntity<>(
					new ApiResponse(true, "Updated successfully", this.addressRepository.save(address.get())),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}

	}

	@Transactional
	public ResponseEntity<?> delete(long id) {
		try {
			this.addressRepository.deleteById(id);
			return new ResponseEntity<>(new ApiResponse(true, "Deleted successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
		}

	}
}
