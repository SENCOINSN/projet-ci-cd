package com.sid.gl.services;

import com.sid.gl.dto.PaymentRequest;
import com.sid.gl.dto.PaymentResponse;
import com.sid.gl.entities.Payment;
import com.sid.gl.entities.PaymentStatus;
import com.sid.gl.entities.PaymentType;
import com.sid.gl.entities.Student;
import com.sid.gl.exceptions.PaymentNotFoundException;
import com.sid.gl.mapper.PaymentMapper;
import com.sid.gl.repository.PaymentRepository;
import com.sid.gl.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;

    public List<PaymentResponse> findAll(){
        return PaymentMapper.toListPaymentResponse(paymentRepository.findAll());
    }

    public List<PaymentResponse> allPaymentsByCode(String code){
        return PaymentMapper.toListPaymentResponse(paymentRepository.findByStudentCode(code));
    }

    public List<PaymentResponse> allPaymentByStatus(PaymentStatus status){
        return PaymentMapper.toListPaymentResponse(paymentRepository.findByPaymentStatus(status));
    }

    public List<PaymentResponse> allPaymentsByType(PaymentType type){
        return PaymentMapper.toListPaymentResponse(paymentRepository.findByPaymentType(type));
    }

    public PaymentResponse getPaymentById(Long id) throws PaymentNotFoundException {
        return paymentRepository.findById(id)
                .map(PaymentMapper::toPaymentResponse)
                .orElseThrow(PaymentNotFoundException::new);
       /* Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isEmpty()){
            log.error("Payment with Id not found ");
            throw new PaymentNotFoundException();
        }
        return PaymentMapper.toPaymentResponse(payment.get());*/
    }

    @Transactional
    public PaymentResponse updatePaymentStatus(PaymentStatus paymentStatus, Long id){
        Payment payment = paymentRepository.findById(id).orElse(null);
       if(payment!=null){
           payment.setPaymentStatus(paymentStatus);
           return PaymentMapper.toPaymentResponse(paymentRepository.save(payment));
       }
       throw new RuntimeException("Payment not found for update");
    }


    public PaymentResponse savePayment(MultipartFile file,
                               PaymentRequest paymentRequest) throws Exception {
        Path folderPath = Paths.get("./payments").toAbsolutePath().normalize();
        try {
            Files.createDirectories(folderPath);
        } catch (Exception ex) {
            throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath =folderPath.resolve(fileName);
        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
        Student student = studentRepository.findByCode(paymentRequest.studentCode());

        Payment payment = Payment.builder()
                .date(LocalDate.now())
                .paymentType(paymentRequest.type())
                .student(student)
                .amount(paymentRequest.amount())
                .paymentStatus(PaymentStatus.CREATED)
                .file(filePath.toUri().toString())
                .build();
        return PaymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        assert payment!=null;
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
