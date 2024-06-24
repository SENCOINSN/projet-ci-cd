package com.sid.gl;

import com.sid.gl.entities.Payment;
import com.sid.gl.entities.PaymentStatus;
import com.sid.gl.entities.PaymentType;
import com.sid.gl.entities.Student;
import com.sid.gl.repository.PaymentRepository;
import com.sid.gl.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Profile("!test")
	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository,
										PaymentRepository paymentRepository){
		System.out.println("elemments added");
		return args -> {
			    studentRepository.save(Student.builder()
					.programId("programId")
					.lastName("lastname")
					.firstName("firstname")
					.code("code")
					.id("id")
					.photo("photo")
					.build());

			 studentRepository.save(Student.builder()
					.programId("programId")
					.lastName("lastname2")
					.firstName("firstname2")
					.code("code1")
					.id("id1")
					.photo("photo")
					.build());

			studentRepository.save(Student.builder()
					.programId("programId")
					.lastName("lastname1")
					.firstName("firstname1")
					.code("code2")
					.id("id2")
					.photo("photo")
					.build());
			PaymentType[] paymentTypes = PaymentType.values();
			Random random = new Random();
			studentRepository.findAll().forEach(student -> {
				for(int i=0;i<10;i++){
					int index = random.nextInt(paymentTypes.length);
					Payment payment = Payment.builder()
							.amount(1200)
							.paymentType(PaymentType.CASH)
							.paymentStatus(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(student)
							.build();
					paymentRepository.save(payment);
				}
			});
		};
	}
}
