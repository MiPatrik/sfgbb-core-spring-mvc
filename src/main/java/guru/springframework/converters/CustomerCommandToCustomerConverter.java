package guru.springframework.converters;

import guru.springframework.commands.CustomerCommand;
import guru.springframework.domain.Address;
import guru.springframework.domain.Customer;
import guru.springframework.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerCommandToCustomerConverter implements Converter<CustomerCommand, Customer> {

	@Override
	public Customer convert(CustomerCommand customerCommand) {

		Customer customer = new Customer();
		customer.setUser(new User());
		customer.setBillingAddress(new Address());
		customer.setShippingAddress(new Address());
		customer.getUser().setId(customerCommand.getUserId());
		customer.getUser().setVersion(customerCommand.getUserVersion());
		customer.setId(customerCommand.getCustomerId());
		customer.setVersion(customerCommand.getCustomerVersion());
		customer.getUser().setUsername(customerCommand.getUserName());
		customer.getUser().setPassword(customerCommand.getPasswordText());
		customer.setFirstName(customerCommand.getFirstName());
		customer.setLastName(customerCommand.getLastName());
		customer.setEmail(customerCommand.getEmail());
		customer.setPhoneNumber(customerCommand.getPhoneNumber());

		return customer;
	}
}
