package com.paymentchain.customer.controller;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.respository.CustomerRepository;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

	@Autowired
	CustomerRepository customerRepository;

	private final WebClient.Builder webClientBuilder;

	public CustomerRestController(WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}

	@GetMapping()
	public List<Customer> list() {
		return customerRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			return new ResponseEntity<>(customer.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable long id, @RequestBody Customer input) {
		Customer save = customerRepository.save(input);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody Customer input) {
		input.getProducts().forEach(x -> x.setCustomer(input));
		Customer save = customerRepository.save(input);
		return ResponseEntity.ok(save);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		customerRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	HttpClient client = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
			.option(ChannelOption.SO_KEEPALIVE, true).option(EpollChannelOption.TCP_KEEPIDLE, 300)
			.option(EpollChannelOption.TCP_KEEPINTVL, 60).responseTimeout(Duration.ofSeconds(1))
			.doOnConnected(connection -> {
				connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
				connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
			});
}
