package com.sid.gl.web;

import com.sid.gl.entities.Payment;
import com.sid.gl.entities.PaymentStatus;
import com.sid.gl.entities.PaymentType;
import com.sid.gl.entities.Student;
import com.sid.gl.repository.PaymentRepository;
import com.sid.gl.repository.StudentRepository;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin("*")
public class PaymentRestController {
    private StudentRepository studentRepository;

    private PaymentRepository paymentRepository;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping(path = "/payments")
    public List<Payment> allPayments(){
        return paymentRepository.findAll();
    }

    @GetMapping(path = "/students/{code}/payments")
    public List<Payment> allPaymentsByCode(@PathVariable("code") String code){
        return paymentRepository.findByStudentCode(code);
    }

    @GetMapping(path = "/paymentsByStatus")
    public List<Payment> allPaymentsByStatus(@RequestParam PaymentStatus paymentStatus){
        return paymentRepository.findByPaymentStatus(paymentStatus);
    }


    @GetMapping(path = "/paymentsByType")
    public List<Payment> allPaymentsByType(@RequestParam PaymentType type){
        return paymentRepository.findByPaymentType(type);
    }

    @GetMapping(path="/payments/{id}")
    public Payment getPaymentById(@PathVariable("id") Long id){
        return paymentRepository.findById(id).orElse(null);
    }

    @GetMapping(path = "/students")
    public List<Student> allStudents(){
        return studentRepository.findAll();
    }

    @GetMapping(path = "/students/{code}")
    public Student getStudentByCode(@PathVariable("code") String code){
        return studentRepository.findByCode(code);
    }

    @GetMapping(path = "/studentsByProgram")
    public List<Student> getStudentByProgramId(@RequestParam(value = "programId") String programId){
        return studentRepository.findByProgramId(programId);
    }

    @GetMapping(path = "students-by-program")
    public Map<String,List<Student>> studentByProgramId(){
      return studentRepository.findAll()
              .stream()
              .flatMap(Stream::ofNullable) //possible change it of flatmap (Stream::ofNullable)
              .collect(Collectors.groupingBy(Student::getProgramId));

    }


    @PutMapping(path = "payments/{id}")
    public Payment updatePaymentStatus(@RequestParam(value = "paymentStatus") PaymentStatus paymentStatus,@PathVariable("id") Long id){
        Payment payment = paymentRepository.findById(id).orElse(null);
        assert payment != null;
        payment.setPaymentStatus(paymentStatus);
       return paymentRepository.save(payment);
    }

    @PostMapping(path = "/payments",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile file,
                              double amount,PaymentType type,
                               String studentCode) throws Exception {

        Path folderPath = Paths.get("./payments").toAbsolutePath().normalize();
        try {
            Files.createDirectories(folderPath);
        } catch (Exception ex) {
            throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath =folderPath.resolve(fileName);
        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
        Student student = studentRepository.findByCode(studentCode);

        Payment payment = Payment.builder()
                .date(LocalDate.now())
                .paymentType(type)
                .student(student)
                .amount(amount)
                .paymentStatus(PaymentStatus.CREATED)
                .file(filePath.toUri().toString())
                .build();
       return paymentRepository.save(payment);

    }

    @GetMapping(path = "/paymentFile/{paymentId}",produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        assert payment!=null;
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }

}
