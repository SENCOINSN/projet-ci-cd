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

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository,
										PaymentRepository paymentRepository){

		return args -> {
			studentRepository.save(Student.builder()
							.id(UUID.randomUUID().toString())
							.code("113030")
							.firstName("adama")
							.lastName("seye")
							.programId("SDIA")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.code("1130370")
					.firstName("adama")
					.lastName("seye")
					.programId("SDIA")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.code("113930")
					.firstName("maty")
					.lastName("seye")
					.programId("SDIA")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.code("113430")
					.firstName("Marie")
					.lastName("seye")
					.programId("GLSID")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.code("1130301")
					.firstName("Daba")
					.lastName("seye")
					.programId("GLSID")
					.build());

			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.code("11303022")
					.firstName("Mame Adam")
					.lastName("seye")
					.programId("BDCC")
					.build());
			PaymentType[] paymentTypes = PaymentType.values();
			Random random = new Random();
			studentRepository.findAll().forEach(student -> {
				for(int i=0;i<10;i++){
					int index = random.nextInt(paymentTypes.length);
					Payment payment = Payment.builder()
							.amount(1000+ (int)Math.random()+20000)
							.paymentType(paymentTypes[index])
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
